package ua.com.crosp.solutions.hearthealthmonitor.base.view;

import android.graphics.Color;
import android.text.InputType;

import androidx.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.simplelist.MaterialSimpleListAdapter;
import com.afollestad.materialdialogs.simplelist.MaterialSimpleListItem;

import io.reactivex.disposables.Disposable;
import ua.com.crosp.solutions.hearthealthmonitor.R;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.entity.FeelingResultEntity;

/**
 * Created by Alexander Molochko on 12/18/16.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

public abstract class BaseMotivationalModeFragment extends BaseGameFragment {
    public static final int DEFAULT_ICON_DIALOG_PADDING = 15;
    protected MaterialDialog mExitConfirmDialog;
    protected Disposable mBackPressDisposable;
    protected Disposable mCountDownDisposable;
    private MaterialDialog mWeatherSelectionDialog;
    private MaterialDialog mFeelingSelectionDialog;
    private MaterialDialog mCommentDialog;
    private MaterialDialog mStartEcgRecordingPromptDialog;

    //================================================================================
    // View implementation
    //================================================================================

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    protected void initGameFeelingDialog() {
        final MaterialSimpleListAdapter adapter =
                new MaterialSimpleListAdapter(
                        new MaterialSimpleListAdapter.Callback() {
                            @Override
                            public void onMaterialListItemSelected(MaterialDialog dialog, int index, MaterialSimpleListItem item) {
                                onFeelingTypeSelected(getSelectedFeelingType(index));
                                dialog.dismiss();
                            }
                        });
        adapter.add(
                new MaterialSimpleListItem.Builder(mContext)
                        .content(R.string.option_game_feeling_good)
                        .icon(R.drawable.ic_feeling_good)
                        .build());
        adapter.add(
                new MaterialSimpleListItem.Builder(mContext)
                        .content(R.string.option_game_feeling_well)
                        .icon(R.drawable.ic_feeling_well)
                        .build());
        adapter.add(
                new MaterialSimpleListItem.Builder(mContext)
                        .content(R.string.option_game_feeling_bad)
                        .icon(R.drawable.ic_feeling_bad)
                        .build());
        adapter.add(
                new MaterialSimpleListItem.Builder(mContext)
                        .content(R.string.option_game_feeling_sick)
                        .icon(R.drawable.ic_feeling_sick)
                        .build());

        mFeelingSelectionDialog = new MaterialDialog.Builder(mContext)
                .title(R.string.title_dialog_select_feeling)
                .content(R.string.content_dialog_feeling_experiment_ended)
                .cancelable(false)
                .adapter(adapter, null).build();
    }

    protected void initWeatherTypeDialog() {
        final MaterialSimpleListAdapter adapter =
                new MaterialSimpleListAdapter(
                        new MaterialSimpleListAdapter.Callback() {
                            @Override
                            public void onMaterialListItemSelected(MaterialDialog dialog, int index, MaterialSimpleListItem item) {
                                onWeatherTypeSelected(getSelectedWeatherTypeByIndex(index));
                                dialog.dismiss();
                            }
                        });
        adapter.add(
                new MaterialSimpleListItem.Builder(mContext)
                        .content(R.string.option_game_weather_sunny)
                        .icon(R.drawable.ic_weather_sunny)
                        .build());
        adapter.add(
                new MaterialSimpleListItem.Builder(mContext)
                        .content(R.string.option_game_weather_cloudy)
                        .icon(R.drawable.ic_weather_cloudy)
                        .build());
        adapter.add(
                new MaterialSimpleListItem.Builder(mContext)
                        .content(R.string.option_game_weather_rainy)
                        .icon(R.drawable.ic_weather_rainy)
                        .build());
        adapter.add(
                new MaterialSimpleListItem.Builder(mContext)
                        .content(R.string.option_game_weather_stormy)
                        .icon(R.drawable.ic_weather_stormy)
                        .build());

        mWeatherSelectionDialog = new MaterialDialog.Builder(mContext)
                .title(R.string.title_dialog_select_weather_type)
                .cancelable(false)
                .adapter(adapter, null).build();
    }

    protected void initExitConfirmDialog() {
        final MaterialSimpleListAdapter adapter =
                new MaterialSimpleListAdapter(
                        new MaterialSimpleListAdapter.Callback() {
                            @Override
                            public void onMaterialListItemSelected(MaterialDialog dialog, int index, MaterialSimpleListItem item) {
                                onExitDialogOptionSelected(index, item.toString());
                                dialog.dismiss();
                            }
                        });
        adapter.add(
                new MaterialSimpleListItem.Builder(mContext)
                        .content(R.string.option_game_stop_without_save)
                        .icon(R.drawable.ic_stop_cross)
                        .iconPadding(DEFAULT_ICON_DIALOG_PADDING)
                        .backgroundColor(provideDefaultAppBackgroundColor())
                        .build());
        adapter.add(
                new MaterialSimpleListItem.Builder(mContext)
                        .content(R.string.option_game_stop_start_ecg_recording)
                        .icon(R.drawable.ic_stop_hand)
                        .iconPadding(DEFAULT_ICON_DIALOG_PADDING)
                        .backgroundColor(provideDefaultAppBackgroundColor())
                        .build());
        adapter.add(
                new MaterialSimpleListItem.Builder(mContext)
                        .content(R.string.option_game_stop_start_cancel)
                        .icon(R.drawable.ic_return)
                        .iconPadding(DEFAULT_ICON_DIALOG_PADDING)
                        .backgroundColor(provideDefaultAppBackgroundColor())
                        .build());


        mExitConfirmDialog = new MaterialDialog.Builder(mContext)
                .title(R.string.title_dialog_confirm_experiment_stop)
                .cancelable(false)
                .adapter(adapter, null).build();
    }

    protected void initStartRecordingPromptDialog() {
        mStartEcgRecordingPromptDialog = new MaterialDialog.Builder(getContext())
                .title(R.string.title_dialog_start_ecg_recording)
                .content(R.string.content_dialog_start_ecg_recording, true)
                .positiveText(R.string.button_text_yes)
                .negativeText(R.string.button_text_no)
                .cancelable(false)
                .onAny(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        onStartRecordingAfterResultAction(which);
                    }
                })
                .show();
    }


    protected void initCommentDialog() {
        mCommentDialog = new MaterialDialog.Builder(getContext())
                .title(R.string.title_dialog_game_result_comment)
                .content(R.string.content_dialog_leave_comment)
                .inputType(
                        InputType.TYPE_CLASS_TEXT
                                | InputType.TYPE_TEXT_VARIATION_PERSON_NAME
                                | InputType.TYPE_TEXT_FLAG_CAP_WORDS | InputType.TYPE_TEXT_FLAG_MULTI_LINE)
                .positiveText(R.string.button_text_submit)
                .cancelable(false)
                .input(
                        R.string.hint_enter_comment,
                        R.string.empty_string,
                        true,
                        new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                onCommentEntered(input.toString());
                            }
                        })
                .show();
    }

    protected void releaseSubscriptions() {
        if (mBackPressDisposable != null) {
            mBackPressDisposable.dispose();
        }
        if (mCountDownDisposable != null) {
            mCountDownDisposable.dispose();
        }

    }

    public void showStartRecordingDialog() {
        if (mStartEcgRecordingPromptDialog == null) {
            initStartRecordingPromptDialog();
        }
        mStartEcgRecordingPromptDialog.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mExitConfirmDialog != null) {
            mExitConfirmDialog.dismiss();
        }
        mExitConfirmDialog = null;
        mStartEcgRecordingPromptDialog = null;
        mCommentDialog = null;
        mWeatherSelectionDialog = null;

    }

    public void showGameExitConfirmationDialog() {
        if (mExitConfirmDialog == null) {
            initExitConfirmDialog();
        }
        mExitConfirmDialog.show();
    }

    public void showWeatherTypeSelectionDialog() {
        if (mWeatherSelectionDialog == null) {
            initWeatherTypeDialog();
        }
        mWeatherSelectionDialog.show();
    }

    public void showFeelingTypeSelectionDialog() {
        if (mFeelingSelectionDialog == null) {
            initGameFeelingDialog();
        }
        mFeelingSelectionDialog.show();
    }

    public void showCommentEnterDialog() {
        if (mCommentDialog == null) {
            initCommentDialog();
        }
        mCommentDialog.show();
    }


    private
    @FeelingResultEntity.FeelingType
    String getSelectedFeelingType(int index) {
        switch (index) {
            case 0:
                return FeelingResultEntity.FEELING_GOOD;
            case 1:
                return FeelingResultEntity.FEELING_WELL;
            case 2:
                return FeelingResultEntity.FEELING_BAD;
            case 3:
                return FeelingResultEntity.FEELING_SICK;
            default:
                return FeelingResultEntity.FEELING_GOOD;
        }
    }

    private
    @FeelingResultEntity.WeatherType
    String getSelectedWeatherTypeByIndex(int index) {
        switch (index) {
            case 0:
                return FeelingResultEntity.DAY_SUNNY;
            case 1:
                return FeelingResultEntity.DAY_CLOUDY;
            case 2:
                return FeelingResultEntity.DAY_RAINY;
            case 3:
                return FeelingResultEntity.DAY_STORMY;
            default:
                return FeelingResultEntity.DAY_CLOUDY;
        }
    }

    protected abstract int provideDefaultAppBackgroundColor();

    protected int getTransparentColor() {
        return Color.TRANSPARENT;
    }

    protected abstract void onExitDialogOptionSelected(int index, String description);

    protected abstract void onFeelingTypeSelected(@FeelingResultEntity.FeelingType String feelingType);

    protected abstract void onWeatherTypeSelected(@FeelingResultEntity.WeatherType String dayType);

    protected abstract void onStartRecordingAfterResultAction(DialogAction dialogAction);

    protected abstract void onCommentEntered(String comment);
}
