package ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.filesystem.filehandlers.write;

import android.media.AudioFormat;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.audio.ReactiveAudioRecorder;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.filesystem.FileManager;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.filesystem.AudioFile;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.filesystem.filehandlers.read.WaveFileReader;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util.Logger;

public class AndroidWavFileHandler extends AndroidSimpleFileHandler implements AudioFile {
    private ReactiveAudioRecorder.Settings mSettings;


    public AndroidWavFileHandler(String filePath, DataOutputStream outputStream) throws FileNotFoundException {
        super(filePath, outputStream);
    }

    public static AudioFile createFromExistingHandler(FileManager.FileHandler fromFilePath, ReactiveAudioRecorder.Settings audioRecorderSettings) throws FileNotFoundException {
        AndroidWavFileHandler androidWavFileHandler = new AndroidWavFileHandler(fromFilePath.getFilePath(), fromFilePath.getOutputStream());
        androidWavFileHandler.setAudioSettings(audioRecorderSettings);
        return androidWavFileHandler;
    }

    public static AndroidWavFileHandler createFromAudioSettings(String filePath, ReactiveAudioRecorder.Settings settings) throws FileNotFoundException {
        AndroidWavFileHandler androidWavFileHandler = new AndroidWavFileHandler(filePath, new DataOutputStream(new FileOutputStream(filePath)));
        androidWavFileHandler.mSettings = settings;
        return androidWavFileHandler;
    }

    //region Factory methods

    @Override
    public void onStartWritingToFile() {

    }

    @Override
    public void close() throws IOException {
        flush();
        super.close();
        writeWavHeader(new File(mFilePath), mSettings.getChannelCount(), mSettings.getSampleRate(), mSettings.getAudiopEncodingFormat());
    }


    private static void writeWavHeader(File file, int channelMask, int sampleRate, int encoding) throws IOException {
        short channels;
        switch (channelMask) {
            case AudioFormat.CHANNEL_IN_MONO:
                channels = 1;
                break;
            case AudioFormat.CHANNEL_IN_STEREO:
                channels = 2;
                break;
            default:
                throw new IllegalArgumentException("Unacceptable channel mask");
        }

        short bitDepth;
        switch (encoding) {
            case AudioFormat.ENCODING_PCM_8BIT:
                bitDepth = 8;
                break;
            case AudioFormat.ENCODING_PCM_16BIT:
                bitDepth = 16;
                break;
            case AudioFormat.ENCODING_PCM_FLOAT:
                bitDepth = 32;
                break;
            default:
                throw new IllegalArgumentException("Unacceptable encoding");
        }

        writeWavHeaderNew(file, channels, sampleRate, bitDepth);
    }

    private static void writeWavHeaderNew(File rawFile, short channels, int sampleRate, short bitDepth) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(rawFile, "rw");
        //seek to beginning
        randomAccessFile.seek(0);
        try {
            writeStringToRAFile(randomAccessFile, "RIFF"); // chunk id
            writeIntToRAFile(randomAccessFile, (int) (36 + rawFile.length())); // chunk size
            writeStringToRAFile(randomAccessFile, "WAVE"); // format
            writeStringToRAFile(randomAccessFile, "fmt "); // subchunk 1 id
            writeIntToRAFile(randomAccessFile, 16); // subchunk 1 size
            writeShortToRAFile(randomAccessFile, (short) 1); // audio format (1 = PCM)
            writeShortToRAFile(randomAccessFile, channels); // number of channels
            writeIntToRAFile(randomAccessFile, sampleRate); // sample rate
            writeIntToRAFile(randomAccessFile, sampleRate * 2); // byte rate
            writeShortToRAFile(randomAccessFile, (short) 2); // block align
            writeShortToRAFile(randomAccessFile, bitDepth); // bits per sample
            writeStringToRAFile(randomAccessFile, "data"); // subchunk 2 id
            writeIntToRAFile(randomAccessFile, (int) rawFile.length()); // subchunk 2 size
        } finally {
            randomAccessFile.close();
        }
    }

    private static void readWavHeaderNew(File rawFile) throws IOException {
     /*   RandomAccessFile randomAccessFile = new RandomAccessFile(rawFile, "r");
        //seek to beginning
        randomAccessFile.seek(0);
        try {
            writeStringToRAFile(randomAccessFile, "RIFF"); // chunk id
            writeIntToRAFile(randomAccessFile, (int) (36 + rawFile.length())); // chunk size
            writeStringToRAFile(randomAccessFile, "WAVE"); // format
            writeStringToRAFile(randomAccessFile, "fmt "); // subchunk 1 id
            writeIntToRAFile(randomAccessFile, 16); // subchunk 1 size
            writeShortToRAFile(randomAccessFile, (short) 1); // audio format (1 = PCM)
            writeShortToRAFile(randomAccessFile, channels); // number of channels
            writeIntToRAFile(randomAccessFile, sampleRate); // sample rate
            writeIntToRAFile(randomAccessFile, sampleRate * 2); // byte rate
            writeShortToRAFile(randomAccessFile, (short) 2); // block align
            writeShortToRAFile(randomAccessFile, bitDepth); // bits per sample
            writeStringToRAFile(randomAccessFile, "data"); // subchunk 2 id
            writeIntToRAFile(randomAccessFile, (int) rawFile.length()); // subchunk 2 size
        } finally {
            randomAccessFile.close();
        }*/
    }

    @Override
    public void setAudioSettings(ReactiveAudioRecorder.Settings settings) {
        mSettings = settings;
    }

    @Override
    public ReactiveAudioRecorder.Settings getAudioSettings() {
        return mSettings;
    }


}