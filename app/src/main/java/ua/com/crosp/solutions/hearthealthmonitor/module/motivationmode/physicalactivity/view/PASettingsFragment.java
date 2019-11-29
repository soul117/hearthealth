package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.view;

import android.os.Bundle;
import android.view.View;

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

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.internal.util.AppendOnlyLinkedArrayList;
import io.reactivex.observers.DisposableObserver;
import ua.com.crosp.solutions.hearthealthmonitor.R;
import ua.com.crosp.solutions.hearthealthmonitor.base.view.BaseToolbarFragment;
import ua.com.crosp.solutions.hearthealthmonitor.common.view.PresetRadioGroup;
import ua.com.crosp.solutions.hearthealthmonitor.common.view.PresetValueButton;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util.Logger;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util.StringUtils;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util.ViewUtils;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.contract.presenter.PASettingsPresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.contract.view.PASettingsViewContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.data.PASettingsEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.di.PhysicalActivityComponent;

/**
 * Created by Alexander Molochko on 2/19/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class PASettingsFragment extends BaseToolbarFragment implements PASettingsViewContract {
    public static final int DEBOUNCE_TIMEOUT = 500;
    public static final int MINIMAL_PRESET_TIME = 30;
    public static final int MINIMAL_PRESET_REPS = 10;
    public static final int DEFAULT_REPS_COUNT = 10;
    private static final long DEFAULT_EXPERIMENT_TIME = 30;
    // Views
    @BindView(R.id.toolbar_main)
    Toolbar mToolbar;
    @BindView(R.id.preset_rest_time_radio_group)
    PresetRadioGroup mPresetTimeRadioGroup;
    @BindView(R.id.preset_reps_radio_group)
    PresetRadioGroup mPresetRepsRadioGroup;
    @BindView(R.id.edit_text_rest_time)
    AppCompatEditText mExperimentTimeEditText;
    @BindView(R.id.edit_text_reps_count)
    AppCompatEditText mRepsCountEditText;
    @BindView(R.id.floating_button_start_experiment)
    FloatingActionButton mStartFloatingButton;
    // Component & modular dependencies
    @Inject
    PASettingsPresenterContract mSettingsPresenterContract;

    //region Variables
    private PASettingsEntity mModeSettings;
    private DisposableObserver<TextViewTextChangeEvent> mExperimentTimeObservable;
    private DisposableObserver<TextViewTextChangeEvent> mRepsCountObservable;
    //endregion

    //region Inline class implementations
    private AppendOnlyLinkedArrayList.NonThrowingPredicate<TextViewTextChangeEvent> mFilterEmptyStringsPredicate = new AppendOnlyLinkedArrayList.NonThrowingPredicate<TextViewTextChangeEvent>() {
        @Override
        public boolean test(TextViewTextChangeEvent textViewTextChangeEvent) {
            return !StringUtils.isBlank(textViewTextChangeEvent.text().toString());
        }
    };
    //endregion

    public PASettingsFragment() {
    }

    @Override
    public int getFragmentLayoutId() {
        return R.layout.fragment_settings_pa_mode;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Injecting dependencies
        this.getComponent(PhysicalActivityComponent.class).inject(this);
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
        mExperimentTimeObservable.dispose();
        mRepsCountObservable.dispose();
    }
    //endregion

    //region View contract implementation

    private void saveCurrentSettings() {
        mModeSettings.setExperimentTime(Long.parseLong(mExperimentTimeEditText.getText().toString()));
        mModeSettings.setSquatCount(Integer.parseInt(mRepsCountEditText.getText().toString()));
        mSettingsPresenterContract.saveSettings(mModeSettings);
    }

    @Override
    public void displayModeSettings(PASettingsEntity modeSettings) {
        mModeSettings = modeSettings;
        long experimentTime = modeSettings.getExperimentTime();
        int repsCount = modeSettings.getRepsCount();
        // Check whether values are correct
        repsCount = repsCount > 0 ? repsCount : DEFAULT_REPS_COUNT;
        experimentTime = experimentTime > 0 ? experimentTime : DEFAULT_EXPERIMENT_TIME;
        ViewUtils.setEditTextSelection(mExperimentTimeEditText, String.valueOf(experimentTime));
        ViewUtils.setEditTextSelection(mRepsCountEditText, String.valueOf(repsCount));
        // setTimeNearestPresetValue(experimentTime);
        //setRepsCountNearestPresetValue(repsCount);
    }

    @OnClick(R.id.floating_button_start_experiment)
    public void startExperiment(View view) {
        saveCurrentSettings();
        mSettingsPresenterContract.startExperiment();
    }

    //region View internal implementation

    private void setListeners() {
        mPresetTimeRadioGroup.setOnCheckedChangeListener(new PresetRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View radioGroup, View radioButton, boolean isChecked, int checkedId) {
                String value = ((PresetValueButton) radioButton).getValue();
                ViewUtils.setEditTextSelection(mExperimentTimeEditText, value);
            }
        });
        mPresetRepsRadioGroup.setOnCheckedChangeListener(new PresetRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View radioGroup, View radioButton, boolean isChecked, int checkedId) {
                String value = ((PresetValueButton) radioButton).getValue();
                ViewUtils.setEditTextSelection(mRepsCountEditText, value);
            }
        });
        mRepsCountObservable = RxTextView.textChangeEvents(mRepsCountEditText)
                .debounce(DEBOUNCE_TIMEOUT, TimeUnit.MILLISECONDS) // default Scheduler is Computation
                .filter(mFilterEmptyStringsPredicate)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getRepsCountEditTextObservable());
        mExperimentTimeObservable = RxTextView.textChangeEvents(mExperimentTimeEditText)
                .debounce(DEBOUNCE_TIMEOUT, TimeUnit.MILLISECONDS) // default Scheduler is Computation
                .filter(mFilterEmptyStringsPredicate)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getExperimentTimeEditTextObservable());
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


    private void setRepsCountNearestPresetValue(Integer enteredValue) {
        // 10 the minimum value for preset buttons
        int coefficient = enteredValue / MINIMAL_PRESET_REPS;
        if (coefficient >= 4) {
            mPresetRepsRadioGroup.checkWithoutEvent(R.id.preset_reps_value_button_40);
        } else if (coefficient >= 2) {
            mPresetRepsRadioGroup.checkWithoutEvent(R.id.preset_reps_value_button_20);
        } else {
            mPresetRepsRadioGroup.checkWithoutEvent(R.id.preset_reps_value_button_10);
        }
    }

    private void setTimeNearestPresetValue(Long enteredValue) {
        // 30 seconds the minimum value for preset buttons
        long coefficient = enteredValue / MINIMAL_PRESET_TIME;
        if (coefficient >= 4) {
            mPresetTimeRadioGroup.checkWithoutEvent(R.id.preset_time_value_button_120);
        } else if (coefficient >= 2) {
            mPresetTimeRadioGroup.checkWithoutEvent(R.id.preset_time_value_button_60);
        } else {
            mPresetTimeRadioGroup.checkWithoutEvent(R.id.preset_time_value_button_30);
        }
    }
    //endregion

    //region Factory methods
    private DisposableObserver<TextViewTextChangeEvent> getExperimentTimeEditTextObservable() {
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
                setTimeNearestPresetValue(Long.parseLong(onTextChangeEvent.text().toString()));
            }
        };
    }

    private DisposableObserver<TextViewTextChangeEvent> getRepsCountEditTextObservable() {
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
                setRepsCountNearestPresetValue(Integer.parseInt(onTextChangeEvent.text().toString()));
            }
        };
    }
    //endregion

    @Override
    public String provideModeSettingsTitle() {
        return getString(R.string.title_mode_rest);
    }

    @Override
    public void setToolbarTitle(String title) {
        mToolbar.setTitle(title);
    }
}
