package ua.com.crosp.solutions.hearthealthmonitor.game.model;

import ua.com.crosp.solutions.hearthealthmonitor.game.graphics.contract.GraphicsRenderer;

public abstract class Rectangle2d extends Shape2d {

    public Rectangle2d(float left, float top, float right, float bottom) {
        super(left, top, right, bottom);
    }

    public float getCenterX() {
        return getLeft() + getWidth() / 2;
    }

    public float getCenterY() {
        return getTop() + getHeight() / 2;
    }

    public Point2d getCenterPoint() {
        return new Point2d(getCenterX(), getCenterY());
    }

    @Override
    public void draw(GraphicsRenderer renderer) {
        renderer.drawRect(getLeft(), getTop(), getRight(), getBottom(), getColor());
    }

    public float getPerimiter() {
        return (getWidth() + getHeight()) * 2;
    }
}
