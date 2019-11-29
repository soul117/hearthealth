package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.diassembly.module;

import dagger.Module;
import dagger.Provides;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerModule;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.ExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.PostExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.usecase.SaveExperimentResultForCurrentUserUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.cotntract.RestModeRouterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.cotntract.data.RestSettingsRepositoryContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.cotntract.domain.RestMainUseCaseContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.cotntract.presenter.RestModeMainPresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.domain.RestModeMainUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.presentation.RestModeMainPresenter;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.cotntract.UserSessionManagerContract;

/**
 * Created by Alexander Molochko on 5/26/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
@Module
@PerModule
public class RestModeExperimentModule {
    public RestModeExperimentModule() {
    }

    @Provides
    @PerModule
    RestModeMainPresenterContract provideRestModeMainPresenter(RestModeRouterContract router, RestMainUseCaseContract useCaseContract, UserSessionManagerContract userSessionManager, SaveExperimentResultForCurrentUserUseCase experimentResultForCurrentUserUseCase) {
        return new RestModeMainPresenter(router, userSessionManager, useCaseContract, experimentResultForCurrentUserUseCase);
    }

    @Provides
    @PerModule
    RestMainUseCaseContract provideRestMainUseCase(RestSettingsRepositoryContract repositoryContract, ExecutionThread executionThread, PostExecutionThread postExecutionThread) {
        return new RestModeMainUseCase(executionThread, postExecutionThread, repositoryContract);
    }


}
