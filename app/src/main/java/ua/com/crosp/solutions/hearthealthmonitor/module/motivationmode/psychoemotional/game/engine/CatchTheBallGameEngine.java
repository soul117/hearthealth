package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.game.engine;

import android.graphics.PointF;


import java.util.Queue;
import java.util.function.Predicate;

import io.reactivex.Single;
import ua.com.crosp.solutions.hearthealthmonitor.R;
import ua.com.crosp.solutions.hearthealthmonitor.game.engine.BaseAndroidGameThread;
import ua.com.crosp.solutions.hearthealthmonitor.game.engine.GameScreen;
import ua.com.crosp.solutions.hearthealthmonitor.game.engine.GameThread;
import ua.com.crosp.solutions.hearthealthmonitor.game.graphics.contract.GraphicsRenderer;
import ua.com.crosp.solutions.hearthealthmonitor.game.infrastructure.input.Input;
import ua.com.crosp.solutions.hearthealthmonitor.game.infrastructure.input.handler.KeyboardInputHandler;
import ua.com.crosp.solutions.hearthealthmonitor.game.infrastructure.input.handler.TouchInputHandler;
import ua.com.crosp.solutions.hearthealthmonitor.game.valueobject.GameUpdateStateBundle;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.threading.ThreadExecutionContext;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.mapper.JsonMapper;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util.MathUtils;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util.TimeUtils;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.entity.GameResultEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.contract.game.CatchTheBallGameEngineContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.contract.game.CatchTheBallGameStrategyContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.contract.game.GameFallingBall;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.contract.view.CatchTheBallViewContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.domain.PESettingsEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.game.model.BallBasket;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.game.model.valueobject.CanvasSize;

