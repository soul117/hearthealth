package ua.com.crosp.solutions.hearthealthmonitor.di.module.debug;

import android.content.Context;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerApplication;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.debug.DebugManager;

import static ua.com.crosp.solutions.hearthealthmonitor.di.named.NamedConstants.Context.APPLICATION_CONTEXT;

/**
 * Created by Alexander Molochko on 10/15/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
@Module
@PerApplication
public class DebugModule {
    @Provides
    @PerApplication
    public DebugManager provideDebugManager(@Named(APPLICATION_CONTEXT) Context applicationContext) {
        return new DebugManager(applicationContext);
    }
}
