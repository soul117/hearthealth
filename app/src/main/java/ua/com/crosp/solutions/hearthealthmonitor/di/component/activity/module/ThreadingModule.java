package ua.com.crosp.solutions.hearthealthmonitor.di.component.activity.module;

import dagger.Module;
import dagger.Provides;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.ExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.PostExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerActivity;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.threading.execution.IoExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.threading.postexecution.AndroidMainPostExecutionThread;

/**
 * Created by Alexander Molochko on 1/22/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
@Module
@PerActivity
public class ThreadingModule {

    @Provides
    @PerActivity
    ExecutionThread provideExecutionThread() {
        return new IoExecutionThread();
    }

    @Provides
    @PerActivity
    PostExecutionThread providePostExecutionThread() {
        return new AndroidMainPostExecutionThread();
    }
}
