package ua.com.crosp.solutions.hearthealthmonitor.base.view;

/**
 * Created by Alexander Molochko on 12/18/16.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

public abstract class BaseGameFragment extends BaseRenderFragment {
    //================================================================================
    // View implementation
    //================================================================================

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showErrorMessage(int messageStringId) {

    }

    public long getIntegerById(int id) {
        return getResources().getInteger(id);
    }

    @Override
    public void showSuccessMessage(int messageStringId) {

    }
}
