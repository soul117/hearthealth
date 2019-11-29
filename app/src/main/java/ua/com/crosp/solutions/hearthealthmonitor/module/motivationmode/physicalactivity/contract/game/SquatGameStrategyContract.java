package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.contract.game;

import androidx.annotation.ColorInt;

import ua.com.crosp.solutions.hearthealthmonitor.game.engine.GameScreen;
import ua.com.crosp.solutions.hearthealthmonitor.game.engine.GameSpeedContract;
import ua.com.crosp.solutions.hearthealthmonitor.game.valueobject.GameSettingsBundle;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.domain.game.engine.SquatGameState;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.game.model.valueobject.CanvasSize;

/**
 * Created by Alexander Molochko on 8/24/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public interface SquatGameStrategyContract {
    public static final String SETTINGS_TIME_IN_MS_PER_ITERATION = "squat_game.time_per_iteration";

    @ColorInt
    int getHumanColor();


    public void setCanvasSize(CanvasSize canvasSize);

    SquatGameState getGameState();

    float getPaddingTopBottomCoefficient();

    float getPaddingLeftRightCoefficient();

    void setState(SquatGameState catchTheBallGameState);

    void onFrameTimeElapsed(long timePerFrame);

    //region Basket events
    boolean shouldGameStop(long timeDifference);

    GameSpeedContract getGameSpeed();

    float getInitialVelocity();

    @ColorInt
    int getGameFieldColor();

    long getMsPerFrame();

    long getGameEstimatedTime();

    //endregion
    GameSettingsBundle getGameSettingsBundle();

    public void setGameInfoProvider(GameInfoProvider gameInfoProvider);



    public interface GameInfoProvider {
        public GameScreen provideCurrentGameScreen();
    }
}
