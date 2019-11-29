package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.domain.game.engine;

import java.util.ArrayList;

import ua.com.crosp.solutions.hearthealthmonitor.game.engine.GameSpeedContract;

/**
 * Created by Alexander Molochko on 2/22/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */


public class SquatGameSpeedManager {
    private GameSpeed mCurrentGameSpeed;

    public SquatGameSpeedManager() {
        mCurrentGameSpeed = GameSpeed.getDefault();
    }

    public GameSpeed getCurrentGameSpeed() {
        return mCurrentGameSpeed;
    }

    public void setCurrentSpeedValue(double currentSpeedValue) {
        mCurrentGameSpeed.setCurrentSpeedValue(currentSpeedValue);
    }

    public static class GameSpeed implements GameSpeedContract {
        private double mCurrentSpeedValue;
        private String mCurrentSpeedName;

        public GameSpeed(float currentSpeedValue, String currentSpeedName) {
            mCurrentSpeedValue = currentSpeedValue;
            mCurrentSpeedName = currentSpeedName;
        }

        public static GameSpeed getDefault() {
            return new GameSpeed(0, "Initial game speed");
        }

        public double getCurrentSpeedValue() {
            return 0;
        }

        public String getCurrentSpeedName() {
            return mCurrentSpeedName;
        }

        public void setCurrentSpeedValue(double currentSpeedValue) {
            mCurrentSpeedValue = currentSpeedValue;
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
