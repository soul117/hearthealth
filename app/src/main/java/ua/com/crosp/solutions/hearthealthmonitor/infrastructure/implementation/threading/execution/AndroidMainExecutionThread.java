package ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.threading.execution;

import io.reactivex.Completable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.ExecutionThread;

/**
 * Created by Alexander Molochko on 10/6/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class AndroidMainExecutionThread implements ExecutionThread {
    @Override
    public Scheduler getScheduler() {
        return AndroidSchedulers.mainThread();
    }

    @Override
    public Completable execute(Runnable runnable) {
        return Completable.fromRunnable(runnable).subscribeOn(getScheduler());
    }

    @Override
    public Disposable executeDirect(Runnable runnable) {
        return getScheduler().scheduleDirect(runnable);
    }

    @Override
    public void executeBlocking(Runnable runnable) {

    }

}
