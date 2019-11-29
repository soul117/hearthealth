package ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.presentation.view.viewmodel;

/**
 * Created by Alexander Molochko on 10/31/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class EcgRawAudioData {
    private int[][] mRawAudioData;

    public EcgRawAudioData() {
    }

    public static EcgRawAudioData fromIntArray(int[][] audioArray) {
        EcgRawAudioData ecgRawAudioData = new EcgRawAudioData();
        ecgRawAudioData.mRawAudioData = audioArray;
        return ecgRawAudioData;
    }

    public int[][] getRawAudioData() {
        return mRawAudioData;
    }

    public int[] getFirstChannelData() {
        return mRawAudioData[0];
    }

    public void setRawAudioData(int[][] rawAudioData) {
        mRawAudioData = rawAudioData;
    }
}
