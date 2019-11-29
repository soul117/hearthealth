package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.domain.game.model;

import ua.com.crosp.solutions.hearthealthmonitor.game.graphics.contract.GraphicsRenderer;
import ua.com.crosp.solutions.hearthealthmonitor.game.model.Point2d;
import ua.com.crosp.solutions.hearthealthmonitor.game.model.Rectangle2d;

/**
 * Created by Alexander Molochko on 2/18/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class Body2d extends Rectangle2d {

    public Body2d(float left, float top, float right, float bottom) {
        super(left, top, right, bottom);
    }

    @Override
    public void draw(GraphicsRenderer renderer) {
        renderer.drawRect(getLeft(), getTop(), getRight(), getBottom(), getColor());
    }


    public boolean isBodyBellowPoint(Point2d point2d) {
        return getBottom() >= point2d.y;
    }

    public boolean isBodyAbovePoint(Point2d point2d) {
        return getTop() <= point2d.y;
    }
}
