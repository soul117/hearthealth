package ua.com.crosp.solutions.hearthealthmonitor.game.valueobject;

import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util.MathUtils;

/**
 * Created by Alexander Molochko on 10/23/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class ScreenAspectRatio {
    private long mWidthRatio;
    private long mHeightRatio;

    public ScreenAspectRatio(long widthRatio, long heightRatio) {
        mWidthRatio = widthRatio;
        mHeightRatio = heightRatio;
    }

    public static ScreenAspectRatio fromScreenSize(ScreenSize screenSize) {
        long gcdRatio = MathUtils.gcd(screenSize.getWidth(), screenSize.getHeight());
        long widthRation = screenSize.getWidth() / gcdRatio;
        long heightRatio = screenSize.getHeight() / gcdRatio;
        return new ScreenAspectRatio(widthRation, heightRatio);
    }

    public ScreenAspectRatio() {

    }

    public long getWidthRatio() {
        return mWidthRatio;
    }

    public void setWidthRatio(long widthRatio) {
        mWidthRatio = widthRatio;
    }

    public long getHeightRatio() {
        return mHeightRatio;
    }

    public void setHeightRatio(long heightRatio) {
        mHeightRatio = heightRatio;
    }
}
