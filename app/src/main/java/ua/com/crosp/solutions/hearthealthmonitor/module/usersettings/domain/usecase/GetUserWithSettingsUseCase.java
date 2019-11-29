package ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.domain.usecase;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.ExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.PostExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.usecase.BaseSingleUseCaseContract;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.valueobject.EntityId;
import ua.com.crosp.solutions.hearthealthmonitor.common.exception.domain.AtLeastOneUserIsRequiredToBeSet;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.cotntract.UserSessionManagerContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.domain.entity.UserEntity;

/**
 * Created by Alexander Molochko on 1/8/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class GetUserWithSettingsUseCase extends BaseSingleUseCaseContract<EntityId, UserEntity> {

    private final UserSessionManagerContract mSessionManager;

    public GetUserWithSettingsUseCase(UserSessionManagerContract sessionManager,
                                      PostExecutionThread postExecutionThread, ExecutionThread executionThread) {
        super(executionThread, postExecutionThread);
        mSessionManager = sessionManager;
    }


    @Override
    public Single<UserEntity> provideSingleObservable(EntityId entityId) {
        return new Single<UserEntity>() {
            @Override
            protected void subscribeActual(@NonNull SingleObserver<? super UserEntity> observer) {
                UserEntity currentUser = mSessionManager.getCurrentSessionUserEntity();
                if (null == currentUser) {
                    observer.onError(new AtLeastOneUserIsRequiredToBeSet("No User is set"));
                } else {
                    observer.onSuccess(currentUser);
                }
            }
        };
    }
}

