package ua.com.crosp.solutions.hearthealthmonitor.di.component.infrastructure.module;

import android.content.Context;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import ua.com.crosp.solutions.hearthealthmonitor.di.named.NamedConstants;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerInfrastructure;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.resource.ResourceProviderContract;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.resource.AndroidResourceProvider;

/**
 * Created by Alexander Molochko on 1/22/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
@PerInfrastructure
@Module
public class ContextModule {

    private final Context mContext;

    public ContextModule(@Named(NamedConstants.Context.GLOBAL_APP_CONTEXT) Context context) {
        mContext = context;
    }

    @PerInfrastructure
    @Provides
    @Named(NamedConstants.Context.GLOBAL_APP_CONTEXT)
    Context provideGloballAppContext() {
        return mContext;
    }

    @PerInfrastructure
    @Provides
    ResourceProviderContract provideResourceProvider(@Named(NamedConstants.Context.GLOBAL_APP_CONTEXT) Context context) {
        return new AndroidResourceProvider(context);
    }
}
