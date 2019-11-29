package ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.usecase;

import io.reactivex.Single;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.ExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.PostExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.usecase.BaseSingleUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.data.ExperimentResultsRepositoryContract;

/**
 * Created by Alexander Molochko on 12/18/16.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

public class RemoveExperimentResultByIdUseCase extends BaseSingleUseCase<Long, Boolean> {
    private ExperimentResultsRepositoryContract mModesRepository;

    public RemoveExperimentResultByIdUseCase(ExperimentResultsRepositoryContract modesRepository, ExecutionThread threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mModesRepository = modesRepository;
    }

    @Override
    public Single<Boolean> provideSingleObservable(Long experimentId) {
        return mModesRepository.removeExperimentById(experimentId);
    }

}
