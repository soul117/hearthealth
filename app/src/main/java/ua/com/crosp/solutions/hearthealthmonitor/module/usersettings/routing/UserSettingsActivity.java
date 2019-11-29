package ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.routing;

import android.os.Bundle;

import javax.inject.Inject;

import ua.com.crosp.solutions.hearthealthmonitor.R;
import ua.com.crosp.solutions.hearthealthmonitor.base.routing.BaseSingleFragmentActivity;
import ua.com.crosp.solutions.hearthealthmonitor.di.contract.ProvidesMultipleComponents;
import ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.cotntract.router.DoctorListRouterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.diassembly.DoctorListComponent;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.routing.ModesActivity;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.common.MotivationalModeNavigator;
import ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.cotntract.router.UserSettingsRouterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.diassembly.UserSettingsComponent;
import ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.view.UserSettingsFragment;

public class UserSettingsActivity extends BaseSingleFragmentActivity implements
        UserSettingsRouterContract, DoctorListRouterContract,
        ProvidesMultipleComponents {

    // Dependencies
    @Inject
    MotivationalModeNavigator mMotivationalModeNavigator;
    // Variables
    protected UserSettingsComponent mUserSettingsComponent;
    private DoctorListComponent mDoctorListComponent;

//region Lifecycle callbacks

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideNavigationBar();
        injectDependencies();
        navigateToInitialScreen();
    }

    @Override
    public int getMainLayoutId() {
        return R.layout.activity_user_settings;
    }

    @Override
    public int getContentContainerId() {
        return R.id.linear_layout_content;
    }

    @Override
    public int getContentLayoutId() {
        return R.layout.content_user_settings;
    }

    @Override
    public int getFragmentContainerLayoutId() {
        return R.id.frame_layout_fragment_container;
    }

    private void injectDependencies() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
//endregion

//region Routing implementation

    @Override
    public void navigateToInitialScreen() {
        switchToSettings();
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
    public void hideNavigationBar() {
    }

    @Override
    public void showNavigationBar() {
    }

    @Override
    public void switchToMainAppScreen() {
        startNewActivityNewTask(ModesActivity.class);
    }
    //endregion

    @Override
    public void switchToSettings() {
        replaceFragment(UserSettingsFragment.class, false);
    }

    public UserSettingsComponent getUserSettingsComponent() {
        // Lazy initialization
        if (mUserSettingsComponent == null) {
            mUserSettingsComponent = getBaseActivityComponent().pluseUserSettingsComponent();
        }
        return mUserSettingsComponent;
    }


    public DoctorListComponent getDoctorListComponent() {
        // Lazy initialization
        if (mDoctorListComponent == null) {
            mDoctorListComponent = getBaseActivityComponent().plusDoctorListComponent();
        }
        return mDoctorListComponent;
    }

    @Override
    public <C> C getComponentByType(Class<C> componentType) {
        if (componentType == UserSettingsComponent.class) {
            return (C) getUserSettingsComponent();
        }
        if (componentType == DoctorListComponent.class) {
            return (C) getDoctorListComponent();
        }
        return null;
    }


}
