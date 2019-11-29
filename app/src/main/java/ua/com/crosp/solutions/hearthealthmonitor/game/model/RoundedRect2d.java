package ua.com.crosp.solutions.hearthealthmonitor.game.model;

import ua.com.crosp.solutions.hearthealthmonitor.game.graphics.contract.GraphicsRenderer;

/**
 * Created by Alexander Molochko on 2/18/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class RoundedRect2d extends Rectangle2d {

    private final float mRy;
    private final float mRx;

    public RoundedRect2d(float left, float top, float right, float bottom, float rX, float rY) {
        super(left, top, right, bottom);
        mRx = rX;
        mRy = rY;
    }

    @Override
    public void draw(GraphicsRenderer renderer) {
        renderer.drawRoundedRect(getLeft(), getTop(), getRight(), getBottom(), mRx, mRy, getColor());
    }


}
