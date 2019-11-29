package ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.contract.service;

import android.os.Bundle;

import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.audio.ReactiveAudioRecorder;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.service.ECGProcessingRecordingService;

/**
 * Created by Alexander Molochko on 11/15/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public interface EcgServiceProxyContract {
    void unsubscribeFromEcgEvents();

    void listenToEcgData(@ECGProcessingRecordingService.RecordingDestinationSource int type, Bundle arguments, EcgRecordingServiceInteraction.ECGDataListener ecgDataListener);

    ReactiveAudioRecorder.Settings getAudioRecordingSettings();

    void unbindFromService();

    void bindToService();

    void startEcgRecordingStreamAndFile(String filePath, Long recordingTime, EcgRecordingServiceInteraction.ECGDataListener ecgDataListener);

    void startEcgRecordingStream(long experimentTimeInMs, EcgRecordingServiceInteraction.ECGDataListener ecgDataListener);

    void destroy();

    void stop();

    double getSamplingRate();

    int getCurrentHeartRate();

    void stopOnTimer();

}
