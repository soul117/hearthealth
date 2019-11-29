package ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.threading.execution;

import io.reactivex.Completable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.ExecutionThread;

/**
 * Created by Alexander Molochko on 10/6/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class IoExecutionThread implements ExecutionThread {
    @Override
    public Scheduler getScheduler() {
        return Schedulers.io();
    }

    @Override
    public Completable execute(Runnable runnable) {
        return null;
    }

    @Override
    public Disposable executeDirect(Runnable runnable) {
        return null;
    }

    @Override
    public void executeBlocking(Runnable runnable) {
    }

}
