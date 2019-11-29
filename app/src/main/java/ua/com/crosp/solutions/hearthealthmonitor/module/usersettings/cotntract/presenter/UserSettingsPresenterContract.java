package ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.cotntract.presenter;

import ua.com.crosp.solutions.hearthealthmonitor.base.presentation.BasePresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.session.domain.entity.UserEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.cotntract.router.UserSettingsRouterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.cotntract.view.UserSettingsViewContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.view.viewmodel.UserSettingsViewModel;

/**
 * Created by Alexander Molochko on 12/19/16.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

public interface UserSettingsPresenterContract extends BasePresenterContract<UserSettingsViewContract, UserSettingsRouterContract> {

    void onBackButtonPress();

    void saveUserSettings(UserSettingsViewModel userEntity);
}
