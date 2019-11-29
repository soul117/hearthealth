package ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.usecase;

import javax.inject.Inject;

import io.reactivex.Single;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.ExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.PostExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.usecase.BaseSingleUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.valueobject.EntityId;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.entity.ExperimentResultEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.data.ExperimentResultsRepositoryContract;

/**
 * Created by Alexander Molochko on 12/18/16.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

public class SaveExperimentResultForCurrentUserUseCase extends BaseSingleUseCase<SaveExperimentResultForCurrentUserUseCase.Params, Long> {
    private ExperimentResultsRepositoryContract mRepository;

    @Inject
    public SaveExperimentResultForCurrentUserUseCase(ExperimentResultsRepositoryContract modesRepository, ExecutionThread threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mRepository = modesRepository;
    }

    @Override
    public Single<Long> provideSingleObservable(Params params) {
        ExperimentResultEntity experimentResultEntity = params.experimentResult;
        if (experimentResultEntity.getEntityId() == EntityId.INVALID_ENTITY_ID) {
            experimentResultEntity.setEntityId(EntityId.generateUniqueId());
        }
        experimentResultEntity.setUserId(new EntityId(params.userId));
        experimentResultEntity.generateIdsForResultsIfRequired();
        return mRepository.saveExperimentResultForUser(params.experimentResult);
    }


    public static class Params {
        public Long userId;
        public ExperimentResultEntity experimentResult;

        public Params(Long userId, ExperimentResultEntity experimentResut) {
            this.userId = userId;
            this.experimentResult = experimentResut;
        }
    }
}
