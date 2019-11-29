package ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.presentation.view.viewmodel;

import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util.DateUtils;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.entity.EcgResultEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.entity.ExperimentResultEntity;

/**
 * Created by Alexander Molochko on 10/31/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class ExperimentEcgResultViewModel {
    private static final int MILLIS_IN_SECOND = 1000;

    public String dateStart;
    public String dateEnd;
    public long estimatedTime;
    public String wasInterrupted;

    public ExperimentEcgResultViewModel() {
    }

    public static ExperimentEcgResultViewModel fromEntity(ExperimentResultEntity experimentResultEntity) {
        ExperimentEcgResultViewModel experimentResultViewModel = new ExperimentEcgResultViewModel();
        EcgResultEntity resultEntity = experimentResultEntity.getEcgResultEntity();
        experimentResultViewModel.dateEnd = DateUtils.formatDateTime(resultEntity.getDateRecordingStop());
        experimentResultViewModel.dateStart = DateUtils.formatDateTime(resultEntity.getDateRecordingStart());
        experimentResultViewModel.estimatedTime = resultEntity.getEstimatedRecordingTimeInSeconds() / MILLIS_IN_SECOND;
        experimentResultViewModel.wasInterrupted = resultEntity.isWasInterrupted() ? "Yes" : "No";

        return experimentResultViewModel;
    }
}
