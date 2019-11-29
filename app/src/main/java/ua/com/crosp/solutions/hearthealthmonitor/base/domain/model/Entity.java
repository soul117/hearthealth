package ua.com.crosp.solutions.hearthealthmonitor.base.domain.model;

import ua.com.crosp.solutions.hearthealthmonitor.base.domain.valueobject.EntityId;

/**
 * Created by Alexander Molochko on 10/14/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class Entity {
    private EntityId mEntityId;

    public EntityId getEntityId() {
        return mEntityId;
    }

    public void setEntityId(EntityId entityId) {
        mEntityId = entityId;
    }

    @Override
    public String toString() {
        return mEntityId.toString();
    }
}
