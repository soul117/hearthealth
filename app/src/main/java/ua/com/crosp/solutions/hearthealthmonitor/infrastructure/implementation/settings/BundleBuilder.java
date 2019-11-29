package ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.settings;

import android.os.Bundle;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Alexander Molochko on 4/28/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class BundleBuilder {
    private Bundle mBundle;

    public BundleBuilder() {
        mBundle = new Bundle();
    }

    public BundleBuilder putInt(String key, int value) {
        mBundle.putInt(key, value);
        return this;
    }

    public BundleBuilder putString(String key, String value) {
        mBundle.putString(key, value);
        return this;
    }

    public BundleBuilder putLong(String key, long value) {
        mBundle.putLong(key, value);
        return this;
    }

    public BundleBuilder putFloat(String key, float value) {
        mBundle.putFloat(key, value);
        return this;
    }

    public BundleBuilder putDouble(String key, double value) {
        mBundle.putDouble(key, value);
        return this;
    }

    public BundleBuilder putSerializable(String key, Serializable value) {
        mBundle.putSerializable(key, value);
        return this;
    }

    public BundleBuilder putStringArray(String key, ArrayList<String> value) {
        mBundle.putStringArrayList(key, value);
        return this;
    }

    public Bundle build() {
        return mBundle;
    }
}
