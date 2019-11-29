package ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.debug;

import android.content.Context;

/**
 * Created by Alexander Molochko on 10/16/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class DebugManager {
    private final Context mAppContext;

    public DebugManager(Context applicationContext) {
        mAppContext = applicationContext;
    }

    public void initAllDebugFeatures() {
        initStethoDebug();
    }

    public void initStethoDebug() {
        /*Stetho.initialize(
                Stetho.newInitializerBuilder(mAppContext)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(mAppContext))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(mAppContext).build())
                        .build());*/
    }
}
