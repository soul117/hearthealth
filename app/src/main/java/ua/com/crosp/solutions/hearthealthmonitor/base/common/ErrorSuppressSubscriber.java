package ua.com.crosp.solutions.hearthealthmonitor.base.common;


import org.reactivestreams.Subscriber;

/**
 * Created by Alexander Molochko on 2/13/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public abstract class ErrorSuppressSubscriber<T> implements Subscriber<T> {
    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable e) {
    }
}