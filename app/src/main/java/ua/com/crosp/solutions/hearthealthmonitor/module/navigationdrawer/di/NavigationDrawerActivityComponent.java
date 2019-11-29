package ua.com.crosp.solutions.hearthealthmonitor.module.navigationdrawer.di;

import dagger.Subcomponent;
import ua.com.crosp.solutions.hearthealthmonitor.base.routing.BaseNavigationDrawerActivity;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerModule;
import ua.com.crosp.solutions.hearthealthmonitor.module.navigationdrawer.di.module.NavigationDrawerModule;

/**
 * Created by Alexander Molochko on 2/21/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
/*@PerModule // Constraints this component to one-per-application or unscoped bindings.
@Subcomponent(modules = {NavigationDrawerModule.class})*/
public interface NavigationDrawerActivityComponent {
    public void inject(BaseNavigationDrawerActivity navigationDrawerActivity);

}
