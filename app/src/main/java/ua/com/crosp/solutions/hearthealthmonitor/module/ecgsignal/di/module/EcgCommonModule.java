package ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.di.module;

import android.app.Activity;
import android.content.Context;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerModule;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.audio.receivers.HeadphonesPlugBroadcastReceiver;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.audio.receivers.HeadphonesPlugNotifier;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.contract.EcgRouterContract;

import static ua.com.crosp.solutions.hearthealthmonitor.di.named.NamedConstants.Context.APPLICATION_CONTEXT;

/**
 * Created by Alexander Molochko on 2/9/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
@Module
@PerModule
public class EcgCommonModule {

    @Provides
    @PerModule
    EcgRouterContract provideEcgRouter(Activity activity) {
        return (EcgRouterContract) activity;
    }


    @Provides
    @PerModule
    HeadphonesPlugNotifier provideHeadphonesPlugNotifier(@Named(APPLICATION_CONTEXT) Context context) {
        return new HeadphonesPlugBroadcastReceiver(context);
    }


}
