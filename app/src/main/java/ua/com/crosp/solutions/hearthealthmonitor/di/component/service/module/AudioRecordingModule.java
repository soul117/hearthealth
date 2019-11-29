package ua.com.crosp.solutions.hearthealthmonitor.di.component.service.module;

import android.content.Context;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import ua.com.crosp.solutions.hearthealthmonitor.configuration.AudioRecordingConfiguration;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerService;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.audio.ReactiveAudioRecorder;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.media.audio.rx.RxStreamAudioRecorder;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util.AndroidAudioUtils;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util.SystemUtils;

import static ua.com.crosp.solutions.hearthealthmonitor.di.named.NamedConstants.Context.APPLICATION_CONTEXT;

/**
 * Created by Alexander Molochko on 4/23/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
@PerService
@Module
public class AudioRecordingModule {
    @Provides
    @PerService
    ReactiveAudioRecorder provideReactiveAudioRecorder(RxStreamAudioRecorder.RecordingStreamCallback callback) {
        return new RxStreamAudioRecorder(callback);
    }

    @Provides
    @PerService
    ReactiveAudioRecorder.Settings provideAudioRecordingSettings(@Named(APPLICATION_CONTEXT) Context context) {
        if (SystemUtils.isRunningOnEmulator(context)) {
            return new RxStreamAudioRecorder.Settings.Builder()
                    .sampleRate(AndroidAudioUtils.SampleRate.RATE_8K)
                    .readBufferSize(AudioRecordingConfiguration.READ_BUFFER_SIZE)
                    .mono()
                    .encodingFormat(AudioRecordingConfiguration.RECORDING_FORMAT)
                    .micAudioSource()
                    .build();
        } else {
            return new RxStreamAudioRecorder.Settings.Builder()
                    .sampleRate(AudioRecordingConfiguration.SAMPLE_RATE)
                    .readBufferSize(AudioRecordingConfiguration.READ_BUFFER_SIZE)
                    .mono()
                    .encodingFormat(AudioRecordingConfiguration.RECORDING_FORMAT)
                    .micAudioSource()
                    .build();
        }
    }
}
