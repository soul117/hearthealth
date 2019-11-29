package ua.com.crosp.solutions.hearthealthmonitor.module.navigationdrawer.presentation;

import javax.inject.Inject;

import ua.com.crosp.solutions.hearthealthmonitor.base.presentation.BaseAppPresenter;
import ua.com.crosp.solutions.hearthealthmonitor.module.navigationdrawer.contract.presentation.NavigationDrawerPresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.navigationdrawer.contract.routing.NavigationDrawerRouterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.navigationdrawer.contract.view.NavigationDrawerViewContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.navigationdrawer.view.model.UserDrawerInfo;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.cotntract.UserSessionManagerContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.domain.entity.UserEntity;

/**
 * Created by Alexander Molochko on 12/19/16.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

public class NavigationDrawerPresenter extends BaseAppPresenter<NavigationDrawerViewContract, NavigationDrawerRouterContract> implements NavigationDrawerPresenterContract {

    private UserSessionManagerContract mUserSessionManagerContract;

    @Inject
    public NavigationDrawerPresenter(NavigationDrawerRouterContract routerContract, UserSessionManagerContract userSessionManagerContract) {
        setRouter(routerContract);
        mUserSessionManagerContract = userSessionManagerContract;
    }


    @Override
    public void onViewReady() {
        UserEntity userEntity = mUserSessionManagerContract.getCurrentSessionUserEntity();
        UserDrawerInfo userDrawerInfo = UserDrawerInfo.fromEntity(userEntity);
        getView().displayCurrentUserInfo(userDrawerInfo);
    }

    @Override
    public void onViewDestroyed() {

    }


    @Override
    public void onUserAvatarClick() {
        getRouter().switchToUserSettings();
    }

    @Override
    public void onMenuItemSelected() {

    }
}