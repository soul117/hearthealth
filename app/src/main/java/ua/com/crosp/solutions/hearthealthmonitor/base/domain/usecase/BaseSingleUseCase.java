package ua.com.crosp.solutions.hearthealthmonitor.base.domain.usecase;


import io.reactivex.Single;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.ExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.PostExecutionThread;

/**
 * Created by Alexander Molochko on 12/19/16.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

public abstract class BaseSingleUseCase<Params, Result> extends BaseUseCase implements BaseSingleUseCaseContractInterface<Params, Result> {
    public BaseSingleUseCase(ExecutionThread threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
    }

    public Single<Result> execute(Params parameters) {
        return provideSingleObservable(parameters)
                .subscribeOn(mThreadExecutor.getScheduler())
                .observeOn(mPostExecutionThread.getScheduler());
    }

    public abstract Single<Result> provideSingleObservable(Params params);

}
