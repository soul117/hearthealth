package ua.com.crosp.solutions.hearthealthmonitor.base.routing;

/**
 * Created by Alexander Molochko on 2/12/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
public interface BaseModuleCommunicationManager {
    void clearAllData();

    Object getModuleInput(Class modulePresenterClass);

    Object getModuleInput(Class modulePresenterClass, boolean clear);

    Object getModuleOutput(Class modulePresenterClass);

    Object getModuleOutput(Class modulePresenterClass, boolean clear);

    void setModuleInput(Class modulePresenterClass, Object inputObject);

    void setModuleOutput(Class modulePresenterClass, Object outputObject);
}
