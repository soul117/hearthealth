package ua.com.crosp.solutions.hearthealthmonitor.module.experiment.data.mapper;

import java.util.List;

import io.realm.RealmList;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.data.model.EcgResultModel;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.entity.EcgResultEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.data.mapper.EcgResultE2MMapperContract;

/**
 * Created by Alexander Molochko on 4/23/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class EcgResultE2MMapper extends EcgResultE2MMapperContract {


    @Override
    public EcgResultModel transform(EcgResultEntity inputItem) {
        EcgResultModel ecgResultModel = new EcgResultModel();
        ecgResultModel.id = inputItem.getEntityId().getId();
        ecgResultModel.dateRecordingStop = inputItem.getDateRecordingStop();
        ecgResultModel.rrIntervalValues = new RealmList<>();
        List<Long> rrValues = inputItem.getRRIntervalValues();
        if (rrValues != null) {
            ecgResultModel.rrIntervalValues.addAll(inputItem.getRRIntervalValues());
        }
        ecgResultModel.dateRecordingStart = inputItem.getDateRecordingStart();
        ecgResultModel.estimatedRecordingTimeInSeconds = inputItem.getEstimatedRecordingTimeInSeconds();
        ecgResultModel.rawRecordingPath = inputItem.getRawRecordingPath().getStringPath();
        ecgResultModel.pdfEcgRecordingPath = inputItem.getPdfRecordingPath().getStringPath();
        ecgResultModel.xmlRecordingPath = inputItem.getXmlRecordingPath().getStringPath();
        ecgResultModel.wasInterrupted = inputItem.isWasInterrupted();
        return ecgResultModel;
    }
}
