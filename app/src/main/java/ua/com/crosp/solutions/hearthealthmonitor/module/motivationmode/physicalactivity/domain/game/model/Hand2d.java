package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.domain.game.model;

import ua.com.crosp.solutions.hearthealthmonitor.game.graphics.contract.GraphicsRenderer;
import ua.com.crosp.solutions.hearthealthmonitor.game.model.Rectangle2d;
import ua.com.crosp.solutions.hearthealthmonitor.game.model.RoundedLine2d;
import ua.com.crosp.solutions.hearthealthmonitor.game.model.VectorGeometry;
import ua.com.crosp.solutions.hearthealthmonitor.game.valueobject.GameSettingsBundle;
import ua.com.crosp.solutions.hearthealthmonitor.game.valueobject.GameUpdateStateBundle;

/**
 * Created by Alexander Molochko on 2/18/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class Hand2d extends Rectangle2d {
    public static final int LEFT_HAND = 1;
    public static final int RIGHT_HAND = 2;
    private float mHandWidth;
    private int mType;
    private RoundedLine2d mBottomLeg, mTopLeg;

    public Hand2d(float left, float top, float right, float bottom, float width, int type) {
        super(left, top, right, bottom);
        mHandWidth = width;
        mType = type;
        createLegs();
    }


    private void createLegs() {
        float height = getHeight();
        float baseHalf = height / 2;
        float legLength = (float) Math.sqrt(baseHalf * baseHalf + height * height);
        if (mType == LEFT_HAND) {
            mTopLeg = new RoundedLine2d(getRight() - legLength, getTop() + baseHalf, getRight(), getTop(), mHandWidth);
            mBottomLeg = new RoundedLine2d(getRight() - legLength, getTop() + baseHalf, getRight(), getBottom(), mHandWidth);
        } else {
            mTopLeg = new RoundedLine2d(getLeft(), getTop(), getLeft() + legLength, getTop() + baseHalf, mHandWidth);
            mBottomLeg = new RoundedLine2d(getLeft(), getBottom(), getLeft() + legLength, getTop() + baseHalf, mHandWidth);
        }

    }

    @Override
    public void draw(GraphicsRenderer renderer) {
        super.draw(renderer);
        mBottomLeg.draw(renderer);
        mTopLeg.draw(renderer);
    }

    public void updateStateByMovementDelta(float movementDelta) {
        // Bottom leg
        mBottomLeg.setTop(mBottomLeg.getTopInitial() + movementDelta);
        mBottomLeg.setBottom(mBottomLeg.getBottomInitial() + movementDelta);
        // Top leg
        mTopLeg.setTop(mTopLeg.getTopInitial() + movementDelta);
        mTopLeg.setBottom(mTopLeg.getBottomInitial() + movementDelta);
    }

    @Override
    public void setColor(int color) {
        mTopLeg.setColor(color);
        mBottomLeg.setColor(color);
    }

    @Override
    public void setVelocityVector(VectorGeometry.VelocityVector velocityVector) {
        super.setVelocityVector(velocityVector);
        mTopLeg.setVelocityVector(getVelocityVector());
        mBottomLeg.setVelocityVector(getVelocityVector());
    }

    public float getHandWidth() {
        return mHandWidth;
    }

    public void setHandWidth(float handWidth) {
        mHandWidth = handWidth;
    }
}