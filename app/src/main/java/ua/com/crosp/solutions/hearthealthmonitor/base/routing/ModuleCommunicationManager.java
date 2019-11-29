package ua.com.crosp.solutions.hearthealthmonitor.base.routing;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alexander Molochko on 2/12/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class ModuleCommunicationManager implements BaseModuleCommunicationManager {
    private Map<Class, Object> mInputData = new HashMap<>();
    private Map<Class, Object> mOutputData = new HashMap<>();

    @Override
    public void clearAllData() {
        mInputData.clear();
        mOutputData.clear();
    }

    @Override
    public Object getModuleInput(Class modulePresenterClass) {
        return this.getModuleInput(modulePresenterClass, false);
    }

    @Override
    public Object getModuleInput(Class modulePresenterClass, boolean clear) {
        Object result = null;
        if (clear) {
            result = mInputData.remove(modulePresenterClass);
        } else {
            result = mInputData.get(modulePresenterClass);
        }
        return result;
    }

    @Override
    public Object getModuleOutput(Class modulePresenterClass) {
        return this.getModuleOutput(modulePresenterClass);
    }

    @Override
    public Object getModuleOutput(Class modulePresenterClass, boolean clear) {
        Object result = null;
        if (clear) {
            result = mOutputData.remove(modulePresenterClass);
        } else {
            result = mOutputData.get(modulePresenterClass);
        }
        return result;
    }

    @Override
    public void setModuleInput(Class modulePresenterClass, Object inputObject) {
        mInputData.put(modulePresenterClass, inputObject);
    }

    @Override
    public void setModuleOutput(Class modulePresenterClass, Object outputObject) {
        mOutputData.put(modulePresenterClass, outputObject);
    }
}
