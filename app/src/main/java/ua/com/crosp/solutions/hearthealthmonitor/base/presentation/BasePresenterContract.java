package ua.com.crosp.solutions.hearthealthmonitor.base.presentation;

import ua.com.crosp.solutions.hearthealthmonitor.base.routing.interfaces.BaseRouter;
import ua.com.crosp.solutions.hearthealthmonitor.base.view.BaseView;

/**
 * Created by Alexander Molochko on 2/10/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public interface BasePresenterContract<V extends BaseView, R extends BaseRouter> {
    public void start();

    public void pause();

    public void destroy();

    public V getView();

    public void setView(V view);

    public R getRouter();

    public void setRouter(R router);

    public void onViewReady();

    public void onViewDestroyed();

    void onViewStop();

    void onViewPause();

    void onViewResume();
}
