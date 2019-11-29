package ua.com.crosp.solutions.hearthealthmonitor.game.model;

/**
 * Created by Alexander Molochko on 2/18/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public abstract class Circle2d extends Shape2d {

    protected float mCenterX, mCenterY;
    protected float mRadius;
    protected float mCenterXInitial, mCenterYInitial;

    public Circle2d(float centerX, float centerY, float radius) {
        mCenterX = centerX;
        mCenterY = centerY;
        mCenterXInitial = centerX;
        mCenterYInitial = centerY;
        mRadius = radius;
    }

    public Circle2d(float centerX, float centerY, float radius, int color) {
        mCenterX = centerX;
        mCenterY = centerY;
        mRadius = radius;
        setColor(color);
    }

    public float getCenterX() {
        return mCenterX;
    }

    public void setCenterX(float centerX) {
        mCenterX = centerX;
    }

    public float getCenterY() {
        return mCenterY;
    }

    public void setCenterY(float centerY) {
        mCenterY = centerY;
    }

    public float getRadius() {
        return mRadius;
    }

    public void setRadius(float radius) {
        mRadius = radius;
    }

    public float getCenterXInitial() {
        return mCenterXInitial;
    }

    public void setCenterXInitial(float centerXInitial) {
        mCenterXInitial = centerXInitial;
    }

    public float getCenterYInitial() {
        return mCenterYInitial;
    }

    public void setCenterYInitial(float centerYInitial) {
        mCenterYInitial = centerYInitial;
    }

    @Override
    public float getTop() {
        return mCenterY - mRadius;
    }

    @Override
    public float getBottom() {
        return mCenterY + mRadius;
    }

    @Override
    public float getLeft() {
        return mCenterX - mRadius;
    }

    @Override
    public float getRight() {
        return mCenterX + mRadius;
    }

}
