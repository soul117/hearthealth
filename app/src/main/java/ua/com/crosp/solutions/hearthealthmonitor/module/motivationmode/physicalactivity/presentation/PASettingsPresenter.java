package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.presentation;

import javax.inject.Inject;

import io.reactivex.functions.BiConsumer;
import ua.com.crosp.solutions.hearthealthmonitor.base.presentation.BaseAppPresenter;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.contract.domain.PASettingsUseCaseContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.contract.presenter.PASettingsPresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.contract.routing.PARouterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.contract.view.PASettingsViewContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.data.PASettingsEntity;

/**
 * Created by Alexander Molochko on 4/21/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class PASettingsPresenter extends BaseAppPresenter<PASettingsViewContract, PARouterContract> implements PASettingsPresenterContract {
    // Component & modular dependencies
    private final PASettingsUseCaseContract mSettingsUseCase;

    @Inject
    public PASettingsPresenter(PARouterContract router,
                               PASettingsUseCaseContract settingsUseCase) {
        setRouter(router);
        mSettingsUseCase = settingsUseCase;
    }

    @Override
    public void onViewReady() {
        mSettingsUseCase.getSettings().subscribe(new BiConsumer<PASettingsEntity, Throwable>() {
            @Override
            public void accept(PASettingsEntity paSettingsEntity, Throwable throwable) throws Exception {
                getView().displayModeSettings(paSettingsEntity);
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
        getRouter().startGame();
    }

    @Override
    public void saveSettings(PASettingsEntity settings) {
        mSettingsUseCase.saveSettings(settings);
    }
}
