package ua.com.crosp.solutions.hearthealthmonitor.game.engine;

import ua.com.crosp.solutions.hearthealthmonitor.game.model.Point2d;
import ua.com.crosp.solutions.hearthealthmonitor.game.valueobject.ScreenAspectCoefficient;
import ua.com.crosp.solutions.hearthealthmonitor.game.valueobject.ScreenAspectRatio;
import ua.com.crosp.solutions.hearthealthmonitor.game.valueobject.ScreenSize;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.game.model.valueobject.CanvasSize;

/**
 * Created by Alexander Molochko on 2/20/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class GameScreen implements AbstractCoordinatesConverter {
    private static final long DEFAULT_SCREEN_COEFFICIENT = 100;
    private static final long DEFAULT_ABSTRACT_SCREEN_WIDTH = 720;
    private static final long DEFAULT_ABSTRACT_SCREEN_HEIGHT = 1280;
    private static final ScreenSize DEFAULT_ABSTRACT_SCREEN_SIZE = new ScreenSize(DEFAULT_ABSTRACT_SCREEN_WIDTH, DEFAULT_ABSTRACT_SCREEN_HEIGHT);
    private Point2d mReusablePoint = new Point2d();
    private ScreenAspectRatio mScreenAspectRatio;
    private ScreenSize mAbstractScreenSize;
    private ScreenSize mRealScreenSize = new ScreenSize();
    private ScreenAspectCoefficient mScreenAspectCoefficient = new ScreenAspectCoefficient();
    private long mScreenCoefficient = DEFAULT_SCREEN_COEFFICIENT;

    public ScreenSize getAbstractScreenSize() {
        return mAbstractScreenSize;
    }

    public void setAbstractScreenSize(ScreenSize abstractScreenSize) {
        mAbstractScreenSize = abstractScreenSize;
    }

    public ScreenSize getRealScreenSize() {
        return mRealScreenSize;
    }

    public void setRealScreenSize(ScreenSize realScreenSize) {
        mRealScreenSize = realScreenSize;
    }

    public GameScreen(CanvasSize canvasSize) {
        updateFromCanvasSize(canvasSize);
    }

    public GameScreen() {

    }

    public void updateFromCanvasSize(CanvasSize canvasSize) {
        mRealScreenSize.setHeight(canvasSize.getHeight());
        mRealScreenSize.setWidth(canvasSize.getWidth());
        onScreenSizeUpdated();
    }

    private void onScreenSizeUpdated() {
        mScreenAspectRatio = ScreenAspectRatio.fromScreenSize(mRealScreenSize);
        mAbstractScreenSize = calculateAbstractScreenSize(mScreenAspectRatio, mScreenCoefficient);
        mScreenAspectCoefficient = ScreenAspectCoefficient.fromScreenSizes(mAbstractScreenSize, mRealScreenSize);
    }

    public ScreenAspectCoefficient getScreenAspectCoefficient() {
        return mScreenAspectCoefficient;
    }

    public void setScreenAspectCoefficient(ScreenAspectCoefficient screenAspectCoefficient) {
        mScreenAspectCoefficient = screenAspectCoefficient;
    }

    public float getWidth() {
        return mAbstractScreenSize.getWidth();
    }

    public float getHeight() {
        return mAbstractScreenSize.getHeight();
    }

    public ScreenAspectRatio getScreenAspectRatio() {
        return mScreenAspectRatio;
    }

    public void setScreenAspectRatio(ScreenAspectRatio screenAspectRatio) {
        mScreenAspectRatio = screenAspectRatio;
    }

    @Override
    public Point2d convertPointToRealPosition(Point2d point2d) {
        mReusablePoint.x = convertXPointToRealPosition(point2d.x);
        mReusablePoint.y = convertYPointToRealPosition(point2d.y);
        return mReusablePoint;

    }

    @Override
    public float convertXPointToRealPosition(float xAbstract) {
        return xAbstract * mScreenAspectCoefficient.getWidthCoefficient();

    }

    @Override
    public float convertYPointToRealPosition(float yAbstract) {
        return yAbstract * mScreenAspectCoefficient.getHeightCoefficient();
    }

    @Override
    public float convertYRealToAbstract(float yReal) {
        return yReal / mScreenAspectCoefficient.getHeightCoefficient();
    }

    @Override
    public float convertXRealToAbstract(float xReal) {
        return xReal / mScreenAspectCoefficient.getHeightCoefficient();
    }

    public static ScreenSize calculateAbstractScreenSize(ScreenAspectRatio aspectRatio, long coefficient) {
        return DEFAULT_ABSTRACT_SCREEN_SIZE;
        // return new ScreenSize(aspectRatio.getWidthRatio() * coefficient, aspectRatio.getHeightRatio() * coefficient);
    }


    public long getScreenCoefficient() {
        return mScreenCoefficient;
    }

    public void setScreenCoefficient(long screenCoefficient) {
        mScreenCoefficient = screenCoefficient;
    }
}
