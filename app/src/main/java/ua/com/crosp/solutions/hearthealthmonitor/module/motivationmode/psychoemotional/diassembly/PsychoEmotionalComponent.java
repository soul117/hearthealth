package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.diassembly;

import dagger.Subcomponent;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerModule;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.diassembly.module.CatchTheBallModule;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.diassembly.module.PsychoEmotionalCommonModule;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.diassembly.module.PsychoEmotionalSettingsModule;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.view.PsychoEmotionalSettingsFragment;

/**
 * Created by Alexander Molochko on 2/21/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
@PerModule // Constraints this component to one-per-application or unscoped bindings.
@Subcomponent(modules = {PsychoEmotionalCommonModule.class, PsychoEmotionalSettingsModule.class})
public interface PsychoEmotionalComponent {
    public void inject(PsychoEmotionalSettingsFragment settingsFragment);

    public PsychoEmotionalGameComponent plusGameComponent(CatchTheBallModule gameModule);
}
