package ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.dicomponent;

import dagger.Subcomponent;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerModule;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.dicomponent.module.ModeDescriptionModule;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.dicomponent.module.ModesListModule;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.presentation.view.ModeDescriptionFragment;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.presentation.view.ModesListFragment;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.routing.ModesActivity;

/**
 * Created by Alexander Molochko on 2/9/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
@PerModule // Constraints this component to one-per-application or unscoped bindings.
@Subcomponent(modules = {ModeDescriptionModule.class, ModesListModule.class})
public interface ModesComponent {
    void inject(ModesListFragment modesListFragment);

    void inject(ModesActivity activity);

    void inject(ModeDescriptionFragment modeDescriptionFragment);
}
