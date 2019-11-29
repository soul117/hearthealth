package ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.physicalactivity.domain.game.model;

import ua.com.crosp.solutions.hearthealthmonitor.game.graphics.contract.GraphicsRenderer;
import ua.com.crosp.solutions.hearthealthmonitor.game.model.Circle2d;

/**
 * Created by Alexander Molochko on 2/18/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class Head2d extends Circle2d {

    public Head2d(float centerX, float centerY, float radius) {
        super(centerX, centerY, radius);
    }

    @Override
    public void draw(GraphicsRenderer renderer) {
        renderer.drawCircle(getCenterX(), getCenterY(), getRadius(), getColor());
    }

}
