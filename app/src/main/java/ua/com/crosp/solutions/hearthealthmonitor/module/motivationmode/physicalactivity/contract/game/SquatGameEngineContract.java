package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.contract.game;

import io.reactivex.Single;
import ua.com.crosp.solutions.hearthealthmonitor.game.engine.GameScreen;
import ua.com.crosp.solutions.hearthealthmonitor.game.infrastructure.input.handler.KeyboardInputHandler;
import ua.com.crosp.solutions.hearthealthmonitor.game.infrastructure.input.handler.TouchInputHandler;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.entity.GameResultEntity;

/**
 * Created by Alexander Molochko on 9/3/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public interface SquatGameEngineContract {

    TouchInputHandler provideTouchInputHandler();

    KeyboardInputHandler provideKeyboardInputHandler();

    void initGameObjects();


    void updateGameState(long gameTime, long timeElapsed);

    void drawGame();

    void initConfiguration(GameScreen canvasSize);

    Single<GameResultEntity> stopExperimentAndGetResults();

    void processGameInput();

    void startGame();

    void pauseGame();

    void resumeGame();

    void stopGame();

    boolean isRunning();

    public interface GameInfoProvider {
        public GameScreen provideCurrentGameScreen();
    }
}
