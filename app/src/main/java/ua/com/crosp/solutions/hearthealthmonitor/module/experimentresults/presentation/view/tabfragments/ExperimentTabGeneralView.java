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
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.presentation.view.viewmodel.ExperimentGeneralResultViewModel;

/**
 * A placeholder fragment containing a simple view.
 */
public class ExperimentTabGeneralView extends BaseSimpleViewGroup {
    // Views

    @BindView(R.id.icon_description_experiment_name)
    IconDescriptionView mIconNameView;
    @BindView(R.id.icon_description_experiment_date)
    IconDescriptionView mIconDateView;
    @BindView(R.id.icon_description_experiment_id)
    IconDescriptionView mIdIconView;

    // Members
    ActionButtonHandler mActionButtonHandler;

    public ExperimentTabGeneralView(Context context) {
        super(context);
    }

    public ExperimentTabGeneralView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ExperimentTabGeneralView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ActionButtonHandler getActionButtonHandler() {
        return mActionButtonHandler;
    }

    public void setActionButtonHandler(ActionButtonHandler actionButtonHandler) {
        mActionButtonHandler = actionButtonHandler;
    }

    // Button listeners

    @OnClick(R.id.icon_button_generate_pdf_report)
    void onGeneratePdfReportClick(View view) {
        if (mActionButtonHandler != null) {
            mActionButtonHandler.onGeneratePdfReportClick();
        }
    }

    @OnClick(R.id.icon_button_generate_xml_report)
    void onGenerateXmlReportClick(View view) {
        if (mActionButtonHandler != null) {
            mActionButtonHandler.onGenerateXmlReportClick();
        }
    }

    @OnClick(R.id.icon_button_sync_cloud)
    void onSyncToCloudClick(View view) {
        if (mActionButtonHandler != null) {
            mActionButtonHandler.onSyncWithCloudClcik();
        }
    }

    //================================================================================
    // Lifecycle callbacks
    //================================================================================
    @Override
    public int getViewLayoutId() {
        return R.layout.view_tab_experiment_result_general;
    }


    //================================================================================
    // View implementation
    //================================================================================

    public void displayResults(ExperimentGeneralResultViewModel viewModel) {
        mIconNameView.setDescription(viewModel.resultName);
        mIconDateView.setDescription(viewModel.resultDate);
        mIdIconView.setDescription(viewModel.id);
    }

    public interface ActionButtonHandler {
        void onGeneratePdfReportClick();

        void onGenerateXmlReportClick();

        void onSyncWithCloudClcik();
    }
}


