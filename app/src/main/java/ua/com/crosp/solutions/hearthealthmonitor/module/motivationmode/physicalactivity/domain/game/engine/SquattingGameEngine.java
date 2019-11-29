package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.domain.game.engine;


import io.reactivex.Single;
import ua.com.crosp.solutions.hearthealthmonitor.R;
import ua.com.crosp.solutions.hearthealthmonitor.game.engine.BaseAndroidGameThread;
import ua.com.crosp.solutions.hearthealthmonitor.game.engine.GameScreen;
import ua.com.crosp.solutions.hearthealthmonitor.game.engine.GameThread;
import ua.com.crosp.solutions.hearthealthmonitor.game.graphics.contract.GraphicsRenderer;
import ua.com.crosp.solutions.hearthealthmonitor.game.infrastructure.input.handler.KeyboardInputHandler;
import ua.com.crosp.solutions.hearthealthmonitor.game.infrastructure.input.handler.TouchInputHandler;
import ua.com.crosp.solutions.hearthealthmonitor.game.model.VectorGeometry;
import ua.com.crosp.solutions.hearthealthmonitor.game.valueobject.GameUpdateStateBundle;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.threading.ThreadExecutionContext;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.mapper.JsonMapper;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util.MathUtils;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util.TimeUtils;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.entity.GameResultEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.contract.game.SquatGameEngineContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.contract.game.SquatGameStrategyContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.data.PASettingsEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.domain.game.model.Human2d;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.contract.view.SquatGameViewContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.game.model.valueobject.CanvasSize;

