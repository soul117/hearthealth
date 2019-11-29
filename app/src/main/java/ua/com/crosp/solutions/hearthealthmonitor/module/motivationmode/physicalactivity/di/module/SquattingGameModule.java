package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.di.module;

import android.content.Context;
import android.graphics.Color;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import ua.com.crosp.solutions.hearthealthmonitor.R;
import ua.com.crosp.solutions.hearthealthmonitor.di.component.game.module.BaseGameDIModule;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerGame;
import ua.com.crosp.solutions.hearthealthmonitor.game.engine.CountdownGameDrawer;
import ua.com.crosp.solutions.hearthealthmonitor.game.graphics.AndroidGraphicsRenderer;
import ua.com.crosp.solutions.hearthealthmonitor.game.graphics.BaseGameView;
import ua.com.crosp.solutions.hearthealthmonitor.game.graphics.contract.GraphicsRenderer;
import ua.com.crosp.solutions.hearthealthmonitor.game.graphics.contract.SurfaceListener;
import ua.com.crosp.solutions.hearthealthmonitor.game.infrastructure.input.handler.AndroidKeyboardHandler;
import ua.com.crosp.solutions.hearthealthmonitor.game.infrastructure.input.handler.IgnoreTouchHandler;
import ua.com.crosp.solutions.hearthealthmonitor.game.infrastructure.input.handler.KeyboardInputHandler;
import ua.com.crosp.solutions.hearthealthmonitor.game.infrastructure.input.handler.TouchInputHandler;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.threading.ThreadExecutionContext;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.threading.execution.AndroidMainThreadExecutionContext;
import ua.com.crosp.solutions.hearthealthmonitor.module.appsettings.data.AppSettingsModel;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.usecase.SaveExperimentResultForCurrentUserUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.contract.data.PASettingsRepositoryContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.contract.game.SquatGameEngineContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.contract.game.SquatGameStrategyContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.contract.presenter.SquatGamePresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.contract.routing.PARouterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.data.PASettingsEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.domain.game.engine.SquattingGameEngine;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.domain.game.strategy.SquatTheGameDefaultStrategy;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.presentation.SquatGamePresenter;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.view.SquattingGameView;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.contract.view.SquatGameViewContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.game.engine.CatchTheBallGameEngine;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.game.engine.MotivationalModeCountdownDrawer;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.cotntract.UserSessionManagerContract;

import static ua.com.crosp.solutions.hearthealthmonitor.di.component.activity.module.ActivityModule.CONTEXT_ACTIVITY;


/**
 * Created by Alexander Molochko on 2/21/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
@Module
@PerGame
public class SquattingGameModule implements BaseGameDIModule {

    public static final int COUNT_DOWN_TIME = 5;

    private final SurfaceListener mSurfaceListener;
    private SquatGameViewContract mGameView;

    public SquattingGameModule(SquatGameViewContract gameViewContract, SurfaceListener surfaceListener) {
        mGameView = gameViewContract;
        mSurfaceListener = surfaceListener;
    }

    @Named(CatchTheBallGameEngine.MAIN_THREAD_CONTEXT)
    @Provides
    @PerGame
    public ThreadExecutionContext provideMainThreadContext(@Named("context.activity") Context context) {
        return new AndroidMainThreadExecutionContext(context);
    }


    @Override
    @Provides
    @PerGame
    public TouchInputHandler provideTouchInputHandler() {
        return new IgnoreTouchHandler();
    }

    @Override
    @Provides
    @PerGame
    public KeyboardInputHandler provideKeyboardInputHandler() {
        return new AndroidKeyboardHandler();
    }

    @Provides
    @PerGame
    public PASettingsEntity provideGameSettings(PASettingsRepositoryContract repositoryContract) {
        return repositoryContract.getSettingsSync();
    }

    @Provides
    @PerGame
    public CountdownGameDrawer.Params provideCountdownParams() {
        return new CountdownGameDrawer.Params(Color.GRAY, Color.BLUE, 50, COUNT_DOWN_TIME);
    }

    @Provides
    @PerGame
    public CountdownGameDrawer provideCountdownGameDrawer(CountdownGameDrawer.Params params, GraphicsRenderer graphicsRenderer) {
        return new MotivationalModeCountdownDrawer(params, graphicsRenderer);
    }

    @Provides
    @PerGame
    public SquatGameStrategyContract provideSquatGameStrategy(PASettingsEntity gameSettings) {
        SquatGameStrategyContract strategyContract;
        switch (gameSettings.getGameModeId()) {
            case R.id.catch_the_balL_game_mode_activation_and_stop:
                strategyContract = new SquatTheGameDefaultStrategy();
                break;
            default:
                strategyContract = new SquatTheGameDefaultStrategy();
                break;
        }
        return strategyContract;
    }

    @Provides
    @PerGame
    public SquatGamePresenterContract provideGameViewPresenter(PARouterContract router, SaveExperimentResultForCurrentUserUseCase saveUseCase, UserSessionManagerContract sessionManager, AppSettingsModel appSettings) {
        return new SquatGamePresenter(appSettings,router, sessionManager, saveUseCase);
    }

    @Override
    @Provides
    @PerGame
    public GraphicsRenderer provideGraphicsRenderer(@Named(CONTEXT_ACTIVITY) Context context) {
        return new AndroidGraphicsRenderer(context);
    }

    @Provides
    @PerGame
    public SquatGameEngineContract provideGmaeEngine(TouchInputHandler touchInputHandler, KeyboardInputHandler keyboardInputHandler, @Named(CatchTheBallGameEngine.MAIN_THREAD_CONTEXT) ThreadExecutionContext threadExecutionContext, SquatGameStrategyContract gameStrategyContract, PASettingsEntity gameSettings) {
        return new SquattingGameEngine.Builder()
                .withGameStrategy(gameStrategyContract)
                .withKeyboardInputHandler(keyboardInputHandler)
                .withView(mGameView)
                .withTouchInputHandler(touchInputHandler)
                .withInitialGameStateSettings(gameSettings)
                .withThreadExecutionContext(threadExecutionContext)
                .build();
    }

    @Provides
    @Override
    @PerGame
    public BaseGameView provideBaseGameView(@Named("context.activity") Context context, TouchInputHandler touchInputHandler, KeyboardInputHandler keyboardInputHandler) {
        return new SquattingGameView(context, touchInputHandler, keyboardInputHandler, mSurfaceListener);
    }


}
