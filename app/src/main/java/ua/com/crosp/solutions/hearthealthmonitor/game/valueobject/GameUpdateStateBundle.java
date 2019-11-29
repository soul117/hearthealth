package ua.com.crosp.solutions.hearthealthmonitor.game.valueobject;

import ua.com.crosp.solutions.hearthealthmonitor.game.engine.GameScreen;
import ua.com.crosp.solutions.hearthealthmonitor.game.engine.GameSpeedContract;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util.TimeUtils;

/**
 * Created by Alexander Molochko on 10/23/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class GameUpdateStateBundle {
    GameScreen mGameScreen;
    long mTimeElapsed;
    long mGameTime;
    long mGameEstimatedTime;
    GameSpeedContract mGameSpeedContract;

    public GameUpdateStateBundle(GameScreen gameScreen, long timeElapsed, long gameTime, long gameEstimatedTime, GameSpeedContract gameSpeedContract) {
        mGameScreen = gameScreen;
        mTimeElapsed = timeElapsed;
        mGameTime = gameTime;
        mGameEstimatedTime = gameEstimatedTime;
        mGameSpeedContract = gameSpeedContract;
    }

    public GameUpdateStateBundle() {

    }

    public long getGameEstimatedTimeInMilliseconds() {
        return TimeUtils.secondsToMillis(mGameEstimatedTime);
    }

    public long getGameEstimatedTimeInSeconds() {
        return mGameEstimatedTime;
    }

    public void setGameEstimatedTime(long gameEstimatedTime) {
        mGameEstimatedTime = gameEstimatedTime;
    }

    public GameScreen getGameScreen() {
        return mGameScreen;
    }

    public void setGameScreen(GameScreen gameScreen) {
        mGameScreen = gameScreen;
    }

    public long getTimeElapsed() {
        return mTimeElapsed;
    }

    public void setTimeElapsed(long timeElapsed) {
        mTimeElapsed = timeElapsed;
    }

    public long getGameTimeMs() {
        return mGameTime;
    }

    public void setGameTime(long gameTime) {
        mGameTime = gameTime;
    }

    public GameSpeedContract getGameSpeed() {
        return mGameSpeedContract;
    }

    public void setGameSpeed(GameSpeedContract gameSpeedContract) {
        mGameSpeedContract = gameSpeedContract;
    }
}
