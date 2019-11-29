package ua.com.crosp.solutions.hearthealthmonitor.base.view;

import androidx.fragment.app.Fragment;

import ua.com.crosp.solutions.hearthealthmonitor.di.contract.ProvidesBaseComponent;
import ua.com.crosp.solutions.hearthealthmonitor.di.contract.ProvidesComponent;
import ua.com.crosp.solutions.hearthealthmonitor.di.contract.ProvidesMultipleComponents;

/**
 * Created by Alexander Molochko on 12/18/16.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

public abstract class BaseDIFragment extends Fragment implements BaseView {

    //================================================================================
    // DI
    //================================================================================
    // TODO get rid of component dependency from view, but unfortunately Android SDK is build in such way
    public <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((ProvidesComponent<C>) getActivity()).getComponent());
    }

    public <C> C getBaseComponent(Class<C> componentType) {
        return componentType.cast(((ProvidesBaseComponent<C>) getActivity()).getBaseActivityComponent());
    }

    public <C> C getComponentByType(Class<C> componentType) {
        return componentType.cast(((ProvidesMultipleComponents) getActivity()).getComponentByType(componentType));
    }
}
