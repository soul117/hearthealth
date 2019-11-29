package ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.presentation.view.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.palette.graphics.Palette;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;

import javax.inject.Inject;

import butterknife.BindView;
import ua.com.crosp.solutions.hearthealthmonitor.R;
import ua.com.crosp.solutions.hearthealthmonitor.base.view.BaseFragment;
import ua.com.crosp.solutions.hearthealthmonitor.common.contants.BundleConstants;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util.Logger;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util.ViewUtils;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.entity.ExperimentResultEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.presenter.ExperimentResultDetailPresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.view.ExperimentResultDetailViewContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.dicomponent.ExperimentResultsComponent;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.presentation.view.adapter.ViewPagerDetailsAdapter;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.presentation.view.tabfragments.ExperimentTabEcgView;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.presentation.view.tabfragments.ExperimentTabFeelingView;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.presentation.view.tabfragments.ExperimentTabGameView;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.presentation.view.tabfragments.ExperimentTabGeneralView;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.presentation.view.viewmodel.ExperimentEcgResultViewModel;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.presentation.view.viewmodel.ExperimentFeelingResultViewModel;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.presentation.view.viewmodel.ExperimentGameResultViewModel;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.presentation.view.viewmodel.ExperimentGeneralResultViewModel;

/**
 * A placeholder fragment containing a simple view.
 */
public class ExperimentResultDetailFragment extends BaseFragment implements ExperimentResultDetailViewContract, ExperimentTabEcgView.ActionButtonHandler, ExperimentTabGeneralView.ActionButtonHandler {
    // Views
    @BindView(R.id.tab_layout_main)
    TabLayout mTabLayoutMain;

    @BindView(R.id.toolbar_tab_layout)
    Toolbar mToolbarTabLayout;

    @BindView(R.id.image_view_tab_header)
    ImageView mImageViewTabParallaxHeader;


    @BindView(R.id.view_pager_main)
    ViewPager mViewPagerMain;

    @BindView(R.id.collapsing_toolbar_main)
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    // Component & modular dependencies
    @Inject
    ExperimentResultDetailPresenterContract mExperimentDetailsPresenter;
    private ExperimentTabGeneralView mGeneralTabView;
    private ExperimentTabGameView mGameTabView;
    private ExperimentTabFeelingView mFeelingTabView;
    private ExperimentTabEcgView mEcgTabView;

    //Variables


    public ExperimentResultDetailFragment() {
    }

    //================================================================================
    // Lifecycle callbacks
    //================================================================================

    @Override
    public int getFragmentLayoutId() {
        return R.layout.fragment_experiment_result_details;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Injecting dependencies
        this.getComponent(ExperimentResultsComponent.class).inject(this);
        // Set presenter this as the presenter view
        mExperimentDetailsPresenter.setView(this);
    }

    private void parseAndSetArguments() {
        Bundle bundle = getArguments();
        long modeId = bundle.getLong(BundleConstants.Arguments.EXPERIMENT_RESULT_ID);
        mExperimentDetailsPresenter.initialize(modeId);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mExperimentDetailsPresenter.onViewReady();
        parseAndSetArguments();
        initViews();
        //setupActionBarMargin();
    }

