package ua.com.crosp.solutions.hearthealthmonitor.game.graphics.contract;

import android.graphics.Rect;

import ua.com.crosp.solutions.hearthealthmonitor.game.engine.AbstractCoordinatesConverter;

public interface GraphicsRenderer {
    public void setCoordinatesConverter(AbstractCoordinatesConverter converter);

    public AbstractCoordinatesConverter getCoordinatesConverter();

    public void clear(int color);

    public void drawPixel(float x, float y, int color);

    public void drawLine(float startX, float startY, float stopX, float stopY, int color);

    public void drawLine(float startX, float startY, float stopX, float stopY, int color, float width);

    public void drawLineRounded(float startX, float startY, float stopX, float stopY, int color, float width);

    public void drawCircle(float x, float y, float radius, int color);

    public void drawOval(float left, float top, float right, float bottom, int color);

    public void drawRect(float left, float top, float right, float bottom, int color);

    public void drawImage(float left, float top, float right, float bottom, int imageSource);

    public void drawRoundedRect(float left, float top, float right, float bottom, float rX, float rY, int color);

    void drawText(String text, float x, float y, int color);

    void drawText(String text, float x, float y, DrawingPaint textPaint);

    void drawText(String text, float x, float y, int color, float textSize);

    public int getCanvasWidth();

    public int getCanvasHeight();

    public void setCurrentCanvas(Object canvas);

    void getClipBounds(Rect rect);

    void drawImageColored(float left, float top, float right, float bottom, int drawableRes, int color);
}
