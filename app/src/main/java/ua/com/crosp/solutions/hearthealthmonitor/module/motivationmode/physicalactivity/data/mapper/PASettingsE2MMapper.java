package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.data.mapper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;
import ua.com.crosp.solutions.hearthealthmonitor.base.presentation.BaseMapper;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.data.PASettingsEntity;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.data.PASettingsModel;

/**
 * Created by Alexander Molochko on 4/23/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class PASettingsE2MMapper extends BaseMapper<PASettingsEntity,PASettingsModel> {

    public PASettingsE2MMapper() {
    }

    @Override
    public PASettingsModel transform(PASettingsEntity inputItem) {
        return new PASettingsModel(inputItem.getExperimentTime(), inputItem.getRepsCount(), inputItem.isEnableSound());
    }

    @Override
    public List<PASettingsModel> transform(List<PASettingsEntity> inputCollection) {
        List<PASettingsModel> transformedItems = new ArrayList<>();
        for (int i = 0; i < inputCollection.size(); i++) {
            transformedItems.add(this.transform(inputCollection.get(i)));
        }
        return transformedItems;
    }

    @Override
    public PASettingsModel apply(@NonNull PASettingsEntity paSettingsEntity) throws Exception {
        return transform(paSettingsEntity);
    }
}
