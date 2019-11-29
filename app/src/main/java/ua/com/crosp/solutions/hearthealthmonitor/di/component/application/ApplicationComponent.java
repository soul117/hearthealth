package ua.com.crosp.solutions.hearthealthmonitor.di.component.application;

import android.app.Application;
import android.content.Context;

import javax.inject.Named;

import dagger.Component;
import ua.com.crosp.solutions.hearthealthmonitor.application.HeartMonitorApplication;
import ua.com.crosp.solutions.hearthealthmonitor.di.component.activity.ActivityComponent;
import ua.com.crosp.solutions.hearthealthmonitor.di.component.activity.module.ActivityModule;
import ua.com.crosp.solutions.hearthealthmonitor.di.component.application.module.ApplicationModule;
import ua.com.crosp.solutions.hearthealthmonitor.di.component.application.module.ApplicationThreadingModule;
import ua.com.crosp.solutions.hearthealthmonitor.di.component.application.module.IconsModule;
import ua.com.crosp.solutions.hearthealthmonitor.di.component.application.module.NavigationModule;
import ua.com.crosp.solutions.hearthealthmonitor.di.component.application.module.UtilsModule;
import ua.com.crosp.solutions.hearthealthmonitor.di.component.application.module.infrastructure.GlobalServicesModule;
import ua.com.crosp.solutions.hearthealthmonitor.di.component.application.module.infrastructure.JsonMappperModule;
import ua.com.crosp.solutions.hearthealthmonitor.di.component.application.module.infrastructure.SciChartModule;
import ua.com.crosp.solutions.hearthealthmonitor.di.component.application.module.infrastructure.SettingsModule;
import ua.com.crosp.solutions.hearthealthmonitor.di.component.infrastructure.InfrastructureComponent;
import ua.com.crosp.solutions.hearthealthmonitor.di.component.persistance.module.RealmDbModule;
import ua.com.crosp.solutions.hearthealthmonitor.di.component.persistance.module.RepositoriesModule;
import ua.com.crosp.solutions.hearthealthmonitor.di.component.service.EcgServiceComponent;
import ua.com.crosp.solutions.hearthealthmonitor.di.component.service.module.ServiceModule;
import ua.com.crosp.solutions.hearthealthmonitor.di.module.debug.DebugModule;
import ua.com.crosp.solutions.hearthealthmonitor.di.module.mapper.MapperModule;
import ua.com.crosp.solutions.hearthealthmonitor.di.module.session.UserSessionModule;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerApplication;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.common.ApplicationNavigator;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.common.MotivationalModeNavigator;

import static ua.com.crosp.solutions.hearthealthmonitor.di.named.NamedConstants.Context.APPLICATION_CONTEXT;

/**
 * Created by Alexander Molochko on 1/8/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
@PerApplication // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = {
        ApplicationModule.class, IconsModule.class,
        RealmDbModule.class,
        UserSessionModule.class, MapperModule.class,
        RepositoriesModule.class, NavigationModule.class,
        RealmDbModule.class, ApplicationThreadingModule.class,
        GlobalServicesModule.class, RepositoriesModule.class,
        SciChartModule.class,
        DebugModule.class, SettingsModule.class,
        JsonMappperModule.class, MapperModule.class}, dependencies = InfrastructureComponent.class)

public interface ApplicationComponent {
    // Injection methods
    void inject(HeartMonitorApplication mainApplication);

    // Exposing application to the children components
    Application exposeApplication();

    // Exposing context to the children components
    @Named(APPLICATION_CONTEXT)
    Context exposeApplicationContext();

    // Exposing navigator to the children components
    ApplicationNavigator exposeNavigator();

    // Exposing mode navigator to the children components
    MotivationalModeNavigator exposeMotivationalModeNavigator();

    // Subcomponents
    ActivityComponent plus(ActivityModule activityModule);

    EcgServiceComponent plus(ServiceModule serviceModule);
}
