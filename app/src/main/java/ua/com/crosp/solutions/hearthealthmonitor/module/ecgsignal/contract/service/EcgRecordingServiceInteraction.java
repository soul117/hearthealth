package ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.contract.service;

import android.os.Bundle;

import com.scichart.core.model.DoubleValues;
import com.scichart.core.model.ShortValues;

import java.io.Serializable;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.audio.ReactiveAudioRecorder;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.audio.common.AudioRecordingBuffer;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.domain.RPeakDetectionInfo;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.service.ECGProcessingRecordingService;

/**
 * Created by Alexander Molochko on 5/7/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public interface EcgRecordingServiceInteraction {
    Flowable<AudioRecordingBuffer> startRecordingStreaming();

    void startRecordingStreamingCallback(Bundle options, ECGDataListener ecgDataListener);

    void unsubscribeFromStreaming(Disposable flowableStream);

    void stopRecording();

    Flowable<AudioRecordingBuffer> startRecordingByType(@ECGProcessingRecordingService.RecordingDestinationSource int recordingType, Bundle arguments);

    void toggleRecording();

    ReactiveAudioRecorder.Settings getAudioRecordingSettings();

    void disposeEventListener();

    int getCurrentHeartRate();

    void stopOnTimer();

    public interface ECGDataListener {
        void onRPeakDetected(RPeakDetectionInfo rPeakDetectionInfo);

        void onEcgDataProcessed(ShortValues ecgSignal, DoubleValues timeValues);

        void onEcgProcessingStart();

        void onEcgProcessingStop();

        void onTimerTick();

        void onTimerStopped();
    }

    public static class EcgServiceOptions implements Serializable {
        public String audioRecordingPath;
        public long recordingTime;
    }
}
