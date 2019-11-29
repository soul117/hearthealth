package ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.audio;


import androidx.annotation.IntDef;
import androidx.annotation.Nullable;

import net.crosp.android.library.ecgaudioprocessor.NativeEcgSignalProcessor;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import io.reactivex.Flowable;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.audio.common.AudioRecordingBuffer;

/**
 * Created by Alexander Molochko on 5/27/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public interface ReactiveAudioRecorder {

    public Settings getSettings();

    // Stream short samples
    public Flowable<AudioRecordingBuffer> startRecordingStreaming(Settings settings);

    // Record to file
    //Flowable<AudioRecordingBuffer> startStreamAndFileRecording(Settings settings, AudioFile fileHandler) throws AudioInitializationException;

   // void startRecordingToFile(Settings settings, AudioFile fileHandler) throws AudioInitializationException;

    boolean isStillRecording();

    public void stopRecording();

    public void pauseRecording();

    public void resumeRecording();

    @Nullable
    public Flowable<AudioRecordingBuffer> getCurrentStream();

    public boolean isPaused();

    // Audio recorder state
    public static final int STATE_STOPPED = 0;
    public static final int STATE_RECORDING = 1;
    public static final int STATE_IDLE = 2;
    public static final int STATE_ERROR = 3;
    public static final int STATE_NOT_AVAILABLE = 4;

    void onUnsubscribeFromStream();

    @IntDef({STATE_STOPPED, STATE_RECORDING, STATE_IDLE, STATE_ERROR, STATE_NOT_AVAILABLE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface RecordingState {
    }

    // Settings that should be provided to ReactiveAudioRecorder

    public interface Settings {
        int getAudiopEncodingFormat();

        int getChannelCount();

        void setEncodingFormat(int encodingFormat);

        void setSampleRate(int sampleRate);

        void setAudioSource(int audioSource);

        void setChannelCount(int channelCount);

        void setRealBufferSize(int realBufferSize);

        int getSampleRate();

        int getRealBufferSize();

        int getReadBufferSize();

        int getAudioSource();

        void setReadBufferSize(int readAudioBufferSize);

        NativeEcgSignalProcessor.EcgProcessingOptions toEcgProcessingOptions();
    }
}
