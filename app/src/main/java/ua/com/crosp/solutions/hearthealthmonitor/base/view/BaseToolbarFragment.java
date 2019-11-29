package ua.com.crosp.solutions.hearthealthmonitor.base.view;

import android.os.Bundle;
import android.view.View;

import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.common.view.ModeSettingsViewContract;

/**
 * Created by Alexander Molochko on 12/18/16.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

public abstract class BaseToolbarFragment extends BaseFragment implements ModeSettingsViewContract {
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setToolbarTitle(provideModeSettingsTitle());
    }
}
