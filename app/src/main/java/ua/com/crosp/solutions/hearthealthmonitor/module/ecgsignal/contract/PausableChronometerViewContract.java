package ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.contract;

/**
 * Created by Alexander Molochko on 5/24/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public interface PausableChronometerViewContract {
    public void stop();

    public void start();

    public void reset();

    public long getCurrentTime();

    public void setCurrentTime(long time);

}
