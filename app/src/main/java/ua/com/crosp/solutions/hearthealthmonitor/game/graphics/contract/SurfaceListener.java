package ua.com.crosp.solutions.hearthealthmonitor.game.graphics.contract;

/**
 * Created by Alexander Molochko on 2/21/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
public interface SurfaceListener {
    void surfaceCreated();

    void surfaceChanged(int format, int width, int height);

    void surfaceDestroyed();
}
