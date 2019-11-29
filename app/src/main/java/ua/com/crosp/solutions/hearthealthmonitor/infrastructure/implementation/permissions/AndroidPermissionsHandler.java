package ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.permissions;

import android.app.Activity;

import com.tbruyelle.rxpermissions2.RxPermissions;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.permissions.PermissionsHandlerContract;

/**
 * Created by Alexander Molochko on 11/15/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class AndroidPermissionsHandler implements PermissionsHandlerContract {
    @Inject
    Activity mActivity;

    public AndroidPermissionsHandler(Activity activity) {
        mActivity = activity;
    }

    @Override
    public Observable<Boolean> requestPermission(String... permissions) {
        RxPermissions rxPermissions = new RxPermissions(mActivity);
        return rxPermissions
                .request(permissions)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Boolean> requestPermission(String permissionString) {
        RxPermissions rxPermissions = new RxPermissions(mActivity);
        return rxPermissions
                .request(permissionString)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread());
    }
}
