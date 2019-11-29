package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.game.model.valueobject;

/**
 * Created by Alexander Molochko on 2/18/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class CanvasSize {

    private long mWidth;
    private long mHeight;

    public CanvasSize(long x, long y) {
        mWidth = x;
        mHeight = y;
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
