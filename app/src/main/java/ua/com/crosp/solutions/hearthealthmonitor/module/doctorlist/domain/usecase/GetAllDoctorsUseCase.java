
package ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.domain.usecase;

import io.reactivex.Single;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.ExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.executor.PostExecutionThread;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.usecase.BaseSingleUseCaseContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.cotntract.data.DoctorsRepositoryContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.domain.entity.DoctorEntity;

/**
 * Created by Alexander Molochko on 1/8/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class GetAllDoctorsUseCase extends BaseSingleUseCaseContract<Void, DoctorEntity.List> {

    private final DoctorsRepositoryContract mRepository;

    public GetAllDoctorsUseCase(DoctorsRepositoryContract repository,
                                PostExecutionThread postExecutionThread, ExecutionThread executionThread) {
        super(executionThread, postExecutionThread);
        mRepository = repository;
    }


    @Override
    public Single<DoctorEntity.List> provideSingleObservable(Void aVoid) {
        return mRepository.getAllDoctors();
    }
}

