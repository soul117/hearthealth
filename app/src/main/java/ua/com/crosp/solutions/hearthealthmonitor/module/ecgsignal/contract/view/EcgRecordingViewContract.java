package ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.contract.view;

import ua.com.crosp.solutions.hearthealthmonitor.base.view.BaseView;

/**
 * Created by Alexander Molochko on 12/19/16.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

public interface EcgRecordingViewContract extends BaseView {

    void resetChartData();

    void showHeartBeat();

    void hideHeartBeat();
}
