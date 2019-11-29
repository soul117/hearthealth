package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.game.engine.strategy;

import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util.RandomUtils;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.contract.game.GameFallingBall;

/**
 * Created by Alexander Molochko on 9/4/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class CTBActivationIncentivesStrategy extends CTBActivationAndStopIncentivesStrategy {

    @Override
    public void onBallWentOutOfBoundaries(GameFallingBall ball) {
        mGameState.increaseMissedBallsCount();
    }

    @Override
    public int getNextBallColor() {
        return RandomUtils.getRandomItemFromArray(CORRECT_BALL_COLORS);
    }

    @Override
    public String getGameType() {
        return "Activation Only Incentives";
    }
}
