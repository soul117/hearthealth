package ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.handler;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Alexander Molochko on 11/28/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public interface ECGSignalProcessor<S, T> {
    // TODO for performance reasons do not return any value to avoid object creation
    public S processSignal(S signal, T time);

    boolean isProcessingBouncingSignal();

    void onStopProcessing();

    public static final class Chain<S, T> extends ArrayList<ECGSignalProcessor> {
        private FinalDataConsumer<S, T> mFinalDataConsumer;

        public Chain(int initialCapacity) {
            super(initialCapacity);
        }

        public Chain() {
        }

        public Chain(FinalDataConsumer<S, T> dataConsumer) {
            mFinalDataConsumer = dataConsumer;
        }

        public Chain(@NonNull Collection<? extends ECGSignalProcessor> c) {
            super(c);
        }


        public void process(S voltage, T time, boolean isDebounced) {
            S currentProcessedData = voltage;
            for (ECGSignalProcessor<S, T> processor : this) {
                if (!isDebounced) {
                    if (processor.isProcessingBouncingSignal()) {
                        currentProcessedData = processor.processSignal(currentProcessedData, time);
                    }
                } else {
                    currentProcessedData = processor.processSignal(currentProcessedData, time);
                }

            }
            if (mFinalDataConsumer != null) {
                mFinalDataConsumer.onDataProcessed(currentProcessedData, time);
            }
        }

        public void stopProcessing() {
            for (ECGSignalProcessor<S, T> processor : this) {
                processor.onStopProcessing();
            }
            if (mFinalDataConsumer != null) {
                mFinalDataConsumer.onStopProcessingData();
            }
        }
    }

    public interface FinalDataConsumer<S, T> {
        void onDataProcessed(S voltage, T time);

        void onStopProcessingData();
    }
}
