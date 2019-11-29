package ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.presentation.mappers;

import ua.com.crosp.solutions.hearthealthmonitor.base.presentation.BaseMapper;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.data.entity.ModeItem;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.presentation.presenter.model.ModeListItem;

/**
 * Created by Alexander Molochko on 2/13/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class ModeItemMapper extends BaseMapper<ModeItem, ModeListItem> {
    @Override
    public ModeListItem transform(ModeItem inputItem) {
        return new ModeListItem(inputItem.getId(), inputItem.getTitle(), inputItem.getDescription(), inputItem.getIcon(), inputItem.getDescriptionPictureUrl());
    }

}
