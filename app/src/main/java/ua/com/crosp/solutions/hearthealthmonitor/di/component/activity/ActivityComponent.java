package ua.com.crosp.solutions.hearthealthmonitor.di.component.activity;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;

import androidx.fragment.app.FragmentManager;

import javax.inject.Named;

import dagger.Subcomponent;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.ExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.PostExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.routing.BaseActivity;
import ua.com.crosp.solutions.hearthealthmonitor.base.routing.BaseAssemblyActivity;
import ua.com.crosp.solutions.hearthealthmonitor.base.routing.BaseNavigationDrawerActivity;
import ua.com.crosp.solutions.hearthealthmonitor.di.component.activity.module.ActivityModule;
import ua.com.crosp.solutions.hearthealthmonitor.di.component.activity.module.PermissionsModule;
import ua.com.crosp.solutions.hearthealthmonitor.di.component.activity.module.SubmodulesModule;
import ua.com.crosp.solutions.hearthealthmonitor.di.component.activity.module.ThreadingModule;
import ua.com.crosp.solutions.hearthealthmonitor.di.named.NamedConstants;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerActivity;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.permissions.PermissionsHandlerContract;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.settings.SettingsManager;
import ua.com.crosp.solutions.hearthealthmonitor.module.appsettings.di.AppSettingsComponent;
import ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.diassembly.DoctorListComponent;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.di.EcgComponent;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.di.EcgRealtimeComponent;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.dicomponent.EcgResultsComponent;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.dicomponent.ExperimentResultsComponent;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.dicomponent.ModesComponent;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.common.ApplicationNavigator;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.di.PhysicalActivityComponent;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.diassembly.PsychoEmotionalComponent;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.diassembly.RestModeComponent;
import ua.com.crosp.solutions.hearthealthmonitor.module.navigationdrawer.di.module.NavigationDrawerModule;
import ua.com.crosp.solutions.hearthealthmonitor.module.start.diassembly.StartComponent;
import ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.diassembly.UserSettingsComponent;

import static ua.com.crosp.solutions.hearthealthmonitor.di.component.activity.module.ActivityModule.CONTEXT_ACTIVITY;

/**
 * Created by Alexander Molochko on 1/22/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
@PerActivity
@Subcomponent(modules = {ActivityModule.class, NavigationDrawerModule.class, ThreadingModule.class, PermissionsModule.class, SubmodulesModule.class})
public interface ActivityComponent {

    public void inject(BaseActivity baseDrawerActivity);

    public void inject(BaseNavigationDrawerActivity baseDrawerActivity);

    public void inject(BaseAssemblyActivity baseAssemblyActivity);

    // Exposing activity to the children components
    Activity exposeActivity();

    @Named(NamedConstants.Fragment.SUPPORT_FRAGMENT_MANAGER)
    FragmentManager exposeFragmentManager();

    LayoutInflater exposeLayoutInflater();

    ApplicationNavigator exposeApplicationNavigator();

    // Exposing activity to the children components
    ExecutionThread exposeExecutionThread();

    PermissionsHandlerContract exposePermissionsHandlerCon();

    // Exposing activity to the children components
    PostExecutionThread exposePostExecutionThread();

    @Named(CONTEXT_ACTIVITY)
    Context exposeActivityContext();


    // Exposing resources to the children components
    SettingsManager exposeSettingsManager();

    // Subcomponents

    ModesComponent plusModesComponent();

    PhysicalActivityComponent plusPhysicalActivityComponent();

    StartComponent plusStartComponent();

    PsychoEmotionalComponent plusPsychoEmotionalComponent();

    UserSettingsComponent pluseUserSettingsComponent();

    DoctorListComponent plusDoctorListComponent();

    ExperimentResultsComponent plusExperimentListComponent();

    AppSettingsComponent plusAppSettingsComponent();

    RestModeComponent plusRestModeComponent();

    EcgComponent plusEcgComponent();

    EcgRealtimeComponent plusEcgRealtimeComponent();

    EcgResultsComponent plusEcgResultsComponent();
    // NavigationDrawerActivityComponent plusNavigationDrawerActivityComponent();
}
