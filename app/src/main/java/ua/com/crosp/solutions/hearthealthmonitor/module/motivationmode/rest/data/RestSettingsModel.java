package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.data;

import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.settings.SettingsBundle;

/**
 * Created by Alexander Molochko on 4/21/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class RestSettingsModel {
    // Constants
    public static final String KEY_REST_TIME = "rest_time";
    // Variables
    private long mRestTime;

    public RestSettingsModel(SettingsBundle settingsBundle) {
        mRestTime = settingsBundle.getLongSetting(KEY_REST_TIME);
    }

    public RestSettingsModel(long restTime) {
        mRestTime = restTime;
    }

    public RestSettingsModel() {

    }

    public void saveStateToBundle(SettingsBundle bundle) {
        bundle.setLongSetting(KEY_REST_TIME, getRestTime());
    }


    public long getRestTime() {
        return mRestTime;
    }

    public void setRestTime(long restTime) {
        mRestTime = restTime;
    }
}
