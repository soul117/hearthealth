package ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.presentation.view.fragment;

import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.scichart.charting.model.dataSeries.IDataSeries;
import com.scichart.charting.model.dataSeries.IXyDataSeries;
import com.scichart.charting.visuals.SciChartSurface;
import com.scichart.charting.visuals.axes.AutoRange;
import com.scichart.charting.visuals.axes.NumericAxis;
import com.scichart.charting.visuals.pointmarkers.EllipsePointMarker;
import com.scichart.charting.visuals.renderableSeries.FastLineRenderableSeries;
import com.scichart.charting.visuals.renderableSeries.XyRenderableSeriesBase;
import com.scichart.charting.visuals.renderableSeries.XyScatterRenderableSeries;
import com.scichart.charting.visuals.renderableSeries.paletteProviders.IPaletteProvider;
import com.scichart.charting.visuals.renderableSeries.paletteProviders.IStrokePaletteProvider;
import com.scichart.charting.visuals.renderableSeries.paletteProviders.PaletteProviderBase;
import com.scichart.core.framework.ISuspendable;
import com.scichart.core.framework.UpdateSuspender;
import com.scichart.core.model.DoubleValues;
import com.scichart.core.model.IntegerValues;
import com.scichart.core.model.LongValues;
import com.scichart.core.model.ShortValues;
import com.scichart.data.model.DoubleRange;
import com.scichart.drawing.common.BrushStyle;
import com.scichart.drawing.common.PenStyle;
import com.scichart.drawing.common.SolidBrushStyle;
import com.scichart.drawing.common.SolidPenStyle;
import com.scichart.drawing.utility.ColorUtil;
import com.scichart.extensions.builders.SciChartBuilder;

import java.util.Collections;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;
import ua.com.crosp.solutions.hearthealthmonitor.R;
import ua.com.crosp.solutions.hearthealthmonitor.base.presentation.BackPressNotifier;
import ua.com.crosp.solutions.hearthealthmonitor.base.view.BaseFragment;
import ua.com.crosp.solutions.hearthealthmonitor.common.notification.ToastNotifications;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.contract.EcgRealtTimeRecordingViewContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.contract.presentation.EcgRealtimeRecordingPresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.di.EcgRealtimeComponent;

