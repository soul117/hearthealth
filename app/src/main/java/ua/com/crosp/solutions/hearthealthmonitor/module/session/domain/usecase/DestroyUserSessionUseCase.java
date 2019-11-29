package ua.com.crosp.solutions.hearthealthmonitor.module.session.domain.usecase;

import io.reactivex.Single;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.ExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.PostExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.usecase.BaseSingleUseCaseContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.cotntract.data.UserSessionRepositoryContract;

/**
 * Created by Alexander Molochko on 1/8/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class DestroyUserSessionUseCase extends BaseSingleUseCaseContract<Long, Boolean> {

    private final UserSessionRepositoryContract mRepository;

    public DestroyUserSessionUseCase(ExecutionThread executionThread, PostExecutionThread postExecutionThread, UserSessionRepositoryContract repositoryContract) {
        super(executionThread, postExecutionThread);
        mRepository = repositoryContract;
    }

    @Override
    public Single<Boolean> provideSingleObservable(Long aLong) {
        return mRepository.destroySession(aLong);
    }
}
