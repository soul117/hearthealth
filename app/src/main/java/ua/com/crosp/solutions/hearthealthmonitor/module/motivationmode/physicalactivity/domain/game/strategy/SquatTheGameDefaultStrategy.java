package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.domain.game.strategy;

import android.graphics.Color;

import ua.com.crosp.solutions.hearthealthmonitor.game.engine.GameSpeedContract;
import ua.com.crosp.solutions.hearthealthmonitor.game.valueobject.GameSettingsBundle;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util.Logger;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util.TimeUtils;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.contract.game.SquatGameStrategyContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.data.PASettingsEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.domain.game.engine.SquatGameState;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.game.model.valueobject.CanvasSize;

/**
 * Created by Alexander Molochko on 9/4/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class SquatTheGameDefaultStrategy implements SquatGameStrategyContract {
    protected static final int DEFAULT_HUMAN_COLOR = Color.WHITE;
    protected CanvasSize mCanvasSize;
    protected SquatGameState mGameState;
    protected long mExperimentTime;
    public static final float PADDING_TOP_BOTTOM_COEFFICIENT = 10;
    public static final float PADDING_LEFT_RIGHT_COEFFICIENT = 7;
    public static final long MS_PER_FRAME = 40;
    public float initialVelocity = 0.05f;
    private GameSettingsBundle mGameSettingsBundle;
    private GameInfoProvider mGameInfoProvider;

    @Override
    public int getHumanColor() {
        return DEFAULT_HUMAN_COLOR;
    }

    @Override
    public int getGameFieldColor() {
        return Color.BLACK;
    }

    @Override
    public long getMsPerFrame() {
        return MS_PER_FRAME;
    }

    @Override
    public long getGameEstimatedTime() {
        return TimeUtils.secondsToMillis(mGameState.getGameSettings().getExperimentTime());
    }

    @Override
    public GameSettingsBundle getGameSettingsBundle() {
        return mGameSettingsBundle;
    }

    @Override
    public void setGameInfoProvider(GameInfoProvider gameInfoProvider) {
        mGameInfoProvider = gameInfoProvider;
    }

    @Override
    public boolean shouldGameStop(long timeDifference) {
        Logger.error("TIME DIFFERENCE " + timeDifference + " EXP TIME " + mExperimentTime);
        return timeDifference >= mExperimentTime;
    }

    @Override
    public GameSpeedContract getGameSpeed() {
        return mGameState.getCurrentGameSpeed();
    }

    @Override
    public float getInitialVelocity() {
        return initialVelocity;
    }

    @Override
    public void setCanvasSize(CanvasSize canvasSize) {
        mCanvasSize = canvasSize;
    }

    @Override
    public SquatGameState getGameState() {
        return mGameState;
    }

    @Override
    public float getPaddingTopBottomCoefficient() {
        return PADDING_TOP_BOTTOM_COEFFICIENT;
    }

    @Override
    public float getPaddingLeftRightCoefficient() {
        return PADDING_LEFT_RIGHT_COEFFICIENT;
    }

    @Override
    public void setState(SquatGameState gameState) {
        mGameState = gameState;
        this.mExperimentTime = TimeUtils.secondsToMillis(mGameState.getGameSettings().getExperimentTime());
        initSettingsBundle();
    }

    @Override
    public void onFrameTimeElapsed(long timePerFrame) {
    }

    private void initSettingsBundle() {
        PASettingsEntity settings = mGameState.getGameSettings();
        double timePerIterationS = settings.getExperimentTime() / settings.getRepsCount();
        mGameSettingsBundle = new GameSettingsBundle();
        Logger.error("TIME PER ITERATION " + timePerIterationS);
        mGameSettingsBundle.putSettingsValue(SETTINGS_TIME_IN_MS_PER_ITERATION, TimeUtils.secondsToMillis((long) Math.ceil(timePerIterationS)));
    }

}
