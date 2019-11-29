package ua.com.crosp.solutions.hearthealthmonitor.game.valueobject;

/**
 * Created by Alexander Molochko on 10/23/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class ScreenAspectCoefficient {
    private float mWidthCoefficient;
    private float mHeightCoefficient;

    public static ScreenAspectCoefficient fromScreenSizes(ScreenSize abstractScreenSize, ScreenSize realScreenSize) {
        return new ScreenAspectCoefficient((float) realScreenSize.getWidth() / (float) abstractScreenSize.getWidth(), (float) realScreenSize.getHeight() / (float) abstractScreenSize.getHeight());
    }

    public ScreenAspectCoefficient() {
    }

    public ScreenAspectCoefficient(float widthCoefficient, float heightCoefficient) {
        mWidthCoefficient = widthCoefficient;
        mHeightCoefficient = heightCoefficient;
    }

    public float getHeightCoefficient() {
        return mHeightCoefficient;
    }

    public void setHeightCoefficient(float heightCoefficient) {
        mHeightCoefficient = heightCoefficient;
    }

    public float getWidthCoefficient() {
        return mWidthCoefficient;
    }

    public void setWidthCoefficient(float widthCoefficient) {
        mWidthCoefficient = widthCoefficient;
    }
}
