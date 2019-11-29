
package ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.domain.usecase;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
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

public class AddNewDoctorUseCase extends BaseSingleUseCaseContract<DoctorEntity, DoctorEntity> {

    private final DoctorsRepositoryContract mRepository;

    public AddNewDoctorUseCase(DoctorsRepositoryContract repository,
                               PostExecutionThread postExecutionThread, ExecutionThread executionThread) {
        super(executionThread, postExecutionThread);
        mRepository = repository;
    }

    @Override
    public Single<DoctorEntity> provideSingleObservable(DoctorEntity doctorEntity) {
        return mRepository.addNewDoctor(doctorEntity)
                .flatMap(new Function<Long, SingleSource<DoctorEntity>>() {
                    @Override
                    public SingleSource<DoctorEntity> apply(@NonNull Long doctorId) throws Exception {
                        return mRepository.getDoctorById(doctorId);
                    }
                });
    }
}

