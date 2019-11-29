package ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.presentation.view.fragment;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

import com.cleveroad.audiovisualization.GLAudioVisualizationView;
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
import ua.com.crosp.solutions.hearthealthmonitor.base.view.BaseFragment;
import ua.com.crosp.solutions.hearthealthmonitor.common.view.PresetRadioGroup;
import ua.com.crosp.solutions.hearthealthmonitor.common.view.PresetValueButton;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.audio.receivers.HeadphonesPlugNotifier;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util.Logger;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util.StringUtils;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util.ViewUtils;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.contract.presentation.EcgMainPresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.contract.view.EcgMainViewContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.di.EcgComponent;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.entity.ExperimentResultEntity;

/**
 * Created by Alexander Molochko on 5/24/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class EcgMainFragment extends BaseFragment implements EcgMainViewContract {
    public static final int DEBOUNCE_TIMEOUT = 500;
    public static final int MINIMAL_PRESET_RECORDING_TIME = 30;
    // Views
    @BindView(R.id.preset_rest_time_radio_group)
    PresetRadioGroup mPresetRecordingTimeRadioGroup;
    @BindView(R.id.audio_visualization_view)
    GLAudioVisualizationView mAudioVisualizationView;
    @BindView(R.id.edit_text_rest_time)
    AppCompatEditText mRecordingTimeEditText;
    @BindView(R.id.image_view_sensor_connection_state)
    ImageView mImageViewSensorState;
    @BindView(R.id.text_view_sensor_state_info)
    TextView mTextViewSensorState;

    @BindView(R.id.button_start_recording)
    Button mButtonStartRecording;

    // Component & modular dependencies
    @Inject
    EcgMainPresenterContract mPresenter;
    @Inject
    HeadphonesPlugNotifier mHeadphonesPlugNotifier;


    //region Variables
    private DisposableObserver<TextViewTextChangeEvent> mRecordingTimeObservable;
    //endregion

    //region Inline class implementations
    private AppendOnlyLinkedArrayList.NonThrowingPredicate<TextViewTextChangeEvent> mFilterEmptyStringsPredicate = new AppendOnlyLinkedArrayList.NonThrowingPredicate<TextViewTextChangeEvent>() {
        @Override
        public boolean test(TextViewTextChangeEvent textViewTextChangeEvent) {
            return !StringUtils.isBlank(textViewTextChangeEvent.text().toString());
        }
    };
    //endregion

    public EcgMainFragment() {
    }

    @Override
    public int getFragmentLayoutId() {
        return R.layout.fragment_ecg_main;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Injecting dependencies
        this.getComponent(EcgComponent.class).inject(this);
        // Set presenter this as the presenter view
        mPresenter.setView(this);
    }

    //region Lifecycle methods

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setListeners();
        mPresenter.onViewReady();
        initVisualizationView();
        setSensorStateView();
    }

    private void setSensorStateView() {
        showNotConnectedSensorState();
    }

    private void initVisualizationView() {
        mPresenter.linkAudioToVisualizationView(mAudioVisualizationView);
    }

    @OnClick(R.id.button_start_recording)
    public void startRecording(View view) {
        releaseAudioVisualizer();
        mPresenter.onStartRecordingClicked(Long.parseLong(mRecordingTimeEditText.getText().toString()));
    }

    @Override
    public void onPause() {
        super.onPause();
        mAudioVisualizationView.onPause();
        // Save settings
    }

    @Override
    public void onDestroy() {
        mPresenter.onViewDestroyed();
        releaseAudioVisualizer();
        super.onDestroy();
    }

    private void releaseAudioVisualizer() {
        mRecordingTimeObservable.dispose();
        mAudioVisualizationView.release();
    }

    @Override
    public void onResume() {
        super.onResume();
        mAudioVisualizationView.onResume();
    }

    //endregion

    //region View contract implementation


    //region View internal implementation

    private void setListeners() {
        mPresetRecordingTimeRadioGroup.setOnCheckedChangeListener(new PresetRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View radioGroup, View radioButton, boolean isChecked, int checkedId) {
                String value = ((PresetValueButton) radioButton).getValue();
                ViewUtils.setEditTextSelection(mRecordingTimeEditText, value);
            }
        });
        mRecordingTimeObservable = RxTextView.textChangeEvents(mRecordingTimeEditText)
                .debounce(DEBOUNCE_TIMEOUT, TimeUnit.MILLISECONDS) // default Scheduler is Computation
                .filter(mFilterEmptyStringsPredicate)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getRecordingTimeEditTextObservable());
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

    //endregion


    @Override
    public void displayExperimentDetails(ExperimentResultEntity experimentResultEntity) {
        mImageViewSensorState.setColorFilter(getResources().getColor(R.color.app_white), PorterDuff.Mode.SRC_IN);
    }

    @Override
    public void showNotConnectedSensorState() {
        mTextViewSensorState.setText(R.string.text_sensor_not_connected);
        mImageViewSensorState.setColorFilter(getResources().getColor(R.color.app_red), PorterDuff.Mode.SRC_IN);
    }

    @Override
    public void showConnectedSensorState() {
        mTextViewSensorState.setText(R.string.text_sensor_connected);
        mImageViewSensorState.setColorFilter(getResources().getColor(R.color.app_secondary), PorterDuff.Mode.SRC_IN);
    }

    @Override
    public void setSavedRecordingTime(Long value) {
        setRecordingTimeNearestPresetValue(value);
        mRecordingTimeEditText.setText(String.valueOf(value));
    }
}
