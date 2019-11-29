package ua.com.crosp.solutions.hearthealthmonitor.base.domain.valueobject;

import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util.RandomUtils;

/**
 * Created by Alexander Molochko on 10/14/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class EntityId {

    public static final EntityId INVALID_ENTITY_ID = new EntityId(-1L);


    private Long mId;

    public EntityId(Long id) {
        mId = id;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    @Override
    public String toString() {
        return String.valueOf(mId);
    }

    public static EntityId generateUniqueId() {
        return new EntityId(RandomUtils.generateUniqueLongId());
    }

    public static boolean isValidId(long id) {
        return id > 0;
    }
}
