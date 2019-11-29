package ua.com.crosp.solutions.hearthealthmonitor.module.start.presentation;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;
import ua.com.crosp.solutions.hearthealthmonitor.base.presentation.BaseAppPresenter;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.cotntract.UserSessionManagerContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.start.cotntract.StartRouterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.start.cotntract.presenter.StartActivityPresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.start.cotntract.view.StartActivityViewContract;

/**
 * Created by Alexander Molochko on 4/21/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class StartActivityPresenter extends BaseAppPresenter<StartActivityViewContract, StartRouterContract> implements StartActivityPresenterContract {
    private StartActivityViewContract mView;
    private UserSessionManagerContract mUserSessionManager;
    // Component & modular dependencies

    @Inject
    public StartActivityPresenter(StartRouterContract router, UserSessionManagerContract seeSessionManagerContract) {
        setRouter(router);
        this.mUserSessionManager = seeSessionManagerContract;
    }

    @Override
    public void onViewReady() {
        checkUserSession();
    }

    @Override
    public void onViewDestroyed() {

    }

    private void checkUserSession() {
        mUserSessionManager.loadPreviousSession()
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (mUserSessionManager.hasPreviousSession()) {
                            switchToMainScreen();
                        } else {
                            showDialogUserInfoRequired();
                        }
                        getView().hideProgressDialog();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mUserSessionManager.destroyCurrentUserSession()
                                .subscribe(new Consumer<Long>() {
                                    @Override
                                    public void accept(Long aLong) throws Exception {
                                        showDialogUserInfoRequired();
                                    }
                                });
                    }
                });
    }

    private void switchToMainScreen() {
        getRouter().switchToMainScreen();
    }

    private void showDialogUserInfoRequired() {
        getView().showNoUserInfoWarning();
    }

    @Override
    public void onUserInfoEnterAccept() {
        getRouter().switchToUserSettings();
    }

    @Override
    public void onUserInfoEnterDecline() {
        getRouter().exitApplication();

    }

}
