package ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.presentation.presenter;

import android.os.Bundle;
import android.util.Pair;

import com.scichart.core.framework.ISuspendable;
import com.scichart.core.framework.UpdateSuspender;
import com.scichart.core.model.DoubleValues;
import com.scichart.core.model.ShortValues;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;
import ua.com.crosp.solutions.hearthealthmonitor.R;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.ExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.valueobject.EntityId;
import ua.com.crosp.solutions.hearthealthmonitor.base.presentation.BaseAppPresenter;
import ua.com.crosp.solutions.hearthealthmonitor.base.presentation.BaseMapper;
import ua.com.crosp.solutions.hearthealthmonitor.common.contants.BundleConstants;
import ua.com.crosp.solutions.hearthealthmonitor.di.named.NamedConstants;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.filesystem.FileManager;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util.DateUtils;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.contract.EcgRealtTimeRecordingViewContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.contract.EcgRealtimeRouterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.contract.presentation.EcgRealtimeRecordingPresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.contract.service.EcgRecordingServiceInteraction;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.contract.service.EcgServiceProxyContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.domain.RPeakDetectionInfo;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.handler.EcgDataRendererConsumer;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.entity.ExperimentResultEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.usecase.GetExperimentResultByIdUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.usecase.SaveExperimentResultForCurrentUserUseCase;

