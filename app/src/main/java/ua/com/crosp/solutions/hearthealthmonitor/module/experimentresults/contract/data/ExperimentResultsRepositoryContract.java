package ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.data;

/**
 * Created by Alexander Molochko on 1/22/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

import io.reactivex.Single;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.entity.ExperimentResultEntity;

public interface ExperimentResultsRepositoryContract {

    Single<ExperimentResultEntity.List> getAllExperimentResults();

    Single<ExperimentResultEntity.List> getAllExperimentResultsForUser(Long userId);

    Single<ExperimentResultEntity> getExperimentResultById(final long experimentId);

    Single<Long> saveExperimentResultForUser(ExperimentResultEntity experimentResut);

    Single<Boolean> removeExperimentById(Long experimentId);
}
