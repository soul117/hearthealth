package ua.com.crosp.solutions.hearthealthmonitor.game.valueobject;

/**
 * Created by Alexander Molochko on 10/23/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class ScreenSize {
    private long mHeight;
    private long mWidth;

    public ScreenSize(long width, long height) {
        mHeight = height;
        mWidth = width;
    }

    public ScreenSize() {


    }

    public long getWidth() {
        return mWidth;
    }

    public void setWidth(long width) {
        mWidth = width;
    }

    public long getHeight() {
        return mHeight;
    }

    public void setHeight(long height) {
        mHeight = height;
    }
}
