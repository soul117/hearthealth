package ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.service;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.IntDef;
import androidx.annotation.Nullable;

import com.scichart.core.model.DoubleValues;
import com.scichart.core.model.ShortValues;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import ua.com.crosp.solutions.hearthealthmonitor.BuildConfig;
import ua.com.crosp.solutions.hearthealthmonitor.R;
import ua.com.crosp.solutions.hearthealthmonitor.application.HeartMonitorApplication;
import ua.com.crosp.solutions.hearthealthmonitor.common.contants.BundleConstants;
import ua.com.crosp.solutions.hearthealthmonitor.common.contants.IntentExtras;
import ua.com.crosp.solutions.hearthealthmonitor.common.fonticons.HeartMonitorIcons;
import ua.com.crosp.solutions.hearthealthmonitor.common.service.BaseService;
import ua.com.crosp.solutions.hearthealthmonitor.di.component.service.module.ServiceModule;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.audio.ReactiveAudioRecorder;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.filesystem.FileManager;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.audio.common.AudioRecordingBuffer;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.media.audio.rx.RxStreamAudioRecorder;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.service.NotificationIntentModel;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util.Logger;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.contract.notification.EcgNotificationManagerContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.contract.service.EcgRecordingService;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.contract.service.EcgRecordingServiceInteraction;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.data.EcgNotificationInfo;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.domain.RPeakDetectionInfo;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.handler.ECGSignalProcessor;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.handler.ECGStreamProcessor;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.handler.ECGStreamProcessorContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.handler.GeliomedECGSignalProcessor;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.routing.EcgConnectionActivity;


