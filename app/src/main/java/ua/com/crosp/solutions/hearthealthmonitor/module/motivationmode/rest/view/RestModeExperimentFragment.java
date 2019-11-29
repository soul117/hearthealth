package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.view;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.DialogAction;
import com.joanzapata.iconify.widget.IconTextView;

import net.crosp.libs.android.circletimeview.CircleTimeView;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;
import ua.com.crosp.solutions.hearthealthmonitor.R;
import ua.com.crosp.solutions.hearthealthmonitor.base.presentation.BackPressNotifier;
import ua.com.crosp.solutions.hearthealthmonitor.base.view.BaseMotivationalModeFragment;
import ua.com.crosp.solutions.hearthealthmonitor.common.notification.ToastNotifications;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.entity.FeelingResultEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.cotntract.presenter.RestModeMainPresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.cotntract.view.RestModeViewContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.diassembly.RestModeComponent;

import static ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.view.CatchTheBallGameFragment.DEFAULT_BACKGROUND_APP_COLOR;

/**
 * A placeholder fragment containing a simple view.
 */
public class RestModeExperimentFragment extends BaseMotivationalModeFragment implements RestModeViewContract, CircleTimeView.CircleTimerListener {
    // Views
    @BindView(R.id.circle_timer_view)
    CircleTimeView mCircleTimerView;

    @BindView(R.id.icon_text_view_restart)
    IconTextView mResetIconTextViewButton;

    @BindView(R.id.icon_text_view_play)
    IconTextView mPlayIconTextViewButton;

    @BindView(R.id.icon_text_view_stop)
    IconTextView mStopIconTextViewButton;
    @Inject
    @Named(DEFAULT_BACKGROUND_APP_COLOR)
    Integer mDefaultBackgroundColor;
    @Inject
    BackPressNotifier mBackPressNotifier;
    // Component & modular dependencies
    @Inject
    RestModeMainPresenterContract mPresenter;

    //Variables

    public RestModeExperimentFragment() {
    }

    //================================================================================
    // Lifecycle callbacks
    //================================================================================


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getFragmentLayoutId(), container, false);
    }

    private void subscribeToBackPressEvents() {
        mBackPressDisposable = mBackPressNotifier.subscribeToBackPressEvents()
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        mPresenter.onBackPressed();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ToastNotifications.showErrorMessage(mContext, throwable.getMessage());
                    }
                });
    }

    public int getFragmentLayoutId() {
        return R.layout.fragment_mode_rest;
    }

    @Override
    public SurfaceView provideSurfaceView() {
        return null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Injecting dependencies
        this.getComponent(RestModeComponent.class).inject(this);
        // Set presenter this as the presenter view
        mPresenter.setView(this);
        subscribeToBackPressEvents();

    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        setupUI();
        mPresenter.onViewReady();

    }

    //================================================================================
    // UI methods
    //================================================================================
    protected void setupUI() {
        setUpTimerView();
    }

    protected void setUpTimerView() {
        mCircleTimerView.setCircleTimerListener(this);
        mCircleTimerView.setEnabled(false);
        mCircleTimerView.setLabelText(R.string.title_have_a_rest);
    }

    //================================================================================
    // View implementation
    //================================================================================

    @OnClick(R.id.icon_text_view_play)
    public void onStartTimerClick() {
        startTimerCountdown();
        hidePlayButton();
        showStopButton();
    }


    @OnClick(R.id.icon_text_view_stop)
    public void onStopTimerClick() {
        showRestartButton();
        showStopButton();
        stopTimerCountdown();
    }

    @OnClick(R.id.icon_text_view_restart)
    public void restartTimer() {
        hideRestartButton();
        hideStopButton();
        showPlayButton();
        setTimerTime(mPresenter.provideRestTime());
    }


    @Override
    public void startTimerCountdown() {
        mCircleTimerView.startTimer();
    }

    @Override
    public void stopTimerCountdown() {
        mCircleTimerView.stopTimer();
    }


    @Override
    public void setTimerTime(long seconds) {
        mCircleTimerView.setCurrentTime((int) seconds);
    }

    @Override
    public void setTimerLabel(String label) {
        mCircleTimerView.setLabelText(label);
    }

    @Override
    public void hideRestartButton() {
        mResetIconTextViewButton.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hidePlayButton() {
        mPlayIconTextViewButton.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideStopButton() {
        mStopIconTextViewButton.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showRestartButton() {
        mResetIconTextViewButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void showStopButton() {
        mStopIconTextViewButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void showPlayButton() {
        mPlayIconTextViewButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void continueGame() {

    }

    @Override
    public void stopGameAndSendResults() {
        mPresenter.onGameFinished(mCircleTimerView.getCurrentTimeInSeconds());
    }

    @Override
    public void stopGameWithoutSavingResults() {
        mPresenter.onGameCancelledWithoutResults();
    }

    @Override
    public void showResultsSuccessfullySavedMessage() {
        ToastNotifications.showSuccessMessage(mContext, getString(R.string.message_results_saved));

    }

    @Override
    public void onTimerStop() {
        mPresenter.onGameFinished(mCircleTimerView.getCurrentTimeInSeconds());
    }

    @Override
    public void onTimerStart(long time) {

    }

    @Override
    public void onTimerTimeValueChanged(long time) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mBackPressDisposable && !mBackPressDisposable.isDisposed()) {
            mBackPressDisposable.dispose();
        }
    }

    @Override
    protected int provideDefaultAppBackgroundColor() {
        return mDefaultBackgroundColor;
    }

    @Override
    protected void onExitDialogOptionSelected(int index, String description) {
        mPresenter.onStopDialogResult(index, description);
    }

    @Override
    protected void onFeelingTypeSelected(@FeelingResultEntity.FeelingType String feelingType) {
        mPresenter.onFeelingTypeSelected(feelingType);
    }

    @Override
    protected void onWeatherTypeSelected(@FeelingResultEntity.WeatherType String weatherType) {
        mPresenter.onWeatherTypeSelected(weatherType);

    }

    @Override
    protected void onStartRecordingAfterResultAction(DialogAction dialogAction) {
        if (dialogAction == DialogAction.NEGATIVE) {
            mPresenter.onCancelStartEcgRecording();
        } else {
            mPresenter.onConfirmStartEcgRecording();
        }
    }

    @Override
    protected void onCommentEntered(String comment) {
        mPresenter.onCommentEntered(comment);
    }

}


