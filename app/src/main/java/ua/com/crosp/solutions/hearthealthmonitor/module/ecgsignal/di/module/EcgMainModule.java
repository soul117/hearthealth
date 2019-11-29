package ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.di.module;

import dagger.Module;
import dagger.Provides;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.ExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.PostExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerModule;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.audio.visualization.AudioRecorderVisualization;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.audio.visualization.AudioRecordingDbmHandler;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.audio.visualization.IAudioRecorder;
import ua.com.crosp.solutions.hearthealthmonitor.module.appsettings.contract.data.AppSettingsRepositoryContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.appsettings.domain.GetAppSettingsUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.contract.EcgRouterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.contract.presentation.EcgMainPresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.presentation.presenter.EcgMainPresenter;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.presentation.view.fragment.HeadphonesPlugNotifierProvider;

/**
 * Created by Alexander Molochko on 2/9/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
@Module
@PerModule
public class EcgMainModule {

    @Provides
    @PerModule
    EcgMainPresenterContract provideEcgMainPresenterContract(EcgRouterContract router, AudioRecordingDbmHandler dbmHandler, IAudioRecorder audioRecorder, HeadphonesPlugNotifierProvider plugNotifierProvider, GetAppSettingsUseCase getAppSettingsUseCase) {
        return new EcgMainPresenter(router, getAppSettingsUseCase, dbmHandler, audioRecorder, plugNotifierProvider);
    }

    @Provides
    @PerModule
    HeadphonesPlugNotifierProvider providePlugNotifierProvider(EcgRouterContract router) {
        return (HeadphonesPlugNotifierProvider) router;
    }

    @Provides
    @PerModule
    GetAppSettingsUseCase provideAppSettingsUseCase(AppSettingsRepositoryContract repo, ExecutionThread executionThread, PostExecutionThread postExecutionThread) {
        return new GetAppSettingsUseCase(repo, executionThread, postExecutionThread);
    }

    @Provides
    @PerModule
    IAudioRecorder provideAudioRecorder() {
        return new AudioRecorderVisualization();
    }

    @Provides
    @PerModule
    AudioRecordingDbmHandler provideAudioDbmHandler() {
        return new AudioRecordingDbmHandler();
    }


}
