package ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.usecase;

import javax.inject.Inject;

import io.reactivex.Single;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.ExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.PostExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.usecase.BaseSingleUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.entity.ExperimentResultEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.data.ExperimentResultsRepositoryContract;

/**
 * Created by Alexander Molochko on 12/18/16.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

public class GetAllExperimentResultsForUserUseCase extends BaseSingleUseCase<Long, ExperimentResultEntity.List> {
    private ExperimentResultsRepositoryContract mRepository;

    @Inject
    public GetAllExperimentResultsForUserUseCase(ExperimentResultsRepositoryContract modesRepository, ExecutionThread threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mRepository = modesRepository;
    }

    @Override
    public Single<ExperimentResultEntity.List> provideSingleObservable(Long userId) {
        return mRepository.getAllExperimentResultsForUser(userId);
    }
}
