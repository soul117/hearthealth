package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.game.engine.strategy;

import android.graphics.Color;

import ua.com.crosp.solutions.hearthealthmonitor.game.engine.GameScreen;
import ua.com.crosp.solutions.hearthealthmonitor.game.model.VectorGeometry;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util.CollectionUtils;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util.RandomUtils;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util.TimeUtils;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.contract.game.CatchTheBallGameStrategyContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.contract.game.GameFallingBall;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.game.engine.CTBGameSpeedManager;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.game.engine.CatchTheBallGameEngine;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.game.engine.CatchTheBallGameState;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.game.model.BallBasket;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.game.model.VectorBall;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.game.model.valueobject.CTBGameScore;

/**
 * Created by Alexander Molochko on 9/4/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class CTBActivationAndStopIncentivesStrategy implements CatchTheBallGameStrategyContract {
    protected static final Integer[] AVAILABLE_BALL_COLORS = {Color.RED, Color.GREEN, Color.BLUE, Color.CYAN};
    protected static final Integer[] CORRECT_BALL_COLORS = {Color.RED, Color.GREEN};
    protected static final Integer[] INVALID_BALL_COLORS = {Color.BLUE, Color.CYAN};
    protected static final int NEW_LEVEL_DOWN_BARRIER = -5;
    protected static final int NEW_LEVEL_UP_BARRIER = 5;
    protected CatchTheBallGameState mGameState;
    protected long mExperimentTime;
    protected GameInfoProvider mGameInfoProvider;

    @Override
    public int getLeftBasketColor() {
        return Color.GREEN;
    }

    @Override
    public int getRightBasketColor() {
        return Color.RED;
    }

    @Override
    public int getGameFieldColor() {
        return Color.BLACK;
    }

    @Override
    public GameFallingBall getBallInInitialPosition(GameScreen gameScreen) {
        return createBallInInitialPosition(gameScreen);
    }

    @Override
    public void setCatchTheBallGameState(CatchTheBallGameState catchTheBallGameState) {
        this.mGameState = catchTheBallGameState;
        this.mExperimentTime = TimeUtils.secondsToMillis(mGameState.getGameSettings().getExperimentTime());
    }

    @Override
    public void onBallIntersectsLeftBasket(GameFallingBall ball, BallBasket ballBasket) {
        if (ball.hasSameColor(ballBasket)) {
            ballBasket.increaseCorrectBallsNumber();
            mGameState.increaseCorrectBallsCount();
        } else {
            ballBasket.increaseInCorrectBallsNumber();
            mGameState.increaseIncorrectBallsCount();
        }
    }

    @Override
    public void onBallIntersectsRightBasket(GameFallingBall ball, BallBasket ballBasket) {
        if (ball.hasSameColor(ballBasket)) {
            ballBasket.increaseCorrectBallsNumber();
            mGameState.increaseCorrectBallsCount();
        } else {
            ballBasket.increaseInCorrectBallsNumber();
            mGameState.increaseIncorrectBallsCount();
        }
    }

    @Override
    public void onBallWentOutOfBoundaries(GameFallingBall ball) {
        if (isInvalidBallColor(ball)) {
            mGameState.increaseCorrectBallsCount();
        } else {
            mGameState.increaseMissedBallsCount();
        }
    }

    @Override
    public void onLeftBasketTouched(GameFallingBall ball, BallBasket ballBasket) {
        if (ball.getFallingDirection() == GameFallingBall.DIRECTION_RIGHT) {
            ball.setFallDirectionToObject(mGameInfoProvider.provideCurrentGameScreen(), GameFallingBall.DIRECTION_STRAIGHT, ballBasket.getCenterPoint());
        } else {
            ball.setFallDirectionToObject(mGameInfoProvider.provideCurrentGameScreen(), GameFallingBall.DIRECTION_LEFT, ballBasket.getCenterPoint());
        }
    }

    @Override
    public void onRightBasketTouched(GameFallingBall ball, BallBasket ballBasket) {
        if (ball.getFallingDirection() == GameFallingBall.DIRECTION_LEFT) {

            ball.setFallDirectionToObject(mGameInfoProvider.provideCurrentGameScreen(), GameFallingBall.DIRECTION_STRAIGHT, ballBasket.getCenterPoint());
        } else {
            ball.setFallDirectionToObject(mGameInfoProvider.provideCurrentGameScreen(), GameFallingBall.DIRECTION_RIGHT, ballBasket.getCenterPoint());
        }
    }

    @Override
    public boolean shouldGameStop(long timeDifference) {
        return timeDifference >= mExperimentTime;
    }

    @Override
    public CTBGameSpeedManager.GameSpeed getGameSpeed() {
        return mGameState.getCurrentGameSpeed();
    }

    @Override
    public void setGameInfoProvider(GameInfoProvider gameInfoProvider) {
        mGameInfoProvider = gameInfoProvider;
    }

    @Override
    public long getGameEstimatedTime() {
        return mExperimentTime;
    }

    @Override
    public CTBGameScore getGamScore() {
        return mGameState.getGameScore();
    }

    @Override
    public String getGameType() {
        return "Activation and Stop Incentives";
    }


    public boolean isInvalidBallColor(GameFallingBall gameFallingBall) {
        return CollectionUtils.contains(INVALID_BALL_COLORS, gameFallingBall.getColor());
    }

    public int getNextBallColor() {
        return RandomUtils.getRandomItemFromArray(AVAILABLE_BALL_COLORS);
    }

    @Override
    public CatchTheBallGameState getGameState() {
        return mGameState;
    }

    private GameFallingBall createBallInInitialPosition(GameScreen gameScreen) {
        float ballRadius = determineCircleRadius(gameScreen);
        float ballCenterX = gameScreen.getWidth() / 2;
        GameFallingBall gameFallingBall = new VectorBall(ballCenterX, -ballRadius, ballRadius, getNextBallColor());
        gameFallingBall.setVelocityVector(new VectorGeometry.VelocityVector(0, mGameState.initialYVelocity));
        controlGameSpeed();
        return gameFallingBall;
    }

    private void controlGameSpeed() {
        // Collect current score
        int missed = getMissedBallsCount();
        int incorrect = getIncorrectBallsCount();
        int correct = getCorrectBallsCount();
        int failed = missed + incorrect;
        int difference = correct - failed;
        boolean newSpeed = false;
        if (difference > NEW_LEVEL_UP_BARRIER) {
            mGameState.speedUp();
            newSpeed = true;
        } else if (difference < NEW_LEVEL_DOWN_BARRIER) {
            mGameState.slowDown();
            newSpeed = true;
        }
        if (newSpeed) {
            mGameState.resetCurrentScore();
        }
    }

    private int getMissedBallsCount() {
        return mGameState.getMissedBallsCount();
    }

    private int getIncorrectBallsCount() {
        return mGameState.getIncorrectBalls();
    }

    private int getCorrectBallsCount() {
        return mGameState.getCorrectBalls();
    }

    private float determineCircleRadius(GameScreen canvasSize) {
        int width = (int) canvasSize.getWidth();
        return width / CatchTheBallGameEngine.CIRCLE_RADIUS_COEFFICIENT;
    }


}
