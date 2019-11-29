package ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.routing;

import android.os.Bundle;

import ua.com.crosp.solutions.hearthealthmonitor.R;
import ua.com.crosp.solutions.hearthealthmonitor.base.routing.BaseNavigationDrawerActivity;
import ua.com.crosp.solutions.hearthealthmonitor.common.contants.BundleConstants;
import ua.com.crosp.solutions.hearthealthmonitor.di.contract.ProvidesComponent;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.ExperimentResultRouterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.dicomponent.ExperimentResultsComponent;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.presentation.view.fragment.ExperimentResultDetailFragment;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.presentation.view.fragment.ExperimentResultsListFragment;

public class ExperimentResultsActivity extends BaseNavigationDrawerActivity implements
        ExperimentResultRouterContract,
        ProvidesComponent<ExperimentResultsComponent> {

    // Dependencies
    // Variables
    protected ExperimentResultsComponent mComponent;

    //region Lifecycle callbacks

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navigateToInitialScreen();
        injectDependencies();
        hideNavigationBar();
    }

    private void injectDependencies() {
        getComponent().inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setCurrentMenuItem(R.id.nav_menu_item_results);
    }
    //endregion

    //region Routing implementation

    @Override
    public void navigateToInitialScreen() {
        replaceFragment(ExperimentResultsListFragment.class, false);
    }

    @Override
    public void navigateBack() {
        onBackPressed();
    }

    @Override
    public void openExperimentDetailsScreen(Long modeId) {
        // Create bundle
        Bundle bundle = new Bundle();
        bundle.putLong(BundleConstants.Arguments.EXPERIMENT_RESULT_ID, modeId);
        replaceFragment(ExperimentResultDetailFragment.class, bundle);
        hideNavigationBar();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    @Override
    public void hideNavigationBar() {
        hideToolbar();
    }

    @Override
    public void showNavigationBar() {
        showToolbar();
    }

    @Override
    public void switchToEcgChartScreen(Long expId) {
        Bundle bundle = new Bundle();
        bundle.putLong(BundleConstants.Arguments.EXPERIMENT_RESULT_ID, expId);
        startNewActivity(EcgResultActivity.class, bundle);
    }
    //endregion
    //================================================================================
    // DI implementation
    //================================================================================

    @Override
    public ExperimentResultsComponent getComponent() {
        // Lazy initialization
        if (mComponent == null) {
            mComponent = getBaseActivityComponent().plusExperimentListComponent();
        }
        return mComponent;
    }


}
