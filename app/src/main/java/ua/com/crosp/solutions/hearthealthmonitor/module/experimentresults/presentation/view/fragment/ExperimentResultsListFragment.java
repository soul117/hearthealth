package ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.presentation.view.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.MaterialDialog;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import ua.com.crosp.solutions.hearthealthmonitor.R;
import ua.com.crosp.solutions.hearthealthmonitor.base.view.BaseFragment;
import ua.com.crosp.solutions.hearthealthmonitor.base.view.listeners.OnItemClickListener;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.presenter.ExperimentResultListPresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.view.ExperimentResultListViewContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.dicomponent.ExperimentResultsComponent;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.presentation.view.adapter.ExperimentResultsAdapter;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.presentation.view.viewmodel.ExperimentResultViewModel;

/**
 * A placeholder fragment containing a simple view.
 */
public class ExperimentResultsListFragment extends BaseFragment implements ExperimentResultListViewContract, OnItemClickListener<ExperimentResultViewModel> {
    // Views
    @BindView(R.id.recycler_view_list)
    RecyclerView mExperimentResultRecyclerView;
    @BindView(R.id.toolbar_main)
    Toolbar mToolbar;
    // Component & modular dependencies
    @Inject
    ExperimentResultListPresenterContract mExperimentListPresenter;

    //Variables
    private ExperimentResultsAdapter mExperimentResultsAdapter;

    public ExperimentResultsListFragment() {
    }

    //================================================================================
    // Lifecycle callbacks
    //================================================================================

    @Override
    public int getFragmentLayoutId() {
        return R.layout.fragment_experiment_results_list;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Injecting dependencies
        this.getComponent(ExperimentResultsComponent.class).inject(this);
        // Set presenter this as the presenter view
        mExperimentListPresenter.setView(this);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupUI();
        mExperimentListPresenter.onViewReady();
        setupToolbar();
    }

    private void setupToolbar() {
        AppCompatActivity parentActivity = ((AppCompatActivity) getActivity());
        parentActivity.setSupportActionBar(mToolbar);
        ActionBar actionBar = parentActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mToolbar.setTitle(getString(R.string.title_experiment_result_list));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExperimentListPresenter.onBackButtonPress();
            }
        });
        setHasOptionsMenu(true);
        //  parentActivity.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.toolbar_transparent_color)));

    }

    //================================================================================
    // UI methods
    //================================================================================
    protected void setupUI() {
        setupExperimentResultList();
    }

    protected void setupExperimentResultList() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        mExperimentResultRecyclerView.setLayoutManager(layoutManager);
        mExperimentResultsAdapter = new ExperimentResultsAdapter(this);
        mExperimentResultRecyclerView.setAdapter(mExperimentResultsAdapter);
    }
    //================================================================================
    // View implementation
    //================================================================================

    @Override
    public void displayExperimentResultList(ExperimentResultViewModel.List modes) {
        mExperimentResultsAdapter.setDataSource(modes);
        mExperimentResultsAdapter.notifyDataSetChanged();
    }

    @Override
    public Single<Integer> showExperimentContextDialog() {
        return Single.create(new SingleOnSubscribe<Integer>() {
            @Override
            public void subscribe(final SingleEmitter<Integer> e) throws Exception {
                new MaterialDialog.Builder(getContext())
                        .title(R.string.title_dialog_experiment_context_menu)
                        .items(R.array.experiment_context_menu)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                e.onSuccess(which);
                            }
                        })
                        .show();
            }
        });
    }

    @Override
    public void onExperimentRemoved(ExperimentResultViewModel item) {
        mExperimentResultsAdapter.onItemRemoved(item);
    }

    @Override
    public void onItemClick(ExperimentResultViewModel item, View inView, int onPosition) {
        mExperimentListPresenter.onExperimentItemClick(item);
    }

    @Override
    public void onItemLongClick(ExperimentResultViewModel item, View inView, int onPosition) {
        mExperimentListPresenter.onExperimentLongClickListener(item);
    }
}


