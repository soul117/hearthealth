package ua.com.crosp.solutions.hearthealthmonitor.di.component.activity.module;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import ua.com.crosp.solutions.hearthealthmonitor.di.named.NamedConstants;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerActivity;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerApplication;
import ua.com.crosp.solutions.hearthealthmonitor.module.appsettings.contract.data.AppSettingsRepositoryContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.appsettings.data.AppSettingsModel;

/**
 * Created by Alexander Molochko on 1/22/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
@Module
@PerActivity
public class ActivityModule {
    private final AppCompatActivity mActivity;
    public static final String CONTEXT_ACTIVITY = "context.activity";

    public ActivityModule(AppCompatActivity activity) {
        this.mActivity = activity;
    }

    @Provides
    @PerActivity
    @Named(CONTEXT_ACTIVITY)
    Context provideActivityContext() {
        return this.mActivity;
    }

    /**
     * Provide the activity to dependents in the graph.
     */
    @Provides
    @PerActivity
    Activity provideActivity() {
        return this.mActivity;
    }


    /**
     * Provide the layout inflater to dependents in the graph.
     */
    @Provides
    @PerActivity
    LayoutInflater provideLayoutInflater() {
        return LayoutInflater.from(mActivity);
    }

    /**
     * Provide the layout inflater to dependents in the graph.
     */
    @Provides
    @Named(NamedConstants.Fragment.SUPPORT_FRAGMENT_MANAGER)
    @PerActivity
    FragmentManager provideSupportFragmentManager() {
        return mActivity.getSupportFragmentManager();
    }

    @Provides
    @PerActivity
    public AppSettingsModel provideAppSettings(AppSettingsRepositoryContract repositoryContract) {
        return repositoryContract.getAppSettings().blockingGet();
    }

    @Provides
    @Named(NamedConstants.Fragment.DEFAULT_FRAGMENT_MANAGER)
    @PerActivity
    android.app.FragmentManager provideFragmentManager() {
        return mActivity.getFragmentManager();
    }
}
