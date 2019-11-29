package ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.routing;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import ua.com.crosp.solutions.hearthealthmonitor.common.contants.BundleConstants;
import ua.com.crosp.solutions.hearthealthmonitor.di.contract.ProvidesComponent;
import ua.com.crosp.solutions.hearthealthmonitor.game.BaseFullscreenActivity;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.EcgResultRouterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.dicomponent.EcgResultsComponent;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.presentation.view.fragment.EcgChartResultFragment;

public class EcgResultActivity extends BaseFullscreenActivity implements
        EcgResultRouterContract,
        ProvidesComponent<EcgResultsComponent> {

    // Dependencies
    // Variables
    protected EcgResultsComponent mComponent;
    private long mExperimentId;

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
        switchToEcgChartScreen(mExperimentId);
    }

    @Override
    public void navigateBack() {
        onBackPressed();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void switchToEcgChartScreen(Long expId) {
        Bundle bundle = new Bundle();
        bundle.putLong(BundleConstants.Arguments.EXPERIMENT_RESULT_ID, expId);
        replaceFragment(EcgChartResultFragment.class, bundle, false);
    }

    //endregion
    //================================================================================
    // DI implementation
    //================================================================================

    @Override
    public EcgResultsComponent getComponent() {
        // Lazy initialization
        if (mComponent == null) {
            mComponent = getBaseActivityComponent().plusEcgResultsComponent();
        }
        return mComponent;
    }
}
