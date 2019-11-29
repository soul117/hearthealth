package ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.cotntract.view;

import ua.com.crosp.solutions.hearthealthmonitor.base.view.BaseView;
import ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.view.viewmodel.DoctorViewModel;
import ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.view.viewmodel.UserSettingsViewModel;

/**
 * Created by Alexander Molochko on 12/19/16.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

public interface UserSettingsViewContract extends BaseView {

    void displayExistingUserSettings(UserSettingsViewModel userEntity);

    void showErrorMessage(String message);

    void updateSelectedDoctorsInfo(DoctorViewModel.List doctorEntities);

    void showNotificationAndDestoryViews();
}
