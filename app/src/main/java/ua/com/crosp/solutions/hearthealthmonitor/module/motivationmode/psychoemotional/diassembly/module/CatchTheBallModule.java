package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.diassembly.module;

import android.content.Context;
import android.content.res.Resources;
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
import ua.com.crosp.solutions.hearthealthmonitor.game.infrastructure.input.handler.KeyboardInputHandler;
import ua.com.crosp.solutions.hearthealthmonitor.game.infrastructure.input.handler.SingleTouchHandler;
import ua.com.crosp.solutions.hearthealthmonitor.game.infrastructure.input.handler.TouchInputHandler;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.threading.ThreadExecutionContext;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.mapper.JsonMapper;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.threading.execution.AndroidMainThreadExecutionContext;
import ua.com.crosp.solutions.hearthealthmonitor.module.appsettings.data.AppSettingsModel;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.usecase.SaveExperimentResultForCurrentUserUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.contract.data.PESettingsRepositoryContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.contract.game.CatchTheBallGameEngineContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.contract.game.CatchTheBallGameStrategyContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.contract.presenter.CatchTheBallGamePresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.contract.routing.PERouterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.contract.view.CatchTheBallViewContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.domain.PESettingsEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.game.engine.CatchTheBallGameEngine;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.game.engine.MotivationalModeCountdownDrawer;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.game.engine.strategy.CTBActivationAndStopIncentivesStrategy;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.game.engine.strategy.CTBActivationIncentivesStrategy;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.presentation.CatchTheBallGamePresenter;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.view.CatchTheBallGameView;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.cotntract.UserSessionManagerContract;

import static ua.com.crosp.solutions.hearthealthmonitor.di.component.activity.module.ActivityModule.CONTEXT_ACTIVITY;

/**
 * Created by Alexander Molochko on 2/21/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
@Module
@PerGame
public class CatchTheBallModule implements BaseGameDIModule {

    public static final int COUNT_DOWN_TIME = 5;
    private final SurfaceListener mSurfaceListener;
    private CatchTheBallViewContract mCatchTheBallViewContract;

    public CatchTheBallModule(CatchTheBallViewContract catchTheBallViewContract, SurfaceListener surfaceListener) {
        mCatchTheBallViewContract = catchTheBallViewContract;
        mSurfaceListener = surfaceListener;
    }

    @Override
    @Provides
    @PerGame
    public TouchInputHandler provideTouchInputHandler() {
        return new SingleTouchHandler();
    }

    @Override
    @Provides
    @PerGame
    public KeyboardInputHandler provideKeyboardInputHandler() {
        return new AndroidKeyboardHandler();
    }


    @Named(CatchTheBallGameEngine.MAIN_THREAD_CONTEXT)
    @Provides
    @PerGame
    public ThreadExecutionContext provideMainThreadContext(@Named("context.activity") Context context) {
        return new AndroidMainThreadExecutionContext(context);
    }


    @Provides
    @PerGame
    public CatchTheBallGamePresenterContract provideGameViewPresenter(PERouterContract router, SaveExperimentResultForCurrentUserUseCase experimentUseCase, Resources resources, UserSessionManagerContract userSessionManager, AppSettingsModel appSettings) {
        return new CatchTheBallGamePresenter(appSettings, experimentUseCase, userSessionManager, router);
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
    public PESettingsEntity provideGameSettings(PESettingsRepositoryContract repositoryContract) {
        return repositoryContract.getSettingsSync();
    }

    @Override
    @Provides
    @PerGame
    public GraphicsRenderer provideGraphicsRenderer(@Named(CONTEXT_ACTIVITY) Context context) {
        return new AndroidGraphicsRenderer(context);
    }


    @Provides
    @PerGame
    public CatchTheBallGameStrategyContract provideGameStrategy(PESettingsEntity gameSettings) {
        CatchTheBallGameStrategyContract strategyContract;
        switch ((int) gameSettings.getGameModeId()) {
            case R.id.catch_the_balL_game_mode_activation_and_stop:
                strategyContract = new CTBActivationAndStopIncentivesStrategy();
                break;
            case R.id.catch_the_balL_game_mode_activation_only:
                strategyContract = new CTBActivationIncentivesStrategy();
                break;
            default:
                strategyContract = new CTBActivationIncentivesStrategy();
                break;
        }
        return strategyContract;
    }


    @Provides
    @PerGame
    public CatchTheBallGameEngineContract provideGameEngine(JsonMapper jsonMapper, TouchInputHandler touchInputHandler, KeyboardInputHandler keyboardInputHandler, CatchTheBallGameStrategyContract gameStrategyContract, PESettingsEntity gameSettings, @Named(CatchTheBallGameEngine.MAIN_THREAD_CONTEXT) ThreadExecutionContext threadExecutionContext) {
        return new CatchTheBallGameEngine.Builder()
                .withGameStrategy(gameStrategyContract)
                .withKeyboardInputHandler(keyboardInputHandler)
                .withJsonMapper(jsonMapper)
                .withView(mCatchTheBallViewContract)
                .withTouchInputHandler(touchInputHandler)
                .withInitialGameStateSettings(gameSettings)
                .withThreadExecutionContext(threadExecutionContext)
                .build();
    }

    @Provides
    @Override
    @PerGame
    public BaseGameView provideBaseGameView(@Named("context.activity") Context context, TouchInputHandler touchInputHandler, KeyboardInputHandler keyboardInputHandler) {
        return new CatchTheBallGameView(context, touchInputHandler, keyboardInputHandler, mSurfaceListener);
    }

}
