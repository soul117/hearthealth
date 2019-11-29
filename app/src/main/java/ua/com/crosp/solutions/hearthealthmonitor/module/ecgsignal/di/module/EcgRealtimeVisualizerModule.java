package ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.di.module;

import android.app.Activity;
import android.content.Context;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.ExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.PostExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.presentation.BackPressNotifier;
import ua.com.crosp.solutions.hearthealthmonitor.di.named.NamedConstants;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerModule;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.filesystem.FileManager;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.threading.execution.AndroidMainExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.contract.EcgRealtimeRouterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.contract.presentation.EcgRealtimeRecordingPresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.contract.service.EcgServiceProxyContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.presentation.presenter.EcgRealTimeRecordingPresenter;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.service.EcgServiceProxy;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.usecase.GetExperimentResultByIdUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.usecase.SaveExperimentResultForCurrentUserUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.data.ExperimentResultsRepositoryContract;

import static ua.com.crosp.solutions.hearthealthmonitor.di.named.NamedConstants.Context.APPLICATION_CONTEXT;

/**
 * Created by Alexander Molochko on 2/9/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
@Module
@PerModule
public class EcgRealtimeVisualizerModule {

    @Provides
    @PerModule
    EcgRealtimeRecordingPresenterContract provideEcgRealTimeVisualizerPresenter(EcgRealtimeRouterContract router, GetExperimentResultByIdUseCase getExperimentUseCase, SaveExperimentResultForCurrentUserUseCase saveExperimentResultForCurrentUserUseCase, FileManager fileManager, EcgServiceProxyContract ecgProxy, ScheduledExecutorService scheduledExecutorService, @Named(NamedConstants.Threading.ANDROID_MAIN_THREAD) ExecutionThread mainExecutionThread) {
        return new EcgRealTimeRecordingPresenter(router, getExperimentUseCase, saveExperimentResultForCurrentUserUseCase, ecgProxy, mainExecutionThread, scheduledExecutorService, fileManager);
    }

    @Provides
    @PerModule
    EcgRealtimeRouterContract provideEcgRealtimeRouter(Activity activity) {
        return (EcgRealtimeRouterContract) activity;
    }

    @Provides
    @PerModule
    ScheduledExecutorService provideScheduledExecutorService() {
        return Executors.newSingleThreadScheduledExecutor();
    }

    @Provides
    @PerModule
    EcgServiceProxyContract provideEcgServiceHandler(@Named(APPLICATION_CONTEXT) Context context) {
        return new EcgServiceProxy(context);
    }

    @Provides
    @PerModule
    BackPressNotifier provideBackPressNotifier(Activity activity) {
        return (BackPressNotifier) activity;
    }

    @Named(NamedConstants.Threading.ANDROID_MAIN_THREAD)
    @Provides
    @PerModule
    ExecutionThread provideMainExecutionThread(@Named(APPLICATION_CONTEXT) Context context) {
        return new AndroidMainExecutionThread();
    }

    @Provides
    @PerModule
    GetExperimentResultByIdUseCase provideGetExperimentResultByIdUseCase(ExperimentResultsRepositoryContract repository, ExecutionThread executionThread, PostExecutionThread postExecutionThread) {
        return new GetExperimentResultByIdUseCase(repository, executionThread, postExecutionThread);
    }
}
