package ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.mapper;

import java.io.File;

/**
 * Created by Alexander Molochko on 4/23/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public interface XmlMapperContract {

    <T> String convertToXmlString(T objectToConvert);

    public <T> T convertFromXmlString(String xmlStringRerpesentation, Class<T> objectClass);

    <T> T convertFromXmlFile(File xmlFile, Class<T> objectClass);

    <T> T convertFromXmlFromAssets(String xmlFilePath, Class<T> objectClass);
}