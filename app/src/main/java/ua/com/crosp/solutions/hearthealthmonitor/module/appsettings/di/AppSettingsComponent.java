package ua.com.crosp.solutions.hearthealthmonitor.module.appsettings.di;

import dagger.Subcomponent;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerModule;
import ua.com.crosp.solutions.hearthealthmonitor.module.appsettings.AppSettingsFragment;
import ua.com.crosp.solutions.hearthealthmonitor.module.appsettings.di.module.AppSettingsModule;

/**
 * Created by Alexander Molochko on 2/21/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
@PerModule // Constraints this component to one-per-application or unscoped bindings.
@Subcomponent(modules = {AppSettingsModule.class})
public interface AppSettingsComponent {

    public void inject(AppSettingsFragment settingsFragment);

}
