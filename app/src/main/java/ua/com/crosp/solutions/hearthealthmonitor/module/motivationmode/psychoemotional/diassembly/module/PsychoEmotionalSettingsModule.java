package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.diassembly.module;

import dagger.Module;
import dagger.Provides;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerModule;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.ExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.PostExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.contract.data.PEGameModeRepositoryContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.contract.data.PESettingsRepositoryContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.contract.presenter.PESettingsPresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.contract.routing.PERouterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.contract.usecase.GetPESettingsUseCaseContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.contract.usecase.PEGameModeUseCaseContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.contract.usecase.PESettingsUseCaseContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.domain.GetPESettingsUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.domain.PEGameModeUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.domain.PESettingsUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.presentation.PESettingsPresenter;

/**
 * Created by Alexander Molochko on 5/26/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
@Module
@PerModule
public class PsychoEmotionalSettingsModule {
    public PsychoEmotionalSettingsModule() {
    }

    @Provides
    @PerModule
    PESettingsPresenterContract provideSettingsPresenter(PERouterContract router,
                                                         PESettingsUseCaseContract useCase, PEGameModeUseCaseContract peGameModeUseCase) {
        return new PESettingsPresenter(router, peGameModeUseCase, useCase);
    }

    @Provides
    @PerModule
    GetPESettingsUseCaseContract provideGetSettingsUseCase(PESettingsRepositoryContract gameModeRepositoryContract, ExecutionThread threadExecutor, PostExecutionThread postExecutionThread) {
        return new GetPESettingsUseCase(gameModeRepositoryContract, threadExecutor, postExecutionThread);
    }

    @Provides
    @PerModule
    PEGameModeUseCaseContract provideGameModeUseCase(PEGameModeRepositoryContract gameModeRepositoryContract) {
        return new PEGameModeUseCase(gameModeRepositoryContract);
    }

    @Provides
    @PerModule
    PESettingsUseCaseContract provideSettingsUseCase(PESettingsRepositoryContract settingsRepository, ExecutionThread executionThread, PostExecutionThread postExecutionThread) {
        return new PESettingsUseCase(executionThread, postExecutionThread, settingsRepository);
    }

}
