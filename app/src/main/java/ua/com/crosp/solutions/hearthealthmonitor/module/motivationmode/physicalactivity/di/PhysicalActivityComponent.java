package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.di;

import dagger.Subcomponent;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerModule;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.di.module.PACommonModule;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.di.module.PASettingsModule;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.di.module.SquattingGameModule;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.view.PASettingsFragment;

/**
 * Created by Alexander Molochko on 2/21/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
@PerModule // Constraints this component to one-per-application or unscoped bindings.
@Subcomponent(modules = {PACommonModule.class, PASettingsModule.class})
public interface PhysicalActivityComponent {

    public void inject(PASettingsFragment settingsFragment);

    public PhysicalActivityGameComponent plusGameComponent(SquattingGameModule squattingGameModule);

}
