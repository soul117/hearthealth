package ua.com.crosp.solutions.hearthealthmonitor.common.view;

import android.view.View;
import android.widget.Checkable;

/**
 * Created by Alexander Molochko on 4/29/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public interface RadioCheckable extends Checkable {
    void addOnCheckChangeListener(OnCheckedChangeListener onCheckedChangeListener);

    void removeOnCheckChangeListener(OnCheckedChangeListener onCheckedChangeListener);

    void setCheckedState();

    void setNormalState();

    public static interface OnCheckedChangeListener {
        void onCheckedChanged(View buttonView, boolean isChecked);
    }
}
