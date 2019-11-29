package ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.presentation.view.notification;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.widget.Chronometer;

import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.contract.PausableChronometerViewContract;

/**
 * Created by Alexander Molochko on 5/28/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class PausableChronometer extends Chronometer implements PausableChronometerViewContract {

    private long timeWhenStopped = 0;

    public PausableChronometer(Context context) {
        super(context);
    }

    public PausableChronometer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PausableChronometer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void start() {
        setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
        super.start();
    }

    @Override
    public void stop() {
        super.stop();
        timeWhenStopped = getBase() - SystemClock.elapsedRealtime();
    }

    public void reset() {
        stop();
        setBase(SystemClock.elapsedRealtime());
        timeWhenStopped = 0;
    }

    public long getCurrentTime() {
        return timeWhenStopped;
    }

    public void setCurrentTime(long time) {
        timeWhenStopped = time;
        setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
    }
}