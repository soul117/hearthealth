package ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.presenter;

import ua.com.crosp.solutions.hearthealthmonitor.base.presentation.BasePresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.EcgResultRouterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.ExperimentResultRouterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.view.EcgResultChartViewContract;

/**
 * Created by Alexander Molochko on 12/19/16.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

public interface EcgChartResultPresenterContract extends BasePresenterContract<EcgResultChartViewContract, EcgResultRouterContract> {

    void onBackButtonPress();

    void initialize(long expId);
}
