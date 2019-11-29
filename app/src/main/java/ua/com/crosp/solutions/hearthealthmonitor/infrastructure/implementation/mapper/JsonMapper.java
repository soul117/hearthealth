package ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.mapper;


import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.util.HashMap;
import java.util.Map;

import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.mapper.JsonMapperContract;

/**
 * Created by Alexander Molochko on 4/23/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class JsonMapper implements JsonMapperContract {

    private Moshi mMoshi;
    private Map<String, JsonAdapter<?>> mJsonAdapterMap = new HashMap<>();

    public JsonMapper() {
        mMoshi = new Moshi.Builder().build();

    }

    @Override
    public <T> String convertToJson(T objectToConvert) {
        String jsonObjectString = "";
        String objectClassName = objectToConvert.getClass().getName();
        try {
            JsonAdapter<T> jsonAdapter = (JsonAdapter<T>) mJsonAdapterMap.get(objectClassName);
            if (null == jsonAdapter) {
                jsonAdapter = (JsonAdapter<T>) mMoshi.adapter(objectToConvert.getClass());
                mJsonAdapterMap.put(objectClassName, jsonAdapter);
            }
            jsonObjectString = jsonAdapter.toJson(objectToConvert);
            return jsonObjectString;
        } catch (Exception ex) {
            ex.printStackTrace();
            return jsonObjectString;
        }
    }

    @Override
    public <T> T convertFromJson(String jsonRepresentation, Class<T> objectClass) {
        T object = null;
        try {
            JsonAdapter<T> jsonAdapter = (JsonAdapter<T>) mJsonAdapterMap.get(objectClass.toString());
            if (null == jsonAdapter) {
                jsonAdapter = mMoshi.adapter(objectClass);
                mJsonAdapterMap.put(objectClass.toString(), jsonAdapter);
            }
            object = jsonAdapter.fromJson(jsonRepresentation);
            return object;
        } catch (Exception ex) {
            ex.printStackTrace();
            return object;
        }
    }
}
