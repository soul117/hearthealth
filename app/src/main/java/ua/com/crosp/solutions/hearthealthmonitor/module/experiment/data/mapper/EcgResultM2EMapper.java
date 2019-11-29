package ua.com.crosp.solutions.hearthealthmonitor.module.experiment.data.mapper;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.valueobject.FilePath;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.data.model.EcgResultModel;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.entity.EcgResultEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.data.mapper.EcgResultM2EMapperContract;

/**
 * Created by Alexander Molochko on 4/23/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class EcgResultM2EMapper extends EcgResultM2EMapperContract {
    @Override
    public EcgResultEntity transform(EcgResultModel inputItem) {
        EcgResultEntity ecgResultEntity = new EcgResultEntity();
        ecgResultEntity.setEntityId(inputItem.id);
        ecgResultEntity.setRawRecordingPath(new FilePath(inputItem.rawRecordingPath));
        ecgResultEntity.setXmlRecordingPath(new FilePath(inputItem.xmlRecordingPath));
        ecgResultEntity.setPdfRecordingPath(new FilePath(inputItem.pdfEcgRecordingPath));
        ecgResultEntity.setEstimatedRecordingTimeInSeconds(inputItem.estimatedRecordingTimeInSeconds);
        ecgResultEntity.setDateRecordingStart(inputItem.dateRecordingStart);
        ecgResultEntity.setDateRecordingStop(inputItem.dateRecordingStop);
        ecgResultEntity.setWasInterrupted(inputItem.wasInterrupted);
        RealmList<Long> rrValues = inputItem.rrIntervalValues;
        List<Long> rrValuesCopied = new ArrayList<>();
        if (rrValues != null) {
            for (Long item : rrValues) {
                rrValuesCopied.add(item);
            }
        }
        ecgResultEntity.setRRIntervalValues(rrValuesCopied);
        return ecgResultEntity;
    }
}
