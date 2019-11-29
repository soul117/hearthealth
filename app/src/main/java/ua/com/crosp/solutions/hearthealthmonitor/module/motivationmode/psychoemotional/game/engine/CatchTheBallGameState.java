package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.game.engine;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.contract.game.CatchTheBallGameStateContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.domain.PESettingsEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.game.model.valueobject.CTBGameScore;

/**
 * Created by Alexander Molochko on 2/21/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class CatchTheBallGameState implements CatchTheBallGameStateContract {
    //region Game levels
    public static final int DEFAULT_GAME_SPEED_COUNT = 10;
    //endregion

    public float initialYVelocity = 0.5f;

    // Game settings
    private PESettingsEntity mGameSettings;
    // Colors
    public int gameFieldColor;
    public int leftBasketColor;
    public int rightBasketColor;

    // Game State

    private CTBGameScore mGameScore;
    public CTBGameSpeedManager mGameSpeedManager;

    // Ball Pass status. Used for detecting different states of a falling ball

    public static final int BALL_STATUS_CORRECT = 0;
    public static final int BALL_STATUS_INCORRECT = 1;
    public static final int BALL_STATUS_MISSED = 2;

    public CatchTheBallGameState(PESettingsEntity initialGameStateSettings) {
        mGameScore = new CTBGameScore();
        mGameSettings = initialGameStateSettings;
        mGameSpeedManager = new CTBGameSpeedManager(initialGameStateSettings.getExperimentTime(), DEFAULT_GAME_SPEED_COUNT);
    }

    public static CatchTheBallGameState fromSettings(PESettingsEntity initialGameStateSettings) {
        CatchTheBallGameState gameState = new CatchTheBallGameState(initialGameStateSettings);
        return gameState;
    }

    public int getCorrectBalls() {
        return mGameScore.getCorrectBallCount();
    }

    public int getIncorrectBalls() {
        return mGameScore.getIncorrectBallsCount();
    }

    public void setGameSettings(PESettingsEntity gameSettings) {
        mGameSettings = gameSettings;
    }


    public PESettingsEntity getGameSettings() {
        return mGameSettings;
    }

    public void speedUp() {
        mGameSpeedManager.speedUp();
    }

    public void slowDown() {
        mGameSpeedManager.slowDown();
    }

    public CTBGameScore getGameScore() {
        return mGameScore;
    }

    @IntDef({BALL_STATUS_CORRECT, BALL_STATUS_INCORRECT, BALL_STATUS_MISSED})
    @Retention(RetentionPolicy.SOURCE)
    public @interface BallPassStatus {
    }


    @Override
    public CTBGameSpeedManager.GameSpeed getCurrentGameSpeed() {
        return mGameSpeedManager.getCurrentGameSpeed();
    }

    @Override
    public int getLeftBasketColor() {
        return leftBasketColor;
    }

    @Override
    public int getRightBasketColor() {
        return rightBasketColor;
    }

    public int getGameFieldColor() {
        return gameFieldColor;
    }

    @Override
    public int getMissedBallsCount() {
        return mGameScore.getMissedBallsCount();
    }

    @Override
    public int getIncorrectBallsCount() {
        return mGameScore.getIncorrectBallsCount();
    }

    @Override
    public int getCorrectBallsCount() {
        return mGameScore.getCorrectBallCount();
    }

    @Override
    public void increaseMissedBallsCount() {
        mGameScore.incrementMissedBallsCount();
    }

    @Override
    public void increaseIncorrectBallsCount() {
        mGameScore.incrementIncorrectBallsCount();
    }

    @Override
    public void increaseCorrectBallsCount() {
        this.mGameScore.incrementCorrectBallsCount();
    }

    @Override
    public void clearMissedBallsCount() {
        mGameScore.clearMissedBallCount();
    }

    @Override
    public void clearIncorrectBallsCount() {
        mGameScore.clearIncorrectBallCount();
    }

    @Override
    public void clearCorrectBallsCount() {
        mGameScore.clearCorrectBallCount();
    }

    public void setGameFieldColor(int gameFieldColor) {
        this.gameFieldColor = gameFieldColor;
    }

    public void setLeftBasketColor(int leftBasketColor) {
        this.leftBasketColor = leftBasketColor;
    }

    public void setRightBasketColor(int rightBasketColor) {
        this.rightBasketColor = rightBasketColor;
    }

    public void resetCurrentScore() {
        clearCorrectBallsCount();
        clearIncorrectBallsCount();
        clearMissedBallsCount();
    }

}
