package ua.com.crosp.solutions.hearthealthmonitor.base.mapper;

/**
 * Created by Alexander Molochko on 4/21/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class MapperConstants {
    private static final String PREFIX = "mapper";
    private static final String DELIMITER = ".";
    public static final String PA_SETTINGS_E2M = PREFIX + DELIMITER + "pa" + DELIMITER + "settings--entity-to-model";
    public static final String PE_SETTINGS_E2M = PREFIX + DELIMITER + "pe" + DELIMITER + "settings--entity-to-model";
    public static final String REST_SETTINGS_E2M = PREFIX + DELIMITER + "rest" + DELIMITER + "settings--entity-to-model";
    public static final String PA_SETTINGS_M2E = PREFIX + DELIMITER + "pa" + DELIMITER + "settings--model-to-entity";
    public static final String PE_SETTINGS_M2E = PREFIX + DELIMITER + "pe" + DELIMITER + "settings--model-to-entity";
    public static final String REST_SETTINGS_M2E = PREFIX + DELIMITER + "rest" + DELIMITER + "settings--model-to-entity";
    public static final String EXPERIMENT_MODE = PREFIX + DELIMITER + "mode" + DELIMITER + "mode_item";
}