/**
 * Created by Alexander Molochko on 2/18/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class SquattingGameEngine implements SquatGameEngineContract, SquatGameStrategyContract.GameInfoProvider {


    public static final int VELOCITY_X_TO_Y_COEFFICIENT = 5;
    private static final long TIME_DEVIATION_PERCENTAGE = 5;
    private SquatGameViewContract mGameView;
    TouchInputHandler mTouchInputHandler;
    KeyboardInputHandler mKeyboardInputHandler;
    private Human2d mHuman2d;
    private GameThread mGameThread;
    private ThreadExecutionContext mMainThreadExecutionContext;
    private SquatGameStrategyContract mGameStrategy;
    private GameScreen mGameScreen = new GameScreen();
    private GameUpdateStateBundle mGameUpdateStateBundle = new GameUpdateStateBundle();
    private long mGameTimePassed;

    private SquattingGameEngine(ThreadExecutionContext executionContext) {
       // AssertNotNull(executionContext);
        mMainThreadExecutionContext = executionContext;
    }

    private Runnable mGameThreadJoinRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                mGameThread.join();
                mGameView.onGameStopped();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public TouchInputHandler provideTouchInputHandler() {
        return mTouchInputHandler;
    }

    @Override
    public KeyboardInputHandler provideKeyboardInputHandler() {
        return mKeyboardInputHandler;
    }

    @Override
    public void initGameObjects() {
        CanvasSize canvasSize = mGameView.getCanvasSize();
        mGameScreen.updateFromCanvasSize(canvasSize);
        mGameStrategy.setGameInfoProvider(this);
        initConfiguration(mGameScreen);
        createHumanObject(mGameScreen);
    }

    private void createHumanObject(GameScreen gameScreen) {
        float bottomTopPadding = gameScreen.getHeight() / mGameStrategy.getPaddingTopBottomCoefficient();
        float leftRightPadding = gameScreen.getWidth() / mGameStrategy.getPaddingLeftRightCoefficient();
        mHuman2d = new Human2d(leftRightPadding, bottomTopPadding, gameScreen.getWidth() - leftRightPadding, gameScreen.getHeight() - bottomTopPadding);
        mHuman2d.setColor(mGameStrategy.getHumanColor());
        float velocity = mGameStrategy.getInitialVelocity();
        mHuman2d.setVelocityVector(new VectorGeometry.VelocityVector(0, velocity));
        mHuman2d.setLegsVelocityVector(new VectorGeometry.VelocityVector(velocity - (velocity / VELOCITY_X_TO_Y_COEFFICIENT), velocity));
    }

    @Override
    public void updateGameState(long gameTime, long timeElapsed) {
        CanvasSize canvasSize = mGameView.getCanvasSize();
        mGameScreen.updateFromCanvasSize(canvasSize);
        mGameStrategy.onFrameTimeElapsed(timeElapsed);
        mGameUpdateStateBundle.setGameTime(gameTime);
        mGameUpdateStateBundle.setGameEstimatedTime(mGameStrategy.getGameEstimatedTime());
        mGameUpdateStateBundle.setTimeElapsed(timeElapsed);
        mHuman2d.updateState(mGameUpdateStateBundle, mGameStrategy.getGameSettingsBundle());
    }

    @Override
    public void drawGame() {
        GraphicsRenderer graphicsRenderer = mGameView.prepareAndGetGraphicsRenderer();
        graphicsRenderer.clear(mGameStrategy.getGameFieldColor());
        graphicsRenderer.setCoordinatesConverter(mGameScreen);
        mHuman2d.draw(graphicsRenderer);
        mGameView.onDrawingFinished();
    }

    @Override
    public void initConfiguration(GameScreen canvasSize) {
        mGameUpdateStateBundle.setGameScreen(mGameScreen);
        mGameUpdateStateBundle.setGameSpeed(mGameStrategy.getGameSpeed());
    }

    @Override
    public Single<GameResultEntity> stopExperimentAndGetResults() {
        stopGame();
        return Single.just(collectGameResults());
    }

    protected GameResultEntity collectGameResults() {
        GameResultEntity gameResultEntity = new GameResultEntity();
        gameResultEntity.setGameEstimatedTime(mGameStrategy.getGameEstimatedTime());
        gameResultEntity.setGameActualTime(mGameTimePassed);
        gameResultEntity.setGameExperimentId(mGameView.getIntegerById(R.integer.mode_physical_activity));
        gameResultEntity.setGameName(mGameView.getStringByResourceId(R.string.game_name_squat));
        gameResultEntity.setGameType(mGameView.getStringByResourceId(R.string.game_type_squatting));
        gameResultEntity.setWasInterrupted(MathUtils.getValueDeviationInPercentage(mGameTimePassed, mGameStrategy.getGameEstimatedTime()) > TIME_DEVIATION_PERCENTAGE);
        return gameResultEntity;
    }

    @Override
    public void processGameInput() {

    }

    @Override
    public void startGame() {
        mGameThread = new SquattingGameThread();
        mGameView.onGameStarted();
        mGameThread.shouldStart();
        mGameThread.start();
    }


    @Override
    public void pauseGame() {
        mGameThread.shouldPause();
    }

    @Override
    public void resumeGame() {
        mGameThread.shouldResume();
    }

    @Override
    public void stopGame() {
        if (mGameThread != null) {
            mGameThread.shouldStop();
            mMainThreadExecutionContext.executeInThreadContext(mGameThreadJoinRunnable);
        }
    }

    @Override
    public boolean isRunning() {
        return mGameThread != null && mGameThread.isRunning();
    }

    public void setTouchInputHandler(TouchInputHandler touchInputHandler) {
        mTouchInputHandler = touchInputHandler;
    }

    public void setKeyboardInputHandler(KeyboardInputHandler keyboardInputHandler) {
        mKeyboardInputHandler = keyboardInputHandler;
    }

    public void setGameView(SquatGameViewContract gameView) {
        mGameView = gameView;
    }

    public void setGameStrategy(SquatGameStrategyContract gameStrategy) {
        mGameStrategy = gameStrategy;
    }

    @Override
    public GameScreen provideCurrentGameScreen() {
        return mGameScreen;
    }


    private class SquattingGameThread extends BaseAndroidGameThread {
        public static final int PAUSE_SLEEP_TIME = 10;
        long totalElapsed = 0;
        long previousTime = TimeUtils.getCurrentTimeMillis();
        long dt = 20;
        long accumulator = 0;

        @Override
        public void run() {
            while (mIsRunning) {
                // Pause game
                while (mIsPaused) {
                    try {
                        Thread.sleep(PAUSE_SLEEP_TIME);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                long currentTime = TimeUtils.getCurrentTimeMillis();
                long frameTime = currentTime - previousTime;
                previousTime = currentTime;
                totalElapsed += frameTime;
                accumulator += frameTime;
                while (accumulator >= dt) {
                    processGameInput();
                    updateGameState(totalElapsed, dt);
                    accumulator -= dt;
                }

                drawGame();
                checkIfGameShouldStop(totalElapsed);
            }
            shutdownGracefully();
        }

        private void shutdownGracefully() {
        }
    }

    private void checkIfGameShouldStop(long currentGameTime) {
        mGameTimePassed = currentGameTime;
        if (mGameStrategy.shouldGameStop(currentGameTime)) {
            mGameView.stopGameAndSendResults();
        }
    }

    //region Inner classes

    public static class Builder {
        private SquatGameViewContract mGameView;
        private SquatGameState mGameState;
        private TouchInputHandler mTouchInputHandler;
        private KeyboardInputHandler mKeyboardInputHandler;
        private SquatGameStrategyContract mGameStrategy;
        private ThreadExecutionContext mThreadExecutionContext;
        private JsonMapper mJsonMapper;

        public Builder() {
        }

        public SquattingGameEngine.Builder withInitialGameStateSettings(PASettingsEntity initialGameStateSettings) {
            this.mGameState = SquatGameState.fromSettings(initialGameStateSettings);
            return this;
        }

        public SquattingGameEngine.Builder withView(SquatGameViewContract view) {
            this.mGameView = view;
            return this;
        }

        public SquattingGameEngine.Builder withTouchInputHandler(TouchInputHandler touchInputHandler) {
            this.mTouchInputHandler = touchInputHandler;
            return this;
        }

        public SquattingGameEngine.Builder withKeyboardInputHandler(KeyboardInputHandler keyboardInputHandler) {
            this.mKeyboardInputHandler = keyboardInputHandler;
            return this;
        }

        public SquattingGameEngine.Builder withGameStrategy(SquatGameStrategyContract strategy) {
            this.mGameStrategy = strategy;
            return this;
        }

        public SquattingGameEngine.Builder withThreadExecutionContext(ThreadExecutionContext threadExecutionContext) {
            this.mThreadExecutionContext = threadExecutionContext;
            return this;
        }

        public SquattingGameEngine build() {
            SquattingGameEngine gameEngine = new SquattingGameEngine(mThreadExecutionContext);
            this.mGameStrategy.setState(this.mGameState);
            gameEngine.setGameStrategy(this.mGameStrategy);
            gameEngine.setGameView(this.mGameView);
            gameEngine.setKeyboardInputHandler(this.mKeyboardInputHandler);
            gameEngine.setTouchInputHandler(this.mTouchInputHandler);
            return gameEngine;
        }


    }
    //endregion

}
