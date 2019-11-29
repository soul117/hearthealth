package ua.com.crosp.solutions.hearthealthmonitor.module.appsettings;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.internal.util.AppendOnlyLinkedArrayList;
import io.reactivex.observers.DisposableObserver;
import ua.com.crosp.solutions.hearthealthmonitor.R;
import ua.com.crosp.solutions.hearthealthmonitor.base.view.BaseToolbarFragment;
import ua.com.crosp.solutions.hearthealthmonitor.common.notification.ToastNotifications;
import ua.com.crosp.solutions.hearthealthmonitor.common.view.PresetRadioGroup;
import ua.com.crosp.solutions.hearthealthmonitor.common.view.PresetValueButton;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util.Logger;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util.StringUtils;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util.ViewUtils;
import ua.com.crosp.solutions.hearthealthmonitor.module.appsettings.contract.presenter.AppSettingsPresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.appsettings.contract.view.AppSettingsViewContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.appsettings.data.AppSettingsModel;
import ua.com.crosp.solutions.hearthealthmonitor.module.appsettings.di.AppSettingsComponent;

/**
 * Created by Alexander Molochko on 2/19/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class AppSettingsFragment extends BaseToolbarFragment implements AppSettingsViewContract {
    public static final int DEBOUNCE_TIMEOUT = 500;
    public static final int MINIMAL_PRESET_RECORDING_TIME = 30;
    public static final int MINIMAL_PRESET_VOLTAGE_SCALE = 10;
    // Views
    @BindView(R.id.toolbar_main)
    Toolbar mToolbar;
    @BindView(R.id.preset_rest_time_radio_group)
    PresetRadioGroup mPresetRecordingTimeRadioGroup;
    @BindView(R.id.preset_reps_radio_group)
    PresetRadioGroup mPresetVoltageScaleGroup;
    @BindView(R.id.edit_text_rest_time)
    AppCompatEditText mRecordingTimeEditText;
    @BindView(R.id.edit_text_reps_count)
    AppCompatEditText mVoltageScaleEditText;
    @BindView(R.id.checkbox_show_countdown_timer)
    CheckBox mCheckBoxShowCountdownTimer;
    // Component & modular dependencies
    @Inject
    AppSettingsPresenterContract mSettingsPresenterContract;

    //region Variables
    private AppSettingsModel mAppSettingsModel;
    private DisposableObserver<TextViewTextChangeEvent> mRecordingTimeObservable;
    private DisposableObserver<TextViewTextChangeEvent> mVoltageScaleCountObservable;
    //endregion

    //region Inline class implementations
    private AppendOnlyLinkedArrayList.NonThrowingPredicate<TextViewTextChangeEvent> mFilterEmptyStringsPredicate = new AppendOnlyLinkedArrayList.NonThrowingPredicate<TextViewTextChangeEvent>() {
        @Override
        public boolean test(TextViewTextChangeEvent textViewTextChangeEvent) {
            return !StringUtils.isBlank(textViewTextChangeEvent.text().toString());
        }
    };
    //endregion

    public AppSettingsFragment() {
    }

    @Override
    public int getFragmentLayoutId() {
        return R.layout.fragment_app_settings;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Injecting dependencies
        this.getComponent(AppSettingsComponent.class).inject(this);
        // Set presenter this as the presenter view
        mSettingsPresenterContract.setView(this);
    }

    //region Lifecycle methods

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setListeners();
        setupToolbar();
        mSettingsPresenterContract.onViewReady();
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
        mRecordingTimeObservable.dispose();
        mVoltageScaleCountObservable.dispose();
    }
    //endregion

    //region View contract implementation

    private void saveCurrentSettings() {
        mAppSettingsModel = new AppSettingsModel();
        mAppSettingsModel.setRecordingTime(Long.parseLong(mRecordingTimeEditText.getText().toString()));
        mAppSettingsModel.setVoltageScale(Long.parseLong(mVoltageScaleEditText.getText().toString()));
        mAppSettingsModel.setShowCountdownTimer(mCheckBoxShowCountdownTimer.isChecked());
        mSettingsPresenterContract.onSaveAppSettingsRequest(mAppSettingsModel);
    }

    //region View internal implementation

    private void setListeners() {
        mPresetRecordingTimeRadioGroup.setOnCheckedChangeListener(new PresetRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View radioGroup, View radioButton, boolean isChecked, int checkedId) {
                String value = ((PresetValueButton) radioButton).getValue();
                ViewUtils.setEditTextSelection(mRecordingTimeEditText, value);
            }
        });
        mPresetVoltageScaleGroup.setOnCheckedChangeListener(new PresetRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View radioGroup, View radioButton, boolean isChecked, int checkedId) {
                String value = ((PresetValueButton) radioButton).getValue();
                ViewUtils.setEditTextSelection(mVoltageScaleEditText, value);
            }
        });
        mVoltageScaleCountObservable = RxTextView.textChangeEvents(mVoltageScaleEditText)
                .debounce(DEBOUNCE_TIMEOUT, TimeUnit.MILLISECONDS) // default Scheduler is Computation
                .filter(mFilterEmptyStringsPredicate)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getVoltageScaleEditTextObservable());
        mRecordingTimeObservable = RxTextView.textChangeEvents(mRecordingTimeEditText)
                .debounce(DEBOUNCE_TIMEOUT, TimeUnit.MILLISECONDS) // default Scheduler is Computation
                .filter(mFilterEmptyStringsPredicate)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getRecordingTimeEditTextObservable());
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
        int coefficient = enteredValue / MINIMAL_PRESET_VOLTAGE_SCALE;
        if (coefficient >= 4) {
            mPresetVoltageScaleGroup.checkWithoutEvent(R.id.preset_voltage_value_button_40);
        } else if (coefficient >= 2) {
            mPresetVoltageScaleGroup.checkWithoutEvent(R.id.preset_voltage_value_button_20);
        } else {
            mPresetVoltageScaleGroup.checkWithoutEvent(R.id.preset_voltage_value_button_10);
        }
    }

    private void setRecordingTimeNearestPresetValue(Long enteredValue) {
        // 30 seconds the minimum value for preset buttons
        long coefficient = enteredValue / MINIMAL_PRESET_RECORDING_TIME;
        if (coefficient >= 4) {
            mPresetRecordingTimeRadioGroup.checkWithoutEvent(R.id.preset_time_value_button_120);
        } else if (coefficient >= 2) {
            mPresetRecordingTimeRadioGroup.checkWithoutEvent(R.id.preset_time_value_button_60);
        } else {
            mPresetRecordingTimeRadioGroup.checkWithoutEvent(R.id.preset_time_value_button_30);
        }
    }
    //endregion

    //region Factory methods
    private DisposableObserver<TextViewTextChangeEvent> getRecordingTimeEditTextObservable() {
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
                setRecordingTimeNearestPresetValue(Long.parseLong(onTextChangeEvent.text().toString()));
            }
        };
    }

    private DisposableObserver<TextViewTextChangeEvent> getVoltageScaleEditTextObservable() {
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
        return getString(R.string.title_toolbar_app_settings);
    }

    @Override
    public void setToolbarTitle(String title) {
        mToolbar.setTitle(title);
    }

    @Override
    public void displayAppSettings(AppSettingsModel appSettings) {
        mAppSettingsModel = appSettings;
        mRecordingTimeEditText.setText(String.valueOf(appSettings.getRecordingTime()));
        mVoltageScaleEditText.setText(String.valueOf(appSettings.getVoltageScale()));
        mCheckBoxShowCountdownTimer.setChecked(appSettings.showCountdownTimer());
    }

    @Override
    public void showSettingsSuccessfullySavedMessage() {
        ToastNotifications.showSuccessMessage(getActivity().getApplicationContext(), getString(R.string.message_successs_save_app_settings));
    }
}
