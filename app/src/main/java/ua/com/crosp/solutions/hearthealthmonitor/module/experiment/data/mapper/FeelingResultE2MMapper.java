package ua.com.crosp.solutions.hearthealthmonitor.module.experiment.data.mapper;

import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.data.model.FeelingResultModel;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.entity.FeelingResultEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.data.mapper.FeelingResultE2MMapperContract;

/**
 * Created by Alexander Molochko on 4/23/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class FeelingResultE2MMapper extends FeelingResultE2MMapperContract {

    @Override
    public FeelingResultModel transform(FeelingResultEntity inputItem) {
        FeelingResultModel feelingResultModel = new FeelingResultModel();
        feelingResultModel.comment = inputItem.getComment();
        feelingResultModel.weatherType = inputItem.getWeatherType();
        feelingResultModel.feelingType = inputItem.getFeelingType();
        feelingResultModel.id = inputItem.getEntityId().getId();
        return feelingResultModel;
    }
}
