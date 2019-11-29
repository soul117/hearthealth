package ua.com.crosp.solutions.hearthealthmonitor.base.presentation;

import ua.com.crosp.solutions.hearthealthmonitor.base.routing.interfaces.BaseRouter;
import ua.com.crosp.solutions.hearthealthmonitor.base.view.BaseView;

/**
 * Created by Alexander Molochko on 12/18/16.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

public abstract class BaseAppPresenter<V extends BaseView, R extends BaseRouter> {
    private V mView;
    private R mRouter;

    public V getView() {
        return mView;
    }

    public void setView(V view) {
        mView = view;
    }

    public R getRouter() {
        return mRouter;
    }

    public void setRouter(R router) {
        mRouter = router;
    }

    public void start() {
    }


    public void pause() {

    }

    public void destroy() {

    }

    public void onViewDestroyed() {

    }

    public void onViewStop() {

    }

    public void onViewPause() {

    }

    public void onViewResume() {

    }

}