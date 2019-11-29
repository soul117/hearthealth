package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.presentation;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.functions.Consumer;
import ua.com.crosp.solutions.hearthealthmonitor.R;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.valueobject.GeneralDate;
import ua.com.crosp.solutions.hearthealthmonitor.base.presentation.BaseAppPresenter;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util.DateUtils;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util.Logger;
import ua.com.crosp.solutions.hearthealthmonitor.module.appsettings.data.AppSettingsModel;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.entity.ExperimentResultEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.entity.FeelingResultEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.entity.GameResultEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.usecase.SaveExperimentResultForCurrentUserUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.contract.presenter.SquatGamePresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.contract.routing.PARouterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.contract.view.SquatGameViewContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.cotntract.UserSessionManagerContract;

/**
 * Created by Alexander Molochko on 4/21/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class SquatGamePresenter extends BaseAppPresenter<SquatGameViewContract, PARouterContract> implements SquatGamePresenterContract {

    // Constants
    public static final int STOP_EXPERIMENT_OPTION_STOP_WITHOUT_SAVING = 0;
    public static final int STOP_EXPERIMENT_OPTION_START_ECG_RECORDING = 1;
    public static final int STOP_EXPERIMENT_OPTION_CANCEL_AND_CONTINUE = 2;
    private final UserSessionManagerContract mUserSessionManager;

    // Component & modular dependencies
    @Inject
    SaveExperimentResultForCurrentUserUseCase mSaveExperimentResultForCurrentUser;
    @Inject
    AppSettingsModel mAppSettingsModel;
    // Temp variables
    private ExperimentResultEntity mExperimentResultEntity;
    private FeelingResultEntity mFeelingResultEntity;

    @Inject
    public SquatGamePresenter(AppSettingsModel appSettingsModel, PARouterContract router, UserSessionManagerContract userSessionManagerContract, SaveExperimentResultForCurrentUserUseCase saveUseCase) {
        setRouter(router);
        mSaveExperimentResultForCurrentUser = saveUseCase;
        mUserSessionManager = userSessionManagerContract;
        mAppSettingsModel = appSettingsModel;
    }

    @Override
    public void onViewReady() {
        getView().setShowCountdown(mAppSettingsModel.showCountdownTimer());
    }

    @Override
    public void onViewDestroyed() {

    }

    @Override
    public void onGameStarted() {

    }


    @Override
    public void onGameCancelledWithoutResults() {
        getRouter().navigateBackForce();
    }

    @Override
    public void onBackPressed() {
        getView().showGameExitConfirmationDialog();
    }

    @Override
    public void onGameFinished(GameResultEntity gameResultEntity) {
        createExperimentResultIfRequired();
        mExperimentResultEntity.setGameResultEntity(gameResultEntity);
        getView().showFeelingTypeSelectionDialog();
    }

    private void createExperimentResultIfRequired() {
        if (mExperimentResultEntity == null) {
            mExperimentResultEntity = ExperimentResultEntity.createNullableEntity();
            mExperimentResultEntity.setExperimentName(getView().getStringByResourceId(R.string.experiment_name_psysical_activity));
            mExperimentResultEntity.setDateOfCreation(new GeneralDate(DateUtils.getCurrentDate()));
            mExperimentResultEntity.setUserId(mUserSessionManager.getCurrentUserEntityId());
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
        Logger.error(text + " " + which);
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
    public void onWeatherTypeSelected(@FeelingResultEntity.WeatherType String weatherType) {
        mFeelingResultEntity.setWeatherType(weatherType);
        getView().showCommentEnterDialog();
    }

    @Override
    public void onConfirmStartEcgRecording() {
        saveCurrentResults()
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long experimentResultId) throws Exception {
                        getView().showResultsSuccessfullySavedMessage();
                        getRouter().navigateToEcgRecordingActivity(experimentResultId);
                    }
                });

    }


    private Single<Long> saveCurrentResults() {
        mExperimentResultEntity.setFeelingResultEntity(mFeelingResultEntity);
        SaveExperimentResultForCurrentUserUseCase.Params params = new SaveExperimentResultForCurrentUserUseCase.Params(mUserSessionManager.getCurrentUserEntityId().getId(), mExperimentResultEntity);
        return mSaveExperimentResultForCurrentUser
                .execute(params);
    }

    @Override
    public void onCommentEntered(String comment) {
        mFeelingResultEntity.setComment(comment);
        getView().showStartRecordingDialog();
    }

    @Override
    public void onFeelingTypeSelected(@FeelingResultEntity.FeelingType String feelingType) {
        mFeelingResultEntity.setFeelingType(feelingType);
        getView().showWeatherTypeSelectionDialog();
    }
}
