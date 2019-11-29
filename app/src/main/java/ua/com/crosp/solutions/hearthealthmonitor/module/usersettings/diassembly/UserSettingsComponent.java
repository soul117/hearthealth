package ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.diassembly;

import dagger.Subcomponent;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerModule;
import ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.diassembly.module.UserSettingsModule;
import ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.view.UserSettingsFragment;

/**
 * Created by Alexander Molochko on 2/21/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
@PerModule // Constraints this component to one-per-application or unscoped bindings.
@Subcomponent(modules = {UserSettingsModule.class})
public interface UserSettingsComponent {
    public void inject(UserSettingsFragment userSettingsFragment);

}
