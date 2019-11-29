package ua.com.crosp.solutions.hearthealthmonitor.common.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


/**
 * Created by user on 05.10.16.
 */

public class ECGViewOld extends SurfaceView implements SurfaceHolder.Callback {
    public static double[] data = new double[500];
    public static int viewPosition = 0;

    private DrawThread drawThread;

    public ECGViewOld(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    public ECGViewOld(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
    }

    public ECGViewOld(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        drawThread = new DrawThread(getHolder(), getResources());
        drawThread.setRunning(true);
        drawThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        // завершаем работу потока
        drawThread.setRunning(false);
        while (retry) {
            try {
                drawThread.join();
                retry = false;
            } catch (InterruptedException e) {
                // если не получилось, то будем пытаться еще и еще
            }
        }
    }
}

class DrawThread extends Thread {
    private boolean runFlag = false;
    private SurfaceHolder surfaceHolder;
    private Bitmap picture;
    private Matrix matrix;
    private long prevTime;

    public DrawThread(SurfaceHolder surfaceHolder, Resources resources) {
        this.surfaceHolder = surfaceHolder;

        // сохраняем текущее время
        prevTime = System.currentTimeMillis();
    }

    public void setRunning(boolean run) {
        runFlag = run;
    }

    @Override
    public void run() {
        Canvas canvas;
        double miny;
        double maxy;
        float x0 = 0;
        float x1 = 0;
        float y0 = 0;
        float y1 = 0;

        while (runFlag) {
            long now = System.currentTimeMillis();
            long elapsedTime = now - prevTime;
            if (elapsedTime > 30) {
                prevTime = now;
                canvas = null;
                try {
                    canvas = surfaceHolder.lockCanvas(null);
                    synchronized (surfaceHolder) {
                        canvas.drawColor(Color.WHITE);

                        // Calc y scale factor
                        miny = 0;
                        maxy = 0;
                        for (int i = 0; i < surfaceHolder.getSurfaceFrame().width(); i++) {
                            int j = i / 2 + ECGViewOld.viewPosition;
                            if ((j >= 0) && (j < ECGViewOld.data.length)) {
                                if (miny == maxy) {
                                    miny = ECGViewOld.data[j] - 1;
                                    maxy = ECGViewOld.data[j] + 1;
                                }
                                if (ECGViewOld.data[j] > maxy) maxy = ECGViewOld.data[j];
                                if (ECGViewOld.data[j] < miny) miny = ECGViewOld.data[j];
                            }
                        }
                        Paint paint = new Paint();
                        paint.setAntiAlias(true);
                        int j = ECGViewOld.viewPosition;
                        for (int i = 0; i < ECGViewOld.data.length - 1; i++) {
                            x0 = x1;
                            y0 = y1;
                            x1 = (float) (1.0 * i * canvas.getWidth() / ECGViewOld.data.length);
                            y1 = (float) (-ECGViewOld.data[j] * canvas.getHeight() / ((maxy - miny) * 4) + canvas.getHeight() / 2);

                            // Draw original data
                            if (i > 0) {
                                paint.setColor(Color.BLACK);
                                canvas.drawLine(x0, y0, x1, y1, paint);
                            }
                            j++;
                            if (j == ECGViewOld.data.length) {
                                j = 0;
                            }

                        }
                    }
                } catch (Exception E) {

                } finally {
                    if (canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }

        }
    }
}