package ua.com.crosp.solutions.hearthealthmonitor.module.appsettings.data;

/**
 * Created by Alexander Molochko on 4/21/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class AppSettingsModel {
    // Constants
    public static final String KEY_SHOW_COUNTDOWN_TIMER = "show_countdowntimer";
    public static final String KEY_VOLTAGE_SCALE = "voltage_scale";
    public static final String KEY_RECORDING_DURATION = "recording_duration";
    private static final long DEFAULT_RECORDING_TIME = 30;
    private static final int DEFAULT_VOLTAGE_SCALE = 20;

    // Variables
    private boolean mShowCountdownTimer = true;
    private long mRecordingTime;
    private long mVoltageScale;

    public AppSettingsModel(long recordingTime, int voltageScale, boolean showCountdownTimer) {
        mShowCountdownTimer = showCountdownTimer;
        mRecordingTime = recordingTime;
        mVoltageScale = voltageScale;
    }

    public AppSettingsModel() {

    }

    public boolean initDefaultValuesIfRequired() {
        boolean anyChanges = false;
        if (mRecordingTime <= 0) {
            mRecordingTime = DEFAULT_RECORDING_TIME;
            anyChanges = true;
        }
        if (mVoltageScale <= 0) {
            mVoltageScale = DEFAULT_VOLTAGE_SCALE;
            anyChanges = true;
        }
        if (anyChanges) {
            mShowCountdownTimer = true;
        }
        return anyChanges;
    }


    public boolean showCountdownTimer() {
        return mShowCountdownTimer;
    }

    public void setShowCountdownTimer(boolean showCountdownTimer) {
        mShowCountdownTimer = showCountdownTimer;
    }

    public long getVoltageScale() {
        return mVoltageScale;
    }

    public void setVoltageScale(long voltageScale) {
        mVoltageScale = voltageScale;
    }

    public long getRecordingTime() {
        return mRecordingTime;
    }

    public void setRecordingTime(long recordingTime) {
        mRecordingTime = recordingTime;
    }
}
