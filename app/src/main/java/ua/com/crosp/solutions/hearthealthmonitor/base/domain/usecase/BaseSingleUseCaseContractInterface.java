package ua.com.crosp.solutions.hearthealthmonitor.base.domain.usecase;


import io.reactivex.Single;

/**
 * Created by Alexander Molochko on 12/19/16.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

public interface BaseSingleUseCaseContractInterface<Params, Result> {

    public Single<Result> execute(Params parameters);

    public abstract Single<Result> provideSingleObservable(Params params);

}
