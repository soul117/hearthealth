package ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.diassembly.module;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.ExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.PostExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.di.scope.PerModule;
import ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.cotntract.data.DoctorsRepositoryContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.cotntract.presenter.DoctorListPresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.cotntract.router.DoctorListRouterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.domain.usecase.AddNewDoctorUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.domain.usecase.GetAllDoctorsUseCase;
import ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.presentation.DoctorsListPresenter;

/**
 * Created by Alexander Molochko on 5/26/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
@Module
@PerModule
public class DoctorListModule {
    public DoctorListModule() {
    }
    @Provides
    @PerModule
    AddNewDoctorUseCase provideAddNewDoctorUseCase(DoctorsRepositoryContract doctorsRepositoryContract, ExecutionThread threadExecutor, PostExecutionThread postExecutionThread) {
        return new AddNewDoctorUseCase(doctorsRepositoryContract, postExecutionThread, threadExecutor);
    }
    @Provides
    @PerModule
    GetAllDoctorsUseCase provideGetAllDoctorsUseCase(DoctorsRepositoryContract doctorsRepositoryContract, ExecutionThread threadExecutor, PostExecutionThread postExecutionThread) {
        return new GetAllDoctorsUseCase(doctorsRepositoryContract, postExecutionThread, threadExecutor);
    }

    @Provides
    @PerModule
    DoctorListPresenterContract provideDoctorListPresenter(DoctorListRouterContract router, GetAllDoctorsUseCase getAllDoctorsUseCase, AddNewDoctorUseCase addNewDoctorUseCase) {
        return new DoctorsListPresenter(router, getAllDoctorsUseCase, addNewDoctorUseCase);
    }

    @Provides
    @PerModule
    DoctorListRouterContract provideRouter(Activity activity) {
        return (DoctorListRouterContract) activity;
    }

}
