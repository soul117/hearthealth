package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.data;

import ua.com.crosp.solutions.hearthealthmonitor.R;

/**
 * Created by Alexander Molochko on 4/21/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class PASettingsEntity {
    private boolean mEnableSound;
    private long mExperimentTime;
    private int mSquatCount;
    private int mGameModeId = R.id.squat_game_mode_default;

    public PASettingsEntity(long experimentTime, int squatCount, boolean enableSound) {
        mEnableSound = enableSound;
        mExperimentTime = experimentTime;
        mSquatCount = squatCount;
    }

    public PASettingsEntity() {

    }

    public boolean isEnableSound() {
        return mEnableSound;
    }

    public void setEnableSound(boolean enableSound) {
        mEnableSound = enableSound;
    }

    public int getRepsCount() {
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

    public int getGameModeId() {
        return mGameModeId;
    }

    public void setGameModeId(int gameModeId) {
        mGameModeId = gameModeId;
    }
}
