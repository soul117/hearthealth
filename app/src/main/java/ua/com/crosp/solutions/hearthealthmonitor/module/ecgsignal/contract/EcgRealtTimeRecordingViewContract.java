package ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.contract;

import android.os.Bundle;

import com.scichart.core.framework.ISuspendable;
import com.scichart.core.model.DoubleValues;
import com.scichart.core.model.LongValues;
import com.scichart.core.model.ShortValues;

import io.reactivex.subjects.PublishSubject;
import ua.com.crosp.solutions.hearthealthmonitor.base.view.BaseView;

/**
 * Created by Alexander Molochko on 2/20/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public interface EcgRealtTimeRecordingViewContract extends BaseView {

    void startDrawing();

    Bundle getArguments();

    void renderNewGraphData(ShortValues voltage, LongValues time);

    void renderNewGraphData(ShortValues voltage, DoubleValues time);

    void showArgumentsErrorMessage();

    void showLoading();

    void hideLoading();

    boolean isRunning();

    ISuspendable getChartSurface();

    void onRPeakDetected(long rPeakDetectedPosition);

    void updateCurrentTime(int currentTimerTicks);

    void stopDrawing();

    PublishSubject<Boolean> showExitConfirmDialog();

    void dismissAllDialogs();
}
