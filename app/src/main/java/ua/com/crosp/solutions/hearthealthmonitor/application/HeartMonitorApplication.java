package ua.com.crosp.solutions.hearthealthmonitor.application;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import androidx.multidex.MultiDex;

import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;
import com.squareup.leakcanary.LeakCanary;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import ua.com.crosp.solutions.hearthealthmonitor.BuildConfig;
import ua.com.crosp.solutions.hearthealthmonitor.common.model.StartServiceBundle;
import ua.com.crosp.solutions.hearthealthmonitor.di.component.application.ApplicationComponent;
import ua.com.crosp.solutions.hearthealthmonitor.di.component.application.DaggerApplicationComponent;
import ua.com.crosp.solutions.hearthealthmonitor.di.component.application.module.ApplicationModule;
import ua.com.crosp.solutions.hearthealthmonitor.di.component.infrastructure.DaggerInfrastructureComponent;
import ua.com.crosp.solutions.hearthealthmonitor.di.component.infrastructure.InfrastructureComponent;
import ua.com.crosp.solutions.hearthealthmonitor.di.component.infrastructure.module.ContextModule;
import ua.com.crosp.solutions.hearthealthmonitor.di.component.persistance.module.RealmDbModule;
import ua.com.crosp.solutions.hearthealthmonitor.di.named.NamedConstants;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.threading.execution.RealmExecutionThread;

/**
 * Created by Alexander Molochko on 12/18/16.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

public class HeartMonitorApplication extends Application {

    // Variables
    // Topmost Application Component ( a.k.a Singleton)
    private ApplicationComponent mApplicationComponent;
    // User session component
    @Inject
    @Named(NamedConstants.Markers.SCI_CHART_LICENSED)
    Boolean mSciChartIsRegistered;
    private RealmDbModule mRealmModule;

    //================================================================================
    // Static methods
    //================================================================================
    // Get Application from anywhere the context is available
    public static HeartMonitorApplication from(Context callingContext) {
        return (HeartMonitorApplication) callingContext.getApplicationContext();
    }

    public static String getPackageNameString() {
        return BuildConfig.APPLICATION_ID;
    }

    //================================================================================
    // Lifecycle callbacks
    //================================================================================
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
      //  LeakCanary.install(this);
        mRealmModule = new RealmDbModule(this, new RealmExecutionThread());
        mApplicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .infrastructureComponent(buildInfrastructureComponent())
                .realmDbModule(mRealmModule)
                .build();
        mApplicationComponent.inject(this);
    }

    private InfrastructureComponent buildInfrastructureComponent() {
        return DaggerInfrastructureComponent
                .builder()
                .contextModule(new ContextModule(this))
                .build();
    }

    /**
     * Start Services, that should live as long as application does
     * Automatically invoked by Dagger 2
     *
     * @param servicesToStart list of services to start
     */
    @Inject
    protected void startServices(List<StartServiceBundle> servicesToStart) {
        for (int i = 0; i < servicesToStart.size(); i++) {
            StartServiceBundle startServiceBundle = servicesToStart.get(i);
            Intent serviceIntent = new Intent(this, startServiceBundle.serviceClass);
            serviceIntent.putExtras(startServiceBundle.serviceArguments);
            startService(serviceIntent);
        }
    }

    /**
     * Injecting font icon, to be used in the app
     * Automatically invoked by Dagger 2
     *
     * @param descriptorList list of icons to use in the app
     */
    @Inject
    protected void initFontIcons(List<IconFontDescriptor> descriptorList) {
        for (int i = 0; i < descriptorList.size(); i++) {
            Iconify.with(descriptorList.get(i));
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        mRealmModule.onTerminate();
    }
    //================================================================================
    // Public methods
    //================================================================================

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }


}