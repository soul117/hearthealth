package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.data;

import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.settings.SettingsBundle;

/**
 * Created by Alexander Molochko on 4/21/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class PASettingsModel {
    // Constants
    public static final String KEY_SOUND_ENABLED = "sound_enabled";
    public static final String KEY_EXPERIMENT_TIME = "experiment_time";
    public static final String KEY_SQUAT_COUNT = "squat_count";
    private static final long DEFAULT_EXPERIMENT_TIME = 60;
    private static final int DEFAULT_SQUAT_COUNT = 20;
    // Variables
    private boolean mEnableSound;
    private long mExperimentTime;
    private int mSquatCount;

    public PASettingsModel(SettingsBundle settingsBundle) {
        mEnableSound = settingsBundle.getBooleanSetting(KEY_SOUND_ENABLED);
        mExperimentTime = settingsBundle.getLongSetting(KEY_EXPERIMENT_TIME);
        mSquatCount = settingsBundle.getIntSettings(KEY_SQUAT_COUNT);
    }

    public PASettingsModel(long experimentTime, int squatCount, boolean enableSound) {
        mEnableSound = enableSound;
        mExperimentTime = experimentTime;
        mSquatCount = squatCount;
    }

    public PASettingsModel() {

    }

    public boolean defaultValuesAreSet(SettingsBundle settingsBundle) {
        boolean anyChanges = false;
        if (mExperimentTime <= 0) {
            mExperimentTime = DEFAULT_EXPERIMENT_TIME;
            settingsBundle.setLongSetting(KEY_EXPERIMENT_TIME, getExperimentTime());
            anyChanges = true;
        }
        if (mSquatCount <= 0) {
            mSquatCount = DEFAULT_SQUAT_COUNT;
            settingsBundle.setIntSetting(KEY_SQUAT_COUNT, getSquatCount());
            anyChanges = true;
        }
        return anyChanges;
    }

    public void saveStateToBundle(SettingsBundle bundle) {
        bundle.setBooleanSetting(KEY_SOUND_ENABLED, isSoundEnabled());
        bundle.setIntSetting(KEY_SQUAT_COUNT, getSquatCount());
        bundle.setLongSetting(KEY_EXPERIMENT_TIME, getExperimentTime());
    }

    public boolean isSoundEnabled() {
        return mEnableSound;
    }

    public void setEnableSound(boolean enableSound) {
        mEnableSound = enableSound;
    }

    public int getSquatCount() {
        return mSquatCount;
    }

    public void setSquatCount(int squatCount) {
        mSquatCount = squatCount;
    }

    public long getExperimentTime() {
        return mExperimentTime;
    }

    public void setExperimentTime(long experimentTime) {
        mExperimentTime = experimentTime;
    }
}