/**
 * Created by Alexander Molochko on 12/19/16.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

public class EcgRealTimeRecordingPresenter extends BaseAppPresenter<EcgRealtTimeRecordingViewContract, EcgRealtimeRouterContract> implements EcgRecordingServiceInteraction.ECGDataListener, EcgRealtimeRecordingPresenterContract, EcgDataRendererConsumer {

    public static final int GO_BACK_DELAY = 900;
    private EcgRealtTimeRecordingViewContract mView;
    private GetExperimentResultByIdUseCase mGetExperimentResultByIdUseCase;
    private SaveExperimentResultForCurrentUserUseCase mSaveExperimentResultForCurrentUserUseCase;
    private ExperimentResultEntity mExperimentResult;
    private EcgServiceProxyContract mEcgServiceProxy;
    private ScheduledFuture<?> mDrawSchedule;
    @Named(NamedConstants.Threading.ANDROID_MAIN_THREAD)
    private ExecutionThread mMainExecutionThread;
    private ScheduledExecutorService mScheduledExecutorService;
    private ProcessEcgRunnable mDrawEcgDataRunnable = new ProcessEcgRunnable();
    BlockingQueue<Pair<ShortValues, DoubleValues>> mEcgDataQueue = new LinkedBlockingQueue<>();


    @Inject
    FileManager mFileManager;
    private int mCurrentTimerTicks = 0;
    private long mExperimentTimeInMs = 0;
    private RPeakDetectionInfo.List mRPEakDetectionList = new RPeakDetectionInfo.List();


    public EcgRealTimeRecordingPresenter(EcgRealtimeRouterContract routerContract, GetExperimentResultByIdUseCase getExperimentResultByIdUseCase, SaveExperimentResultForCurrentUserUseCase saveExperimentResultForCurrentUserUseCase, EcgServiceProxyContract ecgServiceProxy, @Named(NamedConstants.Threading.ANDROID_MAIN_THREAD) ExecutionThread mainExecutionThread, ScheduledExecutorService scheduledExecutorService, FileManager fileManager) {
        mGetExperimentResultByIdUseCase = getExperimentResultByIdUseCase;
        mSaveExperimentResultForCurrentUserUseCase = saveExperimentResultForCurrentUserUseCase;
        mEcgServiceProxy = ecgServiceProxy;
        mMainExecutionThread = mainExecutionThread;
        mScheduledExecutorService = scheduledExecutorService;
        setRouter(routerContract);
        mFileManager = fileManager;
    }

    @Override
    public void onViewReady() {
        mView = getView();
        startRecordingBasedOnArgumentsProvided();
    }

    private void startRecordingBasedOnArgumentsProvided() {
        Bundle arguments = mView.getArguments();
        mView.showLoading();
        if (arguments != null) {
            final long experimentResultId = arguments.getLong(BundleConstants.Arguments.EXPERIMENT_RESULT_ID);
            mExperimentTimeInMs = arguments.getLong(BundleConstants.Arguments.ESTIMATED_RECORDING_TIME);
            if (EntityId.isValidId(experimentResultId)) {
                // Get experiment result
                final StringBuilder fullPath = new StringBuilder();
                mGetExperimentResultByIdUseCase.execute(experimentResultId)
                        .flatMap(new BaseMapper<ExperimentResultEntity, SingleSource<Long>>() {
                            @Override
                            public SingleSource<Long> transform(ExperimentResultEntity inputItem) {
                                // Set path and time of recording
                                mExperimentResult = inputItem;
                                fullPath.append(mFileManager.getFilePathInSandboxDirectory()).append(mExperimentResult.getAudioEcgRecordingPathInitIfRequired());
                                mExperimentResult.setFullRawAudioPath(fullPath.toString());
                                mExperimentResult.getEcgResultEntity().setIdIfRequired();
                                mExperimentResult.getEcgResultEntity().setDateRecordingStart(DateUtils.getCurrentDate());
                                mExperimentResult.getEcgResultEntity().setEstimatedRecordingTimeInSeconds(mExperimentTimeInMs);
                                return mSaveExperimentResultForCurrentUserUseCase.provideSingleObservable(new SaveExperimentResultForCurrentUserUseCase.Params(mExperimentResult.getUserId().getId(), mExperimentResult));
                            }
                        })
                        .subscribe(new BiConsumer<Long, Throwable>() {
                            @Override
                            public void accept(Long aLong, Throwable throwable) throws Exception {
                                mEcgServiceProxy.startEcgRecordingStreamAndFile(fullPath.toString(), mExperimentTimeInMs, EcgRealTimeRecordingPresenter.this);
                            }
                        });
            } else {
                mEcgServiceProxy.startEcgRecordingStream(mExperimentTimeInMs, EcgRealTimeRecordingPresenter.this);
            }

        } else {
            mMainExecutionThread.execute(new Runnable() {
                @Override
                public void run() {
                    getView().showArgumentsErrorMessage();
                    onBackButtonPress();
                }
            }).delay(GO_BACK_DELAY, TimeUnit.MILLISECONDS);

        }
    }


    @Override
    public ISuspendable getSuspendable() {
        return mView.getChartSurface();
    }


    @Override
    public boolean isStillRunning() {
        return mView.isRunning();
    }

    @Override
    public long getTimeUpdateIntervalInMs() {
        int timeInterval = mEcgServiceProxy.getAudioRecordingSettings().getSampleRate() / mEcgServiceProxy.getAudioRecordingSettings().getReadBufferSize();
        return timeInterval;
    }


    @Override
    public void onViewDestroyed() {
        mEcgServiceProxy.destroy();
        mCurrentTimerTicks = 0;

    }

    @Override
    public void onViewStop() {
        super.onViewStop();
        mCurrentTimerTicks = 0;
        mEcgServiceProxy.stop();
        onEcgProcessingStop();
    }

    @Override
    public void onBackButtonPress() {
        if (isTimeElapsed()) {
            getRouter().navigateBackForce();
        } else {
            getView().showExitConfirmDialog()
                    .subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean aBoolean) throws Exception {
                            getView().dismissAllDialogs();
                            if (aBoolean) {
                                getRouter().navigateBackForce();
                            } else {
                                getView().hideLoading();
                            }
                        }
                    });
        }
    }

    @Override
    public double getSamplingRate() {
        return mEcgServiceProxy.getSamplingRate();
    }

    @Override
    public int getCurrentHeartRate() {
        return mEcgServiceProxy.getCurrentHeartRate();
    }

    @Override
    public int getBufferReadSize() {
        return mEcgServiceProxy.getAudioRecordingSettings().getReadBufferSize();
    }

    @Override
    public void onTimerTick() {
        mCurrentTimerTicks++;
        mView.updateCurrentTime(mCurrentTimerTicks);
    }

    private boolean isTimeElapsed() {
        return mCurrentTimerTicks > mExperimentTimeInMs;
    }

    @Override
    public void onTimerStopped() {

        mView.showLoading();
        mView.stopDrawing();
        if (mExperimentResult == null) {
            mMainExecutionThread.execute(new Runnable() {
                @Override
                public void run() {
                    mView.hideLoading();
                    onBackButtonPress();
                }
            }).delay(GO_BACK_DELAY, TimeUnit.MILLISECONDS).subscribe();

        } else {
            mExperimentResult.getEcgResultEntity().setDateRecordingStop(DateUtils.getCurrentDate());
            mExperimentResult.getEcgResultEntity().setRRIntervalValues(getRPeakPositions());
            mSaveExperimentResultForCurrentUserUseCase.provideSingleObservable(new SaveExperimentResultForCurrentUserUseCase.Params(mExperimentResult.getUserId().getId(), mExperimentResult))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BiConsumer<Long, Throwable>() {
                        @Override
                        public void accept(Long aLong, Throwable throwable) throws Exception {
                            mMainExecutionThread.execute(new Runnable() {
                                @Override
                                public void run() {
                                    mView.hideLoading();
                                    mView.showSuccessMessage(R.string.message_success_results_saved);
                                    onBackButtonPress();
                                }
                            }).delay(GO_BACK_DELAY, TimeUnit.MILLISECONDS).subscribe();
                        }
                    });
        }
    }

    private List<Long> getRPeakPositions() {
        return mRPEakDetectionList.convertToSamplePositionList();
    }


    @Override
    public void onRPeakDetected(RPeakDetectionInfo rPeakDetectionInfo) {
        if (isAnyTimerTickOccurred()) {
            mRPEakDetectionList.add(rPeakDetectionInfo);
            mView.onRPeakDetected(rPeakDetectionInfo.sampleDetectedAt);
        }
    }

    @Override
    public void onEcgDataProcessed(ShortValues ecgSignal, DoubleValues timeValues) {
        mEcgDataQueue.add(new Pair<>(ecgSignal, timeValues));
    }

    private boolean isAnyTimerTickOccurred() {
        return mCurrentTimerTicks > 0;
    }

    @Override
    public void onEcgProcessingStart() {
        mMainExecutionThread.executeDirect(new Runnable() {
            @Override
            public void run() {
                startQueueScheduler();
                mView.hideLoading();
                mView.startDrawing();
            }
        });

    }

    @Override
    public void onEcgProcessingStop() {
        mMainExecutionThread.executeDirect(new Runnable() {
            @Override
            public void run() {
                stopQueueProcessing();
                mView.stopDrawing();
            }
        });

    }

    private void stopQueueProcessing() {
        try {
            mDrawSchedule.cancel(true);
        } catch (Exception ex) {

        }
    }

    private void startQueueScheduler() {
        mDrawSchedule = mScheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                if (!isStillRunning()) {
                    // Wait to be cancelled outside
                    return;
                }
                UpdateSuspender.using(getSuspendable(), mDrawEcgDataRunnable);
            }
        }, 0, getTimeUpdateIntervalInMs(), TimeUnit.MILLISECONDS);
    }

    private class ProcessEcgRunnable implements Runnable {
        @Override
        public void run() {
            try {
                Pair<ShortValues, DoubleValues> dataValues = mEcgDataQueue.take();
                mView.renderNewGraphData(dataValues.first, dataValues.second);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }
}