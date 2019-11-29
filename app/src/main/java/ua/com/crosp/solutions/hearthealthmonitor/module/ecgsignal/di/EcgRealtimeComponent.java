package ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.di;

import dagger.Subcomponent;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerModule;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.di.module.EcgRealtimeVisualizerModule;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.presentation.view.fragment.EcgRealTimeRecordingFragment;
import ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.routing.EcgRealtimeActivity;

/**
 * Created by Alexander Molochko on 2/9/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
@PerModule // Constraints this component to one-per-application or unscoped bindings.
@Subcomponent(modules = {EcgRealtimeVisualizerModule.class})
public interface EcgRealtimeComponent {
    void inject(EcgRealTimeRecordingFragment realtimeFragment);

    void inject(EcgRealtimeActivity activity);

}
