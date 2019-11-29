package ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.threading.postexecution;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.PostExecutionThread;

/**
 * Created by Alexander Molochko on 10/6/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class AndroidMainPostExecutionThread implements PostExecutionThread {
    @Override
    public Scheduler getScheduler() {
        return AndroidSchedulers.mainThread();
    }
}
