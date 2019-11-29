package ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.presentation.view.tabfragments;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import butterknife.BindView;
import ua.com.crosp.solutions.hearthealthmonitor.R;
import ua.com.crosp.solutions.hearthealthmonitor.base.view.BaseSimpleViewGroup;
import ua.com.crosp.solutions.hearthealthmonitor.common.view.IconDescriptionView;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.presentation.view.viewmodel.ExperimentFeelingResultViewModel;

/**
 * A placeholder fragment containing a simple view.
 */
public class ExperimentTabFeelingView extends BaseSimpleViewGroup {
    // Views

    @BindView(R.id.icon_description_feeling)
    IconDescriptionView mFeelingIconView;
    @BindView(R.id.icon_description_feeling_weather)
    IconDescriptionView mWeatherConditionIconView;
    @BindView(R.id.icon_description_feeling_comment)
    IconDescriptionView mCommentIconView;

    public ExperimentTabFeelingView(Context context) {
        super(context);
        setupUI();
    }

    public ExperimentTabFeelingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setupUI();
    }

    public ExperimentTabFeelingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupUI();
    }
    //Variables

    //================================================================================
    // Lifecycle callbacks
    //================================================================================

    //================================================================================
    // UI methods
    //================================================================================
    protected void setupUI() {

    }

    //================================================================================
    // View implementation
    //================================================================================

    public void displayResults(ExperimentFeelingResultViewModel viewModel) {
        mFeelingIconView.setDescription(viewModel.feelingType);
        mWeatherConditionIconView.setDescription(viewModel.weatherConditions);
        mCommentIconView.setDescription(viewModel.comment);
    }


    @Override
    public int getViewLayoutId() {
        return R.layout.view_tab_experiment_result_feeling;
    }
}


