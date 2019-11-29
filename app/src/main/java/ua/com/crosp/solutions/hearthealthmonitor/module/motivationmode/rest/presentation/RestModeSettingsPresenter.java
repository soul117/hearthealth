package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.presentation;

import javax.inject.Inject;

import io.reactivex.functions.BiConsumer;
import ua.com.crosp.solutions.hearthealthmonitor.base.presentation.BaseAppPresenter;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.cotntract.RestModeRouterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.cotntract.domain.RestSettingsUseCaseContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.cotntract.presenter.RestModeSettingsPresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.cotntract.view.RestModeSettingsViewContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.data.RestSettingsEntity;

/**
 * Created by Alexander Molochko on 4/21/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class RestModeSettingsPresenter extends BaseAppPresenter<RestModeSettingsViewContract, RestModeRouterContract> implements RestModeSettingsPresenterContract {
    private RestModeSettingsViewContract mRestModeSettingsView;
    private RestSettingsUseCaseContract mSettingsUseCase;
    // Component & modular dependencies

    @Inject
    public RestModeSettingsPresenter(RestModeRouterContract router, RestSettingsUseCaseContract settingsUseCase) {
        mSettingsUseCase = settingsUseCase;
        setRouter(router);
    }

    @Override
    public void setView(RestModeSettingsViewContract view) {
        this.mRestModeSettingsView = view;
    }

    @Override
    public void onViewReady() {
        mSettingsUseCase.getRestModeSettings().subscribe(new BiConsumer<RestSettingsEntity, Throwable>() {
            @Override
            public void accept(RestSettingsEntity restSettingsEntity, Throwable throwable) throws Exception {
                mRestModeSettingsView.displayRestModeSettings(restSettingsEntity);
            }
        });
    }

    @Override
    public void onViewDestroyed() {
        mSettingsUseCase = null;
        mRestModeSettingsView = null;
    }

    @Override
    public void onBackButtonPress() {
        getRouter().navigateBack();
    }

    @Override
    public void startExperiment() {
        getRouter().startRestModeExperiment();
    }

    @Override
    public void saveSettings(RestSettingsEntity settings) {
        mSettingsUseCase.saveRestModeSettings(settings);
    }
}
