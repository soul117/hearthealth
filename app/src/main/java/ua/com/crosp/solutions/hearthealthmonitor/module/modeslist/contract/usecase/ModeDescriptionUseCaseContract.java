package ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.contract.usecase;

import io.reactivex.Single;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.data.entity.ModeItem;

/**
 * Created by Alexander Molochko on 1/8/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public interface ModeDescriptionUseCaseContract {
    public Single<ModeItem> getModeDescriptionWithId(int id);
}
