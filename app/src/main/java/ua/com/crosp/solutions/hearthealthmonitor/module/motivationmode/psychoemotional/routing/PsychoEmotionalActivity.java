package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.routing;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import io.reactivex.subjects.PublishSubject;
import ua.com.crosp.solutions.hearthealthmonitor.base.presentation.BackPressNotifier;
import ua.com.crosp.solutions.hearthealthmonitor.base.routing.MotivationalModeRouterContract;
import ua.com.crosp.solutions.hearthealthmonitor.common.contants.BundleConstants;
import ua.com.crosp.solutions.hearthealthmonitor.di.contract.ProvidesComponent;
import ua.com.crosp.solutions.hearthealthmonitor.game.BaseFullscreenActivity;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.routing.EcgConnectionActivity;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.contract.routing.PERouterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.diassembly.PsychoEmotionalComponent;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.view.CatchTheBallGameFragment;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.view.PsychoEmotionalSettingsFragment;

/**
 * Created by Alexander Molochko on 2/18/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
public class PsychoEmotionalActivity extends BaseFullscreenActivity implements MotivationalModeRouterContract, ProvidesComponent<PsychoEmotionalComponent>, PERouterContract, BackPressNotifier {


    private PsychoEmotionalComponent mDiComponent;
    private PublishSubject<Boolean> mBackPressSubject;

    @Override
    protected void onResume() {
        super.onResume();
        Bundle bundle = getIntent().getExtras();
        String launchMode = bundle.getString(BundleConstants.Arguments.MODE_LAUNCH_SCREEN);
        switchToScreen(launchMode);
    }

    private void switchToScreen(String argument) {
        switch (argument) {
            case BundleConstants.Values.MODE_GAME_SCREEN:
                switchToGameScreen();
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
    public void switchToGameScreen() {
        replaceFragment(CatchTheBallGameFragment.class, false);
    }


    @Override
    public void startGame() {
        switchToGameScreen();
    }

    @Override
    public void switchToSettings() {
        replaceFragment(PsychoEmotionalSettingsFragment.class, false);
    }

    @Override
    public void navigateToEcgRecordingActivity(Long experimentResultId) {
        EcgConnectionActivity.EcgExperimentArguments params = new EcgConnectionActivity.EcgExperimentArguments();
        params.experimentResultId = experimentResultId;
        Bundle bundle = new Bundle();
        bundle.putSerializable(BundleConstants.Arguments.ECG_ACTIVITY, params);
        startNewActivityCleaBackStack(EcgConnectionActivity.class, bundle);
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
    public PsychoEmotionalComponent getComponent() {
        // Lazy initialization
        if (mDiComponent == null) {
            mDiComponent = getBaseActivityComponent().plusPsychoEmotionalComponent();
        }
        return mDiComponent;
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
