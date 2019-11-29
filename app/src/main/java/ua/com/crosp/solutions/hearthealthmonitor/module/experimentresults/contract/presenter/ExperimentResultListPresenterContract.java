package ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.presenter;

import ua.com.crosp.solutions.hearthealthmonitor.base.presentation.BasePresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.ExperimentResultRouterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.view.ExperimentResultListViewContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.presentation.view.viewmodel.ExperimentResultViewModel;

/**
 * Created by Alexander Molochko on 12/19/16.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

public interface ExperimentResultListPresenterContract extends BasePresenterContract<ExperimentResultListViewContract, ExperimentResultRouterContract> {
    void onExperimentItemClick(ExperimentResultViewModel experimentResultViewModel);

    void onBackButtonPress();

    void onExperimentLongClickListener(ExperimentResultViewModel item);
}
