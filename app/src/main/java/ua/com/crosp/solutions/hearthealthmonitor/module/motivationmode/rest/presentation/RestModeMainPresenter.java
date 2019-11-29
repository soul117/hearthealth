package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.presentation;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;
import ua.com.crosp.solutions.hearthealthmonitor.R;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.valueobject.GeneralDate;
import ua.com.crosp.solutions.hearthealthmonitor.base.presentation.BaseAppPresenter;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util.DateUtils;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util.MathUtils;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.entity.ExperimentResultEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.entity.FeelingResultEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.entity.GameResultEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.usecase.SaveExperimentResultForCurrentUserUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.cotntract.RestModeRouterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.cotntract.domain.RestMainUseCaseContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.cotntract.presenter.RestModeMainPresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.cotntract.view.RestModeViewContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.data.RestSettingsEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.cotntract.UserSessionManagerContract;

/**
 * Created by Alexander Molochko on 4/21/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class RestModeMainPresenter extends BaseAppPresenter<RestModeViewContract, RestModeRouterContract> implements RestModeMainPresenterContract {
    // Component & modular dependencies
    public static final int STOP_EXPERIMENT_OPTION_STOP_WITHOUT_SAVING = 0;
    public static final int STOP_EXPERIMENT_OPTION_START_ECG_RECORDING = 1;
    public static final int STOP_EXPERIMENT_OPTION_CANCEL_AND_CONTINUE = 2;
    private static final long TIME_DEVIATION_PERCENTAGE = 15;
    public static final int MS_IN_SECOND = 1000;

    private ExperimentResultEntity mExperimentResultEntity;

    private final UserSessionManagerContract mUserSessionManager;
    @Inject
    SaveExperimentResultForCurrentUserUseCase mSaveExperimentResultForCurrentUser;

    @Inject
    RestMainUseCaseContract mRestMainUseCaseContract;

    private FeelingResultEntity mFeelingResultEntity;

    RestSettingsEntity mRestSettingsEntity;

    @Inject
    public RestModeMainPresenter(RestModeRouterContract router, UserSessionManagerContract userSessionManager, RestMainUseCaseContract restMainUseCaseContract, SaveExperimentResultForCurrentUserUseCase experimentResultForCurrentUserUseCase) {
        mUserSessionManager = userSessionManager;
        setRouter(router);
        this.mRestMainUseCaseContract = restMainUseCaseContract;
        mSaveExperimentResultForCurrentUser = experimentResultForCurrentUserUseCase;

    }

    @Override
    public void onViewReady() {
        final RestModeViewContract view = getView();
        mRestMainUseCaseContract.getRestModeSettings().subscribe(new BiConsumer<RestSettingsEntity, Throwable>() {
            @Override
            public void accept(RestSettingsEntity restSettingsEntity, Throwable throwable) throws Exception {
                mRestSettingsEntity = restSettingsEntity;
                view.setTimerTime(provideRestTime());
            }
        });
    }

    @Override
    public void onViewDestroyed() {

    }

    @Override
    public void onTimerStart(long timeSeconds) {
        RestModeViewContract view = getView();
        view.hidePlayButton();
        view.showStopButton();
    }

    @Override
    public void onTimerRestart(long timeSeconds) {
        RestModeViewContract view = getView();
        view.showPlayButton();
        view.hideRestartButton();
        view.showStopButton();
    }


    @Override
    public long provideRestTime() {
        return mRestSettingsEntity.getRestTime();
    }


    @Override
    public void onGameFinished(GameResultEntity experimentResults) {
        RestModeViewContract view = getView();
        view.showRestartButton();
        view.hideStopButton();
        createExperimentResultIfRequired();
        mExperimentResultEntity.setGameResultEntity(experimentResults);
        getView().showFeelingTypeSelectionDialog();
    }

    private void createExperimentResultIfRequired() {
        if (mExperimentResultEntity == null) {
            mExperimentResultEntity = ExperimentResultEntity.createNullableEntity();
            mExperimentResultEntity.setExperimentName(getView().getStringByResourceId(R.string.experiment_name_rest_after_activity));
            mExperimentResultEntity.setDateOfCreation(new GeneralDate(DateUtils.getCurrentDate()));
            mExperimentResultEntity.setUserId(mUserSessionManager.getCurrentUserEntityId());
            mExperimentResultEntity.setBaseExperimentPathFromUserId(mUserSessionManager.getCurrentUserEntityId().toString());
        }
        if (mFeelingResultEntity == null) {
            mFeelingResultEntity = new FeelingResultEntity();
        }
    }

    @Override
    public void onStopDialogResult(int which, CharSequence text) {
        switch (which) {
            case STOP_EXPERIMENT_OPTION_CANCEL_AND_CONTINUE:
                getView().continueGame();
                break;
            case STOP_EXPERIMENT_OPTION_START_ECG_RECORDING:
                getView().stopGameAndSendResults();
                break;
            case STOP_EXPERIMENT_OPTION_STOP_WITHOUT_SAVING:
                getView().stopGameWithoutSavingResults();
                break;
            default:
                getView().continueGame();
                break;
        }
    }

    @Override
    public void onWeatherTypeSelected(@FeelingResultEntity.WeatherType String weatherType) {
        mFeelingResultEntity.setWeatherType(weatherType);
        getView().showCommentEnterDialog();

    }

    @Override
    public void onBackPressed() {
        getView().showGameExitConfirmationDialog();
    }

    @Override
    public void onGameFinished(long currentTimeInSeconds) {
        long gameEstimatedTime = provideRestTime() * MS_IN_SECOND;
        currentTimeInSeconds *= MS_IN_SECOND;
        GameResultEntity gameResultEntity = new GameResultEntity();
        gameResultEntity.setGameEstimatedTime(gameEstimatedTime);
        gameResultEntity.setGameExperimentId(getView().getIntegerById(R.integer.mode_rest_after_activity));
        gameResultEntity.setGameActualTime(gameEstimatedTime - currentTimeInSeconds);
        gameResultEntity.setWasInterrupted(MathUtils.getValueDeviationInPercentage(gameEstimatedTime, gameEstimatedTime - currentTimeInSeconds) > TIME_DEVIATION_PERCENTAGE);
        gameResultEntity.setGameName(getView().getStringByResourceId(R.string.game_name_rest_after_activity));
        gameResultEntity.setGameType(getView().getStringByResourceId(R.string.game_type_timer));

        onGameFinished(gameResultEntity);
    }

    @Override
    public void onCommentEntered(String comment) {
        mFeelingResultEntity.setComment(comment);
        getView().showStartRecordingDialog();
    }

    private Single<Long> saveCurrentResults() {
        mExperimentResultEntity.setFeelingResultEntity(mFeelingResultEntity);
        SaveExperimentResultForCurrentUserUseCase.Params params = new SaveExperimentResultForCurrentUserUseCase.Params(mUserSessionManager.getCurrentUserEntityId().getId(), mExperimentResultEntity);
        return mSaveExperimentResultForCurrentUser
                .execute(params);
    }


    @Override
    public void onConfirmStartEcgRecording() {
        saveCurrentResults()
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        getView().showResultsSuccessfullySavedMessage();
                        getRouter().navigateToEcgRecordingActivity();
                    }
                });
    }

    @Override
    public void onCancelStartEcgRecording() {
        saveCurrentResults()
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        getView().showResultsSuccessfullySavedMessage();
                        getRouter().navigateBackForce();
                    }
                });
    }

    @Override
    public void onFeelingTypeSelected(@FeelingResultEntity.FeelingType String feelingType) {
        mFeelingResultEntity.setFeelingType(feelingType);
        getView().showWeatherTypeSelectionDialog();
    }

    @Override
    public void onGameStarted() {

    }

    @Override
    public void onGameCancelledWithoutResults() {
        getRouter().navigateBackForce();
    }
}
