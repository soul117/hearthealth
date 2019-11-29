package ua.com.crosp.solutions.hearthealthmonitor.infrastructure.contract.permissions;

import io.reactivex.Observable;

/**
 * Created by Alexander Molochko on 11/15/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public interface PermissionsHandlerContract {

    Observable<Boolean> requestPermission(String... permissions);

    Observable<Boolean> requestPermission(String permissionString);
}
