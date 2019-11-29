package ua.com.crosp.solutions.hearthealthmonitor.di.component.application.module.infrastructure;

import dagger.Module;
import dagger.Provides;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.mapper.JsonMapperContract;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.mapper.JsonMapper;

/**
 * Created by Alexander Molochko on 4/23/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
@Module
public class JsonMappperModule {
    @Provides
    JsonMapperContract providesJsonMapper() {
        return new JsonMapper();
    }

}
