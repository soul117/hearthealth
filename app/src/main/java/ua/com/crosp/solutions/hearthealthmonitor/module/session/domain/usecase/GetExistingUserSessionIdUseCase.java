package ua.com.crosp.solutions.hearthealthmonitor.module.session.domain.usecase;

import io.reactivex.Single;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.ExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.PostExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.usecase.BaseSingleUseCaseContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.cotntract.data.UserSessionSettingsRepositoryContract;

/**
 * Created by Alexander Molochko on 1/8/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class GetExistingUserSessionIdUseCase extends BaseSingleUseCaseContract<Void, Long> {

    private final UserSessionSettingsRepositoryContract mSessionSettingsRepositoryContract;

    public GetExistingUserSessionIdUseCase(UserSessionSettingsRepositoryContract sessionSettingsRepositoryContract, ExecutionThread executionThread, PostExecutionThread postExecutionThread) {
        super(executionThread, postExecutionThread);
        mSessionSettingsRepositoryContract = sessionSettingsRepositoryContract;
    }

    @Override
    public Single<Long> provideSingleObservable(Void aVoid) {
        return mSessionSettingsRepositoryContract.getUserSessionId();
    }
}
