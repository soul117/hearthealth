package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.common;

import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.entity.FeelingResultEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.entity.GameResultEntity;

/**
 * Created by Alexander Molochko on 10/30/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public interface BaseGameViewPresenter {
    void onGameFinished(GameResultEntity experimentResults);

    void onStopDialogResult(int which, CharSequence text);

    void onCancelStartEcgRecording();

    void onWeatherTypeSelected(@FeelingResultEntity.WeatherType String weatherType);

    void onConfirmStartEcgRecording();

    void onCommentEntered(String comment);

    void onFeelingTypeSelected(@FeelingResultEntity.FeelingType String feelingType);

    void onGameStarted();

    void onGameCancelledWithoutResults();


}
