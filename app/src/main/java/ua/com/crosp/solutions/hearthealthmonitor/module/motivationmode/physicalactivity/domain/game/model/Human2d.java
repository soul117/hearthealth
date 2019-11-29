package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.domain.game.model;

import ua.com.crosp.solutions.hearthealthmonitor.game.graphics.contract.GraphicsRenderer;
import ua.com.crosp.solutions.hearthealthmonitor.game.model.Point2d;
import ua.com.crosp.solutions.hearthealthmonitor.game.model.Rectangle2d;
import ua.com.crosp.solutions.hearthealthmonitor.game.model.VectorGeometry;
import ua.com.crosp.solutions.hearthealthmonitor.game.valueobject.GameSettingsBundle;
import ua.com.crosp.solutions.hearthealthmonitor.game.valueobject.GameUpdateStateBundle;

import static ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.contract.game.SquatGameStrategyContract.SETTINGS_TIME_IN_MS_PER_ITERATION;

/**
 * Created by Alexander Molochko on 2/18/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class Human2d extends Rectangle2d {

    public static final int HEAD_TO_BODY_COEFFICIENT = 7;
    public static final int HAND_TO_HEAD_COEFFICIENT = 2;
    private static final float HAND_WIDTH_TO_HEAD_COEFFICIENT = 5;
    private static final float BODY_TO_HEAD_COEFFICIENT = 3;
    public static final float LEG_TO_HAND_COEFFICIENT = 1.5f;
    private static final float HEAD_TO_LEG_COEFFICIENT = 3;
    private BodyMovement mBodyMovement;
    private Hand2d mLeftHand, mRightHand;
    private Leg2d mLeftLeg, mRightLeg;
    private Body2d mBody;
    private Head2d mHead;
    private final static float HEAD_BODY_COEFFICIENT = 1.2f;
    private VectorGeometry.VelocityVector mLegsVelocityVector;
    private Point2d mBodyBottomMostPosition;
    private Point2d mBodyTopMostPosition;
    private double mCurrentSinPositionValue;
    private float mCurrentBodyYPosition;
    private long mCurrentGameTime;
    private float mDistanceFromBodyToBottomPoint;


    public Human2d(float left, float top, float right, float bottom) {
        super(left, top, right, bottom);
        initBodyParts();
    }

    private void initBodyParts() {
        float height = getHeight();
        float width = getWidth();
        float headSize = height / HEAD_TO_BODY_COEFFICIENT;
        float handWidth = headSize / HAND_WIDTH_TO_HEAD_COEFFICIENT;
        float legWidth = handWidth * LEG_TO_HAND_COEFFICIENT;
        float headBottom = getTop() + headSize;
        float bodyHeight = headSize * BODY_TO_HEAD_COEFFICIENT;
        float centerXLine = getCenterX();
        float legHeight = headSize * HEAD_TO_LEG_COEFFICIENT;
        float bodyBottom = headBottom + bodyHeight;
        float bodyWidth = headSize * HEAD_BODY_COEFFICIENT;
        float bodyXLeft = centerXLine - bodyWidth / 2;
        float bodyXRight = centerXLine + bodyWidth / 2;
        float headRadius = headSize / 2;
        mHead = new Head2d(centerXLine, getTop() + headRadius, headRadius);
        mLeftHand = new Hand2d(getLeft(), getTop(), bodyXLeft, getTop() + headSize, handWidth, Hand2d.LEFT_HAND);
        mRightHand = new Hand2d(bodyXRight, getTop(), getRight(), getTop() + headSize, handWidth, Hand2d.RIGHT_HAND);
        mBody = new Body2d(bodyXLeft, headBottom, bodyXRight, bodyBottom);
        mLeftLeg = new Leg2d(bodyXLeft, bodyBottom, bodyXLeft + legWidth, bodyBottom + legHeight, legWidth, Leg2d.LEFT_LEG);
        mRightLeg = new Leg2d(bodyXRight - legWidth, bodyBottom, bodyXRight, bodyBottom + legHeight, legWidth, Leg2d.RIGHT_LEG);
        mBodyBottomMostPosition = new Point2d(centerXLine, bodyBottom + legHeight / 2);
        mBodyTopMostPosition = new Point2d(centerXLine, headBottom);
        mBodyMovement = BodyMovement.DOWN;
        mDistanceFromBodyToBottomPoint = mBodyBottomMostPosition.y - bodyBottom;
    }

    @Override
    public void setVelocityVector(VectorGeometry.VelocityVector velocityVector) {
        super.setVelocityVector(velocityVector);
        mHead.setVelocityVector(mVelocityVector);
        mLeftHand.setVelocityVector(mVelocityVector);
        mRightHand.setVelocityVector(mVelocityVector);
        mBody.setVelocityVector(mVelocityVector);
    }

    public void setLegsVelocityVector(VectorGeometry.VelocityVector legsVelocityVector) {
        mLegsVelocityVector = legsVelocityVector;
        mLeftLeg.setVelocityVector(mLegsVelocityVector);
        mRightLeg.setVelocityVector(mLegsVelocityVector);
    }

    private void inverseMovingDirection() {
        VectorGeometry.VelocityVector mainVector = getVelocityVector();
        mainVector.inverse();
        mLegsVelocityVector.inverse();
        setVelocityVector(mainVector);
        setLegsVelocityVector(mLegsVelocityVector);
    }

    @Override
    public void draw(GraphicsRenderer renderer) {
        mLeftHand.draw(renderer);
        mRightHand.draw(renderer);
        mHead.draw(renderer);
        mBody.draw(renderer);
        mLeftLeg.draw(renderer);
        mRightLeg.draw(renderer);
    }

    public void updateState(GameUpdateStateBundle gameUpdateStateBundle, GameSettingsBundle gameSettingsBundle) {
        BodyMovement movement = mBodyMovement;
        if (mBody.isBodyBellowPoint(mBodyBottomMostPosition)) {
            movement = BodyMovement.UP;
        } else if (mBody.isBodyAbovePoint(mBodyTopMostPosition)) {
            movement = BodyMovement.DOWN;
        }
        if (movement != mBodyMovement) {
            mBodyMovement = movement;
            inverseMovingDirection();
        }
        updateBodyParts(gameUpdateStateBundle, gameSettingsBundle);
    }

    private void updateBodyParts(GameUpdateStateBundle gameUpdateStateBundle, GameSettingsBundle gameSettingsBundle) {
        long timePerIterationMs = gameSettingsBundle.getSettingsValue(SETTINGS_TIME_IN_MS_PER_ITERATION);
        mCurrentGameTime = gameUpdateStateBundle.getGameTimeMs();
        // Calculate current position based on game time sin range : 0..1
        mCurrentSinPositionValue = (Math.sin((float) mCurrentGameTime * Math.PI * 2f / (float) timePerIterationMs - Math.PI / 2f) + 1f) / 2f;
        mCurrentBodyYPosition = (float) (mDistanceFromBodyToBottomPoint * mCurrentSinPositionValue);
        float movementDelta = mBodyTopMostPosition.y - mCurrentBodyYPosition;
        updateBodyPosition(movementDelta);
        updateHeadPosition(movementDelta);
        updateHands(movementDelta);
        updateLegs(movementDelta);
    }

    private void updateLegs(float movementDelta) {
        mLeftLeg.updateFromMovementDelat(movementDelta);
        mRightLeg.updateFromMovementDelat(movementDelta);
    }

    private void updateHands(float movementDelta) {
        mLeftHand.updateStateByMovementDelta(movementDelta);
        mRightHand.updateStateByMovementDelta(movementDelta);
    }

    protected void updateHeadPosition(float movementDelta) {
        mHead.setCenterX(mHead.getCenterXInitial());
        mHead.setCenterY(mHead.getCenterYInitial() + movementDelta);
    }

    protected void updateBodyPosition(float movementDelta) {
        mBody.setBottom(mBody.getBottomInitial() + movementDelta);
        mBody.setTop(mBody.getTopInitial() + movementDelta);
    }

    @Override
    public void setColor(int color) {
        super.setColor(color);
        mLeftHand.setColor(color);
        mHead.setColor(color);
        mRightHand.setColor(color);
        mBody.setColor(color);
        mLeftLeg.setColor(color);
        mRightLeg.setColor(color);
    }

    public enum BodyMovement {
        UP,
        DOWN
    }
}
