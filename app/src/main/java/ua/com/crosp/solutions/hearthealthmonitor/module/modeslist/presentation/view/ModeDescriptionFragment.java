package ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.presentation.view;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import javax.inject.Inject;

import butterknife.BindView;
import ua.com.crosp.solutions.hearthealthmonitor.R;
import ua.com.crosp.solutions.hearthealthmonitor.base.view.BaseFragment;
import ua.com.crosp.solutions.hearthealthmonitor.common.contants.BundleConstants;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.contract.presenter.ModeDescriptionPresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.contract.view.ModeDescriptionViewContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.dicomponent.ModesComponent;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.presentation.presenter.model.ModeListItem;

/**
 * A placeholder fragment containing a simple view.
 */
public class ModeDescriptionFragment extends BaseFragment implements ModeDescriptionViewContract {
    // Views
    @BindView(R.id.text_view_description)
    TextView mDescriptionTextView;

    @BindView(R.id.image_view_background)
    ImageView mBackgroundImageView;

    @BindView(R.id.toolbar_main)
    Toolbar mToolbar;

    @BindView(R.id.floating_button_start)
    FloatingActionButton mStartFloatingButton;

    // Component & modular dependencies
    @Inject
    ModeDescriptionPresenterContract mModeDescriptionPresenter;

    //Variables
    private ModeListItem mModeItem;


    public ModeDescriptionFragment() {
    }

    //================================================================================
    // Lifecycle callbacks
    //================================================================================

    @Override
    public int getFragmentLayoutId() {
        return R.layout.fragment_mode_description;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Injecting dependencies
        this.getComponent(ModesComponent.class).inject(this);
        // Set presenter this as the presenter view
        mModeDescriptionPresenter.setView(this);
    }

    private void parseAndSetArguments() {
        Bundle bundle = getArguments();
        int modeId = bundle.getInt(BundleConstants.Arguments.MODE_ID);
        mModeDescriptionPresenter.initialize(modeId);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mModeDescriptionPresenter.onViewReady();
        parseAndSetArguments();
        setupToolbar();
    }


    //================================================================================
    // Toolbar
    //================================================================================
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
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mModeDescriptionPresenter.onBackButtonPress();
            }
        });
        setHasOptionsMenu(true);
        //  parentActivity.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.toolbar_transparent_color)));

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.mode_description, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_settings:
                mModeDescriptionPresenter.openModeSettings(mModeItem);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //================================================================================
    // View implementation
    //================================================================================

    @Override
    public void displayModeInformation(ModeListItem mode) {
        mModeItem = mode;
        mDescriptionTextView.setText(mode.description);
        mToolbar.setTitle(mode.title);
        mStartFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mModeDescriptionPresenter.openModeExperiment(mModeItem);
            }
        });
    }
}


