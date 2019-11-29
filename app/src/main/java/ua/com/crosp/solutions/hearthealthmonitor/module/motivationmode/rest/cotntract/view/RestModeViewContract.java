package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.cotntract.view;

import ua.com.crosp.solutions.hearthealthmonitor.base.view.BaseView;

/**
 * Created by Alexander Molochko on 12/19/16.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

public interface RestModeViewContract extends BaseView {
    void startTimerCountdown();

    void setTimerTime(long seconds);

    void stopTimerCountdown();

    void setTimerLabel(String label);

    void hideRestartButton();

    void hidePlayButton();

    void hideStopButton();

    void showRestartButton();

    void showStopButton();

    void showPlayButton();

    void showFeelingTypeSelectionDialog();

    void continueGame();

    void stopGameAndSendResults();

    void stopGameWithoutSavingResults();

    void showStartRecordingDialog();

    void showResultsSuccessfullySavedMessage();

    void showCommentEnterDialog();

    void showWeatherTypeSelectionDialog();

    void showGameExitConfirmationDialog();
}
