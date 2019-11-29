package ua.com.crosp.solutions.hearthealthmonitor.module.experiment.data.mapper;

import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.data.model.ExperimentResultModel;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.entity.ExperimentResultEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.data.mapper.EcgResultE2MMapperContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.data.mapper.ExperimentResultE2MMapperContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.data.mapper.FeelingResultE2MMapperContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.data.mapper.GameResultE2MMapperContract;

/**
 * Created by Alexander Molochko on 4/23/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class ExperimentResultE2MMapper extends ExperimentResultE2MMapperContract {
    GameResultE2MMapperContract mGameResultE2MMapperContract;
    FeelingResultE2MMapperContract mFeelingResultE2MMapperContract;
    EcgResultE2MMapperContract mEcgResultE2MMapperContract;

    public ExperimentResultE2MMapper(GameResultE2MMapperContract gameResultE2MMapperContract, FeelingResultE2MMapperContract feelingResultE2MMapperContract, EcgResultE2MMapperContract ecgResultE2MMapperContract) {
        mGameResultE2MMapperContract = gameResultE2MMapperContract;
        mFeelingResultE2MMapperContract = feelingResultE2MMapperContract;
        mEcgResultE2MMapperContract = ecgResultE2MMapperContract;
    }

    @Override
    public ExperimentResultModel transform(ExperimentResultEntity inputItem) {
        ExperimentResultModel experimentResultModel = new ExperimentResultModel();
        experimentResultModel.userId = inputItem.getUserId().getId();
        experimentResultModel.dateCreated = inputItem.getDateOfCreation().getDate();
        experimentResultModel.id = inputItem.getEntityId().getId();
        experimentResultModel.name = inputItem.getExperimentName();
        experimentResultModel.basePath = inputItem.getBaseExperimentPath();
        experimentResultModel.feelingResult = mFeelingResultE2MMapperContract.transform(inputItem.getFeelingResultEntity());
        experimentResultModel.gameResultModel = mGameResultE2MMapperContract.transform(inputItem.getGameResultEntity());
        experimentResultModel.ecgResultModel = mEcgResultE2MMapperContract.transform(inputItem.getEcgResultEntity());
        return experimentResultModel;
    }
}
