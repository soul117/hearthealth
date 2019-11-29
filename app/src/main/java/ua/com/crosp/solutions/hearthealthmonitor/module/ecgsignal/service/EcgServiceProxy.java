package ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.disposables.Disposable;
import ua.com.crosp.solutions.hearthealthmonitor.common.contants.BundleConstants;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.audio.ReactiveAudioRecorder;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util.Logger;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.contract.service.EcgRecordingServiceInteraction;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.contract.service.EcgServiceProxyContract;

import static ua.com.crosp.solutions.hearthealthmonitor.di.named.NamedConstants.Context.APPLICATION_CONTEXT;

/**
 * Created by Alexander Molochko on 5/28/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

/**
 * Proxy for communicating with ECG Processing service
 */
public class EcgServiceProxy implements EcgServiceProxyContract {
    protected EcgServiceConnection mEcgServiceConnection = new EcgServiceConnection();
    private EcgRecordingServiceInteraction mEcgRecordingServiceInteraction;
    @Inject
    @Named(APPLICATION_CONTEXT)
    Context mAppContext;

    private Bundle mBundleArguments;
    private int mRecordingType;
    private EcgRecordingServiceInteraction.ECGDataListener mEcgDataListener;

    public EcgServiceProxy(@Named(APPLICATION_CONTEXT) Context activity) {
        mAppContext = activity;
    }

    @Override
    public void unsubscribeFromEcgEvents() {
        try {
            if (mEcgRecordingServiceInteraction != null) {
                mEcgRecordingServiceInteraction.disposeEventListener();
                mAppContext.getApplicationContext().unbindService(mEcgServiceConnection);
                mEcgRecordingServiceInteraction = null;
            }
        } catch (Exception ex) {

        }
    }

    @Override
    public void bindToService() {
        mAppContext.bindService(new Intent(mAppContext, ECGProcessingRecordingService.class), mEcgServiceConnection, 0);
    }

    @Override
    public void startEcgRecordingStreamAndFile(String filePath, Long recrodingTime, EcgRecordingServiceInteraction.ECGDataListener ecgDataListener) {
        Bundle bundle = new Bundle();
        EcgRecordingServiceInteraction.EcgServiceOptions options = new EcgRecordingServiceInteraction.EcgServiceOptions();
        options.audioRecordingPath = filePath;
        options.recordingTime = recrodingTime;
        bundle.putSerializable(BundleConstants.Arguments.ECG_SERVICE_OPTIONS, options);
        listenToEcgData(ECGProcessingRecordingService.SOURCE_STREAM, bundle, ecgDataListener);
    }

    @Override
    public void startEcgRecordingStream(long experimentTimeInMs, EcgRecordingServiceInteraction.ECGDataListener ecgDataListener) {
        Bundle bundle = new Bundle();
        EcgRecordingServiceInteraction.EcgServiceOptions options = new EcgRecordingServiceInteraction.EcgServiceOptions();
        options.audioRecordingPath = "";
        options.recordingTime = experimentTimeInMs;
        bundle.putSerializable(BundleConstants.Arguments.ECG_SERVICE_OPTIONS, options);
        listenToEcgData(ECGProcessingRecordingService.SOURCE_STREAM, bundle, ecgDataListener);

    }

    @Override
    public void destroy() {
        unbindFromService();
    }

    @Override
    public void stop() {
        unsubscribeFromEcgEvents();
    }

    @Override
    public double getSamplingRate() {
        if (mEcgRecordingServiceInteraction != null) {
            ReactiveAudioRecorder.Settings audioSettings = mEcgRecordingServiceInteraction.getAudioRecordingSettings();
            return audioSettings.getSampleRate();
        }
        return 0;
    }

    @Override
    public int getCurrentHeartRate() {
        if (mEcgRecordingServiceInteraction != null) {
            return mEcgRecordingServiceInteraction.getCurrentHeartRate();
        }
        return 0;
    }

    @Override
    public void stopOnTimer() {
        if (mEcgRecordingServiceInteraction != null) {
            mEcgRecordingServiceInteraction.stopOnTimer();
        }
    }

    @Override
    public void listenToEcgData(@ECGProcessingRecordingService.RecordingDestinationSource int type, Bundle arguments, EcgRecordingServiceInteraction.ECGDataListener ecgDataListener) {
        mRecordingType = type;
        mEcgDataListener = ecgDataListener;
        mBundleArguments = arguments;
        bindToService();
    }

    @Override
    public ReactiveAudioRecorder.Settings getAudioRecordingSettings() {
        return mEcgRecordingServiceInteraction.getAudioRecordingSettings();
    }

    @Override
    public void unbindFromService() {
        if (mEcgRecordingServiceInteraction != null) {
            mEcgRecordingServiceInteraction.disposeEventListener();
            mAppContext.unbindService(mEcgServiceConnection);
            mEcgRecordingServiceInteraction = null;
        }
    }

    // Inner classes

    private class EcgServiceConnection implements ServiceConnection {
        public Disposable mStreamDisposable;

        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            Logger.debug("onServiceConnected");
            mEcgRecordingServiceInteraction = ((ECGProcessingRecordingService.EcgLocalBinder) binder).getServiceInteractor();
            mEcgRecordingServiceInteraction.startRecordingStreamingCallback(mBundleArguments, mEcgDataListener);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

            Logger.debug("onServiceDisconnected");
        }

        public void stopRecording() {
            if (mStreamDisposable != null && !mStreamDisposable.isDisposed()) {
                mStreamDisposable.dispose();
            }
        }
    }


}
