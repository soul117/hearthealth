package ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.dicomponent;

import dagger.Subcomponent;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerModule;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.di.module.EcgResultModule;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.presentation.view.fragment.EcgChartResultFragment;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.routing.EcgResultActivity;

/**
 * Created by Alexander Molochko on 2/9/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
@PerModule // Constraints this component to one-per-application or unscoped bindings.
@Subcomponent(modules = {EcgResultModule.class})
public interface EcgResultsComponent {

    void inject(EcgResultActivity activity);

    void inject(EcgChartResultFragment ecgExperimentResultFragment);
}