/**
 * Created by Alexander Molochko on 5/7/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class ECGProcessingRecordingService extends BaseService implements EcgRecordingService, EcgRecordingServiceInteraction, RxStreamAudioRecorder.RecordingStreamCallback, EcgNotificationManagerContract.IntentHandler, ECGSignalProcessor.FinalDataConsumer<ShortValues, DoubleValues>, GeliomedECGSignalProcessor.ECGRPeakDetectionListener {
    public static final int NOTIFICATIONS_REQUEST_CODE = 999;
    public static final int CONTENT_INTENT_REQUEST_CODE = 332;
    public static final String ACTION_START_PAUSE_ECG_RECORDING = BuildConfig.APPLICATION_ID + ".action.ecgrecording.start";
    public static final String ACTION_STOP_ECG_RECORDING = BuildConfig.APPLICATION_ID + ".action.ecgrecording.stop";
    public static final String ACTION_CLOSE_ECG_RECORDING = BuildConfig.APPLICATION_ID + ".action.ecgrecording.close";
    public static final String ACTION_START_ECG_RECORDING = BuildConfig.APPLICATION_ID + ".action.ecgrecording.startrecording";
    public static final int BUFFER_SIZE_COEFFICIENT = 8;
    public static final int ONE_SECOND_MS = 1000;
    public static final int TIMER_RECORD_DELAY = 5000;
    public static final int STOP_DELAY = 2000;
    Handler mHandler = new Handler();
    @Inject
    ReactiveAudioRecorder.Settings mAudioRecorderSettings;

    @Inject
    ReactiveAudioRecorder mAudioRecorder;

    @Inject
    EcgNotificationManagerContract mNotificationManager;
    @Inject
    FileManager mFileManager;
    // Variables
    private NotificationIntentModel.List mHandleableIntents;
    private EcgNotificationInfo mCurrentEcgNotificationInfo;
    private Flowable<AudioRecordingBuffer> mRecordingStream;
    private Timer mRecordingTickTimer;
    private ECGStreamProcessorContract mEcgStreamProcessor;
    private ECGDataListener mEcgDataListener;
    private EcgServiceOptions mEcgOptions;
    private int mCurrentTimerTicks = 0;


    //region Lifecycle methods
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new EcgLocalBinder();
    }

    @Override
    public void onCreate() {
        Logger.debug("ON Create SERVICE");
        super.onCreate();
        initService();
        // Inject dependencies
        ((HeartMonitorApplication) getApplication())
                .getApplicationComponent()
                .plus(new ServiceModule(this))
                .inject(this);
    }


    @Override
    public boolean onUnbind(Intent intent) {
        Logger.debug(" onUnbind SERVICE");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Logger.debug("ON Destroy SERVICE");
        releaseService();
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handleIntent(intent, flags, startId);
        return START_NOT_STICKY;
    }

    //endregion
    //region Service listener

    @Override
    public void onRPeakDetected(RPeakDetectionInfo rPeakDetectionInfo) {
        if (mEcgDataListener != null) {
            mEcgDataListener.onRPeakDetected(rPeakDetectionInfo);
        }
    }

    @Override
    public void onDataProcessed(ShortValues values, DoubleValues time) {
        if (mEcgDataListener != null) {
            mEcgDataListener.onEcgDataProcessed(values, time);
        }
    }

    @Override
    public void onStopProcessingData() {
        if (mEcgDataListener != null) {
            mEcgDataListener.onEcgProcessingStop();
        }
    }

    //endregion
    //region EcgRecordingServiceInteraction implementation

    @Override
    public Flowable<AudioRecordingBuffer> startRecordingStreaming() {
        mNotificationManager.showStreamingNotification(mCurrentEcgNotificationInfo);
        if (mRecordingStream == null) {
            mRecordingStream = mAudioRecorder.startRecordingStreaming(mAudioRecorderSettings);
        }
        return mRecordingStream;
    }


    @Override
    public void startRecordingStreamingCallback(Bundle options, ECGDataListener ecgDataListener) {
        assert options != null;
        assert ecgDataListener != null;
        mRecordingStream = startRecordingStreaming();
        mEcgDataListener = ecgDataListener;
        mEcgOptions = (EcgServiceOptions) options.getSerializable(BundleConstants.Arguments.ECG_SERVICE_OPTIONS);
        initEcgStreamProcessorIfRequired(mEcgOptions);

    }

    private void initEcgStreamProcessorIfRequired(EcgServiceOptions options) {
        if (mEcgStreamProcessor == null) {
            mEcgStreamProcessor = new ECGStreamProcessor(mRecordingStream, mAudioRecorderSettings, options.audioRecordingPath, mFileManager, this, this);
        }
    }


    @Override
    public void unsubscribeFromStreaming(Disposable flowableStream) {
        if (flowableStream != null && !flowableStream.isDisposed()) {
            flowableStream.dispose();
            mAudioRecorder.onUnsubscribeFromStream();
            if (!mAudioRecorder.isStillRecording()) {
                stopRecording();
            }
        }
    }

    @Override
    public void stopRecording() {
        try {
            mAudioRecorder.stopRecording();
            // TODO prevent fucking segfault in native lib
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mEcgStreamProcessor.stopProcessing();
                    mEcgStreamProcessor = null;
                }
            }, STOP_DELAY);
            clearAllNotifications();
            mRecordingStream = null;
            mCurrentTimerTicks = 0;
            stopTimerForce();
        } catch (Exception ex) {

        } finally {
            if (mEcgDataListener != null) {
                mEcgDataListener.onEcgProcessingStop();
            }
        }

    }

    @Override
    public void disposeEventListener() {
        if (mEcgDataListener != null) {
            mEcgDataListener = null;
        }
    }

    @Override
    public int getCurrentHeartRate() {
        if (mEcgStreamProcessor != null) {
            return mEcgStreamProcessor.getCurrentHeartRate();
        }
        return 0;
    }

    @Override
    public void stopOnTimer() {
        stopRecording();
    }

    @Override
    public Flowable<AudioRecordingBuffer> startRecordingByType(@ECGProcessingRecordingService.RecordingDestinationSource int recordingType, Bundle arguments) {
        switch (recordingType) {
            case SOURCE_STREAM: {
                return startRecordingStreaming();
            }
            default:
                return Flowable.empty();
        }
    }

    @Override
    public void toggleRecording() {
        if (mAudioRecorder.isPaused()) {
            mCurrentEcgNotificationInfo.state = EcgNotificationInfo.STATE_RECORDING;
            mAudioRecorder.resumeRecording();
        } else {
            mAudioRecorder.pauseRecording();
            mCurrentEcgNotificationInfo.state = EcgNotificationInfo.STATE_PAUSED;
        }
        mNotificationManager.showStreamingNotification(mCurrentEcgNotificationInfo);
    }
    //endregion

    //region Notification manager implementation
    @Override
    public void handleIntent(Intent intent, int flags, int startId) {
        if (intent != null) {
            String action = intent.getAction();
            if (action != null) {
                Logger.error("Got intent action" + action);
                switch (action) {
                    case ACTION_START_PAUSE_ECG_RECORDING:
                        toggleRecording();
                        break;
                    case ACTION_CLOSE_ECG_RECORDING:
                        stopRecording();
                        break;
                    case ACTION_STOP_ECG_RECORDING:
                        stopRecording();
                        break;
                    case ACTION_START_ECG_RECORDING:
                        startRecordingFromIntent(intent);
                        break;
                    default:
                        Logger.error("Got unknown intent action" + action);
                        break;
                }
            }
        }
    }


    private void buildNotificationInfo(String title, String description) {
        mCurrentEcgNotificationInfo = new EcgNotificationInfo(title, description, EcgNotificationInfo.STATE_RECORDING, HeartMonitorIcons.hm_psychoemotional.key());
    }

    private void initService() {
        initHandlableIntentsList();
        buildNotificationInfo(getString(R.string.notification_title), getString(R.string.notification_description));
    }

    private void initHandlableIntentsList() {
        mHandleableIntents = new NotificationIntentModel.List();
        Resources resources = getResources();
        mHandleableIntents.add(
                new NotificationIntentModel(this,
                        resources.getString(R.string.notification_label_start),
                        ACTION_START_PAUSE_ECG_RECORDING,
                        R.id.button_play_start,
                        R.id.image_view_play_start,
                        R.drawable.ic_notification_start)
        );
        mHandleableIntents.add(
                new NotificationIntentModel(this,
                        resources.getString(R.string.notification_label_stop),
                        ACTION_STOP_ECG_RECORDING,
                        R.id.button_play_stop,
                        R.id.image_view_play_stop,
                        R.drawable.ic_notification_stop)
        );
        mHandleableIntents.add(
                new NotificationIntentModel(this,
                        resources.getString(R.string.notification_label_close),
                        ACTION_CLOSE_ECG_RECORDING,
                        R.id.button_close,
                        R.id.image_view_close,
                        R.drawable.ic_notification_close)
        );
    }

    @Override
    public NotificationIntentModel providePlayPauseIntentHandler() {
        return mHandleableIntents.getByIntentName(ACTION_START_PAUSE_ECG_RECORDING);
    }

    @Override
    public NotificationIntentModel provideStopIntentHandler() {
        return mHandleableIntents.getByIntentName(ACTION_STOP_ECG_RECORDING);
    }

    @Override
    public NotificationIntentModel provideCloseIntentHandler() {
        return mHandleableIntents.getByIntentName(ACTION_CLOSE_ECG_RECORDING);
    }

    private void clearAllNotifications() {
        mNotificationManager.hideAllNotifications();
    }

    @Override
    public void onRecordingStopped() {
        clearAllNotifications();
    }

    @Override
    public void onRecordingStarted() {
        mEcgDataListener.onEcgProcessingStart();
        startTimer();
    }

    @Override
    public void onRecordingPaused() {

    }

    @Override
    public PendingIntent createContentIntent(EcgNotificationInfo ecgNotificationInfo) {
        Intent openUI = new Intent(this, EcgConnectionActivity.class);
        openUI.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        openUI.putExtra(EcgConnectionActivity.EXTRA_INITIAL_ORIENTATION, true);
        return PendingIntent.getActivity(this, CONTENT_INTENT_REQUEST_CODE, openUI,
                PendingIntent.FLAG_CANCEL_CURRENT);
    }

    private void startRecordingFromIntent(Intent intent) {
        @ECGProcessingRecordingService.RecordingDestinationSource
        int recordingType = intent.getIntExtra(IntentExtras.EcgServiceIntent.RECORDING_DESTINATION_SOURCE, SOURCE_FILE_STREAM);
        Flowable<AudioRecordingBuffer> stream = startRecordingByType(recordingType, intent.getExtras());
        String title = intent.getStringExtra(IntentExtras.EcgServiceIntent.TITLE);
        String description = intent.getStringExtra(IntentExtras.EcgServiceIntent.DESCRIPTION);
        buildNotificationInfo(title, description);
    }
    //endregion

    @Override
    public ReactiveAudioRecorder.Settings getAudioRecordingSettings() {
        return mAudioRecorder.getSettings();
    }


    private void releaseService() {
        mAudioRecorder.stopRecording();
    }


    //region Timer stuff

    private void startTimer() {
        mRecordingTickTimer = new Timer();
        mRecordingTickTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                mCurrentTimerTicks++;
                // TODO !!! This is used to delay processing while carrier is bouncing, should be implemented in more elegant way
                try {

                    if (mEcgDataListener != null) {
                        mEcgDataListener.onTimerTick();
                    }
                    if (mEcgStreamProcessor != null) {
                        mEcgStreamProcessor.setCarrierDebounced(true);
                    }
                    if (mCurrentTimerTicks >= mEcgOptions.recordingTime) {
                        stopRecording();
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        }, TIMER_RECORD_DELAY, ONE_SECOND_MS);//Update text every second
    }

    public void stopTimerForce() {
        if (mRecordingTickTimer != null) {
            try {
                mRecordingTickTimer.cancel();
            } catch (Exception ex) {

            } finally {
                if (mEcgDataListener != null) {
                    mEcgDataListener.onTimerStopped();
                } else {
                    // stopRecording();
                }
                mRecordingTickTimer = null;
            }
        }

    }
    //endregion

    // Audio recorder state
    public static final int SOURCE_FILE = 0;
    public static final int SOURCE_STREAM = 1;
    public static final int SOURCE_FILE_STREAM = 2;


    @IntDef({SOURCE_FILE, SOURCE_STREAM, SOURCE_FILE_STREAM})
    @Retention(RetentionPolicy.SOURCE)
    public @interface RecordingDestinationSource {
    }

    // Binder object implementation that provided public API for a client
    public class EcgLocalBinder extends Binder {
        public EcgRecordingServiceInteraction getServiceInteractor() {
            // Return this instance of LocalService so clients can call public methods
            return ECGProcessingRecordingService.this;
        }
    }
}
