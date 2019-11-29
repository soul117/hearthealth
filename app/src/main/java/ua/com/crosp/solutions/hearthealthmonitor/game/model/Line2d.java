package ua.com.crosp.solutions.hearthealthmonitor.game.model;

import ua.com.crosp.solutions.hearthealthmonitor.game.graphics.contract.GraphicsRenderer;

/**
 * Created by Alexander Molochko on 2/23/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
public class Line2d extends Shape2d {


    private float mWidth;

    public Line2d(float fromX, float fromY, float toX, float toY, float width) {
        super(fromX, fromY, toX, toY);
        mWidth = width;
    }

    @Override
    public void draw(GraphicsRenderer renderer) {
        renderer.drawLine(getLeft(), getTop(), getRight(), getBottom(), getColor(), mWidth);
    }


    @Override
    public float getWidth() {
        return mWidth;
    }

    public void setWidth(float width) {
        mWidth = width;
    }
}
