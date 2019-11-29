package ua.com.crosp.solutions.hearthealthmonitor.di.component.infrastructure.module;

import android.content.Context;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import ua.com.crosp.solutions.hearthealthmonitor.di.named.NamedConstants;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerInfrastructure;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.filesystem.FileManager;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.filesystem.AndroidFileManager;

/**
 * Created by Alexander Molochko on 1/22/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
@PerInfrastructure
@Module
public class IoModule {

    @PerInfrastructure
    @Provides
    FileManager provideFileManager(@Named(NamedConstants.Context.GLOBAL_APP_CONTEXT) Context context) {
        return new AndroidFileManager(context);
    }

}
