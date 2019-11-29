package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.view;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.SurfaceView;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;
import ua.com.crosp.solutions.hearthealthmonitor.R;
import ua.com.crosp.solutions.hearthealthmonitor.base.presentation.BackPressNotifier;
import ua.com.crosp.solutions.hearthealthmonitor.base.view.BaseMotivationalModeFragment;
import ua.com.crosp.solutions.hearthealthmonitor.common.notification.ToastNotifications;
import ua.com.crosp.solutions.hearthealthmonitor.game.engine.CountdownGameDrawer;
import ua.com.crosp.solutions.hearthealthmonitor.game.graphics.BaseGameView;
import ua.com.crosp.solutions.hearthealthmonitor.game.graphics.contract.GraphicsRenderer;
import ua.com.crosp.solutions.hearthealthmonitor.game.graphics.contract.SurfaceListener;
import ua.com.crosp.solutions.hearthealthmonitor.module.appsettings.data.AppSettingsModel;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.entity.FeelingResultEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.entity.GameResultEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.contract.game.CatchTheBallGameEngineContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.contract.presenter.CatchTheBallGamePresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.contract.view.CatchTheBallViewContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.diassembly.PsychoEmotionalComponent;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.diassembly.module.CatchTheBallModule;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.game.model.valueobject.CanvasSize;

/**
 * Created by Alexander Molochko on 2/19/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class CatchTheBallGameFragment extends BaseMotivationalModeFragment implements CatchTheBallViewContract, SurfaceListener {

    public static final String DEFAULT_BACKGROUND_APP_COLOR = "default_background_app_color";
    @Inject
    protected CatchTheBallGameEngineContract mGameEngine;
    @Inject
    protected GraphicsRenderer mGraphicsRenderer;
    @Inject
    protected BaseGameView mGameView;
    @Inject
    protected CountdownGameDrawer mCountdownGameDrawer;
    @Inject
    CatchTheBallGamePresenterContract mPresenter;
    @Inject
    BackPressNotifier mBackPressNotifier;
    @Inject
    AppSettingsModel mAppSettingsModel;
    @Inject
    @Named(DEFAULT_BACKGROUND_APP_COLOR)
    Integer mDefaultBackgroundColor;

    // Variables
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.injectDependencies();
        mPresenter.setView(this);
        subscribeToBackPressEvents();
    }


    private void injectDependencies() {
        PsychoEmotionalComponent psychoEmotionalComponent = getComponent(PsychoEmotionalComponent.class);
        psychoEmotionalComponent
                .plusGameComponent(new CatchTheBallModule(this, this))
                .inject(this);

    }

    private void subscribeToBackPressEvents() {
        mBackPressDisposable = mBackPressNotifier.subscribeToBackPressEvents()
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        mPresenter.onBackPressed();
                        mGameEngine.pauseGame();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ToastNotifications.showErrorMessage(mContext, throwable.getMessage());
                    }
                });
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public SurfaceView provideSurfaceView() {
        return mGameView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        mGameEngine.pauseGame();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mGameEngine.stopGame();
        releaseSubscriptions();
        super.onDestroy();
    }


    @Override
    public void onGameStarted() {
        mGameView.shouldStart();
    }

    @Override
    public void onGameStopped() {
        mGameView.shouldStop();
    }


    @Override
    public GraphicsRenderer prepareAndGetGraphicsRenderer() {
        mGraphicsRenderer.setCurrentCanvas(mGameView.prepareAndGetCanvas());
        return mGraphicsRenderer;
    }

    @Override
    public void onDrawingFinished() {
        mGameView.releaseCanvas();
    }

    @Override
    public CanvasSize getCanvasSize() {
        return mGameView.getCanvasSize();
    }


    @Override
    public void surfaceCreated() {

    }

    @Override
    public void surfaceChanged(int format, int width, int height) {
        if (!mGameEngine.isRunning()) {
            if (mAppSettingsModel.showCountdownTimer()) {
                PublishSubject<Boolean> countdownSubject = mCountdownGameDrawer.startDrawingCountDown(mGameView);
                mCountDownDisposable = countdownSubject
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) throws Exception {
                                mGameEngine.initGameObjects();
                                mGameEngine.startGame();
                            }
                        });
            } else {
                mGameEngine.initGameObjects();
                mGameEngine.startGame();
            }
        }
    }

    @Override
    public void surfaceDestroyed() {
        try {
            mGameEngine.pauseGame();
            mGameView.shouldStop();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public void continueGame() {
        mGameEngine.resumeGame();
    }

    @Override
    public void stopGameWithoutSavingResults() {
        mGameEngine.stopGame();
        mPresenter.onGameCancelledWithoutResults();
    }

    @Override
    public void stopGameAndSendResults() {
        mGameEngine.stopExperimentAndGetResults()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GameResultEntity>() {
                    @Override
                    public void accept(GameResultEntity gameResultEntity) throws Exception {
                        mPresenter.onGameFinished(gameResultEntity);
                    }
                });
    }

    @Override
    public void showResultsSuccessfullySavedMessage() {
        ToastNotifications.showSuccessMessage(mContext, getString(R.string.message_results_saved));
    }

    @Override
    protected int provideDefaultAppBackgroundColor() {
        return mDefaultBackgroundColor;
    }

    @Override
    protected void onExitDialogOptionSelected(int index, String description) {
        mPresenter.onStopDialogResult(index, description);
    }

    @Override
    protected void onFeelingTypeSelected(@FeelingResultEntity.FeelingType String feelingType) {
        mPresenter.onFeelingTypeSelected(feelingType);
    }

    @Override
    protected void onWeatherTypeSelected(@FeelingResultEntity.WeatherType String weatherType) {
        mPresenter.onWeatherTypeSelected(weatherType);

    }

    @Override
    protected void onStartRecordingAfterResultAction(DialogAction dialogAction) {
        if (dialogAction == DialogAction.NEGATIVE) {
            mPresenter.onCancelStartEcgRecording();
        } else {
            mPresenter.onConfirmStartEcgRecording();
        }
    }

    @Override
    protected void onCommentEntered(String comment) {
        mPresenter.onCommentEntered(comment);
    }

}
