package ua.com.crosp.solutions.hearthealthmonitor.module.session.manager;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.valueobject.EntityId;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.cotntract.UserSessionManagerContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.domain.entity.UserEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.domain.entity.UserSessionEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.domain.usecase.CreateUserSessionFromUserUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.domain.usecase.DestroyUserSessionUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.domain.usecase.GetExistingUserSessionIdUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.domain.usecase.GetUserSessionUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.domain.usecase.SaveExistingUserSessionIdUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.domain.usecase.SaveUserSessionUseCase;

/**
 * Created by Alexander Molochko on 10/15/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class UserSessionManager implements UserSessionManagerContract {
    // Dependencies

    // Use cases
    private CreateUserSessionFromUserUseCase mCreateUserSessionFromUserUseCase;
    private SaveUserSessionUseCase mSaveUserSessionUseCase;
    private GetUserSessionUseCase mGetUserSessionUseCase;
    private DestroyUserSessionUseCase mDestroyUserSessionUseCase;
    private SaveExistingUserSessionIdUseCase mSaveExistingUserSessionIdUseCase;
    private GetExistingUserSessionIdUseCase mGetExistingUserSessionIdUseCase;
    // Entities
    private UserSessionEntity mCurrentUserSession;
    private Long mUserSessionId;

    public UserSessionManager(CreateUserSessionFromUserUseCase initDefaultUserSessionUseCase, SaveUserSessionUseCase saveUserSessionUseCase, GetUserSessionUseCase getUserSessionUseCase, DestroyUserSessionUseCase destroyUserSessionUseCase, SaveExistingUserSessionIdUseCase saveUserSettingsUseCase, GetExistingUserSessionIdUseCase getExistingUserSessionIdUseCase) {
        mCreateUserSessionFromUserUseCase = initDefaultUserSessionUseCase;
        mSaveUserSessionUseCase = saveUserSessionUseCase;
        mGetUserSessionUseCase = getUserSessionUseCase;
        mDestroyUserSessionUseCase = destroyUserSessionUseCase;
        mSaveExistingUserSessionIdUseCase = saveUserSettingsUseCase;
        mGetExistingUserSessionIdUseCase = getExistingUserSessionIdUseCase;
    }

    @Override
    public Single<Boolean> loadPreviousSession() {
        return mGetExistingUserSessionIdUseCase.execute(null)
                .flatMap(new Function<Long, SingleSource<UserSessionEntity>>() {
                    @Override
                    public SingleSource<UserSessionEntity> apply(@NonNull Long sessionId) throws Exception {
                        mUserSessionId = sessionId;
                        if (!EntityId.INVALID_ENTITY_ID.getId().equals(sessionId)) {
                            return mGetUserSessionUseCase.execute(sessionId);
                        }
                        return Single.just(UserSessionEntity.INVALID);
                    }
                })
                .map(new Function<UserSessionEntity, Boolean>() {
                    @Override
                    public Boolean apply(@NonNull UserSessionEntity userSessionEntity) throws Exception {
                        mCurrentUserSession = userSessionEntity;
                        return UserSessionEntity.isValidSession(mCurrentUserSession);
                    }
                });
    }

    @Override
    public UserSessionEntity getCurrentUserSession() {
        return mCurrentUserSession;

    }

    @Override
    public UserEntity getCurrentSessionUserEntity() {
        return UserSessionEntity.isValidSession(mCurrentUserSession) ? mCurrentUserSession.getUserEntity() : null;
    }

    @Override
    public Single<Long> destroyCurrentUserSession() {
        return mDestroyUserSessionUseCase.execute(mUserSessionId)
                .flatMap(new Function<Boolean, Single<Long>>() {
                    @Override
                    public Single<Long> apply(@NonNull Boolean aBoolean) throws Exception {
                        mCurrentUserSession = UserSessionEntity.INVALID;
                        return mSaveExistingUserSessionIdUseCase.execute(UserSessionEntity.INVALID.getSessionId().getId());
                    }
                });
    }


    @Override
    public boolean hasPreviousSession() {
        return mCurrentUserSession != null && !mUserSessionId.equals(EntityId.INVALID_ENTITY_ID.getId());
    }


    @Override
    public Single<Long> setUserForSession(final UserEntity userEntity) {
        if (hasPreviousSession()) {
            mCurrentUserSession.setUserEntity(userEntity);
            return mSaveUserSessionUseCase.execute(mCurrentUserSession);
        } else {
            return mCreateUserSessionFromUserUseCase.execute(userEntity)
                    .flatMap(new Function<UserSessionEntity, SingleSource<? extends Long>>() {
                        @Override
                        public SingleSource<? extends Long> apply(@NonNull UserSessionEntity userSessionEntity) throws Exception {
                            mCurrentUserSession = userSessionEntity;
                            mUserSessionId = mCurrentUserSession.getSessionId().getId();
                            return Single.just(mCurrentUserSession.getSessionId().getId());
                        }
                    })
                    .flatMap(new Function<Long, SingleSource<? extends Long>>() {
                        @Override
                        public SingleSource<? extends Long> apply(@NonNull Long sessionId) throws Exception {
                            return mSaveExistingUserSessionIdUseCase.execute(sessionId);
                        }
                    });
        }

    }

    @Override
    public EntityId getCurrentUserEntityId() {
        return mCurrentUserSession.getUserEntity().getEntityId();
    }

}
