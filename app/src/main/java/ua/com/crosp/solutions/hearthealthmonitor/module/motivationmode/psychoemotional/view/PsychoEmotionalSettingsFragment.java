package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.view;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.util.AppendOnlyLinkedArrayList;
import io.reactivex.observers.DisposableObserver;
import ua.com.crosp.solutions.hearthealthmonitor.R;
import ua.com.crosp.solutions.hearthealthmonitor.base.view.BaseToolbarFragment;
import ua.com.crosp.solutions.hearthealthmonitor.common.view.PresetRadioGroup;
import ua.com.crosp.solutions.hearthealthmonitor.common.view.PresetValueButton;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util.Logger;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util.StringUtils;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util.ValueUtils;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util.ViewUtils;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.contract.presenter.PESettingsPresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.contract.view.PESettingsViewContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.data.PEBallGameMode;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.diassembly.PsychoEmotionalComponent;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.domain.PESettingsEntity;

import static ua.com.crosp.solutions.hearthealthmonitor.di.component.activity.module.ActivityModule.CONTEXT_ACTIVITY;

/**
 * Created by Alexander Molochko on 2/19/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class PsychoEmotionalSettingsFragment extends BaseToolbarFragment implements PESettingsViewContract {
    public static final int DEBOUNCE_TIMEOUT = 500;
    public static final int MINIMAL_PRESET_TIME = 30;
    private static final long DEFAULT_EXPERIMENT_TIME = 30;
    public static final int MAX_COEFFICIENT_PRESET_COUNT = 4;
    // Views
    @BindView(R.id.toolbar_main)
    Toolbar mToolbar;
    @BindView(R.id.preset_game_time_radio_group)
    PresetRadioGroup mPresetGameTimeRadioGroup;
    @BindView(R.id.edit_text_game_time)
    AppCompatEditText mGameTimeEditText;
    @BindView(R.id.floating_button_start_experiment)
    FloatingActionButton mStartFloatingButton;
    @BindView(R.id.text_view_mode_description)
    TextView mTextViewGameModeDescription;
    @BindView(R.id.spinner_game_mode)
    Spinner mGameModeSpinner;
    // Component & modular dependencies
    @Inject
    @Named(CONTEXT_ACTIVITY)
    Context mContext;
    @Inject
    PESettingsPresenterContract mSettingsPresenterContract;

    //region Variables
    private PESettingsEntity mModeSettings;

    private DisposableObserver<TextViewTextChangeEvent> mGameTimeObservable;
    //endregion

    //region Inline class implementations
    private AppendOnlyLinkedArrayList.NonThrowingPredicate<TextViewTextChangeEvent> mFilterEmptyStringsPredicate = new AppendOnlyLinkedArrayList.NonThrowingPredicate<TextViewTextChangeEvent>() {
        @Override
        public boolean test(TextViewTextChangeEvent textViewTextChangeEvent) {
            return !StringUtils.isBlank(textViewTextChangeEvent.text().toString());
        }
    };
    private ArrayAdapter<PEBallGameMode> mSpinnerGameModeAdapter;
    private PEBallGameMode.List mAvailableGameModes;
    //endregion

    public PsychoEmotionalSettingsFragment() {
    }

    @Override
    public int getFragmentLayoutId() {
        return R.layout.fragment_settings_psychoemotional_mode;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Injecting dependencies
        this.getComponent(PsychoEmotionalComponent.class).inject(this);
        // Set presenter this as the presenter view
        mSettingsPresenterContract.setView(this);
    }

    //region Lifecycle methods

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSettingsPresenterContract.onViewReady();
        setListeners();
        setupToolbar();

    }

    @Override
    public void onPause() {
        super.onPause();
        // Save settings
        saveCurrentSettings();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        saveCurrentSettings();
        mGameTimeObservable.dispose();

    }
    //endregion

    //region View contract implementation


    @OnClick(R.id.floating_button_start_experiment)
    public void startExperiment(View view) {
        saveCurrentSettings();
        mSettingsPresenterContract.startExperiment();
    }

    //region View internal implementation

    private void setListeners() {
        mPresetGameTimeRadioGroup.setOnCheckedChangeListener(new PresetRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View radioGroup, View radioButton, boolean isChecked, int checkedId) {
                String value = ((PresetValueButton) radioButton).getValue();
                ViewUtils.setEditTextSelection(mGameTimeEditText, value);
            }
        });
        mGameTimeObservable = RxTextView.textChangeEvents(mGameTimeEditText)
                .debounce(DEBOUNCE_TIMEOUT, TimeUnit.MILLISECONDS) // default Scheduler is Computation
                .filter(mFilterEmptyStringsPredicate)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getGameTimeEditTextObservable());
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
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCurrentSettings();
                mSettingsPresenterContract.onBackButtonPress();
            }
        });
        setHasOptionsMenu(true);
    }

    private void setGameTimeNearestPresetValue(Long enteredValue) {
        // 30 seconds the minimum value for preset buttons
        long coefficient = enteredValue / MINIMAL_PRESET_TIME;
        if (coefficient >= MAX_COEFFICIENT_PRESET_COUNT) {
            mPresetGameTimeRadioGroup.checkWithoutEvent(R.id.preset_game_time_value_button_120);
        } else if (coefficient >= 2) {
            mPresetGameTimeRadioGroup.checkWithoutEvent(R.id.preset_game_time_value_button_60);
        } else {
            mPresetGameTimeRadioGroup.checkWithoutEvent(R.id.preset_game_time_value_button_30);
        }
    }
    //endregion

    //region Factory methods
    private DisposableObserver<TextViewTextChangeEvent> getGameTimeEditTextObservable() {
        return new DisposableObserver<TextViewTextChangeEvent>() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable e) {
                Logger.error(e);
            }

            @Override
            public void onNext(TextViewTextChangeEvent onTextChangeEvent) {
                setGameTimeNearestPresetValue(Long.parseLong(onTextChangeEvent.text().toString()));
            }
        };
    }


    //endregion

    //region Listeners
    @OnItemSelected(R.id.spinner_game_mode)
    public void onGameModeSelected(Spinner spinner, int position) {
        PEBallGameMode peBallGameMode = getSelectedGameMode();
        if (peBallGameMode != null) {
            mTextViewGameModeDescription.setText(peBallGameMode.getDescription());
        }
    }

    //endregion
    @Override
    public String provideModeSettingsTitle() {
        return getString(R.string.title_psychoemotional_settings);
    }

    @Override
    public void setToolbarTitle(String title) {
        mToolbar.setTitle(title);
    }

    @Override
    public void displaySettings(PESettingsEntity settingsEntity) {
        mModeSettings = settingsEntity;
        long gameTime = settingsEntity.getExperimentTime();
        // Check whether values are correct
        gameTime = ValueUtils.getValueOrDefaultPositiveLong(gameTime, DEFAULT_EXPERIMENT_TIME);
        ViewUtils.setEditTextSelection(mGameTimeEditText, String.valueOf(gameTime));
        setGameTimeNearestPresetValue(gameTime);
        setUpGameModeSpinner();
    }

    @Override
    public PESettingsEntity getCurrentSettings() {
        mModeSettings.setExperimentTime(Long.parseLong(mGameTimeEditText.getText().toString()));
        mModeSettings.setGameMode(getSelectedGameMode());
        return mModeSettings;
    }

    private void saveCurrentSettings() {
        // Set current values
        mSettingsPresenterContract.saveSettings(getCurrentSettings());
    }

    private PEBallGameMode getSelectedGameMode() {
        return mSpinnerGameModeAdapter.getItem(mGameModeSpinner.getSelectedItemPosition());
    }

    private void setModeFromSettings() {
        int savedModeIndex = mAvailableGameModes.indexOf(mModeSettings.getGameMode());
        mGameModeSpinner.setSelection(savedModeIndex);
        mTextViewGameModeDescription.setText(getSelectedGameMode().getDescription());
    }

    private void setUpGameModeSpinner() {
        mSettingsPresenterContract.getAvailableGameModes()
                .subscribe(new Consumer<PEBallGameMode.List>() {
                    @Override
                    public void accept(PEBallGameMode.List peBallGameModes) throws Exception {
                        mAvailableGameModes = peBallGameModes;
                        mSpinnerGameModeAdapter = new ArrayAdapter<>(mContext, R.layout.spinner_item_game_mode, mAvailableGameModes);
                        mGameModeSpinner.setAdapter(mSpinnerGameModeAdapter);
                        setModeFromSettings();
                    }
                });

    }
}
