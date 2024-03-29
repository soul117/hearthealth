package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.contract.view;

import ua.com.crosp.solutions.hearthealthmonitor.base.view.BaseView;
import ua.com.crosp.solutions.hearthealthmonitor.game.graphics.contract.GraphicsRenderer;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.game.model.valueobject.CanvasSize;

/**
 * Created by Alexander Molochko on 2/20/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public interface CatchTheBallViewContract extends BaseView {
    public void onGameStarted();

    public void onGameStopped();

    public GraphicsRenderer prepareAndGetGraphicsRenderer();

    public void onDrawingFinished();

    public CanvasSize getCanvasSize();

    void showGameExitConfirmationDialog();

    void continueGame();

    void stopGameWithoutSavingResults();

    void stopGameAndSendResults();

    void showFeelingTypeSelectionDialog();

    void showWeatherTypeSelectionDialog();

    void showCommentEnterDialog();

    void showStartRecordingDialog();

    void showResultsSuccessfullySavedMessage();
}
