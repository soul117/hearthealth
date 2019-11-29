package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.domain.game.model;

import ua.com.crosp.solutions.hearthealthmonitor.game.graphics.contract.GraphicsRenderer;
import ua.com.crosp.solutions.hearthealthmonitor.game.model.Rectangle2d;
import ua.com.crosp.solutions.hearthealthmonitor.game.model.RoundedLine2d;

/**
 * Created by Alexander Molochko on 2/18/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class Leg2d extends Rectangle2d {
    public static final int LEFT_LEG = 1;
    public static final int RIGHT_LEG = 2;
    private final int mType;
    private float mWidth;
    private RoundedLine2d mThigh, mCalf;

    public Leg2d(float left, float top, float right, float bottom, float width, int type) {
        super(left, top, right, bottom);
        mWidth = width;
        mType = type;
        createLeg();
    }

    @Override
    public void draw(GraphicsRenderer renderer) {
        mThigh.draw(renderer);
        mCalf.draw(renderer);
    }

    public void updateFromMovementDelat(float movementDelta) {
        float sideMovement = (float) (movementDelta / 1.8);
        if (mType == LEFT_LEG) {
            float mCalfNewSidePosition = (float) (mCalf.getLeftInitial() - sideMovement);
            float mCalfNewTop = (float) (mCalf.getTopInitial() + (movementDelta / 2));
            // Thigh
            mThigh.setRight(mCalfNewSidePosition);
            mThigh.setBottom(mCalfNewTop);
            mThigh.setTop((float) (mThigh.getTopInitial() + movementDelta));
            // Calf
            mCalf.setLeft(mCalfNewSidePosition);
            mCalf.setTop(mCalfNewTop);
        } else {
            float mCalfNewSidePosition = (float) (mCalf.getLeftInitial() + sideMovement);
            float mCalfNewTop = (float) (mCalf.getTopInitial() + (movementDelta / 2));
            mThigh.setRight(mCalfNewSidePosition);
            mThigh.setBottom(mCalfNewTop);
            mThigh.setTop((float) (mThigh.getTopInitial() + movementDelta));
            // Calf
            mCalf.setLeft(mCalfNewSidePosition);
            mCalf.setTop(mCalfNewTop);
        }
    }


    private void createLeg() {
        float delta = 0;
        float height = getHeight();
        float halfHeight = height / 2;
        float leftX = getLeft() + mWidth / 2;
        float rightX = getRight() - mWidth / 2;
        float halfLeg = getTop() + halfHeight;
        mThigh = new RoundedLine2d(leftX, getTop(), leftX, halfLeg + delta, mWidth);
        mCalf = new RoundedLine2d(leftX, getTop() + halfHeight, leftX, getBottom(), mWidth);
    }

    @Override
    public void setColor(int color) {
        super.setColor(color);
        mThigh.setColor(color);
        mCalf.setColor(color);
    }
}
