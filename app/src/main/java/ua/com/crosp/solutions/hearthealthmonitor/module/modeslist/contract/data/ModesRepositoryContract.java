package ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.contract.data;

/**
 * Created by Alexander Molochko on 1/22/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

import java.util.List;

import io.reactivex.Single;
import ua.com.crosp.solutions.hearthealthmonitor.module.modeslist.data.entity.ModeItem;

/**
 * Interface that represents a Repository for getting {@link ModeItem} related data.
 */
public interface ModesRepositoryContract {
    /**
     * Get an {@link io.reactivex.Observable} which will emit a ModelList of {@link ModeItem}.
     */
    Single<List<ModeItem>> getAllModes();

    /**
     * Get an {@link io.reactivex.Observable} which will emit a {@link ModeItem}.
     *
     * @param userId The user id used to retrieve user data.
     */
    Single<ModeItem> getModeById(final int userId);
}
