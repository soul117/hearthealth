package ua.com.crosp.solutions.hearthealthmonitor.di.component.service;

import android.content.Context;

import javax.inject.Named;

import dagger.Subcomponent;
import ua.com.crosp.solutions.hearthealthmonitor.di.component.service.module.AudioRecordingModule;
import ua.com.crosp.solutions.hearthealthmonitor.di.component.service.module.EcgServiceModule;
import ua.com.crosp.solutions.hearthealthmonitor.di.component.service.module.NotificationModule;
import ua.com.crosp.solutions.hearthealthmonitor.di.component.service.module.ServiceModule;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerService;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.service.ECGProcessingRecordingService;

import static ua.com.crosp.solutions.hearthealthmonitor.di.component.service.module.ServiceModule.CONTEXT_SERVICE;

/**
 * Created by Alexander Molochko on 1/22/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
@PerService
@Subcomponent(modules = {ServiceModule.class, EcgServiceModule.class, AudioRecordingModule.class, NotificationModule.class})
public interface EcgServiceComponent {

    public void inject(ECGProcessingRecordingService ecgRecordingService);

    @Named(CONTEXT_SERVICE)
    Context exposeServiceContext();

}
