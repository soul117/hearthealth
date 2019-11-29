package ua.com.crosp.solutions.hearthealthmonitor.game.infrastructure.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class CompassHandler implements SensorEventListener {
    float mYaw;
    float mPitch;
    float mRoll;

    public CompassHandler(Context context) {
        SensorManager manager = (SensorManager) context
                .getSystemService(Context.SENSOR_SERVICE);
        if (manager.getSensorList(Sensor.TYPE_ORIENTATION).size() != 0) {
            Sensor compass = manager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
            manager.registerListener(this, compass,
                    SensorManager.SENSOR_DELAY_GAME);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // nothing to do here
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        mYaw = event.values[0];
        mPitch = event.values[1];
        mRoll = event.values[2];
    }

    public float getYaw() {
        return mYaw;
    }

    public float getPitch() {
        return mPitch;
    }

    public float getRoll() {
        return mRoll;
    }
}
