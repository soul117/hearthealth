package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.diassembly;

import dagger.Subcomponent;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerGame;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.diassembly.module.CatchTheBallModule;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.view.CatchTheBallGameFragment;

/**
 * Created by Alexander Molochko on 2/21/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
@PerGame // Constraints this component to one-per-application or unscoped bindings.
@Subcomponent(modules = {CatchTheBallModule.class})
public interface PsychoEmotionalGameComponent {
    public void inject(CatchTheBallGameFragment ballGameFragment);
}
