package ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.presentation.view.viewmodel;

import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.entity.ExperimentResultEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.entity.FeelingResultEntity;

/**
 * Created by Alexander Molochko on 10/31/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class ExperimentFeelingResultViewModel {
    public String feelingType;
    public String comment;
    public String weatherConditions;
    public boolean wasInterrupted;

    public ExperimentFeelingResultViewModel() {
    }

    public static ExperimentFeelingResultViewModel fromEntity(ExperimentResultEntity experimentResultEntity) {
        ExperimentFeelingResultViewModel experimentResultViewModel = new ExperimentFeelingResultViewModel();
        FeelingResultEntity feelingResultEntity = experimentResultEntity.getFeelingResultEntity();
        experimentResultViewModel.feelingType = feelingResultEntity.getFeelingType();
        experimentResultViewModel.weatherConditions = feelingResultEntity.getWeatherType();
        experimentResultViewModel.comment = feelingResultEntity.getComment();

        return experimentResultViewModel;
    }

}
