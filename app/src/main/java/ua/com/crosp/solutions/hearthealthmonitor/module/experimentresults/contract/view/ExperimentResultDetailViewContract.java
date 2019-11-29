package ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.view;

import ua.com.crosp.solutions.hearthealthmonitor.base.view.BaseView;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.entity.ExperimentResultEntity;

/**
 * Created by Alexander Molochko on 12/19/16.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

public interface ExperimentResultDetailViewContract extends BaseView {

    void displayExperimentDetails(ExperimentResultEntity experimentResultEntity);
}
