package ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.presentation.view;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import ua.com.crosp.solutions.hearthealthmonitor.R;
import ua.com.crosp.solutions.hearthealthmonitor.base.view.BaseFragment;
import ua.com.crosp.solutions.hearthealthmonitor.base.view.listeners.OnItemClickListener;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.contract.presenter.ModesListPresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.contract.view.ModesListViewContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.dicomponent.ModesComponent;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.presentation.presenter.model.ModeListItem;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.presentation.view.adapter.ModesListAdapter;

/**
 * A placeholder fragment containing a simple view.
 */
public class ModesListFragment extends BaseFragment implements ModesListViewContract, OnItemClickListener<ModeListItem> {
    // Views
    @BindView(R.id.recycler_view_list)
    RecyclerView mModesRecyclerView;

    // Component & modular dependencies
    @Inject
    ModesListPresenterContract mModesListPresenter;

    //Variables
    private ModesListAdapter mModesListAdapter;

    public ModesListFragment() {
    }

    //================================================================================
    // Lifecycle callbacks
    //================================================================================

    @Override
    public int getFragmentLayoutId() {
        return R.layout.fragment_modes_list;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Injecting dependencies
        this.getComponent(ModesComponent.class).inject(this);
        // Set presenter this as the presenter view
        mModesListPresenter.setView(this);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupUI();
        mModesListPresenter.onViewReady();
     //   EcgSignalProcessorDemodulator lib = new EcgSignalProcessorDemodulator();
     //   short[] result = lib.demodulate(new short[]{2, 323, 32, 32});
    //    Logger.error(Arrays.asList(result));
    }

    //================================================================================
    // UI methods
    //================================================================================
    protected void setupUI() {
        setupModesList();
    }

    protected void setupModesList() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        mModesRecyclerView.setLayoutManager(layoutManager);
        mModesListAdapter = new ModesListAdapter(this);
        mModesRecyclerView.setAdapter(mModesListAdapter);
    }
    //================================================================================
    // View implementation
    //================================================================================

    @Override
    public void displayModesList(List<ModeListItem> modes) {
        mModesListAdapter.setDataSource(modes);
        mModesListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(ModeListItem item, View inView, int onPosition) {
        mModesListPresenter.onModeItemClick(item);
    }

    @Override
    public void onItemLongClick(ModeListItem item, View inView, int onPosition) {

    }
}


