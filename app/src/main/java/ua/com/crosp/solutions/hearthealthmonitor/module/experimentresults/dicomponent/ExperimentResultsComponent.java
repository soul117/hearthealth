package ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.dicomponent;

import dagger.Subcomponent;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerModule;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.dicomponent.module.ExperimentDetailModule;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.dicomponent.module.ExperimentListModule;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.presentation.view.fragment.ExperimentResultDetailFragment;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.presentation.view.fragment.ExperimentResultsListFragment;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.routing.ExperimentResultsActivity;

/**
 * Created by Alexander Molochko on 2/9/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
@PerModule // Constraints this component to one-per-application or unscoped bindings.
@Subcomponent(modules = {ExperimentDetailModule.class, ExperimentListModule.class})
public interface ExperimentResultsComponent {
    void inject(ExperimentResultsListFragment modesListFragment);

    void inject(ExperimentResultsActivity activity);

    void inject(ExperimentResultDetailFragment modeDescriptionFragment);
}
