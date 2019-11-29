package ua.com.crosp.solutions.hearthealthmonitor.configuration;

import ua.com.crosp.solutions.hearthealthmonitor.configuration.base.BaseConfiguration;

/**
 * Created by Alexander Molochko on 10/28/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class DateTimeConfiguration implements BaseConfiguration {
    public static final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";
    public static final String DEFAULT_DATE_TIME_FORMAT = "dd/MM/yyyy hh:mm:ss";
    public static final String DEFAULT_DATE_TIME_FILE_FORMAT = "dd_MM_yyyy__hh_mm_ss";
    public static final String DEFAULT_TIME_ZONE = "UTC+02:00";
    public static final String DEFAULT_TIME_FORMAT = "%s:%s:%s";

}
