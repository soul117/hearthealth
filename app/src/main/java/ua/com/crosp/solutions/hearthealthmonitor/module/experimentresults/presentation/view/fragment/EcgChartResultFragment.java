package ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.presentation.view.fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;

import com.scichart.charting.model.dataSeries.IDataSeries;
import com.scichart.charting.model.dataSeries.XyDataSeries;
import com.scichart.charting.visuals.SciChartSurface;
import com.scichart.charting.visuals.axes.AutoRange;
import com.scichart.charting.visuals.axes.NumericAxis;
import com.scichart.charting.visuals.renderableSeries.FastLineRenderableSeries;
import com.scichart.core.framework.UpdateSuspender;
import com.scichart.drawing.common.PenStyle;
import com.scichart.drawing.common.SolidPenStyle;
import com.scichart.extensions.builders.SciChartBuilder;

import java.util.Collections;

import javax.inject.Inject;

import butterknife.BindView;
import ua.com.crosp.solutions.hearthealthmonitor.R;
import ua.com.crosp.solutions.hearthealthmonitor.base.view.BaseFragment;
import ua.com.crosp.solutions.hearthealthmonitor.common.contants.BundleConstants;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.presenter.EcgChartResultPresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.view.EcgResultChartViewContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.dicomponent.EcgResultsComponent;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.presentation.view.viewmodel.EcgChartAudioData;

/**
 * Created by Alexander Molochko on 5/24/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class EcgChartResultFragment extends BaseFragment implements EcgResultChartViewContract {
    public static final String ECG_MAIN_Y_AXIS = "ECG_Main_Y_Axis";
    public static final float LINE_THICKNESS = 1f;

    // Views
    @BindView(R.id.ecg_chart)
    SciChartSurface mSciChartSurface;

    @Inject
    EcgChartResultPresenterContract mPresenter;
    private SciChartBuilder mSciChartBuilder;
    private XyDataSeries<Double, Short> mEcgDataSeries;

    @Override
    public int getFragmentLayoutId() {
        return R.layout.fragment_ecg_chart_result;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.injectDependencies();
    }

    private void injectDependencies() {
        // Injecting dependencies
        this.getComponent(EcgResultsComponent.class).inject(this);
        // Set presenter this as the presenter view
        mPresenter.setView(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.onViewReady();
        parseAndSetArguments();
        setupChart();
    }

    private void parseAndSetArguments() {
        Bundle bundle = getArguments();
        long experimentId = bundle.getLong(BundleConstants.Arguments.EXPERIMENT_RESULT_ID);
        mPresenter.initialize(experimentId);
    }

    //region Lifecycle
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        releaseResources();
        mPresenter.onViewDestroyed();
        super.onDestroy();
    }


    @Override
    public void displayChartData(EcgChartAudioData ecgRawAudioData) {
        if (mEcgDataSeries == null) {
            initDefaultChartData();
        }
        mEcgDataSeries.clear();
        mEcgDataSeries.append(ecgRawAudioData.getTimeValues(), ecgRawAudioData.getEcgValues());
    }

    private void initDefaultChartData() {
        mEcgDataSeries = mSciChartBuilder
                .newXyDataSeries(Double.class, Short.class)
                .build();
    }

    private void releaseResources() {
        try {
            SciChartBuilder.dispose();
            // mCheckQueueEcgValuesThread.stopProcessing();
            //mThreadExecutor.shutdown();
        } catch (Exception ex) {

        }
    }


    private void setupChart() {
        SciChartBuilder.init(this.getActivity());
        mSciChartBuilder = SciChartBuilder.instance();
        initDefaultChartData();
        final NumericAxis xAxis = generateEcgXAxis();
        final NumericAxis yAxis = generateEcgYAxis(ECG_MAIN_Y_AXIS);
        // Main line drawer
        final FastLineRenderableSeries mainEcgLineRenderable = generateRenderableSeries(mEcgDataSeries, ECG_MAIN_Y_AXIS, new SolidPenStyle(getResources().getColor(R.color.app_ecg), true, LINE_THICKNESS, null));
        // Sweed line drawer
        UpdateSuspender.using(mSciChartSurface, new Runnable() {
            @Override
            public void run() {
                Collections.addAll(mSciChartSurface.getXAxes(), xAxis);
                Collections.addAll(mSciChartSurface.getYAxes(), yAxis);
                Collections.addAll(mSciChartSurface.getRenderableSeries(), mainEcgLineRenderable);
                Collections.addAll(mSciChartSurface.getChartModifiers(), mSciChartBuilder.newModifierGroupWithDefaultModifiers().build());
            }
        });
    }

    private FastLineRenderableSeries generateRenderableSeries(IDataSeries<Double, Short> dataSeries, String yAxisId, PenStyle penStyle) {
        final FastLineRenderableSeries lineRenderable = new FastLineRenderableSeries();
        lineRenderable.setDataSeries(dataSeries);
        lineRenderable.setYAxisId(yAxisId);
        //  lineRenderable.setPaletteProvider(paletteProvider);
        lineRenderable.setStrokeStyle(penStyle);
        return lineRenderable;
    }

    private NumericAxis generateEcgXAxis() {
        return mSciChartBuilder
                .newNumericAxis()
                .withAxisTitle(getString(R.string.title_axis_x_time))
                // .withDrawLabels(false)
                //   .withVisibleRange(new DoubleRange(0.0, mPresenter.getVisibleRangeCoefficient()))
                //  .withDrawMinorGridLines(false)
                //   .withVisibleRange(0, 2040)
                .withGrowBy(0.05d, 0.05d)
                //.withVisibleRange(xVisibleRange).withAutoRangeMode(AutoRange.Never)
                // .withVisibleRange(xVisibleRange).withAutoRangeMode(AutoRange.Never)
                .withAutoRangeMode(AutoRange.Once)
                .build();
    }

    private NumericAxis generateEcgYAxis(String axisId) {
        return mSciChartBuilder
                .newNumericAxis()
                .withAxisTitle(getString(R.string.title_axis_y_voltage))
                .withAxisId(axisId)
                //  .withAxisAlignment(AxisAlignment.Left)
                //.withAutoRangeMode(AutoRange.Always)
                .withAutoRangeMode(AutoRange.Once)
                .build();
    }

}
