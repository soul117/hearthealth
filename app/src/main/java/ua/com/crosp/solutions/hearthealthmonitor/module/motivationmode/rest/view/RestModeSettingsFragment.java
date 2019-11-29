package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.view;

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
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.cotntract.presenter.RestModeSettingsPresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.cotntract.view.RestModeSettingsViewContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.data.RestSettingsEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.diassembly.RestModeComponent;

/**
 * Created by Alexander Molochko on 2/19/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class RestModeSettingsFragment extends BaseToolbarFragment implements RestModeSettingsViewContract {
    public static final int DEBOUNCE_TIMEOUT = 500;
    public static final int MINIMAL_PRESET_REST_TIME = 30;
    private static final long DEFAULT_REST_TIME = 30;
    // Views
    @BindView(R.id.toolbar_main)
    Toolbar mToolbar;
    @BindView(R.id.preset_rest_time_radio_group)
    PresetRadioGroup mPresetRestTimeRadioGroup;
    @BindView(R.id.edit_text_rest_time)
    AppCompatEditText mRestTimeEditText;
    @BindView(R.id.floating_button_start_experiment)
    FloatingActionButton mStartFloatingButton;
    // Component & modular dependencies
    @Inject
    RestModeSettingsPresenterContract mSettingsPresenterContract;

    //region Variables
    private RestSettingsEntity mModeSettings;
    private DisposableObserver<TextViewTextChangeEvent> mRestTimeObservable;
    //endregion

    //region Inline class implementations
    private AppendOnlyLinkedArrayList.NonThrowingPredicate<TextViewTextChangeEvent> mFilterEmptyStringsPredicate = new AppendOnlyLinkedArrayList.NonThrowingPredicate<TextViewTextChangeEvent>() {
        @Override
        public boolean test(TextViewTextChangeEvent textViewTextChangeEvent) {
            return !StringUtils.isBlank(textViewTextChangeEvent.text().toString());
        }
    };
    //endregion

    public RestModeSettingsFragment() {
    }

    @Override
    public int getFragmentLayoutId() {
        return R.layout.fragment_settings_rest_mode;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Injecting dependencies
        this.getComponent(RestModeComponent.class).inject(this);
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
        mRestTimeObservable.dispose();
    }
    //endregion

    //region View contract implementation

    private void saveCurrentSettings() {
        mModeSettings.setRestTime(Long.parseLong(mRestTimeEditText.getText().toString()));
        mSettingsPresenterContract.saveSettings(mModeSettings);
    }

    @Override
    public void displayRestModeSettings(RestSettingsEntity settingsEntity) {
        mModeSettings = settingsEntity;
        long restTime = mModeSettings.getRestTime();
        // Check whether values are correct
        restTime = restTime > 0 ? restTime : DEFAULT_REST_TIME;
        ViewUtils.setEditTextSelection(mRestTimeEditText, String.valueOf(restTime));
    }

    @OnClick(R.id.floating_button_start_experiment)
    public void startExperiment(View view) {
        saveCurrentSettings();
        mSettingsPresenterContract.startExperiment();
    }

    //================================================================================
    // View internal implementation
    //================================================================================

    private void setListeners() {
        mPresetRestTimeRadioGroup.setOnCheckedChangeListener(new PresetRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View radioGroup, View radioButton, boolean isChecked, int checkedId) {
                String value = ((PresetValueButton) radioButton).getValue();
                ViewUtils.setEditTextSelection(mRestTimeEditText, value);
            }
        });
        mRestTimeObservable = RxTextView.textChangeEvents(mRestTimeEditText)
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
        //  parentActivity.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.toolbar_transparent_color)));

    }

    private void setRestTimeNearestPresetValue(Long enteredValue) {
        // 30 seconds the minimum value for preset buttons
        long coefficient = enteredValue / MINIMAL_PRESET_REST_TIME;
        if (coefficient >= 4) {
            mPresetRestTimeRadioGroup.checkWithoutEvent(R.id.preset_time_value_button_120);
        } else if (coefficient >= 2) {
            mPresetRestTimeRadioGroup.checkWithoutEvent(R.id.preset_time_value_button_60);
        } else {
            mPresetRestTimeRadioGroup.checkWithoutEvent(R.id.preset_time_value_button_30);
        }
    }


    //================================================================================
    // Factory methods
    //================================================================================
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
                setRestTimeNearestPresetValue(Long.parseLong(onTextChangeEvent.text().toString()));
            }
        };
    }

    @Override
    public String provideModeSettingsTitle() {
        return getString(R.string.title_mode_rest);
    }

    @Override
    public void setToolbarTitle(String title) {
        mToolbar.setTitle(title);
    }


}
