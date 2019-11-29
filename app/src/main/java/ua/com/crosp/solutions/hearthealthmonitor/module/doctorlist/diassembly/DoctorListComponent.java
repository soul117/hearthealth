package ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.diassembly;

import dagger.Subcomponent;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerModule;
import ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.diassembly.module.DoctorListModule;
import ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.view.dialog.DoctorsListDialogFragment;

/**
 * Created by Alexander Molochko on 2/21/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
@PerModule // Constraints this component to one-per-application or unscoped bindings.
@Subcomponent(modules = {DoctorListModule.class})
public interface DoctorListComponent {
    public void inject(DoctorsListDialogFragment doctorsListDialogFragment);

}
