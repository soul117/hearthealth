package ua.com.crosp.solutions.hearthealthmonitor.di.component.service.module;

import android.app.Service;
import android.content.Context;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerService;

/**
 * Created by Alexander Molochko on 5/28/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
@PerService
@Module
public class ServiceModule {
    public static final String CONTEXT_SERVICE = "context.service";
    private final Service mService;

    public ServiceModule(Service service) {
        mService = service;
    }

    @Provides
    @PerService
    @Named(CONTEXT_SERVICE)
    Context provideActivityContext() {
        return this.mService;
    }

}
