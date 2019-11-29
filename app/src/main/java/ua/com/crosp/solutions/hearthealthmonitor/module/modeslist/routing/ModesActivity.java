package ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.routing;

import android.os.Bundle;

import javax.inject.Inject;

import ua.com.crosp.solutions.hearthealthmonitor.R;
import ua.com.crosp.solutions.hearthealthmonitor.base.routing.BaseNavigationDrawerActivity;
import ua.com.crosp.solutions.hearthealthmonitor.common.contants.BundleConstants;
import ua.com.crosp.solutions.hearthealthmonitor.di.contract.ProvidesComponent;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.settings.BundleBuilder;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.contract.ModesRouterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.dicomponent.ModesComponent;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.presentation.view.ModeDescriptionFragment;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.presentation.view.ModesListFragment;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.common.MotivationalModeNavigator;

public class ModesActivity extends BaseNavigationDrawerActivity implements
        ModesRouterContract,
        ProvidesComponent<ModesComponent> {

    // Dependencies
    @Inject
    MotivationalModeNavigator mMotivationalModeNavigator;
    // Variables
    protected ModesComponent mModesComponent;

    //region Lifecycle callbacks

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navigateToInitialScreen();
        injectDependencies();
    }

    private void injectDependencies() {
        getComponent().inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setCurrentMenuItem(R.id.nav_menu_item_modes_list);
    }
    //endregion

    //region Routing implementation

    @Override
    public void navigateToInitialScreen() {
        replaceFragment(ModesListFragment.class, false);
    }

    @Override
    public void navigateBack() {
        onBackPressed();
    }

    @Override
    public void openModeDescription(int modeId) {
        // Create bundle
        Bundle bundle = new Bundle();
        bundle.putInt(BundleConstants.Arguments.MODE_ID, modeId);
        replaceFragment(ModeDescriptionFragment.class, bundle);
    }

    @Override
    public void startMotivationalMode(int modeId) {
        Class motivationalModuleClass = mMotivationalModeNavigator.getModeActivityClassById(modeId);

        Bundle bundle = new BundleBuilder()
                .putString(BundleConstants.Arguments.MODE_LAUNCH_SCREEN, BundleConstants.Values.MODE_GAME_SCREEN)
                .build();
        startNewActivity(motivationalModuleClass, bundle);
    }

    @Override
    public void openMotivationalModeSettings(int modeId) {
        Class motivationalModuleClass = mMotivationalModeNavigator.getModeActivityClassById(modeId);
        Bundle bundle = new BundleBuilder()
                .putString(BundleConstants.Arguments.MODE_LAUNCH_SCREEN, BundleConstants.Values.MODE_SETTINGS_SCREEN)
                .build();
        startNewActivity(motivationalModuleClass, bundle);
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
    //endregion
    //================================================================================
    // DI implementation
    //================================================================================

    @Override
    public ModesComponent getComponent() {
        // Lazy initialization
        if (mModesComponent == null) {
            mModesComponent = getBaseActivityComponent().plusModesComponent();
        }
        return mModesComponent;
    }


}
