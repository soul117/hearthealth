package ua.com.crosp.solutions.hearthealthmonitor.game.engine;

import ua.com.crosp.solutions.hearthealthmonitor.game.infrastructure.input.handler.KeyboardInputHandler;
import ua.com.crosp.solutions.hearthealthmonitor.game.infrastructure.input.handler.TouchInputHandler;

public interface GameEngineController {
    TouchInputHandler provideTouchInputHandler();

    KeyboardInputHandler provideKeyboardInputHandler();

    void initGameObjects();

    void updateGameState(long gameTime, long timeElapsed);

    void drawGame();

    void processGameInput();

    void startGame();

    void pauseGame();

    void resumeGame();

    void stopGame();

    boolean isRunning();
}