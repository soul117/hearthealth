package ua.com.crosp.solutions.hearthealthmonitor.di.component.application.module;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.ExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.PostExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.di.named.NamedConstants;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerApplication;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.threading.execution.AndroidMainExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.threading.execution.IoExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.threading.execution.RealmExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.threading.postexecution.AndroidMainPostExecutionThread;

/**
 * Created by Alexander Molochko on 1/22/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
@Module
@PerApplication
public class ApplicationThreadingModule {

    @Provides
    @PerApplication
    @Named(NamedConstants.Threading.APPLICATION_EXECUTION_THREAD)
    ExecutionThread provideExecutionThread() {
        return new IoExecutionThread();
    }


    @Provides
    @PerApplication
    @Named(NamedConstants.Threading.APPLICATION_MAIN_EXECUTION_THREAD)
    ExecutionThread provideMainExecutionThread() {
        return new AndroidMainExecutionThread();
    }

    @Provides
    @Named(NamedConstants.Threading.APPLICATION_POST_EXECUTION_THREAD)
    @PerApplication
    PostExecutionThread providePostExecutionThread() {
        return new AndroidMainPostExecutionThread();
    }

    @Provides
    @Named(NamedConstants.Threading.REALM_DB_OPERATION_THREAD)
    @PerApplication
    ExecutionThread provideRealmOperationThread() {
        return new RealmExecutionThread();
    }
}
