package ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.handler;

/**
 * Created by Alexander Molochko on 11/28/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public interface ECGStreamProcessorContract {

    ECGSignalProcessor.Chain getECGSignalProcessorChain();

    void setECGSignalProcessorChain(ECGSignalProcessor.Chain processorChain);

    int getCurrentHeartRate();

    void stopProcessing();

    boolean isCarrierDebounced();

    void setCarrierDebounced(boolean carrierDebounced);
}
