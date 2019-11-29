package ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.resource;

import android.content.Context;

import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.resource.ResourceProviderContract;

/**
 * Created by Alexander Molochko on 12/21/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class AndroidResourceProvider implements ResourceProviderContract {
    private Context mContext;

    public AndroidResourceProvider(Context context) {
        mContext = context;
    }

    @Override
    public String getStringById(int id) {
        return mContext.getString(id);
    }

    @Override
    public int getColorById(int id) {
        return mContext.getResources().getColor(id);
    }
}
