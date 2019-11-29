package ua.com.crosp.solutions.hearthealthmonitor.base.routing;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.IntentCompat;
import androidx.fragment.app.FragmentManager;

import javax.inject.Inject;
import javax.inject.Named;

import ua.com.crosp.solutions.hearthealthmonitor.application.HeartMonitorApplication;
import ua.com.crosp.solutions.hearthealthmonitor.base.routing.interfaces.ActivityController;
import ua.com.crosp.solutions.hearthealthmonitor.common.notification.ToastNotifications;
import ua.com.crosp.solutions.hearthealthmonitor.di.component.activity.ActivityComponent;
import ua.com.crosp.solutions.hearthealthmonitor.di.component.activity.module.ActivityModule;
import ua.com.crosp.solutions.hearthealthmonitor.di.component.application.ApplicationComponent;
import ua.com.crosp.solutions.hearthealthmonitor.di.contract.ProvidesBaseComponent;
import ua.com.crosp.solutions.hearthealthmonitor.di.named.NamedConstants;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.common.ApplicationNavigator;

/**
 * Created by Alexander Molochko on 12/12/2016.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

public abstract class BaseActivity extends AppCompatActivity implements ActivityController, ProvidesBaseComponent<ActivityComponent> {
    // Views
    protected ViewGroup mContentContainer;
    // Variables
    @Inject
    protected LayoutInflater mLayoutInflater;
    @Inject
    @Named(NamedConstants.Fragment.SUPPORT_FRAGMENT_MANAGER)
    protected FragmentManager mFragmentManager;
    @Inject
    protected ApplicationNavigator mApplicationNavigator;
    // Dagger dependency graph
    protected ActivityComponent mActivityComponent;


    //================================================================================
    // Lifecycle callbacks
    //================================================================================

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeSetContent();
        setContentView(getMainLayoutId());
        afterSetContent();
        initDependencyComponents();
        mContentContainer = (ViewGroup) findViewById(getContentContainerId());
        if (mContentContainer != null) {
            mLayoutInflater.inflate(getContentLayoutId(), mContentContainer, true);
        }

    }

    private void initDependencyComponents() {
        // Inject dagger dependencies
        ApplicationComponent applicationComponent = HeartMonitorApplication.from(this).getApplicationComponent();
        mActivityComponent = applicationComponent.plus(new ActivityModule(this));
        mActivityComponent.inject(this);
    }

    @Override
    public ActivityComponent getBaseActivityComponent() {
        if (mActivityComponent == null) {
            initDependencyComponents();
        }
        return mActivityComponent;
    }

    //================================================================================
    // Hooks for executing actions during execution of activity lifecycle methods
    //================================================================================
    public void beforeSetContent() {

    }

    public void afterSetContent() {

    }
    //================================================================================
    // Template methods to be overridden by child classes
    // Get layout id in order to inflate it and add to the root view
    //================================================================================

    /**
     * Provide main layout id, layout resource used for setting content view
     *
     * @return main layout id @LayoutRes
     */
    public abstract
    @LayoutRes
    int getMainLayoutId();

    /**
     * Lauout id inside a layout provided by the  {@link #getMainLayoutId()} method.
     *
     * @return
     */
    public abstract
    @IdRes
    int getContentContainerId();

    /**
     * Provide main content layout
     * This layout is inflated inside a layout provided by the {@link #getContentContainerId()} method
     *
     * @return id of main content layout, where main screen content will be put
     */
    public abstract
    @LayoutRes
    int getContentLayoutId();

    //================================================================================
    // Public methods
    //================================================================================

    public LayoutInflater provideLayoutInflater() {
        return mLayoutInflater;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void navigateBackForce() {
        super.onBackPressed();
    }


    public String getStringByResourceId(int stringId) {
        return getString(stringId);
    }

    //================================================================================
    // ActivityController implementation
    //================================================================================
    @Override
    public void startNewActivity(Class activityClass) {
        startActivity(new Intent(this, activityClass));
    }

    @Override
    public void startNewActivityNewTask(Class activityClass) {
        Intent intent = new Intent(this, activityClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void startNewActivityCleaBackStack(Class activityClass) {
        startNewActivityCleaBackStack(activityClass, null);
    }

    @Override
    public void startNewActivityCleaBackStack(Class activityClass, Bundle bundle) {
        Intent intent = new Intent(this, activityClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }


    @Override
    public void startNewActivity(Class activityClass, Bundle bundle) {
        startActivity(new Intent(this, activityClass).putExtras(bundle));
    }

    @Override
    public void startNewActivityForResult(Class activityClass, int requestCode) {
        Intent intent = new Intent(this, activityClass);
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void startNewActivityForResult(Class activityClass, Bundle bundle, int requestCode) {
        Intent intent = new Intent(this, activityClass);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void startNewActivityClearTop(Class activityClass) {
        Intent intent = new Intent(this, activityClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void startNewActivitySingleTop(Class activityClass) {
        Intent intent = new Intent(this, activityClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public ApplicationNavigator getApplicationNavigator() {
        return mApplicationNavigator;
    }

    public void showErrorMessage(int messageStringId) {
        ToastNotifications.showErrorMessage(this.getApplicationContext(), getString(messageStringId));
    }


    public void showSuccessMessage(int messageStringId) {
        ToastNotifications.showSuccessMessage(this.getApplicationContext(), getString(messageStringId));
    }

    public long getIntegerById(int id) {
        return getResources().getInteger(id);
    }


}
