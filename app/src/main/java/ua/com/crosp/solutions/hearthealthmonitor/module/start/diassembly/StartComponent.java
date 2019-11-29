package ua.com.crosp.solutions.hearthealthmonitor.module.start.diassembly;

import dagger.Subcomponent;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerModule;
import ua.com.crosp.solutions.hearthealthmonitor.module.start.diassembly.module.StartModule;
import ua.com.crosp.solutions.hearthealthmonitor.module.start.routing.StartActivity;

/**
 * Created by Alexander Molochko on 2/9/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
@PerModule // Constraints this component to one-per-application or unscoped bindings.
@Subcomponent(modules = {StartModule.class})
public interface StartComponent {
    void inject(StartActivity startActivity);
}
