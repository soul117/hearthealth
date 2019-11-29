package ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.presentation.presenter;

import com.cleveroad.audiovisualization.AudioVisualization;

import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;
import ua.com.crosp.solutions.hearthealthmonitor.R;
import ua.com.crosp.solutions.hearthealthmonitor.base.presentation.BaseAppPresenter;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.audio.receivers.HeadphonesPlugNotifier;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.audio.visualization.AudioRecordingDbmHandler;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.audio.visualization.IAudioRecorder;
import ua.com.crosp.solutions.hearthealthmonitor.module.appsettings.data.AppSettingsModel;
import ua.com.crosp.solutions.hearthealthmonitor.module.appsettings.domain.GetAppSettingsUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.contract.EcgRouterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.contract.presentation.EcgMainPresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.contract.view.EcgMainViewContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.presentation.view.fragment.HeadphonesPlugNotifierProvider;

/**
 * Created by Alexander Molochko on 12/19/16.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

public class EcgMainPresenter extends BaseAppPresenter<EcgMainViewContract, EcgRouterContract> implements EcgMainPresenterContract {
    private final GetAppSettingsUseCase mGetAppSettingsUseCase;
    private AudioRecordingDbmHandler mDbmHandler;
    private IAudioRecorder mAudioRecorder;
    private HeadphonesPlugNotifierProvider mPlugNotifierProvider;
    private boolean mIsSensorConnected = false;

    public EcgMainPresenter(EcgRouterContract routerContract, GetAppSettingsUseCase getAppSettingsUseCase, AudioRecordingDbmHandler dbmHandler, IAudioRecorder audioRecorder, HeadphonesPlugNotifierProvider plugNotifierProvider) {
        mGetAppSettingsUseCase = getAppSettingsUseCase;
        mDbmHandler = dbmHandler;
        mAudioRecorder = audioRecorder;
        mPlugNotifierProvider = plugNotifierProvider;
        setRouter(routerContract);
    }


    @Override
    public void onViewReady() {
        mGetAppSettingsUseCase.provideSingleObservable(null)
                .subscribe(new BiConsumer<AppSettingsModel, Throwable>() {
                    @Override
                    public void accept(AppSettingsModel appSettingsModel, Throwable throwable) throws Exception {
                        getView().setSavedRecordingTime(appSettingsModel.getRecordingTime());
                    }
                });

        mPlugNotifierProvider.provideHeadphonesPlugNotifier()
                .getEventSubject()
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        switch (integer) {
                            case HeadphonesPlugNotifier.PLUG:
                                mIsSensorConnected = true;
                                getView().showConnectedSensorState();
                                break;
                            case HeadphonesPlugNotifier.UNPLUG:
                                mIsSensorConnected = false;
                                getView().showNotConnectedSensorState();
                                break;
                            default:
                                mIsSensorConnected = false;
                                getView().showNotConnectedSensorState();
                                break;
                        }
                    }
                });
    }

    @Override
    public void onViewDestroyed() {
        mPlugNotifierProvider.unsubscribeFromHeadphonePlugEvents();
        releaseAudioVisualizer();
    }

    private void releaseAudioVisualizer() {
        try {
            mAudioRecorder.finishRecord();
            mDbmHandler.stop();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onBackButtonPress() {
        getRouter().navigateBackForce();
    }

    @Override
    public void linkAudioToVisualizationView(AudioVisualization audioVisualization) {
      /*  mAudioRecorder.recordingCallback(mDbmHandler);
        audioVisualization.linkTo(mDbmHandler);
        mAudioRecorder.startRecord();*/
    }

    @Override
    public void onStartRecordingClicked(long recordingTime) {
        if (mIsSensorConnected) {
            releaseAudioVisualizer();
            getRouter().switchToRealtimeRecordingScreen(recordingTime);
        } else {
            getView().showErrorMessage(R.string.error_message_sensor_not_connected);
        }
    }

}