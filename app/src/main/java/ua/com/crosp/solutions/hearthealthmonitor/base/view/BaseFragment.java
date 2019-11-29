package ua.com.crosp.solutions.hearthealthmonitor.base.view;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import butterknife.ButterKnife;
import ua.com.crosp.solutions.hearthealthmonitor.common.notification.ToastNotifications;

/**
 * Created by Alexander Molochko on 12/18/16.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

public abstract class BaseFragment extends BaseDIFragment {
    // Variables
    public Context mContext;
    private Handler mHandler;

    //================================================================================
    // Template methods
    //================================================================================
    @LayoutRes
    public abstract int getFragmentLayoutId();

    //================================================================================
    // Lifecycle
    //================================================================================
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = getContext();
        this.mHandler = new Handler();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Inject views in child fragments
        ButterKnife.bind(this, view);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(getFragmentLayoutId(), container, false);
    }
    //================================================================================
    // View implementation
    //================================================================================


    @Override
    public String getStringByResourceId(int stringId) {
        return mContext.getResources().getString(stringId);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void showErrorMessage(final int messageStringId) {
        if (isAdded()) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    ToastNotifications.showErrorMessage(getContext().getApplicationContext(), getString(messageStringId));
                }
            });
        }
    }

    @Override
    public long getIntegerById(int id) {
        return getResources().getInteger(id);
    }

    @Override
    public void showSuccessMessage(final int messageStringId) {
        if (isAdded()) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    ToastNotifications.showSuccessMessage(getContext().getApplicationContext(), getString(messageStringId));
                }
            });
        }
    }
}
