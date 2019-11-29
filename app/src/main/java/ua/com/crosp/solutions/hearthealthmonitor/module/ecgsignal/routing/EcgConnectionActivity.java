package ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.routing;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import androidx.annotation.IntDef;

import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;
import ua.com.crosp.solutions.hearthealthmonitor.R;
import ua.com.crosp.solutions.hearthealthmonitor.base.routing.BaseNavigationDrawerActivity;
import ua.com.crosp.solutions.hearthealthmonitor.common.contants.BundleConstants;
import ua.com.crosp.solutions.hearthealthmonitor.common.notification.ToastNotifications;
import ua.com.crosp.solutions.hearthealthmonitor.di.contract.ProvidesComponent;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.permissions.PermissionsHandlerContract;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.audio.receivers.HeadphonesPlugNotifier;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.contract.EcgRouterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.di.EcgComponent;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.presentation.view.fragment.EcgMainFragment;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.presentation.view.fragment.HeadphonesPlugNotifierProvider;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.presentation.view.fragment.EcgChartResultFragment;

import static ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.routing.EcgConnectionActivity.EcgExperimentArguments.ECG_RESULT_SCREEN;
import static ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.routing.EcgConnectionActivity.EcgExperimentArguments.MAIN_SCREEN;
import static ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.routing.EcgConnectionActivity.EcgExperimentArguments.RECORDING_SCREEN;

/**
 * Created by Alexander Molochko on 5/28/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class EcgConnectionActivity extends BaseNavigationDrawerActivity implements EcgRouterContract, ProvidesComponent<EcgComponent>, HeadphonesPlugNotifierProvider {

    public static final String EXTRA_INITIAL_ORIENTATION = "orientation";
    private EcgComponent mComponent;
    private EcgExperimentArguments mArguments;
    @Inject
    PermissionsHandlerContract mPermissionsHandler;
    @Inject
    HeadphonesPlugNotifier mHeadphonesPlugNotifier;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDependencies();
        parseAttributes();
        checkPermissions();
        hideToolbar();
    }

    private void checkPermissions() {
        mPermissionsHandler.requestPermission(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            switchToScreen();
                        } else {
                            ToastNotifications.showErrorMessage(getApplicationContext(), getString(R.string.message_error_permission_record));
                            finish();
                        }
                    }
                });
    }

    private void injectDependencies() {
        getComponent().inject(this);
    }

    private void switchToScreen() {
        if (null == mArguments) {
            switchToMainEcgScreen();
        } else {
            switchToScreenByType(mArguments.initialScreen);
        }
    }


    private void switchToScreenByType(@EcgExperimentArguments.InitialScreen int initialScreen) {
        switch (initialScreen) {
            case MAIN_SCREEN:
                switchToMainEcgScreen();
                break;
            case ECG_RESULT_SCREEN:
                switchToResultScreen();
                break;
            case RECORDING_SCREEN:
                switchToRealtimeRecordingScreen(0);
                break;
            default:
                switchToMainEcgScreen();
                break;
        }
    }

    private void parseAttributes() {
        Bundle bundleArguments = getIntent().getExtras();
        if (bundleArguments != null) {
            mArguments = (EcgExperimentArguments) bundleArguments.getSerializable(BundleConstants.Arguments.ECG_ACTIVITY);
        }
    }

    @Override
    protected void onPause() {
        try {
            unregisterReceiver(mHeadphonesPlugNotifier);
        } catch (Exception ex) {

        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        registerHeadphonePlugReceiver();
        super.onResume();
    }

    private void registerHeadphonePlugReceiver() {
        IntentFilter filter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        registerReceiver(mHeadphonesPlugNotifier, filter);
    }

    @Override
    public void navigateToInitialScreen() {
        switchToMainEcgScreen();
    }

    @Override
    public void navigateBack() {
        onBackPressed();
    }


    @Override
    public void switchToMainEcgScreen() {
        replaceFragment(EcgMainFragment.class, false);
    }


    @Override
    public void switchToResultScreen() {
        replaceFragment(EcgChartResultFragment.class, false);
    }

    @Override
    public void switchToRealtimeRecordingScreen(long estimatedTime) {
        if (mArguments != null) {
            Bundle bundle = new Bundle();
            bundle.putLong(BundleConstants.Arguments.EXPERIMENT_RESULT_ID, mArguments.experimentResultId);
            bundle.putLong(BundleConstants.Arguments.ESTIMATED_RECORDING_TIME, estimatedTime);
            startNewActivityCleaBackStack(EcgRealtimeActivity.class, bundle);
        } else {
            Bundle bundle = new Bundle();
            bundle.putLong(BundleConstants.Arguments.ESTIMATED_RECORDING_TIME, estimatedTime);
            startNewActivityCleaBackStack(EcgRealtimeActivity.class, bundle);
        }
    }

    @Override
    public EcgComponent getComponent() {
        // Lazy initialization
        if (mComponent == null) {
            mComponent = getBaseActivityComponent().plusEcgComponent();
        }
        return mComponent;
    }

    @Override
    public HeadphonesPlugNotifier provideHeadphonesPlugNotifier() {
        return mHeadphonesPlugNotifier;
    }

    @Override
    public void unsubscribeFromHeadphonePlugEvents() {
        try {
            unregisterReceiver(mHeadphonesPlugNotifier);
        } catch (Exception ex) {

        }
    }

    public static class EcgExperimentArguments implements Serializable {
        @InitialScreen
        public int initialScreen;
        public Long experimentResultId;

        public static final int MAIN_SCREEN = 0;
        public static final int RECORDING_SCREEN = 1;
        public static final int ECG_RESULT_SCREEN = 2;

        @IntDef({MAIN_SCREEN, RECORDING_SCREEN, ECG_RESULT_SCREEN})
        @Retention(RetentionPolicy.SOURCE)
        public @interface InitialScreen {
        }

    }
}
