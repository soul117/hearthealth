package ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.cotntract.view;

import ua.com.crosp.solutions.hearthealthmonitor.base.view.BaseView;
import ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.view.viewmodel.DoctorListItem;

/**
 * Created by Alexander Molochko on 12/19/16.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

public interface DoctorListView extends BaseView {
    void displayDoctors(DoctorListItem.List doctorEntities);

    void showAddNewDoctorInputView();
}
