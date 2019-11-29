package ua.com.crosp.solutions.hearthealthmonitor.module.appsettings.presentation;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;
import ua.com.crosp.solutions.hearthealthmonitor.base.presentation.BaseAppPresenter;
import ua.com.crosp.solutions.hearthealthmonitor.module.appsettings.contract.presenter.AppSettingsPresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.appsettings.contract.routing.AppSettingsRouter;
import ua.com.crosp.solutions.hearthealthmonitor.module.appsettings.contract.view.AppSettingsViewContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.appsettings.data.AppSettingsModel;
import ua.com.crosp.solutions.hearthealthmonitor.module.appsettings.domain.GetAppSettingsUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.module.appsettings.domain.SaveAppSettingsUseCase;

/**
 * Created by Alexander Molochko on 4/21/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class AppSettingsPresenter extends BaseAppPresenter<AppSettingsViewContract, AppSettingsRouter> implements AppSettingsPresenterContract {
    // Component & modular dependencies
    private final GetAppSettingsUseCase mGetAppSettingsUseCase;
    private final SaveAppSettingsUseCase mSaveAppSettingsUseCase;

    @Inject
    public AppSettingsPresenter(AppSettingsRouter router, GetAppSettingsUseCase getAppSettingsUseCase, SaveAppSettingsUseCase saveAppSettingsUseCase) {
        mGetAppSettingsUseCase = getAppSettingsUseCase;
        mSaveAppSettingsUseCase = saveAppSettingsUseCase;
        setRouter(router);
    }

    @Override
    public void onViewReady() {
        mGetAppSettingsUseCase.execute(null)
                .subscribe(new Consumer<AppSettingsModel>() {
                    @Override
                    public void accept(AppSettingsModel appSettingsModel) throws Exception {
                        getView().displayAppSettings(appSettingsModel);
                    }
                });
    }

    @Override
    public void onViewDestroyed() {

    }

    @Override
    public void onViewStop() {

    }

    @Override
    public void onViewPause() {

    }

    @Override
    public void onViewResume() {

    }

    @Override
    public void onBackButtonPress() {
        getRouter().navigateBack();
    }

    @Override
    public void onSaveAppSettingsRequest(AppSettingsModel settings) {
        mSaveAppSettingsUseCase.execute(settings)
                .subscribe(new Consumer<Void>() {
                    @Override
                    public void accept(Void aVoid) throws Exception {
                        //  getView().showSettingsSuccessfullySavedMessage();

                    }
                });
    }
}
