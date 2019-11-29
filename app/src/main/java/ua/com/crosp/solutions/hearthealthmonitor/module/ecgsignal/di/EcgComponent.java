package ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.di;

import dagger.Subcomponent;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerModule;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.di.module.EcgCommonModule;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.di.module.EcgMainModule;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.presentation.view.fragment.EcgMainFragment;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.routing.EcgConnectionActivity;

/**
 * Created by Alexander Molochko on 2/9/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
@PerModule // Constraints this component to one-per-application or unscoped bindings.
@Subcomponent(modules = {EcgCommonModule.class, EcgMainModule.class})
public interface EcgComponent {
    void inject(EcgConnectionActivity activity);

    void inject(EcgMainFragment ecgMainFragment);


}
