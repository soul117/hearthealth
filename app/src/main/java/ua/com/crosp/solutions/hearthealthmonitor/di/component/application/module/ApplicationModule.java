package ua.com.crosp.solutions.hearthealthmonitor.di.component.application.module;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerApplication;

import static ua.com.crosp.solutions.hearthealthmonitor.di.named.NamedConstants.Context.APPLICATION_CONTEXT;
import static ua.com.crosp.solutions.hearthealthmonitor.di.named.NamedConstants.Environment.SANDBOX_PATH;

/**
 * Created by Alexander Molochko on 1/8/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

@Module
@PerApplication
public class ApplicationModule {

    Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    @PerApplication
    Application provideApplication() {
        return mApplication;
    }

    /**
     * Provice resources to dependents in the graph.
     */
    @Provides
    @PerApplication
    Resources provideResources() {
        return this.mApplication.getResources();
    }

    @Provides
    @PerApplication
    @Named(APPLICATION_CONTEXT)
    public Context provideApplicationContext() {
        return mApplication.getApplicationContext();
    }

    @Provides
    @PerApplication
    @Named(SANDBOX_PATH)
    public String provideSandboxPath() {
        return mApplication.getApplicationInfo().dataDir;
    }
}