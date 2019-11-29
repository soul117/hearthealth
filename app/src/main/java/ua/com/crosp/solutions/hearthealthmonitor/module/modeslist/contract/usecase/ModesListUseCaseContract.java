package ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.contract.usecase;

import java.util.List;

import io.reactivex.Single;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.usecase.BaseSingleUseCaseContractInterface;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.data.entity.ModeItem;

/**
 * Created by Alexander Molochko on 1/8/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public interface ModesListUseCaseContract extends BaseSingleUseCaseContractInterface<Void, ModeItem.List> {
    public Single<List<ModeItem>> getModesList();
}
