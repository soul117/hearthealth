package ua.com.crosp.solutions.hearthealthmonitor.di.component.application.module.infrastructure;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerApplication;
import ua.com.crosp.solutions.hearthealthmonitor.common.model.StartServiceBundle;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.service.ECGProcessingRecordingService;

/**
 * Created by Alexander Molochko on 5/25/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

@Module
@PerApplication
public class GlobalServicesModule {
    @Provides
    @PerApplication
    public List<StartServiceBundle> provideGlobalServicesStartBundles() {
        List<StartServiceBundle> startServiceBundles = new ArrayList<>();
        startServiceBundles.add(new StartServiceBundle(ECGProcessingRecordingService.class));
        return startServiceBundles;
    }
}
