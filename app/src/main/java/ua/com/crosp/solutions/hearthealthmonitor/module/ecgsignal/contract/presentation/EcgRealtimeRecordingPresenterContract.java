package ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.contract.presentation;

import ua.com.crosp.solutions.hearthealthmonitor.base.presentation.BasePresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.contract.EcgRealtTimeRecordingViewContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.contract.EcgRealtimeRouterContract;

/**
 * Created by Alexander Molochko on 12/19/16.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

public interface EcgRealtimeRecordingPresenterContract extends BasePresenterContract<EcgRealtTimeRecordingViewContract, EcgRealtimeRouterContract> {

    void onBackButtonPress();

    double getSamplingRate();

    int getCurrentHeartRate();

    int getBufferReadSize();
}
