package ua.com.crosp.solutions.hearthealthmonitor.module.session.data.repository;

import javax.inject.Named;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.realm.Realm;
import io.realm.RealmResults;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.ExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.common.exception.data.SessionNotFoundException;
import ua.com.crosp.solutions.hearthealthmonitor.di.named.NamedConstants;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.database.BaseRealmRepository;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.cotntract.data.UserSessionDataProvider;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.cotntract.data.UserSessionE2MMapperContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.cotntract.data.UserSessionM2EMapperContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.cotntract.data.UserSessionRepositoryContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.data.model.UserSessionModel;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.data.model.UserSessionModelFields;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.domain.entity.UserSessionEntity;

/**
 * Created by Alexander Molochko on 4/23/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class UserSessionRepository extends BaseRealmRepository implements UserSessionRepositoryContract, UserSessionDataProvider {
    private Realm mRealmDb;
    private UserSessionE2MMapperContract mUserSessionE2MMapper;
    private UserSessionM2EMapperContract mUserSessionM2EMapper;

    public UserSessionRepository(Realm realmDb, UserSessionE2MMapperContract userSessionE2MMapper, UserSessionM2EMapperContract userSessionM2EMapper, @Named(NamedConstants.Threading.REALM_DB_OPERATION_THREAD) ExecutionThread realmThread) {
        super(realmThread);
        mRealmDb = realmDb;
        mUserSessionE2MMapper = userSessionE2MMapper;
        mUserSessionM2EMapper = userSessionM2EMapper;

    }


    @Override
    public Single<UserSessionEntity> getUserSession(final Long sessionId) {
        return wrappInRealmDbThread(new Single<UserSessionModel>() {
            @Override
            protected void subscribeActual(@NonNull SingleObserver<? super UserSessionModel> observer) {
                UserSessionModel sessionModel = mRealmDb.where(UserSessionModel.class)
                        .equalTo(UserSessionModelFields.SESSION_ID, sessionId).findFirst();
                if (null == sessionModel) {
                    observer.onError(new SessionNotFoundException("Session with id " + sessionId + " was not found"));
                } else {
                    observer.onSuccess(sessionModel);
                }
            }
        }.map(mUserSessionM2EMapper));
    }

    @Override
    public Single<Long> saveUserSession(final UserSessionEntity userSession) {
        return wrappInRealmDbThread(new Single<Long>() {
            @Override
            protected void subscribeActual(@NonNull SingleObserver<? super Long> observer) {
                mRealmDb.beginTransaction();
                UserSessionModel sessionModel = mUserSessionE2MMapper.transform(userSession);
                mRealmDb.copyToRealmOrUpdate(sessionModel);
                mRealmDb.commitTransaction();
                observer.onSuccess(userSession.getSessionId().getId());
            }
        });

    }

    @Override
    public Single<UserSessionEntity> createNewSession(final UserSessionEntity userSession) {
        return wrappInRealmDbThread(new Single<UserSessionEntity>() {
            @Override
            protected void subscribeActual(@NonNull SingleObserver<? super UserSessionEntity> observer) {
                UserSessionModel userSessionModel = mUserSessionE2MMapper.transform(userSession);
                mRealmDb.beginTransaction();
                mRealmDb.copyToRealmOrUpdate(userSessionModel);
                mRealmDb.commitTransaction();
                observer.onSuccess(userSession);
            }
        });
    }

    @Override
    public Single<Boolean> destroySession(final Long sessionId) {
        return wrappInRealmDbThread(new Single<Boolean>() {
            @Override
            protected void subscribeActual(@NonNull SingleObserver<? super Boolean> observer) {
                final RealmResults<UserSessionModel> results = mRealmDb.where(UserSessionModel.class)
                        .equalTo(UserSessionModelFields.SESSION_ID, sessionId)
                        .findAll();
                if (results.isEmpty()) {
                    observer.onSuccess(false);
                } else {
                    results.deleteAllFromRealm();
                    observer.onSuccess(true);
                }
            }
        });

    }


}
