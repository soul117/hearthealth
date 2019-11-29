package ua.com.crosp.solutions.hearthealthmonitor.module.appsettings.di.module;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.ExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.PostExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.presentation.BackPressNotifier;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerModule;
import ua.com.crosp.solutions.hearthealthmonitor.module.appsettings.contract.data.AppSettingsRepositoryContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.appsettings.contract.presenter.AppSettingsPresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.appsettings.contract.routing.AppSettingsRouter;
import ua.com.crosp.solutions.hearthealthmonitor.module.appsettings.domain.GetAppSettingsUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.module.appsettings.domain.SaveAppSettingsUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.module.appsettings.presentation.AppSettingsPresenter;

/**
 * Created by Alexander Molochko on 4/26/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

@Module
@PerModule
public class AppSettingsModule {
    @Provides
    @PerModule
    AppSettingsRouter provideAppSettingsRouter(Activity activity) {
        return (AppSettingsRouter) activity;
    }

    @Provides
    @PerModule
    AppSettingsPresenterContract provideAppSettingsPresenter(AppSettingsRouter router, GetAppSettingsUseCase getUseCase, SaveAppSettingsUseCase saveUseCase) {
        return new AppSettingsPresenter(router, getUseCase, saveUseCase);
    }


    @Provides
    @PerModule
    GetAppSettingsUseCase provideAppSettingsUseCase(AppSettingsRepositoryContract repo, ExecutionThread executionThread, PostExecutionThread postExecutionThread) {
        return new GetAppSettingsUseCase(repo, executionThread, postExecutionThread);
    }

    @Provides
    @PerModule
    SaveAppSettingsUseCase provideSaveAppSettingsUseCase(AppSettingsRepositoryContract repo, ExecutionThread executionThread, PostExecutionThread postExecutionThread) {
        return new SaveAppSettingsUseCase(repo, executionThread, postExecutionThread);
    }

    @Provides
    @PerModule
    BackPressNotifier provideBackPressNotifier(Activity activity) {
        return (BackPressNotifier) activity;
    }

}
