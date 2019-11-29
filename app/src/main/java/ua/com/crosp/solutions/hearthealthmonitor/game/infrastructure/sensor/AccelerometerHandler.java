package ua.com.crosp.solutions.hearthealthmonitor.game.infrastructure.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class AccelerometerHandler implements SensorEventListener {
    float mAccelX;
    float mAccelY;
    float mAccelZ;

    public AccelerometerHandler(Context context) {
        SensorManager manager = (SensorManager) context
                .getSystemService(Context.SENSOR_SERVICE);
        if (manager.getSensorList(Sensor.TYPE_ACCELEROMETER).size() != 0) {
            Sensor accelerometer = manager.getSensorList(
                    Sensor.TYPE_ACCELEROMETER).get(0);
            manager.registerListener(this, accelerometer,
                    SensorManager.SENSOR_DELAY_GAME);
        }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // nothing to do here
    }

    public void onSensorChanged(SensorEvent event) {
        mAccelX = event.values[0];
        mAccelY = event.values[1];
        mAccelZ = event.values[2];
    }

    public float getAccelX() {
        return mAccelX;
    }

    public float getAccelY() {
        return mAccelY;
    }

    public float getAccelZ() {
        return mAccelZ;
    }
}
