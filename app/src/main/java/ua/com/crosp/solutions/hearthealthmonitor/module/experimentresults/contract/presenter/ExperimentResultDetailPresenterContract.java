package ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.presenter;

import ua.com.crosp.solutions.hearthealthmonitor.base.presentation.BasePresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.ExperimentResultRouterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.view.ExperimentResultDetailViewContract;

/**
 * Created by Alexander Molochko on 12/19/16.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

public interface ExperimentResultDetailPresenterContract extends BasePresenterContract<ExperimentResultDetailViewContract, ExperimentResultRouterContract> {

    void initialize(long modeId);

    void onBackButtonPress();

    void showEcgResultChart();

    void onGenerateXMLReport();
}
