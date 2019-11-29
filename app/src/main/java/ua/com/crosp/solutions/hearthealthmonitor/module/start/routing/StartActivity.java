package ua.com.crosp.solutions.hearthealthmonitor.module.start.routing;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import javax.inject.Inject;

import ua.com.crosp.solutions.hearthealthmonitor.R;
import ua.com.crosp.solutions.hearthealthmonitor.di.contract.ProvidesComponent;
import ua.com.crosp.solutions.hearthealthmonitor.game.BaseFullscreenActivity;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.routing.ModesActivity;
import ua.com.crosp.solutions.hearthealthmonitor.module.start.cotntract.StartRouterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.start.cotntract.presenter.StartActivityPresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.start.cotntract.view.StartActivityViewContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.start.diassembly.StartComponent;
import ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.routing.UserSettingsActivity;

/**
 * Created by Alexander Molochko on 2/18/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class StartActivity extends BaseFullscreenActivity implements StartRouterContract, ProvidesComponent<StartComponent>, StartActivityViewContract {
    private StartComponent mDiComponent;
    @Inject
    StartActivityPresenterContract mPresenter;
    private MaterialDialog mProgressDialog;

    @Override
    public void navigateToInitialScreen() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inject dependencies
        this.getComponent().inject(this);
        mPresenter.setView(this);
        initProgressDialog();
    }

    private void initProgressDialog() {
        mProgressDialog = new MaterialDialog.Builder(this)
                .title(R.string.title_dialog_loading)
                .content(R.string.content_dialog_please_wait)
                .progress(true, 0)
                .autoDismiss(false)
                .build();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onViewReady();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onViewDestroyed();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.onViewDestroyed();
    }

    @Override
    public void navigateBack() {
        super.onBackPressed();
    }

    @Override
    public StartComponent getComponent() {
        // Lazy initialization
        if (mDiComponent == null) {
            mDiComponent = getBaseActivityComponent().plusStartComponent();
        }
        return mDiComponent;
    }

    @Override
    public int provideOrientation() {
        return ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    }


    @Override
    public void switchToMainScreen() {
        startNewActivity(ModesActivity.class);
    }

    @Override
    public void switchToUserSettings() {
        startNewActivity(UserSettingsActivity.class);
    }

    @Override
    public void exitApplication() {
        mApplicationNavigator.exitApplication();
    }

    @Override
    public void showNoUserInfoWarning() {
        new MaterialDialog.Builder(this)
                .iconRes(R.drawable.ic_user_info)
                .limitIconToDefaultSize() // limits the displayed icon size to 48dp
                .title(R.string.title_dialog_no_user_info_set)
                .content(R.string.message_error_no_user_info)
                .positiveText(R.string.button_text_yes)
                .negativeText(R.string.button_text_no)
                .onAny(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (which == DialogAction.POSITIVE) {
                            mPresenter.onUserInfoEnterAccept();
                        } else if (which == DialogAction.NEGATIVE) {
                            mPresenter.onUserInfoEnterDecline();
                        }
                    }
                })
                .show();

    }

    @Override
    public void showProgressDialog() {
        mProgressDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        mProgressDialog.dismiss();
    }

}
