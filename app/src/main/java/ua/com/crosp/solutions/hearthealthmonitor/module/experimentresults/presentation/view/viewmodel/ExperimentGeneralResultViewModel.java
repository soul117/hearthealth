package ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.presentation.view.viewmodel;

import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util.DateUtils;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.entity.ExperimentResultEntity;

/**
 * Created by Alexander Molochko on 10/31/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class ExperimentGeneralResultViewModel {
    public String resultName;
    public String resultDate;
    public String description;
    public String id;

    public ExperimentGeneralResultViewModel() {
    }

    public static ExperimentGeneralResultViewModel fromEntity(ExperimentResultEntity experimentResultEntity) {
        ExperimentGeneralResultViewModel experimentResultViewModel = new ExperimentGeneralResultViewModel();
        experimentResultViewModel.description = experimentResultEntity.getGameResultEntity().getGameName();
        experimentResultViewModel.resultName = experimentResultEntity.getExperimentName();
        experimentResultViewModel.id = experimentResultEntity.getEntityId().toString();
        experimentResultViewModel.resultDate = DateUtils.formatDateTime(experimentResultEntity.getDateOfCreation().getDate());
        return experimentResultViewModel;
    }

}
