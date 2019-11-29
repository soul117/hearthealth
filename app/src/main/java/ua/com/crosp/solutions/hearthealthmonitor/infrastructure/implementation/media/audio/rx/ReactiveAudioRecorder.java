package ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.media.audio.rx;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

import org.reactivestreams.Subscriber;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

import io.reactivex.Flowable;


/**
 * Created by Alexander Molochko on 5/27/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class ReactiveAudioRecorder extends Flowable<short[]> implements Runnable {
    private String mFilePath;
    private final int mSampleRate;
    private final int mChannels;
    private final int mBitsPerSecond;
    private final int mAudioSource;
    private AudioRecord audioRecorder = null;
    private int bufferSize;
    private boolean isRecording = false;
    private final Object mPauseLock;
    private boolean mPaused;
    private Subscriber<? super short[]> mSubscriber;
    private Thread thread;
    private DataOutputStream mDataOutputStream;

    @Override
    protected void subscribeActual(Subscriber<? super short[]> subscriber) {
        mSubscriber = subscriber;
    }

    public static class Builder {
        int mSampleRate;
        int mBitsPerSecond;
        int mChannels;
        int mAudioSource;
        String mFilePath;

        public Builder(String filePath) {
            mFilePath = filePath;
            mSampleRate = 44100;
            mBitsPerSecond = AudioFormat.ENCODING_PCM_16BIT;
            mAudioSource = MediaRecorder.AudioSource.MIC;
            mChannels = AudioFormat.CHANNEL_IN_MONO;
        }

        public ReactiveAudioRecorder.Builder sampleRate(int sampleRate) {
            mSampleRate = sampleRate;
            return this;
        }

        public ReactiveAudioRecorder.Builder stereo() {
            mChannels = 2;
            return this;
        }

        public ReactiveAudioRecorder.Builder mono() {
            mChannels = 1;
            return this;
        }

        public ReactiveAudioRecorder.Builder audioSourceMic() {
            mAudioSource = MediaRecorder.AudioSource.MIC;
            return this;
        }

        public ReactiveAudioRecorder.Builder audioSourceCamcorder() {
            mAudioSource = MediaRecorder.AudioSource.CAMCORDER;
            return this;
        }

        public ReactiveAudioRecorder build() {
            return new ReactiveAudioRecorder(this.mFilePath, this.mSampleRate, this.mChannels,
                    this.mBitsPerSecond, this.mAudioSource);
        }
    }

    private ReactiveAudioRecorder(String filePath, int sampleRate, int channels, int bitsPerSecond,
                                  int audioSource) {
        mFilePath = filePath;
        mSampleRate = sampleRate;
        mChannels = channels == 1 ? AudioFormat.CHANNEL_IN_MONO : AudioFormat.CHANNEL_IN_STEREO;
        mBitsPerSecond = bitsPerSecond;
        mAudioSource = audioSource;

        mPauseLock = new Object();
        mPaused = false;
    }

    public void start() throws RuntimeException, FileNotFoundException {
        this.mDataOutputStream = new DataOutputStream(
                new BufferedOutputStream(new FileOutputStream(this.mFilePath)));

        this.bufferSize = AudioRecord.getMinBufferSize(mSampleRate, mChannels,
                AudioFormat.ENCODING_PCM_16BIT);

        if (bufferSize != AudioRecord.ERROR_BAD_VALUE && bufferSize != AudioRecord.ERROR) {
            audioRecorder = new AudioRecord(mAudioSource, mSampleRate, mChannels,
                    AudioFormat.ENCODING_PCM_16BIT, bufferSize * 10);

            if (audioRecorder.getState() == AudioRecord.STATE_INITIALIZED) {
                audioRecorder.startRecording();
                isRecording = true;
                thread = new Thread(this);
                thread.start();
            } else {
                throw new RuntimeException("Unable to create AudioRecord instance");
            }

        } else {
            throw new RuntimeException("Unable to get minimum buffer size");
        }
    }

    public void stop() throws IOException {
        isRecording = false;
        if (audioRecorder != null) {
            if (audioRecorder.getRecordingState() == AudioRecord.RECORDSTATE_RECORDING) {
                audioRecorder.stop();
            }
            if (audioRecorder.getState() == AudioRecord.STATE_INITIALIZED) {
                audioRecorder.release();
            }
        }
    }

    /**
     * Call this on pause.
     */
    public void pause() {
        synchronized (mPauseLock) {
            mPaused = true;
        }
    }

    /**
     * Call this on resume.
     */
    public void resume() {
        synchronized (mPauseLock) {
            mPaused = false;
            mPauseLock.notifyAll();
        }
    }

    @Override
    public void run() {
        short[] tempBuf = new short[bufferSize / 2];

        while (isRecording && audioRecorder.getRecordingState() == AudioRecord.RECORDSTATE_RECORDING) {
            audioRecorder.read(tempBuf, 0, tempBuf.length);
            mSubscriber.onNext(tempBuf);
            synchronized (mPauseLock) {
                while (mPaused) {
                    try {
                        mPauseLock.wait();
                    } catch (InterruptedException e) {
                        mSubscriber.onError(e);
                    }
                }
            }
        }
    }

    public boolean isRecording() {
        return (audioRecorder != null) && (audioRecorder.getRecordingState() == AudioRecord.RECORDSTATE_RECORDING);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();

        if (audioRecorder != null && audioRecorder.getState() == AudioRecord.STATE_INITIALIZED) {
            audioRecorder.stop();
            audioRecorder.release();
        }

        audioRecorder = null;
        thread = null;
    }

    public boolean isRecordingStopped() {
        return audioRecorder == null || audioRecorder.getRecordingState() == 1;
    }

    private void convertFileToWave(File file) throws IOException {
        try {
            rawToWave(file, mSampleRate, 16);
        } catch (IOException e) {
            throw new IOException("Unable to convert file");
        }
    }

    //channels -> Mono or Stereo
    //bitsPerSecond -> 16 or 8 (currently android supports 16 only)
    private void rawToWave(File rawFile, int sampleRate, int bitsPerSecond) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(rawFile, "rw");
        int channels = mChannels == AudioFormat.CHANNEL_IN_MONO ? 1 : 2;
        //seek to beginning
        randomAccessFile.seek(0);
        try {
            writeString(randomAccessFile, "RIFF"); // chunk id
            writeInt(randomAccessFile, (int) (36 + rawFile.length())); // chunk size
            writeString(randomAccessFile, "WAVE"); // format
            writeString(randomAccessFile, "fmt "); // subchunk 1 id
            writeInt(randomAccessFile, 16); // subchunk 1 size
            writeShort(randomAccessFile, (short) 1); // audio format (1 = PCM)
            writeShort(randomAccessFile, (short) channels); // number of channels
            writeInt(randomAccessFile, sampleRate); // sample rate
            writeInt(randomAccessFile, sampleRate * 2); // byte rate
            writeShort(randomAccessFile, (short) 2); // block align
            writeShort(randomAccessFile, (short) bitsPerSecond); // bits per sample
            writeString(randomAccessFile, "data"); // subchunk 2 id
            writeInt(randomAccessFile, (int) rawFile.length()); // subchunk 2 size
        } finally {
            randomAccessFile.close();
        }
    }

    private void writeInt(final RandomAccessFile output, final int value) throws IOException {
        output.write(value);
        output.write(value >> 8);
        output.write(value >> 16);
        output.write(value >> 24);
    }

    private void writeShort(final RandomAccessFile output, final short value) throws IOException {
        output.write(value);
        output.write(value >> 8);
    }

    private void writeString(final RandomAccessFile output, final String value) throws IOException {
        for (int i = 0; i < value.length(); i++) {
            output.write(value.charAt(i));
        }
    }

    public void writeShortsToFile(short[] shorts) throws IOException {
        for (int i = 0; i < shorts.length; i++) {
            mDataOutputStream.writeByte(shorts[i] & 0xFF);
            mDataOutputStream.writeByte((shorts[i] >> 8) & 0xFF);
        }
    }

    public void completeRecording() throws IOException {
        mDataOutputStream.flush();
        mDataOutputStream.close();

        File file = new File(mFilePath);
        convertFileToWave(file);
    }

}
