package ua.com.crosp.solutions.hearthealthmonitor.di.component.service.module;

import android.app.NotificationManager;
import android.content.Context;

import androidx.core.app.NotificationManagerCompat;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerApplication;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerService;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.filesystem.FileManager;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.media.audio.rx.RxStreamAudioRecorder;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.contract.notification.EcgNotificationManagerContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.contract.notification.EcgNotificationViewContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.contract.service.EcgRecordingService;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.contract.service.EcgRecordingServiceInteraction;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.handler.ECGWavFileWriter;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.presentation.view.notification.EcgNotificationView;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.service.EcgNotificationManager;

import static ua.com.crosp.solutions.hearthealthmonitor.di.component.service.module.ServiceModule.CONTEXT_SERVICE;

/**
 * Created by Alexander Molochko on 4/23/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
@PerApplication
@Module
public class EcgServiceModule {
    @Provides
    @PerService
    EcgRecordingService provideEcgRecordingService(@Named(CONTEXT_SERVICE) Context context) {
        return (EcgRecordingService) context;
    }

    @Provides
    @PerService
    RxStreamAudioRecorder.RecordingStreamCallback provideRecordingStreamCallback(@Named(CONTEXT_SERVICE) Context context) {
        return (RxStreamAudioRecorder.RecordingStreamCallback) context;
    }


    @Provides
    @PerService
    EcgRecordingServiceInteraction provideServiceInteraction(@Named(CONTEXT_SERVICE) Context context) {
        return (EcgRecordingServiceInteraction) context;
    }

    @Provides
    @PerService
    EcgNotificationManagerContract.IntentHandler provideIntentHandler(@Named(CONTEXT_SERVICE) Context context) {
        return (EcgNotificationManagerContract.IntentHandler) context;
    }

    @Provides
    @PerService
    EcgNotificationViewContract provideNotificationView(@Named(CONTEXT_SERVICE) Context context, EcgNotificationManagerContract.IntentHandler intentHandler) {
        return new EcgNotificationView(context, intentHandler);
    }

    @Provides
    @PerService
    EcgNotificationManagerContract provideEcgNotificationManager(@Named(CONTEXT_SERVICE) Context context, NotificationManagerCompat managerCompat, EcgNotificationManagerContract.IntentHandler intentHandler, EcgNotificationViewContract notificationViewContract, NotificationManager manager) {
        return new EcgNotificationManager(context, manager, managerCompat, intentHandler, notificationViewContract);
    }


    @Provides
    @PerService
    ECGWavFileWriter provideECGWavFileWriter(FileManager fileManager) {
        return new ECGWavFileWriter(fileManager);
    }

}
