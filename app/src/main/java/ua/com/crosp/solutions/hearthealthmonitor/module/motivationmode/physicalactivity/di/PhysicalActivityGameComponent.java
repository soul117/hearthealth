package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.di;

import dagger.Subcomponent;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerGame;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.di.module.SquattingGameModule;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.view.SquatGameFragment;

/**
 * Created by Alexander Molochko on 2/21/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
@PerGame // Constraints this component to one-per-application or unscoped bindings.
@Subcomponent(modules = {SquattingGameModule.class})

public interface PhysicalActivityGameComponent {
    public void inject(SquatGameFragment physicalActivityFragment);
}
