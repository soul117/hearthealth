package ua.com.crosp.solutions.hearthealthmonitor.module.experimentresults.presentation.view.viewmodel;

import com.scichart.core.model.DoubleValues;
import com.scichart.core.model.ShortValues;

/**
 * Created by Alexander Molochko on 10/31/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class EcgChartAudioData {
    private DoubleValues mTimeValues;
    private ShortValues mEcgValues;
    public static final double TIME_INCREMENT = 0.4f;

    public EcgChartAudioData() {
        mTimeValues = new DoubleValues();
        mEcgValues = new ShortValues();
    }

    public static EcgChartAudioData fromRawData(EcgRawAudioData inputItem) {
        EcgChartAudioData data = new EcgChartAudioData();
        try {

            DoubleValues timeValues = data.mTimeValues;
            ShortValues ecgValues = data.mEcgValues;
            double time = 0;
            int[] rawValues = inputItem.getFirstChannelData();
            short[] tempShortArray = new short[2];
            for (int i = 0; i < rawValues.length; i++) {
                ecgValues.add((short) rawValues[i]);
            /*intToShort(rawValues[i], tempShortArray);
            ecgValues.add(tempShortArray[0]);
            ecgValues.add(tempShortArray[1]);
            // Repeat twice cause int = 2 shorts
            time += TIME_INCREMENT;
            timeValues.add(time);
            time += TIME_INCREMENT;
            timeValues.add(time);*/
                time += TIME_INCREMENT;
                timeValues.add(time);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            return data;
        }
    }

    public static void intToShort(int value, short[] shorts) {
        shorts[1] = (short) (value >> 16 & 0xff);
        shorts[0] = (short) (value & 0xff);
    }

    public DoubleValues getTimeValues() {
        return mTimeValues;
    }

    public void setTimeValues(DoubleValues timeValues) {
        mTimeValues = timeValues;
    }

    public ShortValues getEcgValues() {
        return mEcgValues;
    }

    public void setEcgValues(ShortValues ecgValues) {
        mEcgValues = ecgValues;
    }
}
