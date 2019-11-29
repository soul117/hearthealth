package ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.contract.presentation;

import com.cleveroad.audiovisualization.AudioVisualization;

import ua.com.crosp.solutions.hearthealthmonitor.base.presentation.BasePresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.contract.EcgRouterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.contract.view.EcgMainViewContract;

/**
 * Created by Alexander Molochko on 12/19/16.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

public interface EcgMainPresenterContract extends BasePresenterContract<EcgMainViewContract, EcgRouterContract> {

    void onBackButtonPress();

    void linkAudioToVisualizationView(AudioVisualization audioVisualization);

    void onStartRecordingClicked(long recordingTime);
}
