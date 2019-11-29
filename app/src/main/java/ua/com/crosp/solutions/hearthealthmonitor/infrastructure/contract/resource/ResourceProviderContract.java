package ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.resource;

/**
 * Created by Alexander Molochko on 12/21/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public interface ResourceProviderContract {
    String getStringById(int id);

    int getColorById(int id);
}
