package ua.com.crosp.solutions.hearthealthmonitor.module.session.domain.usecase;

import io.reactivex.Single;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.ExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.PostExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.usecase.BaseSingleUseCaseContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.cotntract.data.UserSessionRepositoryContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.domain.entity.UserSessionEntity;

/**
 * Created by Alexander Molochko on 1/8/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class GetUserSessionUseCase extends BaseSingleUseCaseContract<Long, UserSessionEntity> {
    private final UserSessionRepositoryContract mRepository;

    public GetUserSessionUseCase(ExecutionThread executionThread, PostExecutionThread postExecutionThread, UserSessionRepositoryContract repositoryContract) {
        super(executionThread, postExecutionThread);
        this.mRepository = repositoryContract;
    }

    @Override
    public Single<UserSessionEntity> provideSingleObservable(Long sessionId) {
        return mRepository.getUserSession(sessionId);
    }
}
