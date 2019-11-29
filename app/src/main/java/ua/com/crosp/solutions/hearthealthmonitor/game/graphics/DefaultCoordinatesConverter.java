package ua.com.crosp.solutions.hearthealthmonitor.game.graphics;

import ua.com.crosp.solutions.hearthealthmonitor.game.engine.AbstractCoordinatesConverter;
import ua.com.crosp.solutions.hearthealthmonitor.game.model.Point2d;

/**
 * Created by Alexander Molochko on 10/26/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class DefaultCoordinatesConverter implements AbstractCoordinatesConverter {
    @Override
    public Point2d convertPointToRealPosition(Point2d point2d) {
        return point2d;
    }

    @Override
    public float convertXPointToRealPosition(float xAbstract) {
        return xAbstract;
    }

    @Override
    public float convertYPointToRealPosition(float yAbstract) {
        return yAbstract;
    }

    @Override
    public float convertYRealToAbstract(float yReal) {
        return yReal;
    }

    @Override
    public float convertXRealToAbstract(float xReal) {
        return xReal;
    }
}
