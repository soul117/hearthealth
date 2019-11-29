package ua.com.crosp.solutions.hearthealthmonitor.game.engine;

/**
 * Created by Alexander Molochko on 2/20/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public interface GameThread {
    void shouldStart();

    void shouldStop();

    void shouldPause();

    void shouldResume();

    void start();

    boolean isRunning();

    void run();

    public boolean isPaused();

    void join() throws InterruptedException;

}
