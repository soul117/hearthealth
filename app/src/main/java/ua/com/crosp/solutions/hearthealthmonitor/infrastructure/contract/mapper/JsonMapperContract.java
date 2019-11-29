package ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.mapper;

/**
 * Created by Alexander Molochko on 4/23/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public interface JsonMapperContract {
    abstract <T> String convertToJson(T objectToConvert);

    public <T> T convertFromJson(String jsonRepresentation, Class<T> objectClass);
}