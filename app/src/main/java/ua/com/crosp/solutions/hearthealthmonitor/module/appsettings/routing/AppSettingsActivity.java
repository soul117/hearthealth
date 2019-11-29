package ua.com.crosp.solutions.hearthealthmonitor.module.appsettings.routing;

import android.os.Bundle;

import io.reactivex.subjects.PublishSubject;
import ua.com.crosp.solutions.hearthealthmonitor.R;
import ua.com.crosp.solutions.hearthealthmonitor.base.presentation.BackPressNotifier;
import ua.com.crosp.solutions.hearthealthmonitor.base.routing.BaseNavigationDrawerActivity;
import ua.com.crosp.solutions.hearthealthmonitor.di.contract.ProvidesComponent;
import ua.com.crosp.solutions.hearthealthmonitor.module.appsettings.AppSettingsFragment;
import ua.com.crosp.solutions.hearthealthmonitor.module.appsettings.contract.routing.AppSettingsRouter;
import ua.com.crosp.solutions.hearthealthmonitor.module.appsettings.di.AppSettingsComponent;

/**
 * Created by Alexander Molochko on 2/18/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class AppSettingsActivity extends BaseNavigationDrawerActivity implements ProvidesComponent<AppSettingsComponent>, AppSettingsRouter, BackPressNotifier {
    // Variables
    protected AppSettingsComponent mPAComponent;
    private PublishSubject<Boolean> mBackPressSubject;

    @Override
    protected void onResume() {
        super.onResume();
        hideToolbar();
    }

    @Override
    public void switchToSettings() {
        replaceFragment(AppSettingsFragment.class, false);
    }


    @Override
    public void navigateToInitialScreen() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCurrentMenuItem(R.id.nav_menu_item_app_settings);
        navigateToInitialScreen();
        switchToSettings();
    }

    @Override
    public void navigateBack() {
        onBackPressed();
    }


    @Override
    public AppSettingsComponent getComponent() {
        // Lazy initialization
        if (mPAComponent == null) {
            mPAComponent = getBaseActivityComponent().plusAppSettingsComponent();
        }
        return mPAComponent;
    }

    @Override
    public void onBackPressed() {
        if (mBackPressSubject != null) {
            mBackPressSubject.onNext(true);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public PublishSubject<Boolean> subscribeToBackPressEvents() {
        if (mBackPressSubject == null) {
            mBackPressSubject = PublishSubject.create();
        }
        return mBackPressSubject;
    }
}
