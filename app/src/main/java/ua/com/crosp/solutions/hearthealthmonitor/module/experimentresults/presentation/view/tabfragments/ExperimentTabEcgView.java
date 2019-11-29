package ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.presentation.view.tabfragments;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import butterknife.BindView;
import butterknife.OnClick;
import ua.com.crosp.solutions.hearthealthmonitor.R;
import ua.com.crosp.solutions.hearthealthmonitor.base.view.BaseSimpleViewGroup;
import ua.com.crosp.solutions.hearthealthmonitor.common.view.IconDescriptionView;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.presentation.view.viewmodel.ExperimentEcgResultViewModel;

/**
 * A placeholder fragment containing a simple view.
 */
public class ExperimentTabEcgView extends BaseSimpleViewGroup {
    // Views

    @BindView(R.id.icon_description_ecg_date_start)
    IconDescriptionView mStartDateIconView;
    @BindView(R.id.icon_description_ecg_date_end)
    IconDescriptionView mEndDateIconView;
    @BindView(R.id.icon_description_ecg_time_estimated)
    IconDescriptionView mEstimatedTimeIconView;
    @BindView(R.id.icon_description_ecg_was_interrupted)
    IconDescriptionView mWasInterruptedIconView;

    // Members
    ActionButtonHandler mActionButtonHandler;

    public ExperimentTabEcgView(Context context) {
        super(context);
    }

    public ExperimentTabEcgView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ExperimentTabEcgView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //Variables

    //================================================================================
    // Lifecycle callbacks
    //================================================================================
    @Override
    public int getViewLayoutId() {
        return R.layout.view_tab_experiment_result_ecg;
    }

    public ActionButtonHandler getActionButtonHandler() {
        return mActionButtonHandler;
    }

    public void setActionButtonHandler(ActionButtonHandler actionButtonHandler) {
        mActionButtonHandler = actionButtonHandler;
    }

    // Button listeners

    @OnClick(R.id.icon_button_show_ecg)
    void onShowEcgChartClick(View view) {
        if (mActionButtonHandler != null) {
            mActionButtonHandler.onShowEcgChartClick();
        }
    }

    //================================================================================
    // View implementation
    //================================================================================

    public void displayResults(ExperimentEcgResultViewModel viewModel) {
        mEndDateIconView.setDescription(viewModel.dateEnd);
        mStartDateIconView.setDescription(viewModel.dateStart);
        mEstimatedTimeIconView.setDescription(String.valueOf(viewModel.estimatedTime));
        mWasInterruptedIconView.setDescription(viewModel.wasInterrupted);
    }

    public interface ActionButtonHandler {
        void onShowEcgChartClick();
    }
}


