package ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.cotntract.data;

/**
 * Created by Alexander Molochko on 1/22/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

import io.reactivex.Single;
import ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.domain.entity.DoctorEntity;

public interface DoctorsRepositoryContract {
    Single<DoctorEntity> getDoctorById(Long doctorId);

    Single<DoctorEntity.List> getAllDoctors();

    Single<Long> addNewDoctor(DoctorEntity doctorEntity);
}
