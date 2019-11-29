package ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.di.module;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.ExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.PostExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerModule;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.filesystem.FileManager;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.usecase.GetExperimentResultByIdUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.EcgResultRouterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.data.ExperimentResultsRepositoryContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.presenter.EcgChartResultPresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.domain.ReadRawEcgDataUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.presentation.presenter.EcgResultChartPresenter;

/**
 * Created by Alexander Molochko on 2/9/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
@Module
@PerModule
public class EcgResultModule {

    @Provides
    @PerModule
    EcgChartResultPresenterContract provideEcgResultPresenter(GetExperimentResultByIdUseCase experimentUseCase, ReadRawEcgDataUseCase wavFileUseCase, EcgResultRouterContract router) {
        return new EcgResultChartPresenter(router, experimentUseCase, wavFileUseCase);
    }

    @Provides
    @PerModule
    EcgResultRouterContract provideEcgResultRouter(Activity activity) {
        return (EcgResultRouterContract) activity;
    }

    @Provides
    @PerModule
    ReadRawEcgDataUseCase provideReadRawEcgDataUseCase(ExecutionThread executionThread, PostExecutionThread postExecution, FileManager fileManager) {
        return new ReadRawEcgDataUseCase(executionThread, postExecution, fileManager);
    }

    @Provides
    @PerModule
    GetExperimentResultByIdUseCase provideGetExperimentResultByIdUseCase(ExperimentResultsRepositoryContract repository, ExecutionThread executionThread, PostExecutionThread postExecutionThread) {
        return new GetExperimentResultByIdUseCase(repository, executionThread, postExecutionThread);
    }
}
