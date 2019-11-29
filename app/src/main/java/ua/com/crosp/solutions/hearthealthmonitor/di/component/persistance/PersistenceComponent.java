package ua.com.crosp.solutions.hearthealthmonitor.di.component.persistance;

import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.contract.data.ModesRepositoryContract;

/**
 * Created by Alexander Molochko on 1/8/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
//@Component(dependencies = {ApplicationComponent.class}, modules = {})
public interface PersistenceComponent {
    ModesRepositoryContract exposeModesRepository();
}
