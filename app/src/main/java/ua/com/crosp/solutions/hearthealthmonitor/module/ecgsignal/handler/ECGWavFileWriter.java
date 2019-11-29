package ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.handler;

import com.scichart.core.model.DoubleValues;
import com.scichart.core.model.ShortValues;

import java.io.FileNotFoundException;
import java.io.IOException;

import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.audio.ReactiveAudioRecorder;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.filesystem.FileManager;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.filesystem.filehandlers.write.AndroidWavFileHandler;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util.Logger;

/**
 * Created by Alexander Molochko on 11/28/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
public class ECGWavFileWriter implements ECGSignalProcessor<ShortValues, DoubleValues> {

    private AndroidWavFileHandler mAudioFileHandler;
    private FileManager mFileManager;

    public ECGWavFileWriter(FileManager fileManager) {
        mFileManager = fileManager;
    }

    public void initWithFilePathAndSettings(String filePath, ReactiveAudioRecorder.Settings settings) throws FileNotFoundException {
        mFileManager.makeDirectoriesIfNotExist(filePath);
        mAudioFileHandler = AndroidWavFileHandler.createFromAudioSettings(filePath, settings);
        mAudioFileHandler.onStartWritingToFile();
    }


    @Override
    public ShortValues processSignal(ShortValues signal, DoubleValues time) {
        try {
            mAudioFileHandler.writeShortArray(signal.getItemsArray());
        } catch (IOException e) {
            e.printStackTrace();
            this.stopWritingAndSave();
        }
        return signal;
    }

    @Override
    public boolean isProcessingBouncingSignal() {
        return false;
    }

    @Override
    public void onStopProcessing() {
        stopWritingAndSave();
    }

    private void stopWritingAndSave() {
        try {
            mAudioFileHandler.close();
            Logger.error("CLOSED FILE !! " + mAudioFileHandler.getFilePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}