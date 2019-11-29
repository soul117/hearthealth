package ua.com.crosp.solutions.hearthealthmonitor.module.session.domain.usecase;

import io.reactivex.Single;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.ExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.PostExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.usecase.BaseSingleUseCaseContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.cotntract.data.UserSessionRepositoryContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.domain.entity.UserSessionEntity;

/**
 * Created by Alexander Molochko on 10/21/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class SaveUserSessionUseCase extends BaseSingleUseCaseContract<UserSessionEntity, Long> {
    private final UserSessionRepositoryContract mRepository;

    public SaveUserSessionUseCase(ExecutionThread executionThread, PostExecutionThread postExecutionThread, UserSessionRepositoryContract repository) {
        super(executionThread, postExecutionThread);
        mRepository = repository;
    }

    @Override
    public Single<Long> provideSingleObservable(UserSessionEntity userSessionEntity) {
        return mRepository.saveUserSession(userSessionEntity);
    }
}