/**
 * Created by Alexander Molochko on 5/24/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class EcgRealTimeRecordingFragment extends BaseFragment implements EcgRealtTimeRecordingViewContract {

    public static final double FIFO_COEFFICIENT = 0.5;
    public static final int BPM_DELAY = 300;
    public static final String BPM_FORMAT = "%s";
    public static final float LINE_THICKNESS = 1.5f;
    public static final String ECG_MAIN_Y_AXIS = "ECG_Main_Y_Axis";
    public static final int CIRCLE_SIZE = 15;

    private int mFifoCapacity;
    private final static long TIME_INTERVAL = 10;
    private final static int MAX_VISIBLE_RANGE_VALUE = 5;
    private final DoubleRange mXVisibleRange = new DoubleRange((double) 0, (double) MAX_VISIBLE_RANGE_VALUE);

    @Inject
    BackPressNotifier mBackPressNotifier;

    @BindView(R.id.sci_chart_ecg)
    SciChartSurface mSciChartSurfaceEcg;

    @BindView(R.id.image_view_heart_icon)
    ImageView mImageViewHeartIcon;

    @BindView(R.id.text_view_bpm_rate)
    TextView mBpmTextView;

    @BindView(R.id.text_view_current_time)
    TextView mCurrentTimeTextView;


    @Inject
    EcgRealtimeRecordingPresenterContract mPresenter;

    // Variables
    private MaterialDialog mLoadingDialog;
    // Chart variables
    protected SciChartBuilder mSciChartBuilder = SciChartBuilder.instance();
    private IXyDataSeries<Double, Double> mEcgDataSeries;
    private IXyDataSeries<Double, Double> mEcgSweepDataSeries;
    private IXyDataSeries<Double, Double> mLastEcgSweepDataSeries;

    private EcgDataBatchData mEcgDataBatchData;
    // Queue
    private AtomicBoolean mIsRunning = new AtomicBoolean(false);
    private Handler mHandler = new Handler();
    private Runnable mUpdateHeartBeatRunnable = new Runnable() {
        @Override
        public void run() {
            mImageViewHeartIcon.setVisibility(View.VISIBLE);
            mImageViewHeartIcon.postDelayed(mHideHeartBPMIconRunnable, BPM_DELAY);
            mBpmTextView.setText(String.format(BPM_FORMAT, String.valueOf(mPresenter.getCurrentHeartRate())));
        }
    };
    private int mCurrentTicks = 0;
    private Disposable mBackPressDisposable;
    private MaterialDialog mExitDialogHandler;

    @Override
    public int getFragmentLayoutId() {
        return R.layout.fragment_ecg_recording;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.injectDependencies();
    }

    private void injectDependencies() {
        // Injecting dependencies
        this.getComponent(EcgRealtimeComponent.class).inject(this);
        // Set presenter this as the presenter view
        mPresenter.setView(this);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.onViewReady();
        subscribeToBackPressEvents();
    }

    private void initChartData() {
        SciChartBuilder.init(this.getActivity());
        mSciChartBuilder = SciChartBuilder.instance();
        mEcgDataSeries = mSciChartBuilder
                .newXyDataSeries(Double.class, Double.class)
                .withFifoCapacity(mFifoCapacity)
                .build();

        mEcgSweepDataSeries = mSciChartBuilder
                .newXyDataSeries(Double.class, Double.class)
                .withFifoCapacity(mFifoCapacity)
                .build();

        mLastEcgSweepDataSeries = mSciChartBuilder
                .newXyDataSeries(Double.class, Double.class)
                .withFifoCapacity(1)
                .build();

        mEcgDataBatchData = new EcgDataBatchData((int) mPresenter.getSamplingRate(), getDecimationCoefficient());
    }

    private int getDecimationCoefficient() {
        return mPresenter.getBufferReadSize() / (MAX_VISIBLE_RANGE_VALUE * MAX_VISIBLE_RANGE_VALUE);
    }

    private void setupChart() {
        // mFifoCapacity = (int) (mPresenter.getSamplingRate() / (getDecimationCoefficient()));
        mFifoCapacity = calculateFifoSize();
        initChartData();
        final NumericAxis xAxis = generateEcgXAxis();
        final NumericAxis yAxis = generateEcgYAxis(ECG_MAIN_Y_AXIS);
        // Main line drawer
        final FastLineRenderableSeries mainEcgLineRenderable = generateRenderableSeries(mEcgDataSeries, ECG_MAIN_Y_AXIS, new DimTracePaletteProvider(), new SolidPenStyle(getResources().getColor(R.color.app_ecg), true, LINE_THICKNESS, null));
        final FastLineRenderableSeries sweepEcgLineRenderable = generateRenderableSeries(mEcgSweepDataSeries, ECG_MAIN_Y_AXIS, new DimTracePaletteProvider(), new SolidPenStyle(getResources().getColor(R.color.app_ecg), true, LINE_THICKNESS, null));
        final XyScatterRenderableSeries lastEcgPointRenderable = generateScatterRenderableSeriesForLastAppendedPoint(mLastEcgSweepDataSeries, ECG_MAIN_Y_AXIS, new SolidBrushStyle(getResources().getColor(R.color.white)), new SolidPenStyle(getResources().getColor(R.color.white), true, 1f, null), CIRCLE_SIZE);
        // Sweed line drawer
        UpdateSuspender.using(mSciChartSurfaceEcg, new Runnable() {
            @Override
            public void run() {
                // mSciChartSurfaceEcg.setXAxes(mXAxes);
                // mSciChartSurfaceEcg.setYAxes(mYAxes);
                Collections.addAll(mSciChartSurfaceEcg.getXAxes(), xAxis);
                Collections.addAll(mSciChartSurfaceEcg.getYAxes(), yAxis);
                Collections.addAll(mSciChartSurfaceEcg.getRenderableSeries(), mainEcgLineRenderable);
                Collections.addAll(mSciChartSurfaceEcg.getRenderableSeries(), sweepEcgLineRenderable);
                Collections.addAll(mSciChartSurfaceEcg.getRenderableSeries(), lastEcgPointRenderable);
            }
        });
    }

    private int calculateFifoSize() {
        return (((int) mPresenter.getSamplingRate() * MAX_VISIBLE_RANGE_VALUE) / getDecimationCoefficient());
    }


    private NumericAxis generateEcgXAxis() {
        return mSciChartBuilder
                .newNumericAxis()
                .withAxisTitle(getString(R.string.title_axis_x_time))
                //.withGrowBy(1.05d, 1.05d)
                //   .withVisibleRange(new DoubleRange(0.0, mPresenter.getVisibleRangeCoefficient()))
                .withDrawMajorBands(false)
                .withDrawMinorGridLines(false)
                .withVisibleRange(mXVisibleRange)
                .withVisibility(View.GONE)
                // .withVisibleRange(0, 8000 * 22)
                // .withGrowBy(0.1d, 0.1d)
                //.withVisibleRange(mXVisibleRange).withAutoRangeMode(AutoRange.Never)
                // .withVisibleRange(mXVisibleRange).withAutoRangeMode(AutoRange.Never)
                .withAutoRangeMode(AutoRange.Never)
                .build();
    }

    private NumericAxis generateEcgYAxis(String axisId) {
        return mSciChartBuilder
                .newNumericAxis()
                .withAxisTitle(getString(R.string.title_axis_y_voltage))
                // .withGrowBy(0.05d, 0.05d)
                .withAxisId(axisId)
                .withDrawLabels(false)
                //  .withDrawMajorTicks(false)
                // .withDrawMinorTicks(false)
                // .withDrawMajorBands(false)
                .withDrawMajorGridLines(false)
                .withDrawMinorGridLines(false)
                .withDrawMajorBands(false)
                .withVisibility(View.GONE)
                //  .withAxisAlignment(AxisAlignment.Left)
                //.withAutoRangeMode(AutoRange.Always)
                .withVisibleRange(Short.MIN_VALUE / 3, Short.MAX_VALUE / 3)
                .build();
    }


    private XyScatterRenderableSeries generateScatterRenderableSeriesForLastAppendedPoint(IDataSeries<Double, Double> dataSeries, String yAxisId, BrushStyle brushStyle, PenStyle penStyle, int size) {
        final XyScatterRenderableSeries lineRenderable = new XyScatterRenderableSeries();
        lineRenderable.setDataSeries(dataSeries);
        lineRenderable.setYAxisId(yAxisId);
        EllipsePointMarker ellipsePointMaker = new EllipsePointMarker();
        ellipsePointMaker.setFillStyle(brushStyle);
        ellipsePointMaker.setSize(size, size);
        lineRenderable.setPointMarker(ellipsePointMaker);
        lineRenderable.setStrokeStyle(penStyle);
        return lineRenderable;
    }

    private FastLineRenderableSeries generateRenderableSeries(IDataSeries<Double, Double> dataSeries, String yAxisId, IPaletteProvider paletteProvider, PenStyle penStyle) {
        final FastLineRenderableSeries lineRenderable = new FastLineRenderableSeries();
        lineRenderable.setDataSeries(dataSeries);
        lineRenderable.setYAxisId(yAxisId);
        lineRenderable.setPaletteProvider(paletteProvider);
        lineRenderable.setStrokeStyle(penStyle);
        return lineRenderable;
    }

    @Override
    public void startDrawing() {
        setupChart();
        mIsRunning.set(true);
    }


    @Override
    public void onResume() {
        mPresenter.onViewResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        mPresenter.onViewPause();
        super.onPause();
    }

    private void releaseResources() {
        try {
            mIsRunning.set(false);
            SciChartBuilder.dispose();
            // mCheckQueueEcgValuesThread.stopProcessing();
            //mThreadExecutor.shutdown();
        } catch (Exception ex) {

        }
    }

    protected void releaseSubscriptions() {
        if (mBackPressDisposable != null) {
            mBackPressDisposable.dispose();
        }
    }

    @Override
    public void onStop() {
        mPresenter.onViewStop();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        mPresenter.onViewDestroyed();
        releaseResources();
        releaseSubscriptions();
        dismissAllDialogs();
        super.onDestroy();
    }

    @Override
    public void renderNewGraphData(ShortValues voltage, LongValues time) {

    }

    @Override
    public void renderNewGraphData(ShortValues voltage, DoubleValues time) {
        // Update
        mEcgDataBatchData.updateData(voltage, time);
        mEcgDataSeries.append(mEcgDataBatchData.xValues, mEcgDataBatchData.ecgHeartRateValuesA);
        mEcgSweepDataSeries.append(mEcgDataBatchData.xValues, mEcgDataBatchData.ecgHeartRateValuesB);
        mLastEcgSweepDataSeries.append(mEcgDataBatchData.lastTimeValue, mEcgDataBatchData.lastEcgValue);
    }

    @Override
    public void showArgumentsErrorMessage() {
        ToastNotifications.showErrorMessage(getContext(), getString(R.string.message_error_no_arguments));
    }

    @Override
    public void showLoading() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    mLoadingDialog = new MaterialDialog.Builder(getContext())
                            .title(R.string.title_dialog_loading)
                            .content(R.string.text_please_wait)
                            .progress(true, 0)
                            .progressIndeterminateStyle(true)
                            .show();
                } catch (Exception ex) {

                }
            }
        });

    }

    @Override
    public void hideLoading() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    if (mLoadingDialog != null) {
                        mLoadingDialog.dismiss();
                    }
                } catch (Exception ex) {

                }
            }
        });

    }

    @Override
    public void stopDrawing() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    SciChartBuilder.dispose();
                } catch (Exception ex) {

                }
            }
        });

    }

    private void subscribeToBackPressEvents() {
        mBackPressDisposable = mBackPressNotifier.subscribeToBackPressEvents()
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        mPresenter.onBackButtonPress();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ToastNotifications.showErrorMessage(mContext, throwable.getMessage());
                    }
                });
    }

    @Override
    public PublishSubject<Boolean> showExitConfirmDialog() {
        final PublishSubject<Boolean> subject = PublishSubject.create();
        mExitDialogHandler = new MaterialDialog.Builder(getActivity())
                .title(R.string.title_dialog_go_back_ecg_recording)
                .content(R.string.content_dialog_go_back_ecg_recording, true)
                .positiveText(R.string.button_text_yes)
                .negativeText(R.string.button_text_no)
                .cancelable(false)
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                        dialog.cancel();
                        subject.onNext(false);
                        subject.onComplete();
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                        dialog.cancel();
                        subject.onNext(true);
                        subject.onComplete();

                    }
                })
                .show();
        return subject;
    }

    @Override
    public void dismissAllDialogs() {
        try {
            if (mExitDialogHandler != null) {
                mExitDialogHandler.dismiss();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean isRunning() {
        return mIsRunning.get();
    }

    @Override
    public ISuspendable getChartSurface() {
        return mSciChartSurfaceEcg;
    }

    @Override
    public void onRPeakDetected(long rPeakDetectedPosition) {
        mHandler.post(mUpdateHeartBeatRunnable);
    }


    private Runnable mUpdateTimerRunnable = new Runnable() {
        @Override
        public void run() {
            mCurrentTimeTextView.setText(String.valueOf(mCurrentTicks));
        }
    };

    @Override
    public void updateCurrentTime(int currentTimerTicks) {
        mCurrentTicks = currentTimerTicks;
        mHandler.post(mUpdateTimerRunnable);
    }


    private Runnable mHideHeartBPMIconRunnable = new Runnable() {
        @Override
        public void run() {
            mImageViewHeartIcon.setVisibility(View.INVISIBLE);
        }
    };

    private class DimTracePaletteProvider extends PaletteProviderBase<XyRenderableSeriesBase> implements IStrokePaletteProvider {
        private final IntegerValues colors = new IntegerValues();

        private float startOpacity = 0.2f; // fade out start opacity
        private float endOpacity = 1.0f; // fade out end opacity
        private float opacityDiff = endOpacity - startOpacity;

        protected DimTracePaletteProvider() {
            super(XyRenderableSeriesBase.class);
        }

        @Override
        public void update() {
            int size = renderableSeries.getCurrentRenderPassData().pointsCount();
            colors.setSize(size);

            int defaultColor = renderableSeries.getStrokeStyle().getColor();
            int[] colorsArray = colors.getItemsArray();

            int lastIndex = size - 1;
            for (int i = 0; i < lastIndex; i++) {
                float fraction = i / (float) size;
                float opacity = startOpacity + fraction * opacityDiff;
                colorsArray[i] = ColorUtil.argb(defaultColor, opacity);
            }
        }

        @Override
        public IntegerValues getStrokeColors() {
            return colors;
        }
    }

    private class EcgDataBatchData {
        public static final int TRACE_A = 3;
        public static final int TRACE_B = 4;
        public static final double DIFF_COEFFICIENT = 0.009;
        private final double sampleRate;
        private final int decimationCoefficient;
        private double lastTimeValue;
        int currentTrace = TRACE_A;
        DoubleValues xValues = new DoubleValues();
        DoubleValues ecgHeartRateValuesA = new DoubleValues();
        DoubleValues ecgHeartRateValuesB = new DoubleValues();
        Double lastEcgValue = 0d;

        public EcgDataBatchData(int samplingRate, int decCoefficient) {
            sampleRate = samplingRate;
            decimationCoefficient = decCoefficient;
        }

        public int getCurrentTrace() {
            return currentTrace;
        }

        public void setCurrentTrace(int currentTrace) {
            this.currentTrace = currentTrace;
        }

        public void clearValues() {
            xValues.clear();
            ecgHeartRateValuesA.clear();
            ecgHeartRateValuesB.clear();
        }

        public void toggleTrace() {
            if (currentTrace == TRACE_A) {
                ecgHeartRateValuesA.add(Double.NaN);
                setCurrentTrace(TRACE_B);
            } else {
                ecgHeartRateValuesB.add(Double.NaN);
                setCurrentTrace(TRACE_A);

            }
        }

        public void updateData(ShortValues voltage, DoubleValues timeValues) {
            clearValues();
            Double normalizedTime = 0.0;
            lastEcgValue = 0d;
            for (int i = 0; i < voltage.size(); i = i + decimationCoefficient) {
                normalizedTime = timeValues.get(i) / sampleRate % MAX_VISIBLE_RANGE_VALUE;

                xValues.add(normalizedTime);
                //xValues.add(timeValues.get(i));
                short currentVoltage = voltage.get(i);
                if (currentTrace == TRACE_A) {
                    ecgHeartRateValuesB.add(Double.NaN);
                    ecgHeartRateValuesA.add(currentVoltage);
                } else {
                    ecgHeartRateValuesA.add(Double.NaN);
                    ecgHeartRateValuesB.add(currentVoltage);
                }
                lastEcgValue = (double) currentVoltage;
                if (MAX_VISIBLE_RANGE_VALUE - normalizedTime <= DIFF_COEFFICIENT) {
                    toggleTrace();
                }
            }
            // Get last value
            lastTimeValue = normalizedTime;
        }
    }
}
