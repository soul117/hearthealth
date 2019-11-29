package ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.mapper.xml;


import android.content.res.AssetManager;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.transform.RegistryMatcher;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.mapper.XmlMapperContract;

/**
 * Created by Alexander Molochko on 4/23/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class XmlMapper implements XmlMapperContract {

    public static final String BIOSS_DATE_FORMAT = "yyyy-MM-dd'T'hh:mm:ss'Z'";
    private final AssetManager mAssetManager;
    private Serializer mXmlSerializer;
    private RegistryMatcher mRegistryMatcher;

    public XmlMapper(AssetManager assetManager) {
        mAssetManager = assetManager;
        initCustomRegistryMatchers();
        mXmlSerializer = new Persister(mRegistryMatcher);
    }

    private void initCustomRegistryMatchers() {
        mRegistryMatcher = new RegistryMatcher();
        // Date format used in fil
        DateFormat format = new SimpleDateFormat(BIOSS_DATE_FORMAT, Locale.US);
        mRegistryMatcher.bind(Date.class, new DateFormatTransformer(format));

    }

    @Override
    public <T> String convertToXmlString(T objectToConvert) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        String outputString = "";
        try {
            mXmlSerializer.write(objectToConvert, byteArrayOutputStream);
            outputString = new String(byteArrayOutputStream.toByteArray(), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outputString;
    }


    @Override
    public <T> T convertFromXmlString(String xmlFileContent, Class<T> objectClass) {
        try {
            return mXmlSerializer.read(objectClass, xmlFileContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> T convertFromXmlFile(File xmlFile, Class<T> objectClass) {
        try {
            return mXmlSerializer.read(objectClass, xmlFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> T convertFromXmlFromAssets(String xmlFilePath, Class<T> objectClass) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(mAssetManager.open(xmlFilePath)));

            // do reading, usually loop until end of file reading
            return mXmlSerializer.read(objectClass, reader);
        } catch (Exception e) {
            e.printStackTrace();
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
        return null;
    }

}
