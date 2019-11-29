package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.contract.game;

import androidx.annotation.ColorInt;

import ua.com.crosp.solutions.hearthealthmonitor.game.engine.GameScreen;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.game.engine.CTBGameSpeedManager;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.game.engine.CatchTheBallGameState;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.game.model.BallBasket;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.game.model.valueobject.CTBGameScore;

/**
 * Created by Alexander Molochko on 8/24/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public interface CatchTheBallGameStrategyContract {

    @ColorInt
    int getLeftBasketColor();

    @ColorInt
    int getRightBasketColor();

    @ColorInt
    int getGameFieldColor();

    GameFallingBall getBallInInitialPosition(GameScreen gameScreen);


    public int getNextBallColor();

    CatchTheBallGameState getGameState();

    //region Ball events

    void setCatchTheBallGameState(CatchTheBallGameState catchTheBallGameState);

    void onBallIntersectsLeftBasket(GameFallingBall ball, BallBasket ballBasket);

    void onBallIntersectsRightBasket(GameFallingBall ball, BallBasket ballBasket);

    void onBallWentOutOfBoundaries(GameFallingBall ball);

    //endregion

    //region Basket events

    void onLeftBasketTouched(GameFallingBall ball, BallBasket ballBasket);

    void onRightBasketTouched(GameFallingBall ball, BallBasket ballBasket);

    boolean shouldGameStop(long timeDifference);

    CTBGameSpeedManager.GameSpeed getGameSpeed();

    public void setGameInfoProvider(GameInfoProvider gameInfoProvider);

    long getGameEstimatedTime();

    CTBGameScore getGamScore();

    String getGameType();

    //endregion

    public interface GameInfoProvider {
        public GameScreen provideCurrentGameScreen();
    }
}
