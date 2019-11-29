package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.data;

/**
 * Created by Alexander Molochko on 4/21/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class RestSettingsEntity {
    private long mRestTime;

    public RestSettingsEntity(long experimentTime) {
        mRestTime = experimentTime;
    }

    public RestSettingsEntity() {

    }

    public long getRestTime() {
        return mRestTime;
    }

    public void setRestTime(long experimentTimeInSeconds) {
        mRestTime = experimentTimeInSeconds;
    }
}
