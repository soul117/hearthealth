package ua.com.crosp.solutions.hearthealthmonitor.game.graphics;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.DisplayMetrics;

import androidx.annotation.RequiresApi;
import androidx.core.graphics.drawable.DrawableCompat;

import ua.com.crosp.solutions.hearthealthmonitor.game.engine.AbstractCoordinatesConverter;
import ua.com.crosp.solutions.hearthealthmonitor.game.graphics.contract.DrawingPaint;
import ua.com.crosp.solutions.hearthealthmonitor.game.graphics.contract.GraphicsRenderer;

public class AndroidGraphicsRenderer implements GraphicsRenderer {
    private final Context context;
    Canvas mCanvas;
    Paint mPaint = new Paint();
    RectF mSharedRect = new RectF();
    private AbstractCoordinatesConverter mCoordinatesConverter = new DefaultCoordinatesConverter();

    public AndroidGraphicsRenderer(Context context) {
        this.context = context;
    }

    @Override
    public void setCoordinatesConverter(AbstractCoordinatesConverter converter) {
        mCoordinatesConverter = converter;
    }

    @Override
    public AbstractCoordinatesConverter getCoordinatesConverter() {
        return mCoordinatesConverter;
    }

    public void clear(int color) {
        mCanvas.drawRGB((color & 0xff0000) >> 16, (color & 0xff00) >> 8,
                (color & 0xff));
    }

    @Override
    public void drawPixel(float x, float y, int color) {
        mPaint.setColor(color);
        x = mCoordinatesConverter.convertXPointToRealPosition(x);
        y = mCoordinatesConverter.convertYPointToRealPosition(y);
        mCanvas.drawPoint(x, y, mPaint);
    }

    @Override
    public void drawLine(float startX, float startY, float stopX, float stopY, int color) {
        mPaint.setColor(color);
        startX = mCoordinatesConverter.convertXPointToRealPosition(startX);
        stopX = mCoordinatesConverter.convertXPointToRealPosition(stopX);
        startY = mCoordinatesConverter.convertYPointToRealPosition(startY);
        stopY = mCoordinatesConverter.convertYPointToRealPosition(stopY);
        mCanvas.drawLine(startX, startY, stopX, stopY, mPaint);
    }

    @Override
    public void drawLine(float startX, float startY, float stopX, float stopY, int color, float width) {
        mPaint.setColor(color);
        float previousStrokeWidth = mPaint.getStrokeWidth();
        Style previousStyle = mPaint.getStyle();
        width = mCoordinatesConverter.convertXPointToRealPosition(width);
        mPaint.setStrokeWidth(width);
        mPaint.setStyle(Style.FILL_AND_STROKE);
        startX = mCoordinatesConverter.convertXPointToRealPosition(startX);
        stopX = mCoordinatesConverter.convertXPointToRealPosition(stopX);
        startY = mCoordinatesConverter.convertYPointToRealPosition(startY);
        stopY = mCoordinatesConverter.convertYPointToRealPosition(stopY);
        mCanvas.drawLine(startX, startY, stopX, stopY, mPaint);
        mPaint.setStrokeWidth(previousStrokeWidth);
        mPaint.setStyle(previousStyle);
    }

    @Override
    public void drawLineRounded(float startX, float startY, float stopX, float stopY, int color, float width) {
        Paint.Cap previousCap = mPaint.getStrokeCap();
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        this.drawLine(startX, startY, stopX, stopY, color, width);
        mPaint.setStrokeCap(previousCap);
    }

