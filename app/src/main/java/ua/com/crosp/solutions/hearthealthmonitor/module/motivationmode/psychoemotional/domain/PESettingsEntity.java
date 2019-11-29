package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.domain;

import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.data.PEBallGameMode;

/**
 * Created by Alexander Molochko on 4/21/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class PESettingsEntity {

    private PEBallGameMode mGameMode = PEBallGameMode.getDefaultGameModes().get(0);
    private long mGameTime;

    public PESettingsEntity(PEBallGameMode gameMode, long gameTime) {
        mGameMode = gameMode;
        mGameTime = gameTime;
    }

    public PESettingsEntity() {

    }

    public long getExperimentTime() {
        return mGameTime;
    }

    public void setExperimentTime(long experimentTimeInSeconds) {
        mGameTime = experimentTimeInSeconds;
    }

    public long getGameModeId() {
        return mGameMode.getId();
    }

    public PEBallGameMode getGameMode() {
        return mGameMode;
    }

    public void setGameMode(PEBallGameMode gameMode) {
        mGameMode = gameMode;
    }

}