    private void setupActionBarMargin() {
        int actionBarHeight = ViewUtils.getActionBarHeight((AppCompatActivity) getActivity());
        ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) mToolbarTabLayout.getLayoutParams();
        marginParams.setMargins(0, 0, 0, actionBarHeight);
        mToolbarTabLayout.setLayoutParams(marginParams);

    }

    private void initViews() {
        initToolbar();
        initViewPager();
        initTabsLayout();
    }

    private void initToolbar() {
        AppCompatActivity appCompatActivity = ((AppCompatActivity) getActivity());
        appCompatActivity.setSupportActionBar(mToolbarTabLayout);
        ActionBar actionBar = appCompatActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getStringByResourceId(R.string.title_toolbar_experiment_details));
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mToolbarTabLayout.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExperimentDetailsPresenter.onBackButtonPress();
            }
        });

    }

    private void initTabsLayout() {
        mTabLayoutMain.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                mViewPagerMain.setCurrentItem(tab.getPosition());
                Logger.error("onTabSelected: pos: " + tab.getPosition());
                switch (tab.getPosition()) {
                    case 0:
                        setPaletteForParallaxHeader(R.drawable.experiment_result_header);
                        break;
                    case 1:
                        setPaletteForParallaxHeader(R.drawable.background_experiment_game_activity);

                        break;
                    case 2:
                        setPaletteForParallaxHeader(R.drawable.bacgkround_experiment_feeling);

                        break;
                    case 3:
                        setPaletteForParallaxHeader(R.drawable.background_experiment_ecg);
                        break;
                    default:
                        break;
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mTabLayoutMain.setupWithViewPager(mViewPagerMain);
    }


    protected void initViewPager() {
        ViewPagerDetailsAdapter adapter = new ViewPagerDetailsAdapter();
        // General fragment
        mGeneralTabView = new ExperimentTabGeneralView(getContext());
        mGameTabView = new ExperimentTabGameView(getContext());
        mFeelingTabView = new ExperimentTabFeelingView(getContext());
        mEcgTabView = new ExperimentTabEcgView(getContext());
        mGeneralTabView.setActionButtonHandler(this);
        mEcgTabView.setActionButtonHandler(this);
        adapter.addView(mGeneralTabView, getString(R.string.tab_name_general));
        adapter.addView(mGameTabView, getString(R.string.tab_name_game));
        adapter.addView(mFeelingTabView, getString(R.string.tab_name_feeling));
        adapter.addView(mEcgTabView, getString(R.string.tab_name_ecg));
        mViewPagerMain.setAdapter(adapter);
    }

    protected void setPaletteForParallaxHeader(int resourceId) {
        try {
            mImageViewTabParallaxHeader.setBackgroundResource(resourceId);
            mImageViewTabParallaxHeader.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resourceId);
            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                @SuppressWarnings("ResourceType")
                @Override
                public void onGenerated(Palette palette) {

                    int vibrantColor = palette.getVibrantColor(R.color.light_grey);
                    int vibrantDarkColor = palette.getDarkVibrantColor(R.color.dark_grey);
                    mCollapsingToolbarLayout.setContentScrimColor(vibrantColor);
                    mCollapsingToolbarLayout.setStatusBarScrimColor(vibrantDarkColor);
                }
            });

        } catch (Exception e) {
            // if Bitmap fetch fails, fallback to primary colors
            mCollapsingToolbarLayout.setContentScrimColor(
                    ContextCompat.getColor(getContext(), R.color.dark_grey)
            );
            mCollapsingToolbarLayout.setStatusBarScrimColor(
                    ContextCompat.getColor(getContext(), R.color.light_grey)
            );
        }

    }
    //================================================================================
    // View implementation
    //================================================================================

    @Override
    public void displayExperimentDetails(ExperimentResultEntity experimentResultEntity) {
        if (null != mGeneralTabView) {
            mGeneralTabView.displayResults(ExperimentGeneralResultViewModel.fromEntity(experimentResultEntity));
        }
        if (null != mGameTabView) {
            mGameTabView.displayResults(ExperimentGameResultViewModel.fromEntity(experimentResultEntity));
        }
        if (null != mFeelingTabView) {
            mFeelingTabView.displayResults(ExperimentFeelingResultViewModel.fromEntity(experimentResultEntity));
        }
        if (null != mEcgTabView) {
            mEcgTabView.displayResults(ExperimentEcgResultViewModel.fromEntity(experimentResultEntity));
        }
    }

    @Override
    public void onShowEcgChartClick() {
        mExperimentDetailsPresenter.showEcgResultChart();
    }

    @Override
    public void onGeneratePdfReportClick() {

    }

    @Override
    public void onGenerateXmlReportClick() {
        mExperimentDetailsPresenter.onGenerateXMLReport();
    }

    @Override
    public void onSyncWithCloudClcik() {

    }
}


