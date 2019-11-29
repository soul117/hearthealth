package ua.com.crosp.solutions.hearthealthmonitor.base.routing;

import android.os.Bundle;

import javax.inject.Inject;

/**
 * Created by Alexander Molochko on 12/12/2016.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

public abstract class BaseAssemblyActivity extends BaseSingleFragmentActivity {
    @Inject
    protected BaseModuleCommunicationManager mModuleCommunicationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityComponent.inject(this);
        getModuleCommunicationManager();
    }

    public BaseModuleCommunicationManager getModuleCommunicationManager() {
        return mModuleCommunicationManager;
    }

    public void setModuleCommunicationManager(BaseModuleCommunicationManager moduleCommunicationManager) {
        mModuleCommunicationManager = moduleCommunicationManager;
    }


    public Object getModuleInput(Class modulePresenterClass) {
        return mModuleCommunicationManager.getModuleInput(modulePresenterClass);
    }

    public Object getModuleInput(Class modulePresenterClass, boolean clear) {
        return mModuleCommunicationManager.getModuleInput(modulePresenterClass, clear);
    }

    public Object getModuleOutput(Class modulePresenterClass) {
        return mModuleCommunicationManager.getModuleOutput(modulePresenterClass);
    }


    public Object getModuleOutput(Class modulePresenterClass, boolean clear) {
        return mModuleCommunicationManager.getModuleOutput(modulePresenterClass, clear);
    }

    public void setModuleInput(Class modulePresenterClass, Object inputObject) {
        mModuleCommunicationManager.setModuleInput(modulePresenterClass, inputObject);

    }

    public void setModuleOutput(Class modulePresenterClass, Object outputObject) {
        mModuleCommunicationManager.setModuleOutput(modulePresenterClass, outputObject);
    }

}
