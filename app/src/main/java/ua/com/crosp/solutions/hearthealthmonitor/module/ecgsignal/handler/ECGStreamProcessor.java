package ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.handler;

import com.scichart.core.model.DoubleValues;
import com.scichart.core.model.ShortValues;

import net.crosp.android.library.ecgaudioprocessor.NativeEcgSignalProcessor;

import java.io.FileNotFoundException;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.audio.ReactiveAudioRecorder;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.filesystem.FileManager;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.audio.common.AudioRecordingBuffer;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.domain.HeartRateCalculator;

/**
 * Created by Alexander Molochko on 11/28/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
public class ECGStreamProcessor implements ECGStreamProcessorContract, Consumer<AudioRecordingBuffer> {
    private final FileManager mFileManager;
    private ECGSignalProcessor.FinalDataConsumer<ShortValues, DoubleValues> mFinalProcessingListener;
    private ReactiveAudioRecorder.Settings mRecordingSettings;

    private ECGSignalProcessor.Chain<ShortValues, DoubleValues> mProcessorsChain;
    private String mEcgAudioFilePath;
    private boolean mCarrierDebounced = false;
    // Signal processors
    private ECGWavFileWriter mEcgWavFileWriter;
    private final Flowable<AudioRecordingBuffer> mAudioStream;
    private GeliomedECGSignalProcessor.ECGRPeakDetectionListener mEcgEventListener;
    private GeliomedECGSignalProcessor mNativeGeliomedSignalProcess;
    private Disposable mStreamDisposable;


    public ECGStreamProcessor(Flowable<AudioRecordingBuffer> dataSource, ReactiveAudioRecorder.Settings recordingSettings, String audioFilePath, FileManager fileManager, GeliomedECGSignalProcessor.ECGRPeakDetectionListener ecgEventListener, ECGSignalProcessor.FinalDataConsumer<ShortValues, DoubleValues> finalProcessingListener) {
        mRecordingSettings = recordingSettings;
        mFileManager = fileManager;
        mAudioStream = dataSource;
        mEcgAudioFilePath = audioFilePath;
        mFinalProcessingListener = finalProcessingListener;
        mEcgEventListener = ecgEventListener;
        init();
    }

    private void init() {
        initSignalProcessorsChain();
        startListenToStream();
    }

    private void startListenToStream() {
        if (mAudioStream != null) {
            mStreamDisposable = mAudioStream.subscribe(this);
        }
    }

    // Method is called only after service is connected !!! Service determines proper
    // audio settings, therefore this should be postponed until is connected
    private void initSignalProcessorsChain() {
        try {
            // Create chain
            if (mFinalProcessingListener != null) {
                mProcessorsChain = new ECGSignalProcessor.Chain<>(mFinalProcessingListener);
            } else {
                mProcessorsChain = new ECGSignalProcessor.Chain<>();
            }
            // Create native signal processor
            mNativeGeliomedSignalProcess = new GeliomedECGSignalProcessor(mFileManager.getFilePathInSandboxDirectory(), generateEcgProcessingOptions(), new HeartRateCalculator());
            mNativeGeliomedSignalProcess.addRPeakListener(mEcgEventListener);
            // Create WAV_EXTENSION audio file writer
            mProcessorsChain.add(mNativeGeliomedSignalProcess);
            if (isValidFilePath(mEcgAudioFilePath)) {
                mEcgWavFileWriter = new ECGWavFileWriter(mFileManager);
                mEcgWavFileWriter.initWithFilePathAndSettings(mEcgAudioFilePath, mRecordingSettings);
                mProcessorsChain.add(mEcgWavFileWriter);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected NativeEcgSignalProcessor.EcgProcessingOptions generateEcgProcessingOptions() {
        return mRecordingSettings.toEcgProcessingOptions();
    }

    private boolean isValidFilePath(String sandboxPath) {
        return sandboxPath != null && !sandboxPath.isEmpty() && !sandboxPath.equals("/");
    }

    /*
        private void stopProcessing() {
            if (mDrawSchedule != null) {
                mDrawSchedule.cancel(true);
            }
            mScheduledExecutorService.shutdown();
        }
    */
    @Override
    public void stopProcessing() {
        if (mProcessorsChain != null) {
            mProcessorsChain.stopProcessing();
            if (mStreamDisposable != null && !mStreamDisposable.isDisposed()) {
                mStreamDisposable.dispose();
            }
        }
    }

    @Override
    public ECGSignalProcessor.Chain getECGSignalProcessorChain() {
        return mProcessorsChain;
    }

    @Override
    public void setECGSignalProcessorChain(ECGSignalProcessor.Chain processorChain) {
        mProcessorsChain = processorChain;
    }

    @Override
    public int getCurrentHeartRate() {
        if (mNativeGeliomedSignalProcess != null) {
            return mNativeGeliomedSignalProcess.getCurrentHeartRate();
        }
        return 0;
    }

    @Override
    public void accept(AudioRecordingBuffer audioRecordingBuffer) throws Exception {
        mProcessorsChain.process(audioRecordingBuffer.getVoltageBufferContent(), audioRecordingBuffer.getTimeBufferContent(), isCarrierDebounced());
    }

    @Override
    public boolean isCarrierDebounced() {
        return mCarrierDebounced;
    }

    @Override
    public void setCarrierDebounced(boolean carrierDebounced) {
        mCarrierDebounced = carrierDebounced;
    }
}