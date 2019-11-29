package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.diassembly;

import dagger.Subcomponent;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerModule;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.diassembly.module.RestModeCommonModule;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.diassembly.module.RestModeExperimentModule;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.diassembly.module.RestModeSettingsModule;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.view.RestModeExperimentFragment;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.rest.view.RestModeSettingsFragment;

/**
 * Created by Alexander Molochko on 2/21/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
@PerModule // Constraints this component to one-per-application or unscoped bindings.
@Subcomponent(modules = {RestModeExperimentModule.class, RestModeCommonModule.class, RestModeSettingsModule.class})
public interface RestModeComponent {

    public void inject(RestModeExperimentFragment restModeFragment);

    public void inject(RestModeSettingsFragment restModeSettingsFragment);

}
