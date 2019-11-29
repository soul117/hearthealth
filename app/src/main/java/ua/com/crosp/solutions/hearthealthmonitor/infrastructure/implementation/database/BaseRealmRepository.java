package ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.database;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Single;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.ExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.di.named.NamedConstants;

/**
 * Created by Alexander Molochko on 10/21/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class BaseRealmRepository {
    @Inject
    @Named(NamedConstants.Threading.REALM_DB_OPERATION_THREAD)
    protected
    ExecutionThread mRealmExecutionThread;

    public BaseRealmRepository(ExecutionThread realmExecutionThread) {
        mRealmExecutionThread = realmExecutionThread;
    }

    public <T> Single<T> wrappInRealmDbThread(Single<T> targetObservable) {
        return targetObservable.subscribeOn(mRealmExecutionThread.getScheduler());
    }

}
