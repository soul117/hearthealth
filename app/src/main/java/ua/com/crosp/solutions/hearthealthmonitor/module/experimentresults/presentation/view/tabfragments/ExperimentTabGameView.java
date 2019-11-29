package ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.presentation.view.tabfragments;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import butterknife.BindView;
import ua.com.crosp.solutions.hearthealthmonitor.R;
import ua.com.crosp.solutions.hearthealthmonitor.base.view.BaseSimpleViewGroup;
import ua.com.crosp.solutions.hearthealthmonitor.common.view.IconDescriptionView;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.presentation.view.viewmodel.ExperimentGameResultViewModel;

/**
 * A placeholder fragment containing a simple view.
 */
public class ExperimentTabGameView extends BaseSimpleViewGroup {
    // Views

    @BindView(R.id.icon_description_game_name)
    IconDescriptionView mNameIconView;
    @BindView(R.id.icon_description_game_type)
    IconDescriptionView mTypeIconView;
    @BindView(R.id.icon_description_game_actual_time)
    IconDescriptionView mActualTimeIconView;
    @BindView(R.id.icon_description_game_estimated_time)
    IconDescriptionView mEstimatedTimeIconView;
    @BindView(R.id.icon_description_game_was_interrupted)
    IconDescriptionView mWasInterruptedIconView;
    @BindView(R.id.icon_description_game_additional_info)
    IconDescriptionView mAdditionalInfoIconView;

    public ExperimentTabGameView(Context context) {
        super(context);
        setupUI();
    }

    public ExperimentTabGameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setupUI();
    }

    public ExperimentTabGameView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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

    public void displayResults(ExperimentGameResultViewModel viewModel) {
        mNameIconView.setDescription(viewModel.gameName);
        mTypeIconView.setDescription(viewModel.gameType);
        mActualTimeIconView.setDescription(String.valueOf(viewModel.gameActualTime));
        mEstimatedTimeIconView.setDescription(String.valueOf(viewModel.gameEstimatedTime));
        mWasInterruptedIconView.setDescription(viewModel.wasInterrupted ? getStringByResourceId(R.string.yes) : getStringByResourceId(R.string.no));
        if (viewModel.customJsonInfo != null && !viewModel.customJsonInfo.isEmpty()) {
            mAdditionalInfoIconView.setVisibility(View.VISIBLE);
            mAdditionalInfoIconView.setDescription(viewModel.customJsonInfo);
        } else {
            mAdditionalInfoIconView.setVisibility(View.GONE);
        }
    }


    @Override
    public int getViewLayoutId() {
        return R.layout.view_tab_experiment_result_game;
    }
}


