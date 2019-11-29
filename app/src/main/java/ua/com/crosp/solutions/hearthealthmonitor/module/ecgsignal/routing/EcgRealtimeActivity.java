package ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.routing;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import io.reactivex.subjects.PublishSubject;
import ua.com.crosp.solutions.hearthealthmonitor.base.presentation.BackPressNotifier;
import ua.com.crosp.solutions.hearthealthmonitor.common.contants.BundleConstants;
import ua.com.crosp.solutions.hearthealthmonitor.di.contract.ProvidesComponent;
import ua.com.crosp.solutions.hearthealthmonitor.game.BaseFullscreenActivity;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.contract.EcgRealtimeRouterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.di.EcgRealtimeComponent;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.presentation.view.fragment.EcgRealTimeRecordingFragment;

public class EcgRealtimeActivity extends BaseFullscreenActivity implements
        EcgRealtimeRouterContract, BackPressNotifier,
        ProvidesComponent<EcgRealtimeComponent> {

    // Dependencies
    // Variables
    protected EcgRealtimeComponent mComponent;
    private long mExperimentId;
    private long mRecordingTime;
    private PublishSubject<Boolean> mBackPressSubject;

    //region Lifecycle callbacks

    @Override
    public int provideOrientation() {
        return ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parseArguments();
        navigateToInitialScreen();
        injectDependencies();
    }

    private void parseArguments() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mExperimentId = bundle.getLong(BundleConstants.Arguments.EXPERIMENT_RESULT_ID);
            mRecordingTime = bundle.getLong(BundleConstants.Arguments.ESTIMATED_RECORDING_TIME);
        }
    }

    private void injectDependencies() {
        getComponent().inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
    //endregion

    //region Routing implementation

    @Override
    public void navigateToInitialScreen() {
        switchToRealtimeRecordingScreen();
    }

    @Override
    public void navigateBack() {
        onBackPressed();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    //endregion
    //================================================================================
    // DI implementation
    //================================================================================

    @Override
    public EcgRealtimeComponent getComponent() {
        // Lazy initialization
        if (mComponent == null) {
            mComponent = getBaseActivityComponent().plusEcgRealtimeComponent();
        }
        return mComponent;
    }


    @Override
    public void switchToRealtimeRecordingScreen() {
        Bundle bundle = new Bundle();
        bundle.putLong(BundleConstants.Arguments.EXPERIMENT_RESULT_ID, mExperimentId);
        bundle.putLong(BundleConstants.Arguments.ESTIMATED_RECORDING_TIME, mRecordingTime);
        replaceFragment(EcgRealTimeRecordingFragment.class, bundle, false);

    }

    @Override
    public void onBackPressed() {
        if (mBackPressSubject != null) {
            mBackPressSubject.onNext(true);
        } else {
            super.onBackPressed();
           // finish();
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
