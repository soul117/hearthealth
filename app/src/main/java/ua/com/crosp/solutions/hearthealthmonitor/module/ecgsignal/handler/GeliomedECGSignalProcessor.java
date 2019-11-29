package ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.handler;

import com.scichart.core.model.DoubleValues;
import com.scichart.core.model.ShortValues;

import net.crosp.android.library.ecgaudioprocessor.NativeEcgSignalProcessor;

import java.util.ArrayList;
import java.util.List;

import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util.Logger;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util.TimeUtils;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.domain.HeartRateCalculator;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.domain.RPeakDetectionInfo;

/**
 * Created by Alexander Molochko on 12/2/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class GeliomedECGSignalProcessor implements ECGSignalProcessor<ShortValues, DoubleValues> {
    public static final double TIME_INCREMENT = 1;
    // Signal processors
    private NativeEcgSignalProcessor mEcgProcessor;
    HeartRateCalculator mHeartRateCalulator;

    private double mCurrentTime = 0.0;
    private List<ECGRPeakDetectionListener> mRPeakListeners;

    public GeliomedECGSignalProcessor(String sandboxPath, NativeEcgSignalProcessor.EcgProcessingOptions ecgProcessingOptions, HeartRateCalculator heartRateCalculator) {
        NativeEcgSignalProcessor.EcgProcessingOptions processingOptions = ecgProcessingOptions;
        processingOptions.sandboxPath = sandboxPath;
        mHeartRateCalulator = heartRateCalculator;
        mRPeakListeners = new ArrayList<>();
        mEcgProcessor = NativeEcgSignalProcessor.initLibraryWithDefaultParameters(processingOptions);
    }


    @Override
    public ShortValues processSignal(ShortValues signal, DoubleValues time) {
        try {
            short[] result = mEcgProcessor.demodulate(signal.getItemsArray());
            int rPeakDetectedPosition = mEcgProcessor.detectRPeaks(result);
            // R peak was detected
            if (rPeakDetectedPosition > 0) {
                RPeakDetectionInfo rPeakDetectionInfo = new RPeakDetectionInfo(rPeakDetectedPosition, TimeUtils.getCurrentTimeMillis());
                mHeartRateCalulator.onRPeakDetected(rPeakDetectionInfo);
                notifyListenersOnRPeakDetected(rPeakDetectionInfo);
            }
            if (time.size() > result.length) {
                time.setSize(result.length);
            }
            double[] timeValues = time.getItemsArray();
            for (int i = 0; i < result.length; i++) {
                mCurrentTime += TIME_INCREMENT;
                timeValues[i] = mCurrentTime;
            }
            return new ShortValues(result);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public boolean isProcessingBouncingSignal() {
        return true;
    }

    public void addRPeakListener(ECGRPeakDetectionListener ecgrPeakDetectionListener) {
        assert ecgrPeakDetectionListener != null;
        mRPeakListeners.add(ecgrPeakDetectionListener);
    }

    protected void notifyListenersOnRPeakDetected(RPeakDetectionInfo rPeakDetectionInfo) {
        if (mRPeakListeners != null && !mRPeakListeners.isEmpty()) {
            for (ECGRPeakDetectionListener listener : mRPeakListeners) {
                listener.onRPeakDetected(rPeakDetectionInfo);
            }
        }
    }

    @Override
    public void onStopProcessing() {
        Logger.error(this.getClass().getName() + " onStopProcessing");
        try {
            mEcgProcessor.release();
        } catch (Exception ex) {

        }
        // NOP
    }

    public int getCurrentHeartRate() {
        return mHeartRateCalulator.getAverageHeartBeatRate();
    }

    public interface ECGRPeakDetectionListener {
        void onRPeakDetected(RPeakDetectionInfo rPeakDetectionInfo);
    }
}
