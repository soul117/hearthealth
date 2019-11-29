package ua.com.crosp.solutions.hearthealthmonitor.base.domain.usecase;


import io.reactivex.Scheduler;
import io.reactivex.Single;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.ExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.PostExecutionThread;

/**
 * Created by Alexander Molochko on 12/19/16.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

public abstract class BaseSingleUseCaseContract<Params, Result> {
    private ExecutionThread mExecutionThread;
    private PostExecutionThread mPostExecutionThread;

    protected BaseSingleUseCaseContract(ExecutionThread executionThread, PostExecutionThread postExecutionThread) {
        mExecutionThread = executionThread;
        mPostExecutionThread = postExecutionThread;
    }

    public Single<Result> execute(Params parameters) {
        return provideSingleObservable(parameters)
                .subscribeOn(provideExecutionScheduler())
                .observeOn(providePostExecutionScheduler());
    }

    public Scheduler provideExecutionScheduler() {
        return mExecutionThread.getScheduler();
    }

    public Scheduler providePostExecutionScheduler() {
        return mPostExecutionThread.getScheduler();
    }

    public abstract Single<Result> provideSingleObservable(Params params);

}
