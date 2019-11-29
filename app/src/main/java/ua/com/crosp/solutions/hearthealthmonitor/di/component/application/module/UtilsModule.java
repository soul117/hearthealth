package ua.com.crosp.solutions.hearthealthmonitor.di.component.application.module;

import dagger.Module;
import dagger.Provides;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerApplication;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerInfrastructure;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.random.RandomValuesGeneratorContract;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.mapper.JsonMapper;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.random.RandomValuesGenerator;

/**
 * Created by Alexander Molochko on 2/6/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
@Module
@PerInfrastructure
public class UtilsModule {

    public UtilsModule() {
        // Utils.init(application);
    }

    @Provides
    @PerInfrastructure
    public RandomValuesGeneratorContract provideRandomGenerator() {
        return new RandomValuesGenerator();
    }

    @Provides
    @PerInfrastructure
    public JsonMapper provideJsonMapper() {
        return new JsonMapper();
    }
}
