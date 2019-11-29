package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.presentation;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.functions.BiConsumer;
import ua.com.crosp.solutions.hearthealthmonitor.base.presentation.BaseAppPresenter;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.contract.presenter.PESettingsPresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.contract.routing.PERouterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.contract.usecase.PEGameModeUseCaseContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.contract.usecase.PESettingsUseCaseContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.contract.view.PESettingsViewContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.data.PEBallGameMode;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.domain.PESettingsEntity;

/**
 * Created by Alexander Molochko on 4/21/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class PESettingsPresenter extends BaseAppPresenter<PESettingsViewContract, PERouterContract> implements PESettingsPresenterContract {
    // Component & modular dependencies
    private final PEGameModeUseCaseContract mGameModeUseCase;
    private final PESettingsUseCaseContract mSettingsUseCase;

    @Inject
    public PESettingsPresenter(PERouterContract router,
                               PEGameModeUseCaseContract peGameModeUseCase, PESettingsUseCaseContract settingsUseCase) {
        mGameModeUseCase = peGameModeUseCase;
        mSettingsUseCase = settingsUseCase;
        setRouter(router);
    }

    @Override
    public void onViewReady() {
        mSettingsUseCase.getSettings().subscribe(new BiConsumer<PESettingsEntity, Throwable>() {
            @Override
            public void accept(PESettingsEntity peSettingsEntity, Throwable throwable) throws Exception {
                getView().displaySettings(peSettingsEntity);
            }
        });
    }

    @Override
    public void onViewDestroyed() {

    }

    @Override
    public void onBackButtonPress() {
        getRouter().navigateBack();
    }

    @Override
    public void startExperiment() {
        PESettingsEntity currentSettings = getView().getCurrentSettings();
        saveSettings(currentSettings);
        getRouter().startGame();
    }

    @Override
    public void saveSettings(PESettingsEntity settings) {
        mSettingsUseCase.saveSettings(settings);
    }

    @Override
    public Single<PEBallGameMode.List> getAvailableGameModes() {
        return mGameModeUseCase.getAvailableGameModes();
    }


}
