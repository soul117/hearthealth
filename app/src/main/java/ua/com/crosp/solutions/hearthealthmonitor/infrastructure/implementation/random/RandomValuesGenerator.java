package ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.random;

import java.util.UUID;

import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.random.RandomValuesGeneratorContract;

/**
 * Created by Alexander Molochko on 10/15/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class RandomValuesGenerator implements RandomValuesGeneratorContract {
    public String generateRandomUUID() {
        return UUID.randomUUID().toString();
    }
}
