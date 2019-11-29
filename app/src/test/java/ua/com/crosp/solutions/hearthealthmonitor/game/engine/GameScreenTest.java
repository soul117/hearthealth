package ua.com.crosp.solutions.hearthealthmonitor.game.engine;

import junit.framework.Assert;

import org.junit.Test;

import ua.com.crosp.solutions.hearthealthmonitor.game.model.Point2d;
import ua.com.crosp.solutions.hearthealthmonitor.game.valueobject.ScreenAspectCoefficient;
import ua.com.crosp.solutions.hearthealthmonitor.game.valueobject.ScreenAspectRatio;
import ua.com.crosp.solutions.hearthealthmonitor.game.valueobject.ScreenSize;
import ua.com.crosp.solutions.hearthealthmonitor.module.motivationmode.psychoemotional.game.model.valueobject.CanvasSize;

/**
 * Created by Alexander Molochko on 10/24/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
public class GameScreenTest {
    @Test
    public void updateFromCanvasSize() throws Exception {

    }

    @Test
    public void screenSizeRatio_CanvasSizeProvided_ShouldBeCalculated() throws Exception {
        // Arrange
        CanvasSize canvasSize = new CanvasSize(480, 800);
        GameScreen gameScreen = new GameScreen(canvasSize);

        // Act
        ScreenAspectRatio screenAspectRatio = gameScreen.getScreenAspectRatio();

        // Assert

        Assert.assertEquals(3, screenAspectRatio.getWidthRatio());
        Assert.assertEquals(5, screenAspectRatio.getHeightRatio());
    }

    @Test
    public void abstractScreenSize_DefaultCoefficientAndCanvasSizeProvided_ShouldBeCalculated() throws Exception {
        // Arrange
        CanvasSize canvasSize = new CanvasSize(480, 800);
        GameScreen gameScreen = new GameScreen(canvasSize);

        // Act
        ScreenSize abstractScreenSize = gameScreen.getAbstractScreenSize();

        // Assert

        Assert.assertEquals(3 * gameScreen.getScreenCoefficient(), abstractScreenSize.getWidth());
        Assert.assertEquals(5 * gameScreen.getScreenCoefficient(), abstractScreenSize.getHeight());
    }

    @Test
    public void screenCoefficient_DefaultCoefficientAndCanvasSizeProvided_ShouldBeCalculated() throws Exception {
        // Arrange
        float width = 480;
        float height = 800;
        CanvasSize canvasSize = new CanvasSize((long) width, (long) height);
        GameScreen gameScreen = new GameScreen(canvasSize);

        // Act
        ScreenAspectCoefficient screenAspectCoefficient = gameScreen.getScreenAspectCoefficient();

        // Assert

        Assert.assertEquals(width / (3 * 100f), screenAspectCoefficient.getWidthCoefficient());
        Assert.assertEquals(height / (5 * 100f), screenAspectCoefficient.getHeightCoefficient());
    }

    @Test
    public void realScreenPoint_AbstractPointProvided_ShouldConvertPointToReal() throws Exception {
        // Arrange
        float width = 480;
        float height = 800;
        CanvasSize canvasSize = new CanvasSize((long) width, (long) height);
        GameScreen gameScreen = new GameScreen(canvasSize);
        Point2d abstractPoint = new Point2d((3 * 100f) / 2, (5 * 100f) / 2);

        // Act
        Point2d realPoint = gameScreen.convertPointToRealPosition(abstractPoint);

        // Assert

        Assert.assertEquals(width / 2, realPoint.x);
        Assert.assertEquals(height / 2, realPoint.y);
    }

    @Test
    public void getHeight() throws Exception {

    }

    @Test
    public void convertXPointToRealPosition() throws Exception {

    }

    @Test
    public void convertYPointToRealPosition() throws Exception {

    }

    @Test
    public void calculateAbstractScreenSize() throws Exception {

    }

    @Test
    public void calculateScreenAspectCoefficient() throws Exception {

    }

    @Test
    public void calculateScreenAspectRatio() throws Exception {

    }

    @Test
    public void getScreenCoefficient() throws Exception {

    }

    @Test
    public void setScreenCoefficient() throws Exception {

    }

}