/**
 * Created by Alexander Molochko on 2/18/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class CatchTheBallGameEngine implements CatchTheBallGameEngineContract, CatchTheBallGameStrategyContract.GameInfoProvider {
    //region Constants
    public static final String MAIN_THREAD_CONTEXT = "thread_context.main_thread";
    public static final int TOUCH_LEFT_BASKET = -11;
    public static final int TOUCH_RIGHT_BASKET = 11;
    public static final float CIRCLE_RADIUS_COEFFICIENT = 15.0f;
    public static final float BASKET_WIDTH_COEFFICIENT = 5.0f;
    public static final float BASKET_HEIGHT_COEFFICIENT = 7.0f;
    public static final float PADDING_BOTTOM_COEFFICIENT = 2.0f;
    public static final float PADDING_SIDE_COEFFICIENT = 2.0f;
    public static final double MS_PER_FRAME = 60;
    private static final long TIME_DEVIATION_PERCENTAGE = 15;
    //endregion

    // Dependencies
    private CatchTheBallViewContract mCatchTheBallGameView;
    private TouchInputHandler mTouchInputHandler;
    private KeyboardInputHandler mKeyboardInputHandler;
    private ThreadExecutionContext mMainThreadExecutionContext;
    private CatchTheBallGameStrategyContract mGameStrategy;
    private GameUpdateStateBundle mGameUpdateStateBundle;
    // Variables
    private GameFallingBall mFallingBall;
    private BallBasket mLeftBasket, mRightBasket;
    private GameThread mGameThread;
    private GameScreen mGameScreen = new GameScreen();
    private long mGameTimePassed;
    private JsonMapper mJsonMapper;

    private CatchTheBallGameEngine(ThreadExecutionContext executionContext, JsonMapper jsonMapper) {
        mJsonMapper = jsonMapper;
        //Assert.assertNotNull(executionContext);
        mMainThreadExecutionContext = executionContext;
    }

    private Predicate<Input.TouchEvent> mTouchEventPredicate = new Predicate<Input.TouchEvent>() {
        @Override
        public boolean test(Input.TouchEvent touchEvent) {
            boolean isClickOnBasket = false;
            if (touchEvent.type == Input.TouchEvent.TOUCH_DOWN) {

                if (mLeftBasket.isPointWithin(mGameScreen.convertXRealToAbstract(touchEvent.x), mGameScreen.convertYRealToAbstract(touchEvent.y))) {
                    touchEvent.additionalType = TOUCH_LEFT_BASKET;
                    isClickOnBasket = true;
                } else if (mRightBasket.isPointWithin(mGameScreen.convertXRealToAbstract(touchEvent.x), mGameScreen.convertYRealToAbstract(touchEvent.y))) {
                    isClickOnBasket = true;
                    touchEvent.additionalType = TOUCH_RIGHT_BASKET;
                }
            }
            return isClickOnBasket;
        }
    };


    private Runnable mGameThreadJoinRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                mGameThread.join();
                mCatchTheBallGameView.onGameStopped();
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
        CanvasSize canvasSize = mCatchTheBallGameView.getCanvasSize();
        mGameScreen.updateFromCanvasSize(canvasSize);
        initConfiguration(mGameScreen);
        createLeftBasket(mGameScreen);
        createRightBasket(mGameScreen);
        mFallingBall = mGameStrategy.getBallInInitialPosition(mGameScreen);
    }

    private PointF determineBasketSize(GameScreen gameScreen) {
        int width = (int) gameScreen.getRealScreenSize().getWidth();
        float basketWidth = width / BASKET_WIDTH_COEFFICIENT;
        float basketHeight = width / BASKET_HEIGHT_COEFFICIENT;
        return new PointF(basketWidth, basketHeight);
    }

    //region GameField initialization method
    private void createLeftBasket(GameScreen gameScreen) {
        PointF basketSize = determineBasketSize(gameScreen);
        float paddingFromBottom = basketSize.y / PADDING_BOTTOM_COEFFICIENT;
        float paddingFromLeft = basketSize.x / PADDING_SIDE_COEFFICIENT;
        float basketLeft = paddingFromLeft;
        float basketRight = basketLeft + basketSize.x;
        float basketBottom = gameScreen.getHeight() - paddingFromBottom;
        float basketTop = basketBottom - basketSize.y;
        mLeftBasket = new BallBasket(basketLeft, gameScreen.convertYPointToRealPosition(basketTop), basketRight, gameScreen.convertYPointToRealPosition(basketBottom));
        mLeftBasket.setColor(mGameStrategy.getLeftBasketColor());
    }

    private void createRightBasket(GameScreen gameScreen) {
        PointF basketSize = determineBasketSize(gameScreen);
        float paddingFromBottom = basketSize.y / PADDING_BOTTOM_COEFFICIENT;
        float paddingFromRight = basketSize.x / PADDING_SIDE_COEFFICIENT;
        float basketRight = gameScreen.getWidth() - paddingFromRight;
        float basketLeft = basketRight - basketSize.x;
        float basketBottom = gameScreen.getHeight() - paddingFromBottom;
        float basketTop = basketBottom - basketSize.y;
        mRightBasket = new BallBasket(basketLeft, basketTop, basketRight, basketBottom);
        mRightBasket.setColor(mGameStrategy.getRightBasketColor());
    }
    //endregion

    @Override
    public void updateGameState(long gameTime, long timeElapsed) {
        CanvasSize canvasSize = mCatchTheBallGameView.getCanvasSize();
        mGameScreen.updateFromCanvasSize(canvasSize);
        detectBallCollisions(mGameScreen);
        mGameScreen.updateFromCanvasSize(canvasSize);
        mGameUpdateStateBundle.setTimeElapsed(timeElapsed);
        mGameUpdateStateBundle.setGameTime(gameTime);
        mFallingBall.updateState(mGameUpdateStateBundle, null);
    }

    @Override
    public void drawGame() {
        GraphicsRenderer graphicsRenderer = mCatchTheBallGameView.prepareAndGetGraphicsRenderer();
        graphicsRenderer.setCoordinatesConverter(mGameScreen);
        graphicsRenderer.clear(mGameStrategy.getGameFieldColor());
        mFallingBall.draw(graphicsRenderer);
        mLeftBasket.draw(graphicsRenderer);
        mRightBasket.draw(graphicsRenderer);
        mCatchTheBallGameView.onDrawingFinished();
    }

    @Override
    public void initConfiguration(GameScreen gameScreen) {
        mGameStrategy.setGameInfoProvider(this);
        mGameUpdateStateBundle = new GameUpdateStateBundle();
        mGameUpdateStateBundle.setGameScreen(mGameScreen);
        mGameUpdateStateBundle.setGameSpeed(mGameStrategy.getGameSpeed());
    }

    @Override
    public Single<GameResultEntity> stopExperimentAndGetResults() {
        stopGame();
        return Single.just(collectGameResults());

    }

    @Override
    public void processGameInput() {
        Queue<Input.TouchEvent> touchEvents = mTouchInputHandler.handleTouchEventsByPredicate(mTouchEventPredicate);
        for (int i = 0; i < touchEvents.size(); i++) {
            Input.TouchEvent touchEvent = touchEvents.remove();
            handleBasketTouchEvent(touchEvent);
        }
    }

    //region Game state methods
    @Override
    public void startGame() {
        mGameThread = new CatchTheBallGameThread();
        mCatchTheBallGameView.onGameStarted();
        mGameThread.shouldStart();
        mGameThread.start();
    }


    @Override
    public void pauseGame() {
        if (mGameThread != null) {
            mGameThread.shouldPause();
        }
    }

    @Override
    public void resumeGame() {
        if (mGameThread != null) {
            mGameThread.shouldResume();
        }
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

    @Override
    public GameScreen provideCurrentGameScreen() {
        return mGameScreen;
    }

    //endregion


    private class CatchTheBallGameThread extends BaseAndroidGameThread {
        public static final int PAUSE_SLEEP_TIME = 10;
        long totalElapsed = 0;
        long previousTime = TimeUtils.getCurrentTimeMillis();
        long dt = 5;
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

    private void checkIfGameShouldStop(long gameTimePassed) {
        mGameTimePassed = gameTimePassed;
        if (mGameStrategy.shouldGameStop(gameTimePassed)) {
            mCatchTheBallGameView.stopGameAndSendResults();
        }
    }

    protected GameResultEntity collectGameResults() {
        GameResultEntity gameResultEntity = new GameResultEntity();
        gameResultEntity.setGameEstimatedTime(mGameStrategy.getGameEstimatedTime());
        gameResultEntity.setGameActualTime(mGameTimePassed);
        gameResultEntity.setWasInterrupted(MathUtils.getValueDeviationInPercentage(mGameTimePassed, mGameStrategy.getGameEstimatedTime()) > TIME_DEVIATION_PERCENTAGE);
        gameResultEntity.setGameName(mCatchTheBallGameView.getStringByResourceId(R.string.game_name_catch_the_ball));
        gameResultEntity.setGameType(mGameStrategy.getGameType());
        gameResultEntity.setGameExperimentId(mCatchTheBallGameView.getIntegerById(R.integer.mode_psychoemotional));
        gameResultEntity.setCustomJsonObject(mJsonMapper.convertToJson(mGameStrategy.getGamScore()));
        return gameResultEntity;
    }
    //================================================================================
    // Game Logic
    //================================================================================

    private void handleBasketTouchEvent(Input.TouchEvent touchEvent) {
        if (touchEvent.additionalType == TOUCH_LEFT_BASKET) {
            mGameStrategy.onLeftBasketTouched(mFallingBall, mLeftBasket);
        } else if (touchEvent.additionalType == TOUCH_RIGHT_BASKET) {
            mGameStrategy.onRightBasketTouched(mFallingBall, mRightBasket);
        }
    }

    private void detectBallCollisions(GameScreen gameScreen) {
        boolean createNewBall = false;
        if (mFallingBall.isIntersecting(mLeftBasket)) {
            mGameStrategy.onBallIntersectsLeftBasket(mFallingBall, mLeftBasket);
            createNewBall = true;
        }
        if (mFallingBall.isIntersecting(mRightBasket)) {
            mGameStrategy.onBallIntersectsRightBasket(mFallingBall, mRightBasket);
            createNewBall = true;
        }
        if (mFallingBall.wentOutOfBoundaries(gameScreen)) {
            mGameStrategy.onBallWentOutOfBoundaries(mFallingBall);
            createNewBall = true;
        }
        if (createNewBall) {
            mFallingBall = createBallInInitialPosition(gameScreen);
        }
    }

    private GameFallingBall createBallInInitialPosition(GameScreen gameScreen) {
        mFallingBall = mGameStrategy.getBallInInitialPosition(gameScreen);
        return mFallingBall;
    }

    //region Getters & Setters
    public CatchTheBallViewContract getCatchTheBallGameView() {
        return mCatchTheBallGameView;
    }

    public void setGameView(CatchTheBallViewContract catchTheBallGameView) {
        mCatchTheBallGameView = catchTheBallGameView;
    }

    public TouchInputHandler getTouchInputHandler() {
        return mTouchInputHandler;
    }

    public void setTouchInputHandler(TouchInputHandler touchInputHandler) {
        mTouchInputHandler = touchInputHandler;
    }

    public KeyboardInputHandler getKeyboardInputHandler() {
        return mKeyboardInputHandler;
    }

    public void setKeyboardInputHandler(KeyboardInputHandler keyboardInputHandler) {
        mKeyboardInputHandler = keyboardInputHandler;
    }

    public CatchTheBallGameStrategyContract getGameStrategy() {
        return mGameStrategy;
    }

    public void setGameStrategy(CatchTheBallGameStrategyContract gameStrategy) {
        mGameStrategy = gameStrategy;
    }

    //endregion

    //region Inner classes

    public static class Builder {
        private CatchTheBallViewContract mCatchTheBallGameView;
        private CatchTheBallGameState mGameState;
        private TouchInputHandler mTouchInputHandler;
        private KeyboardInputHandler mKeyboardInputHandler;
        private CatchTheBallGameStrategyContract mCatchTheBallGameStrategy;
        private ThreadExecutionContext mThreadExecutionContext;
        private JsonMapper mJsonMapper;

        public Builder() {
        }

        public Builder withInitialGameStateSettings(PESettingsEntity initialGameStateSettings) {
            this.mGameState = CatchTheBallGameState.fromSettings(initialGameStateSettings);
            return this;
        }

        public Builder withView(CatchTheBallViewContract view) {
            this.mCatchTheBallGameView = view;
            return this;
        }

        public Builder withTouchInputHandler(TouchInputHandler touchInputHandler) {
            this.mTouchInputHandler = touchInputHandler;
            return this;
        }

        public Builder withJsonMapper(JsonMapper jsonMapper) {
            this.mJsonMapper = jsonMapper;
            return this;
        }

        public Builder withKeyboardInputHandler(KeyboardInputHandler keyboardInputHandler) {
            this.mKeyboardInputHandler = keyboardInputHandler;
            return this;
        }

        public Builder withGameStrategy(CatchTheBallGameStrategyContract strategy) {
            this.mCatchTheBallGameStrategy = strategy;
            return this;
        }

        public Builder withThreadExecutionContext(ThreadExecutionContext threadExecutionContext) {
            this.mThreadExecutionContext = threadExecutionContext;
            return this;
        }

        public CatchTheBallGameEngine build() {
            CatchTheBallGameEngine gameEngine = new CatchTheBallGameEngine(mThreadExecutionContext, mJsonMapper);
            this.mCatchTheBallGameStrategy.setCatchTheBallGameState(this.mGameState);
            gameEngine.setGameStrategy(this.mCatchTheBallGameStrategy);
            gameEngine.setGameView(this.mCatchTheBallGameView);
            gameEngine.setKeyboardInputHandler(this.mKeyboardInputHandler);
            gameEngine.setTouchInputHandler(this.mTouchInputHandler);
            return gameEngine;
        }


    }
    //endregion
}
