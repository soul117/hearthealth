package ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.threading.execution;

import io.reactivex.Completable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.ExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util.Logger;

/**
 * Created by Alexander Molochko on 10/6/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class RealmExecutionThread implements ExecutionThread {
    @Override
    public Scheduler getScheduler() {
        return Schedulers.single();
    }

    @Override
    public Completable execute(Runnable runnable) {
        return Completable.fromRunnable(runnable).subscribeOn(getScheduler());
    }

    @Override
    public Disposable executeDirect(Runnable runnable) {
        return null;
    }

    @Override
    public void executeBlocking(Runnable runnable) {
        Logger.error("BEFORE BLOCKING CALL " + Thread.currentThread().getName());
        Completable.fromRunnable(runnable).subscribeOn(getScheduler()).blockingGet();
        Logger.error("AFTER BLOCKING CALL " + Thread.currentThread().getName());

    }

}
