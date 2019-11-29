package ua.com.crosp.solutions.hearthealthmonitor.game.engine;

import ua.com.crosp.solutions.hearthealthmonitor.game.model.Point2d;

/**
 * Created by Alexander Molochko on 10/26/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public interface AbstractCoordinatesConverter {
    public Point2d convertPointToRealPosition(Point2d point2d);

    public float convertXPointToRealPosition(float xAbstract);

    public float convertYPointToRealPosition(float yAbstract);

    public float convertYRealToAbstract(float yReal);

    public float convertXRealToAbstract(float xReal);
}