    @Override
    public void drawCircle(float cx, float cy, float radius, int color) {
        cx = mCoordinatesConverter.convertXPointToRealPosition(cx);
        cy = mCoordinatesConverter.convertYPointToRealPosition(cy);
        radius = mCoordinatesConverter.convertXPointToRealPosition(radius);
        mPaint.setColor(color);
        mCanvas.drawCircle(cx, cy, radius, mPaint);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void drawOval(float left, float top, float right, float bottom, int color) {
        left = mCoordinatesConverter.convertXPointToRealPosition(left);
        right = mCoordinatesConverter.convertXPointToRealPosition(right);
        top = mCoordinatesConverter.convertYPointToRealPosition(top);
        bottom = mCoordinatesConverter.convertYPointToRealPosition(bottom);
        mPaint.setColor(color);
        mCanvas.drawOval(left, top, right, bottom, mPaint);
    }

    @Override
    public void drawRect(float left, float top, float right, float bottom, int color) {
        left = mCoordinatesConverter.convertXPointToRealPosition(left);
        right = mCoordinatesConverter.convertXPointToRealPosition(right);
        top = mCoordinatesConverter.convertYPointToRealPosition(top);
        bottom = mCoordinatesConverter.convertYPointToRealPosition(bottom);
        mPaint.setColor(color);
        mPaint.setStyle(Style.FILL);
        mCanvas.drawRect(left, top, right, bottom, mPaint);
    }

    @Override
    public void drawImage(float left, float top, float right, float bottom, int imageSource) {
        Drawable d = context.getResources().getDrawable(imageSource, null);
        d.setBounds((int) left, (int) top, (int) right, (int) bottom);
        d.draw(mCanvas);
    }

    @Override
    public void drawRoundedRect(float left, float top, float right, float bottom, float rX, float rY, int color) {
        mPaint.setColor(color);
        mPaint.setStyle(Style.FILL);
        left = mCoordinatesConverter.convertXPointToRealPosition(left);
        right = mCoordinatesConverter.convertXPointToRealPosition(right);
        top = mCoordinatesConverter.convertYPointToRealPosition(top);
        bottom = mCoordinatesConverter.convertYPointToRealPosition(bottom);
        mSharedRect.bottom = bottom;
        mSharedRect.left = left;
        mSharedRect.right = right;
        mSharedRect.top = top;
        mCanvas.drawRoundRect(mSharedRect, rX, rY, mPaint);
    }

    @Override
    public void drawText(String text, float x, float y, int color) {
        x = mCoordinatesConverter.convertXPointToRealPosition(x);
        y = mCoordinatesConverter.convertYPointToRealPosition(y);
        mPaint.setColor(color);
        mPaint.setAntiAlias(true);
        mCanvas.drawText(text, x, y, mPaint);
    }

    @Override
    public void drawText(String text, float x, float y, DrawingPaint textPaint) {
        x = mCoordinatesConverter.convertXPointToRealPosition(x);
        y = mCoordinatesConverter.convertYPointToRealPosition(y);
        mPaint.setColor(textPaint.getColor());
        mPaint.setTextSize(textPaint.getTextSize());
        mPaint.setTextAlign(textPaint.getTextAlign());
        mCanvas.drawText(text, x, y, mPaint);
    }


    @Override
    public void drawText(String text, float x, float y, int color, float textSize) {
        x = mCoordinatesConverter.convertXPointToRealPosition(x);
        y = mCoordinatesConverter.convertYPointToRealPosition(y);
        mPaint.setColor(color);
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(textSize);
        mCanvas.drawText(text, x, y, mPaint);
    }

    @Override
    public void setCurrentCanvas(Object canvas) {
        mCanvas = (Canvas) canvas;
    }

    @Override
    public void getClipBounds(Rect rect) {
        mCanvas.getClipBounds(rect);
    }

    public static Drawable setTint(Drawable d, int color) {
        Drawable wrappedDrawable = DrawableCompat.wrap(d);
        DrawableCompat.setTint(wrappedDrawable, color);
        return wrappedDrawable;
    }

    @Override
    public void drawImageColored(float left, float top, float right, float bottom, int drawableRes, int color) {
        Drawable d = context.getResources().getDrawable(drawableRes, null);
        setTint(d, color);
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;
        d.setBounds((int) left, (int) (top), (int)(right), (int) bottom);
        d.draw(mCanvas);
    }

    public void setCurrentPaint(Paint paint) {
        mPaint = paint;
    }

    public int getCanvasWidth() {
        return mCanvas.getWidth();
    }

    public int getCanvasHeight() {
        return mCanvas.getHeight();
    }
}

