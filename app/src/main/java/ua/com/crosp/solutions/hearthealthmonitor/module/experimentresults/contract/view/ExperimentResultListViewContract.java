package ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.view;

import io.reactivex.Single;
import ua.com.crosp.solutions.hearthealthmonitor.base.view.BaseView;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.presentation.view.viewmodel.ExperimentResultViewModel;

/**
 * Created by Alexander Molochko on 12/19/16.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

public interface ExperimentResultListViewContract extends BaseView {
    void displayExperimentResultList(ExperimentResultViewModel.List modes);

    Single<Integer> showExperimentContextDialog();

    void onExperimentRemoved(ExperimentResultViewModel item);
}
