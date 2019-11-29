package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.game.engine;

import java.util.ArrayList;

import ua.com.crosp.solutions.hearthealthmonitor.game.engine.GameSpeedContract;

/**
 * Created by Alexander Molochko on 2/22/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */


public class CTBGameSpeedManager {
    public static final int INCREMENT_COEFFICIENT = 2;
    private final long mExperimentTime;
    private final int mGameSpeedCount;
    private GameSpeed mCurrentGameSpeed;
    private static final String BASE_GAME_SPEED_NAME_FORMAT = "GameSpeedContract %d";
    private GameSpeed.List mGameSpeeds;
    private float mIncrementCoefficient;

    public CTBGameSpeedManager(long experimentTime, int gameSpeedCount) {
        this.mExperimentTime = experimentTime;
        this.mGameSpeedCount = gameSpeedCount;
        this.initGameSpeedTypes();
        this.mCurrentGameSpeed = mGameSpeeds.getLowestSpeed();
    }

    private void initGameSpeedTypes() {
        mIncrementCoefficient = determineCoefficientByGameTime(mExperimentTime, mGameSpeedCount);
        this.mGameSpeeds = new GameSpeed.List();
        for (int i = 1; i <= mGameSpeedCount; i++) {
            mGameSpeeds.add(new GameSpeed(mIncrementCoefficient * i, String.format(BASE_GAME_SPEED_NAME_FORMAT, i)));
        }
    }

    private float determineCoefficientByGameTime(long experimentTime, int gameSpeedCount) {
        float coefficient = experimentTime / gameSpeedCount;
        coefficient = coefficient > INCREMENT_COEFFICIENT ? coefficient : INCREMENT_COEFFICIENT;
        return coefficient;
    }

    public void slowDown() {
        int currentIndex = mGameSpeeds.indexOf(mCurrentGameSpeed);
        this.mCurrentGameSpeed = currentIndex > 0 ? mGameSpeeds.get(currentIndex - 1) : mGameSpeeds.getLowestSpeed();
    }

    public void speedUp() {
        int currentIndex = mGameSpeeds.indexOf(mCurrentGameSpeed);
        this.mCurrentGameSpeed = currentIndex < mGameSpeeds.size() - 1 ? mGameSpeeds.get(currentIndex + 1) : mGameSpeeds.getHighestSpeed();
    }

    public GameSpeed getCurrentGameSpeed() {
        return mCurrentGameSpeed;
    }

    public static class GameSpeed implements GameSpeedContract {
        private float mCurrentSpeedValue;
        private String mCurrentSpeedName;

        public GameSpeed(float currentSpeedValue, String currentSpeedName) {
            mCurrentSpeedValue = currentSpeedValue;
            mCurrentSpeedName = currentSpeedName;
        }

        public double getCurrentSpeedValue() {
            return mCurrentSpeedValue;
        }

        public String getCurrentSpeedName() {
            return mCurrentSpeedName;
        }

        public static class List extends ArrayList<GameSpeed> {
            public GameSpeed getHighestSpeed() {
                return this.get(this.size() - 1);
            }

            public GameSpeed getLowestSpeed() {
                return this.get(0);
            }
        }
    }
}
