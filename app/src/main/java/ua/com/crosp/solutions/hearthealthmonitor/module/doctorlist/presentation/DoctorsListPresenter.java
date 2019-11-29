package ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.presentation;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import ua.com.crosp.solutions.hearthealthmonitor.base.presentation.BaseAppPresenter;
import ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.cotntract.presenter.DoctorListPresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.cotntract.router.DoctorListRouterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.cotntract.view.DoctorListView;
import ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.domain.entity.DoctorEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.domain.usecase.AddNewDoctorUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.domain.usecase.GetAllDoctorsUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.view.viewmodel.DoctorListItem;

/**
 * Created by Alexander Molochko on 4/21/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class DoctorsListPresenter extends BaseAppPresenter<DoctorListView, DoctorListRouterContract> implements DoctorListPresenterContract {
    private final GetAllDoctorsUseCase mGetAllDoctorsUseCase;
    private final AddNewDoctorUseCase mAddNewDoctorUseCase;
    // Component & modular dependencies
    private Function<DoctorEntity.List, DoctorListItem.List> mConvertToViewModelFunction = new Function<DoctorEntity.List, DoctorListItem.List>() {
        @Override
        public DoctorListItem.List apply(@NonNull DoctorEntity.List doctorEntities) throws Exception {
            return DoctorListItem.List.fromEntities(doctorEntities);
        }
    };

    @Inject
    public DoctorsListPresenter(DoctorListRouterContract router, GetAllDoctorsUseCase getAllDoctorsUseCase, AddNewDoctorUseCase addNewDoctorUseCase) {
        mGetAllDoctorsUseCase = getAllDoctorsUseCase;
        mAddNewDoctorUseCase = addNewDoctorUseCase;
        setRouter(router);
    }

    @Override
    public void onViewReady() {
        mGetAllDoctorsUseCase.execute(null)
                .map(mConvertToViewModelFunction)
                .subscribe(new Consumer<DoctorListItem.List>() {
                    @Override
                    public void accept(DoctorListItem.List doctorListItems) throws Exception {
                        getView().displayDoctors(doctorListItems);
                    }
                });
    }

    @Override
    public void onViewDestroyed() {

    }

    @Override
    public void onBackButtonPress() {

    }

    @Override
    public Single<DoctorListItem.List> getAllDoctorsList() {
        return mGetAllDoctorsUseCase.execute(null)
                .map(mConvertToViewModelFunction);
    }

    @Override
    public void onAddNewDoctorRequest() {
        getView().showAddNewDoctorInputView();
    }

    @Override
    public Single<DoctorListItem> addNewDoctor(final DoctorListItem doctorListItem) {
        DoctorEntity doctorEntity = doctorListItem.toEntity();
        return mAddNewDoctorUseCase
                .execute(doctorEntity)
                .flatMap(new Function<DoctorEntity, SingleSource<DoctorListItem>>() {
                    @Override
                    public SingleSource<DoctorListItem> apply(@NonNull DoctorEntity doctorEntity) throws Exception {
                        doctorListItem.setId(doctorEntity.getEntityId().getId());
                        return Single.just(doctorListItem);
                    }
                });
    }
}
