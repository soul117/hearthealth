package ua.com.crosp.solutions.hearthealthmonitor.module.experiment.data.mapper;

import ua.com.crosp.solutions.hearthealthmonitor.base.domain.valueobject.EntityId;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.valueobject.GeneralDate;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.data.model.ExperimentResultModel;
import ua.com.crosp.solutions.hearthealthmonitor.module.experiment.domain.entity.ExperimentResultEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.data.mapper.EcgResultM2EMapperContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.data.mapper.ExperimentResultM2EMapperContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.data.mapper.FeelingResultM2EMapperContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.contract.data.mapper.GameResultM2EMapperContract;

/**
 * Created by Alexander Molochko on 4/23/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class ExperimentResultM2EMapper extends ExperimentResultM2EMapperContract {
    GameResultM2EMapperContract mGameResultE2MMapperContract;
    FeelingResultM2EMapperContract mFeelingResultE2MMapperContract;
    EcgResultM2EMapperContract mEcgResultE2MMapperContract;

    public ExperimentResultM2EMapper(GameResultM2EMapperContract gameResultE2MMapperContract, FeelingResultM2EMapperContract feelingResultE2MMapperContract, EcgResultM2EMapperContract ecgResultE2MMapperContract) {
        mGameResultE2MMapperContract = gameResultE2MMapperContract;
        mFeelingResultE2MMapperContract = feelingResultE2MMapperContract;
        mEcgResultE2MMapperContract = ecgResultE2MMapperContract;
    }

    @Override
    public ExperimentResultEntity transform(ExperimentResultModel inputItem) {
        ExperimentResultEntity experimentResultEntity = new ExperimentResultEntity();
        experimentResultEntity.setUserId(new EntityId(inputItem.userId));
        experimentResultEntity.setGameResultEntity(mGameResultE2MMapperContract.transform(inputItem.gameResultModel));
        experimentResultEntity.setFeelingResultEntity(mFeelingResultE2MMapperContract.transform(inputItem.feelingResult));
        experimentResultEntity.setEcgResultEntity(mEcgResultE2MMapperContract.transform(inputItem.ecgResultModel));
        experimentResultEntity.setDateOfCreation(new GeneralDate(inputItem.dateCreated));
        experimentResultEntity.setExperimentName(inputItem.name);
        experimentResultEntity.setBaseExperimentPath(inputItem.basePath);
        experimentResultEntity.setEntityId(inputItem.id);
        return experimentResultEntity;
    }
}
