package ua.com.crosp.solutions.hearthealthmonitor.di.component.infrastructure.module;

import android.content.Context;
import android.content.res.AssetManager;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import ua.com.crosp.solutions.hearthealthmonitor.di.named.NamedConstants;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerInfrastructure;

/**
 * Created by Alexander Molochko on 1/22/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
@PerInfrastructure
@Module
public class AssetsModule {

    @PerInfrastructure
    @Provides
    AssetManager provideAssetManager(@Named(NamedConstants.Context.GLOBAL_APP_CONTEXT) Context context) {
        return context.getAssets();
    }

}
