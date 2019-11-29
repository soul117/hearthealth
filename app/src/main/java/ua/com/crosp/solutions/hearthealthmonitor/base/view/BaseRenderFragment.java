package ua.com.crosp.solutions.hearthealthmonitor.base.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

/**
 * Created by Alexander Molochko on 12/18/16.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

public abstract class BaseRenderFragment extends BaseDIFragment {

    // Variables
    protected Context mContext;

    //================================================================================
    // Template methods
    //================================================================================
    public abstract SurfaceView provideSurfaceView();

    //================================================================================
    // Lifecycle
    //================================================================================
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = getContext();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return provideSurfaceView();
    }
    //================================================================================
    // View implementation
    //================================================================================

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    public String getStringByResourceId(int stringId) {
        return getString(stringId);
    }

}
