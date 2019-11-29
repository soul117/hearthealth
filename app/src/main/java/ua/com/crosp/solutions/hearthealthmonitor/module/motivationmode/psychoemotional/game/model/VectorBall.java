package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.game.model;

import ua.com.crosp.solutions.hearthealthmonitor.game.engine.GameScreen;
import ua.com.crosp.solutions.hearthealthmonitor.game.engine.GameSpeedContract;
import ua.com.crosp.solutions.hearthealthmonitor.game.graphics.contract.GraphicsRenderer;
import ua.com.crosp.solutions.hearthealthmonitor.game.model.Circle2d;
import ua.com.crosp.solutions.hearthealthmonitor.game.model.Point2d;
import ua.com.crosp.solutions.hearthealthmonitor.game.model.VectorGeometry;
import ua.com.crosp.solutions.hearthealthmonitor.game.valueobject.GameSettingsBundle;
import ua.com.crosp.solutions.hearthealthmonitor.game.valueobject.GameUpdateStateBundle;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.contract.game.GameFallingBall;

/**
 * Created by Alexander Molochko on 2/18/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class VectorBall extends Circle2d implements GameFallingBall {
    public static final int PADDING_COEFFICIENT = 10;
    private VectorGeometry.VelocityVector mVelocityVector;
    private Point2d mCenterPoint = new Point2d();
    private float mBoost = 0.0f;

    @FallingDirection
    private int mCurrentFallingDirection = DIRECTION_STRAIGHT;
    //region Constants

    //endregion

    public VectorBall(float centerX, float centerY, float radius, int color) {
        super(centerX, centerY, radius, color);
        mVelocityVector = getInitialVelocityVector();
        mVelocityVector.convertToUnitVector();
    }


    @Override
    public void draw(GraphicsRenderer renderer) {
        renderer.drawCircle(getCenterX(), getCenterY(), getRadius(), getColor());
    }

    @Override
    public void updateState(GameUpdateStateBundle gameUpdateStateBundle, GameSettingsBundle gameSettingsBundle) {
        double timeElapsed = gameUpdateStateBundle.getTimeElapsed();
        GameSpeedContract gameSpeed = gameUpdateStateBundle.getGameSpeed();
        mCenterX += (mVelocityVector.x * timeElapsed);
        mCenterY += (mVelocityVector.y * timeElapsed + gameSpeed.getCurrentSpeedValue());
    }


    public void increaseVelocity(float deltaVelocity) {
        increaseXVelocity(deltaVelocity);
        increaseYVelocity(deltaVelocity);
    }

    public void decreaseVelocity(float deltaVelocity) {
        decreaseXVelocity(deltaVelocity);
        decreaseYVelocity(deltaVelocity);
    }

    public void decreaseYVelocity(float velocity) {
        mVelocityVector.y -= velocity;
    }

    public void decreaseXVelocity(float velocity) {
        mVelocityVector.x -= velocity;
    }

    public void increaseXVelocity(float velocity) {
        mVelocityVector.x += velocity;
    }

    public void increaseYVelocity(float velocity) {
        mVelocityVector.y += velocity;
    }

    public VectorGeometry.VelocityVector getVelocityVector() {
        return mVelocityVector;
    }

    public void setVelocityVector(VectorGeometry.VelocityVector velocityVector) {
        mVelocityVector = velocityVector;
    }

    @Override
    public boolean wentOutOfBoundaries(GameScreen gameScreen) {
        return getCenterY() >= gameScreen.getHeight();
    }

    @Override
    public int getFallingDirection() {
        return mCurrentFallingDirection;
    }

    public float getBoost() {
        return mBoost;
    }

    public void setBoost(float boost) {
        mBoost = boost;
    }

    public void setFallDirection(@FallingDirection int fallingDirection) {
        this.mCurrentFallingDirection = fallingDirection;
    }

    @Override
    public void setFallDirectionToObject(GameScreen gameScreen, @FallingDirection int fallingDirection, Point2d objectTo) {
        switch (fallingDirection) {
            case DIRECTION_LEFT:
                this.setVectorToObject(objectTo);
                break;
            case DIRECTION_RIGHT:
                this.setVectorToObject(objectTo);
                break;
            case DIRECTION_STRAIGHT:
                mCenterPoint.x = mCenterX;
                mCenterPoint.y = mCenterY;
                this.mVelocityVector = getVectorToCenterBottom(mCenterPoint, gameScreen);
                break;
            default:
                break;
        }
        this.mCurrentFallingDirection = fallingDirection;

    }

    private void setVectorToObject(Point2d objectTo) {
        mVelocityVector = VectorGeometry.vectorFromTo(mCenterX, mCenterY, objectTo.x, objectTo.y);
        mVelocityVector.convertToUnitVector();
    }

    //region Vector methods
    private static VectorGeometry.VelocityVector getVectorToCenterBottom(Point2d centerPoint, GameScreen gameScreen) {
        VectorGeometry.VelocityVector vector = VectorGeometry.vectorFromTo(centerPoint.x, centerPoint.y, gameScreen.getWidth() / 2, gameScreen.getHeight());
        vector.convertToUnitVector();
        return vector;
    }

    private static VectorGeometry.VelocityVector getInitialVelocityVector() {
        return new VectorGeometry.VelocityVector(0, 1);
    }

    //endregion
}
