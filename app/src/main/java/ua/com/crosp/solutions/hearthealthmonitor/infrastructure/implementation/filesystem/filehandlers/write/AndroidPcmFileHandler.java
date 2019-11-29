package ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.filesystem.filehandlers.write;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;

import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.audio.ReactiveAudioRecorder;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.filesystem.AudioFile;

public class AndroidPcmFileHandler extends AndroidSimpleFileHandler implements AudioFile {
    private ReactiveAudioRecorder.Settings mSettings;


    public AndroidPcmFileHandler(String filePath, DataOutputStream outputStream) throws FileNotFoundException {
        super(filePath, outputStream);
    }
    //region Factory methods


    @Override
    public void setAudioSettings(ReactiveAudioRecorder.Settings settings) {
        mSettings = settings;
    }

    @Override
    public ReactiveAudioRecorder.Settings getAudioSettings() {
        return mSettings;
    }
}