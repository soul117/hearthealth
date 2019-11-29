package ua.com.crosp.solutions.hearthealthmonitor.game.engine;

/**
 * Created by Alexander Molochko on 2/20/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class BaseAndroidGameThread extends Thread implements GameThread {
    protected transient boolean mIsRunning = false;
    protected transient boolean mIsPaused = false;

    @Override
    public void shouldStart() {
        mIsRunning = true;
    }

    @Override
    public void shouldStop() {
        mIsRunning = false;
        mIsPaused = false;
    }

    @Override
    public void shouldPause() {
        mIsPaused = true;
    }

    @Override
    public void shouldResume() {
        mIsPaused = false;
    }

    @Override
    public boolean isRunning() {
        return mIsRunning;
    }

    public boolean isPaused() {
        return mIsPaused;
    }

}
