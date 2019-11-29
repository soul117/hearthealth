package ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.cotntract.presenter;

import io.reactivex.Single;
import ua.com.crosp.solutions.hearthealthmonitor.base.presentation.BasePresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.cotntract.router.DoctorListRouterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.cotntract.view.DoctorListView;
import ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.view.viewmodel.DoctorListItem;

/**
 * Created by Alexander Molochko on 12/19/16.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

public interface DoctorListPresenterContract extends BasePresenterContract<DoctorListView, DoctorListRouterContract> {

    void onBackButtonPress();

    Single<DoctorListItem.List> getAllDoctorsList();

    void onAddNewDoctorRequest();

    Single<DoctorListItem> addNewDoctor(DoctorListItem doctorListItem);
}
