package ua.com.crosp.solutions.hearthealthmonitor.base.routing;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import ua.com.crosp.solutions.hearthealthmonitor.R;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.valueobject.Gender;
import ua.com.crosp.solutions.hearthealthmonitor.base.routing.interfaces.BaseMenuRouter;
import ua.com.crosp.solutions.hearthealthmonitor.base.routing.interfaces.BaseRouter;
import ua.com.crosp.solutions.hearthealthmonitor.base.routing.interfaces.ToolbarController;
import ua.com.crosp.solutions.hearthealthmonitor.common.view.NavigationDrawerListItem;
import ua.com.crosp.solutions.hearthealthmonitor.module.navigationdrawer.contract.presentation.NavigationDrawerPresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.navigationdrawer.contract.routing.NavigationDrawerRouterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.navigationdrawer.contract.view.NavigationDrawerViewContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.navigationdrawer.view.model.UserDrawerInfo;
import ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.routing.UserSettingsActivity;

/**
 * Created by Alexander Molochko on 1/9/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public abstract class BaseNavigationDrawerActivity extends BaseSingleFragmentActivity implements NavigationDrawerViewContract, NavigationDrawerRouterContract, BaseRouter, BaseMenuRouter, ToolbarController {

    public static final String USER_INFO_FORMAT = "%d | %s | %d y.o.";
    // Views
    @BindView(R.id.drawer_layout)
    DrawerLayout mMainDrawerLayout;

    @BindView(R.id.toolbar_main)
    Toolbar mMainToolbar;

    @BindView(R.id.scroll_view_navigation_drawer_content)
    ScrollView mDrawerContainerScrollView;

    @BindView(R.id.navigation_view_left)
    NavigationView mLeftNavigationView;

    @BindView(R.id.linear_layout_navigation_drawer_content)
    LinearLayout mDrawerContentLinearLayout;

    @Inject
    NavigationDrawerPresenterContract mNavigationDrawerPresenter;

    //region Navigation Drawer views

    TextView mTextViewFirstName, mTextViewLastName, mTextViewUserInfo;
    CircleImageView mCircleImageViewAvatar;

    //endregion

    public View mDrawerHeaderView;

    View mDrawerMainLayoutView;

    LinearLayout mDrawerMenuItemsLinearLayout;


    // UI Variables
    ActionBar mActionBar;
    ActionBarDrawerToggle mDrawerToggle;

    // Variables
    private Map<Integer, NavigationDrawerListItem> mMenuItemsMap = new HashMap<>();
    private int mCurrentMenuItemId;

    // Listeners

    View.OnClickListener mMenuItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            clearMenuSelection();
            mCurrentMenuItemId = v.getId();
            setCurrentMenuItem(mCurrentMenuItemId);
            navigateTo(mCurrentMenuItemId);
        }
    };


    //================================================================================
    // Lifecycle callbacks
    //================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inject views
        ButterKnife.bind(this);
        initUI();
        mActivityComponent.inject(this);
        notifyPresenter();
    }

    private void notifyPresenter() {
        mNavigationDrawerPresenter.setView(this);
        mNavigationDrawerPresenter.onViewReady();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
    //================================================================================
    // UI methods
    //================================================================================

    // Template method
    protected void initUI() {
        initToolbar();
        initNavigationDrawer();
    }

    protected void initNavigationDrawer() {
        mDrawerToggle = createDrawerToggle(mMainDrawerLayout, mMainToolbar);
        mMainDrawerLayout.addDrawerListener(mDrawerToggle);
        LayoutInflater layoutInflater = provideLayoutInflater();
        mDrawerHeaderView = layoutInflater.inflate(getDrawerHeaderLayoutId(), mDrawerContentLinearLayout, true);
        mDrawerMainLayoutView = layoutInflater.inflate(getDrawerMainLayoutId(), mDrawerContentLinearLayout, true);
        mDrawerMenuItemsLinearLayout = mDrawerMainLayoutView.findViewById(R.id.linear_layout_nav_items_container);
        findDrawerHeaderViews();
        setNavigationDrawerListeners();
        collectMenuItems();
        setupDrawerItems();
    }

    protected void findDrawerHeaderViews() {
        mTextViewFirstName = mDrawerHeaderView.findViewById(R.id.text_view_user_first_name);
        mTextViewLastName = mDrawerHeaderView.findViewById(R.id.text_view_user_last_name);
        mTextViewUserInfo = mDrawerHeaderView.findViewById(R.id.text_view_user_info);
        mCircleImageViewAvatar = mDrawerHeaderView.findViewById(R.id.circle_image_view_user_logo);
    }


    protected void collectMenuItems() {
        for (int i = 0; i < mDrawerMenuItemsLinearLayout.getChildCount(); i++) {
            NavigationDrawerListItem menuItem = (NavigationDrawerListItem) mDrawerMenuItemsLinearLayout.getChildAt(i);
            mMenuItemsMap.put(menuItem.getId(), menuItem);
        }
    }

    protected void initToolbar() {
        setSupportActionBar(mMainToolbar);
        mActionBar = getSupportActionBar();
    }

    protected void clearMenuSelection() {
        for (Map.Entry<Integer, NavigationDrawerListItem> entry : mMenuItemsMap.entrySet()) {
            entry.getValue().setStateNormal();
        }
    }

    protected void setupDrawerItems() {
        for (Map.Entry<Integer, NavigationDrawerListItem> entry : mMenuItemsMap.entrySet()) {
            entry.getValue().setOnClickListener(mMenuItemClickListener);
        }
    }

    protected ActionBarDrawerToggle createDrawerToggle(DrawerLayout drawerLayout, Toolbar toolbar) {
        return new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
    }

    //================================================================================
    // Navigation Drawer implementation
    //================================================================================

    @Override
    public void displayCurrentUserInfo(UserDrawerInfo userDrawerInfo) {
        mTextViewFirstName.setText(userDrawerInfo.firstName);
        mTextViewLastName.setText(userDrawerInfo.lastName);
        userDrawerInfo.gender = userDrawerInfo.gender.contentEquals(Gender.GENDER_FEMALE) ? getString(R.string.female) : getString(R.string.male);
        mTextViewUserInfo.setText(String.format(USER_INFO_FORMAT, userDrawerInfo.personalId, userDrawerInfo.gender, userDrawerInfo.age));
    }

    private void setNavigationDrawerListeners() {
        mCircleImageViewAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNavigationDrawerPresenter.onUserAvatarClick();
            }
        });
    }

    //================================================================================
    // Routing implementation
    //================================================================================

    @Override
    public void setCurrentMenuItem(@IdRes int menuItemId) {
        NavigationDrawerListItem menuItem = mMenuItemsMap.get(menuItemId);
        if (menuItem != null) {
            clearMenuSelection();
            menuItem.setStateSelected();
        }
    }

    @Override
    public void switchToUserSettings() {
        startNewActivityClearTop(UserSettingsActivity.class);
    }

    //================================================================================
    // Navigation implementation
    //================================================================================
    // Navigation between main app components
    @Override
    public void navigateTo(int menuItemId) {
        Class activityClass = mApplicationNavigator.getActivityModuleClassById(menuItemId);
        startNewActivitySingleTop(activityClass);
    }

    @Override
    public void navigateToInitialModule() {
        // Go to initial screen
        navigateTo(mApplicationNavigator.getInitialModuleId());
    }
    //================================================================================
    // Override for layout inflating
    //================================================================================

    @Override
    public int getMainLayoutId() {
        return R.layout.activity_base_drawer;
    }

    @Override
    public int getContentLayoutId() {
        return R.layout.content_toolbar_single_fragment;
    }

    @Override
    public int getFragmentContainerLayoutId() {
        return R.id.frame_layout_fragment_container;
    }

    @Override
    public int getContentContainerId() {
        return R.id.linear_layout_content;
    }

    // Drawer specific methods

    @LayoutRes
    public int getDrawerHeaderLayoutId() {
        return R.layout.navigation_drawer_header;
    }

    @LayoutRes
    public int getDrawerMainLayoutId() {
        return R.layout.navigation_drawer_main;
    }

    //================================================================================
    // ToolbarController implementation
    //================================================================================


    @Override
    public String getToolbarTitle() {
        return mMainToolbar.getTitle().toString();
    }

    @Override
    public Toolbar getToolbar() {
        return mMainToolbar;
    }

    @Override
    public void setToolbarTitle(String title) {
        mMainToolbar.setTitle(title);
    }

    @Override
    public void hideToolbar() {
        if (mActionBar != null) {
            mActionBar.hide();
        }
    }

    @Override
    public void showToolbar() {
        if (mActionBar != null) {
            mActionBar.show();
        }
    }

}
