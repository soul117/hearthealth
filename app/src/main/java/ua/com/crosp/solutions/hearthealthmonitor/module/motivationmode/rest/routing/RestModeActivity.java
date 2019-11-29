package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.routing;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import io.reactivex.subjects.PublishSubject;
import ua.com.crosp.solutions.hearthealthmonitor.base.presentation.BackPressNotifier;
import ua.com.crosp.solutions.hearthealthmonitor.common.contants.BundleConstants;
import ua.com.crosp.solutions.hearthealthmonitor.di.contract.ProvidesComponent;
import ua.com.crosp.solutions.hearthealthmonitor.game.BaseFullscreenActivity;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.cotntract.RestModeRouterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.diassembly.RestModeComponent;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.view.RestModeExperimentFragment;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.view.RestModeSettingsFragment;

/**
 * Created by Alexander Molochko on 2/18/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class RestModeActivity extends BaseFullscreenActivity implements RestModeRouterContract, ProvidesComponent<RestModeComponent>,BackPressNotifier {
    // Variables
    protected RestModeComponent mRestModeComponent;
    private PublishSubject<Boolean> mBackPressSubject;
    @Override
    protected void onResume() {
        super.onResume();
        Bundle bundle = getIntent().getExtras();
        String launchMode = BundleConstants.Values.MODE_GAME_SCREEN;
        if (bundle != null) {
            launchMode = bundle.getString(BundleConstants.Arguments.MODE_LAUNCH_SCREEN);
        }
        switchToScreen(launchMode);
    }

    private void switchToScreen(String argument) {
        switch (argument) {
            case BundleConstants.Values.MODE_GAME_SCREEN:
                startRestModeExperiment();
                break;
            case BundleConstants.Values.MODE_SETTINGS_SCREEN:
                switchToSettings();
                break;
            default:
                switchToSettings();
                break;
        }
    }

    @Override
    public void startRestModeExperiment() {
        replaceFragment(RestModeExperimentFragment.class, false);
    }

    @Override
    public void switchToSettings() {
        replaceFragment(RestModeSettingsFragment.class, false);
    }

    @Override
    public void navigateToEcgRecordingActivity() {
        startActivity(new Intent("ua.com.crosp.solutions.ecgmonitor.START_ECG_RECORDING"));
    }

    @Override
    public void navigateToInitialScreen() {
        switchToSettings();
    }


    @Override
    public void navigateBack() {
        onBackPressed();
    }

    @Override
    public int provideOrientation() {
        return ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    }


    @Override
    public RestModeComponent getComponent() {
        // Lazy initialization
        if (mRestModeComponent == null) {
            mRestModeComponent = getBaseActivityComponent().plusRestModeComponent();
        }
        return mRestModeComponent;
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
