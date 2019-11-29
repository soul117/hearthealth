package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.presentation;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.functions.Consumer;
import ua.com.crosp.solutions.hearthealthmonitor.R;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.valueobject.GeneralDate;
import ua.com.crosp.solutions.hearthealthmonitor.base.presentation.BaseAppPresenter;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util.DateUtils;
import ua.com.crosp.solutions.hearthealthmonitor.module.appsettings.data.AppSettingsModel;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.entity.ExperimentResultEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.entity.FeelingResultEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.entity.GameResultEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.usecase.SaveExperimentResultForCurrentUserUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.contract.presenter.CatchTheBallGamePresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.contract.routing.PERouterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.contract.view.CatchTheBallViewContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.cotntract.UserSessionManagerContract;

/**
 * Created by Alexander Molochko on 4/21/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class CatchTheBallGamePresenter extends BaseAppPresenter<CatchTheBallViewContract, PERouterContract> implements CatchTheBallGamePresenterContract {

    // Constants
    public static final int STOP_EXPERIMENT_OPTION_STOP_WITHOUT_SAVING = 0;
    public static final int STOP_EXPERIMENT_OPTION_START_ECG_RECORDING = 1;
    public static final int STOP_EXPERIMENT_OPTION_CANCEL_AND_CONTINUE = 2;
    private final UserSessionManagerContract mUserSessionManager;

    // Component & modular dependencies

    // Temp variables
    private ExperimentResultEntity mExperimentResultEntity;
    private FeelingResultEntity mFeelingResultEntity;
    @Inject
    AppSettingsModel mAppSettingsModel;
    @Inject
    SaveExperimentResultForCurrentUserUseCase mSaveExperimentResultForCurrentUser;


    @Inject
    public CatchTheBallGamePresenter(AppSettingsModel appSettingsModel, SaveExperimentResultForCurrentUserUseCase experimentResultForCurrentUserUseCase, UserSessionManagerContract userSessionManagerContract, PERouterContract router) {
        setRouter(router);
        mUserSessionManager = userSessionManagerContract;
        mAppSettingsModel = appSettingsModel;
        mSaveExperimentResultForCurrentUser = experimentResultForCurrentUserUseCase;
    }

    @Override
    public void onViewReady() {
    }

    @Override
    public void onViewDestroyed() {

    }

    // TODO move to the inner layers use cases or business
    private void createExperimentResultIfRequired() {
        if (mExperimentResultEntity == null) {
            mExperimentResultEntity = ExperimentResultEntity.createNullableEntity();
            mExperimentResultEntity.setExperimentName(getView().getStringByResourceId(R.string.experiment_name_psycho_emotional));
            mExperimentResultEntity.setDateOfCreation(new GeneralDate(DateUtils.getCurrentDate()));
            mExperimentResultEntity.setUserId(mUserSessionManager.getCurrentUserEntityId());
            mExperimentResultEntity.setBaseExperimentPathFromUserId(mUserSessionManager.getCurrentUserEntityId().toString());
        }
        if (mFeelingResultEntity == null) {
            mFeelingResultEntity = new FeelingResultEntity();
        }
    }

    @Override
    public void onGameFinished(GameResultEntity gameResultEntity) {
        createExperimentResultIfRequired();
        mExperimentResultEntity.setGameResultEntity(gameResultEntity);
        getView().showFeelingTypeSelectionDialog();
    }

    // First, select feeling
    @Override
    public void onFeelingTypeSelected(@FeelingResultEntity.FeelingType String feelingType) {
        mFeelingResultEntity.setFeelingType(feelingType);
        getView().showWeatherTypeSelectionDialog();
    }

    // Secondly, select weather type
    @Override
    public void onWeatherTypeSelected(@FeelingResultEntity.WeatherType String weatherType) {
        mFeelingResultEntity.setWeatherType(weatherType);
        getView().showCommentEnterDialog();

    }

    // Lastly, enter comment
    @Override
    public void onCommentEntered(String comment) {
        mFeelingResultEntity.setComment(comment);
        getView().showStartRecordingDialog();
    }

    @Override
    public void onConfirmStartEcgRecording() {
        saveCurrentResults()
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long experimentId) throws Exception {
                        getView().showResultsSuccessfullySavedMessage();
                        getRouter().navigateToEcgRecordingActivity(experimentId);
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

    private Single<Long> saveCurrentResults() {
        mExperimentResultEntity.setFeelingResultEntity(mFeelingResultEntity);
        SaveExperimentResultForCurrentUserUseCase.Params params = new SaveExperimentResultForCurrentUserUseCase.Params(mUserSessionManager.getCurrentUserEntityId().getId(), mExperimentResultEntity);
        return mSaveExperimentResultForCurrentUser
                .execute(params);
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

}
