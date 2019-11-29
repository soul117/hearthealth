package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.contract.game;

import androidx.annotation.ColorInt;

import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.game.engine.CTBGameSpeedManager;

/**
 * Created by Alexander Molochko on 2/21/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public interface CatchTheBallGameStateContract {

    CTBGameSpeedManager.GameSpeed getCurrentGameSpeed();

    @ColorInt
    int getLeftBasketColor();

    @ColorInt
    int getRightBasketColor();


    @ColorInt
    int getGameFieldColor();

    int getMissedBallsCount();

    int getIncorrectBallsCount();

    int getCorrectBallsCount();

    void increaseMissedBallsCount();

    void increaseIncorrectBallsCount();

    void increaseCorrectBallsCount();

    void clearMissedBallsCount();

    void clearIncorrectBallsCount();

    void clearCorrectBallsCount();

}
