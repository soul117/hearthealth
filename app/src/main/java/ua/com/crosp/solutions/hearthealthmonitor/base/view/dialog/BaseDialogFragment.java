package ua.com.crosp.solutions.hearthealthmonitor.base.view.dialog;

import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;

import butterknife.ButterKnife;
import ua.com.crosp.solutions.hearthealthmonitor.di.contract.ProvidesComponent;
import ua.com.crosp.solutions.hearthealthmonitor.di.contract.ProvidesMultipleComponents;

/**
 * Created by Alexander Molochko on 10/18/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class BaseDialogFragment extends DialogFragment implements DialogInterface {
    public BaseDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Injecting dependencies

    }

    public void bindViews() {
        ButterKnife.bind(this, getView());
    }

    public <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((ProvidesComponent<C>) getActivity()).getComponent());
    }

    public <C> C getComponentByType(Class<C> componentType) {
        return componentType.cast(((ProvidesMultipleComponents) getActivity()).getComponentByType(componentType));
    }


    @Override
    public Context getContext() {
        return super.getActivity();
    }

    @Override
    public void cancel() {
        getDialog().cancel();
    }

    public String getStringByResourceId(int stringId) {
        return getString(stringId);
    }

    public void showErrorMessage(int messageStringId) {

    }

    public void showSuccessMessage(int messageStringId) {

    }

    public long getIntegerById(int id) {
        return getResources().getInteger(id);
    }


}
