package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.diassembly.module;

import dagger.Module;
import dagger.Provides;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerModule;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.ExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.PostExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.cotntract.RestModeRouterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.cotntract.data.RestSettingsRepositoryContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.cotntract.domain.RestSettingsUseCaseContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.cotntract.presenter.RestModeSettingsPresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.domain.RestModeSettingsUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.presentation.RestModeSettingsPresenter;

/**
 * Created by Alexander Molochko on 5/26/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
@Module
@PerModule
public class RestModeSettingsModule {
    public RestModeSettingsModule() {
    }


    @Provides
    @PerModule
    RestSettingsUseCaseContract provideRestModeSettingsUseCase(RestSettingsRepositoryContract repositoryContract, ExecutionThread threadExecutor, PostExecutionThread postExecutionThread) {
        return new RestModeSettingsUseCase(repositoryContract, threadExecutor, postExecutionThread);
    }

    @Provides
    @PerModule
    RestModeSettingsPresenterContract provideRestModeMainPresenter(RestModeRouterContract router, RestSettingsUseCaseContract useCaseContract) {
        return new RestModeSettingsPresenter(router, useCaseContract);
    }


}
