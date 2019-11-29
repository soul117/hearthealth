package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.contract.view;

import ua.com.crosp.solutions.hearthealthmonitor.game.graphics.contract.GraphicsRenderer;
import ua.com.crosp.solutions.hearthealthmonitor.game.model.Point2d;

/**
 * Created by Alexander Molochko on 2/20/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public interface PAViewContract {
    public void onGameStarted();

    public void onGameStopped();

    public GraphicsRenderer prepareAndGetGraphicsRenderer();

    public void onDrawingFinished();

    public Point2d getCanvasSize();

}
