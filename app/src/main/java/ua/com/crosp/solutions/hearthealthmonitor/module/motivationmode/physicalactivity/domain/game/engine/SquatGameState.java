package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.domain.game.engine;

import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.data.PASettingsEntity;

/**
 * Created by Alexander Molochko on 2/21/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class SquatGameState {
    public float initialYVelocity = 0.5f;
    private float currentGameVelocity = initialYVelocity;
    // Game settings
    private PASettingsEntity mGameSettings;

    // Colors
    public int gameFieldColor;
    public int canvasColor;
    public int humanColor;
    // Game State

    public SquatGameSpeedManager mGameSpeedManager;
    private long mTimePerSquatInMs;
    private double mGameSpeedValue;

    // Ball Pass status. Used for detecting different states of a falling ball
    public SquatGameState(PASettingsEntity initialGameStateSettings) {
        mGameSettings = initialGameStateSettings;
        mGameSpeedManager = new SquatGameSpeedManager();
    }

    public static SquatGameState fromSettings(PASettingsEntity initialGameStateSettings) {
        SquatGameState gameState = new SquatGameState(initialGameStateSettings);
        return gameState;
    }


    public void setGameSettings(PASettingsEntity gameSettings) {
        mGameSettings = gameSettings;
    }


    public PASettingsEntity getGameSettings() {
        return mGameSettings;
    }


    public SquatGameSpeedManager.GameSpeed getCurrentGameSpeed() {
        return mGameSpeedManager.getCurrentGameSpeed();
    }

    public int getGameFieldColor() {
        return gameFieldColor;
    }

    public void setGameFieldColor(int gameFieldColor) {
        this.gameFieldColor = gameFieldColor;
    }


    public void setTimePerSquatInMs(long timePerSquatInMs) {
        mTimePerSquatInMs = timePerSquatInMs;
    }

    public double getTimePerSquat() {
        return mTimePerSquatInMs;
    }

    public void setGameSpeedValue(double gameSpeedValue) {
        mGameSpeedManager.setCurrentSpeedValue(gameSpeedValue);
    }
}
