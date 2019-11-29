package ua.com.crosp.solutions.hearthealthmonitor.module.experiment.data.mapper;

import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.data.model.FeelingResultModel;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.entity.FeelingResultEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.data.mapper.FeelingResultM2EMapperContract;

/**
 * Created by Alexander Molochko on 4/23/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class FeelingResultM2EMapper extends FeelingResultM2EMapperContract {

    @Override
    public FeelingResultEntity transform(FeelingResultModel inputItem) {
        FeelingResultEntity feelingResultEntity = new FeelingResultEntity();
        feelingResultEntity.setComment(inputItem.comment);
        feelingResultEntity.setEntityId(inputItem.id);
        feelingResultEntity.setWeatherType(inputItem.weatherType);
        feelingResultEntity.setFeelingType(inputItem.feelingType);
        return feelingResultEntity;
    }
}
