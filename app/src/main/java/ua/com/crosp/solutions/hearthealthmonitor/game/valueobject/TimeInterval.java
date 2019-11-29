package ua.com.crosp.solutions.hearthealthmonitor.game.valueobject;

/**
 * Created by Alexander Molochko on 10/23/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class TimeInterval {
    private double mStartTime;
    private double mEndTime;

    public double calculateDifference() {
        return mEndTime - mStartTime;
    }

    // Getters & Setters
    public double getEndTime() {
        return mEndTime;
    }

    public void setEndTime(double endTime) {
        mEndTime = endTime;
    }

    public double getStartTime() {
        return mStartTime;
    }

    public void setStartTime(double startTime) {
        mStartTime = startTime;
    }
}
