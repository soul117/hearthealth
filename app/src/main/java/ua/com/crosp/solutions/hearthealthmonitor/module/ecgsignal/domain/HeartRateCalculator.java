package ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.domain;

/**
 * Created by Alexander Molochko on 11/30/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class HeartRateCalculator {
    private static final long RR_INTERVAL_UPPER_BOUND = 1300;
    private static final long RR_INTERVAL_LOWER_BOUND = 500;
    public static final int SECONDS_IN_MINUTE = 60;
    public static final int MS_IN_SECOND = 1000;
    private RPeakDetectionInfo.List mRPeakDectionInfoList = new RPeakDetectionInfo.List();

    public void onRPeakDetected(RPeakDetectionInfo rPeakDectionInfo) {
        this.mRPeakDectionInfoList.add(rPeakDectionInfo);
    }

    public int getAverageHeartBeatRate() {
        return calculateAverageHeartBeatRate();
    }

    public int getRecentHeartBeatRate() {
        return calculateRecentHeartBeatRate();
    }

    private int calculateRecentHeartBeatRate() {
        int detectionsCount = mRPeakDectionInfoList.size();
        if (detectionsCount > 2) {
            int offset = 1;
            long lastValue = 0;
            long preLastValue = 0;
            long interval = lastValue - preLastValue;
            while (offset < detectionsCount && !isValidRRInterval(interval)) {
                offset++;
                lastValue = mRPeakDectionInfoList.get(detectionsCount - offset).timeDetectedAt;
                preLastValue = mRPeakDectionInfoList.get(detectionsCount - (offset + 1)).timeDetectedAt;
                interval = lastValue - preLastValue;
            }
            return convertRRToHeartRate(interval);
        } else {
            return 0;
        }
    }

    private int calculateAverageHeartBeatRate() {
        long rrIntervalSum = 0L;
        int validIntervals = 0;
        for (int i = 1; i < mRPeakDectionInfoList.size(); i++) {
            // rr_interval = x[n] - x[n-1]
            long rrInterval = mRPeakDectionInfoList.get(i).timeDetectedAt - mRPeakDectionInfoList.get(i - 1).timeDetectedAt;
            if (isValidRRInterval(rrInterval)) {
                rrIntervalSum += rrInterval;
                validIntervals++;
            }
        }
        return validIntervals > 0 ? convertRRToHeartRate(rrIntervalSum / validIntervals) : 0;
    }

    private int convertRRToHeartRate(long rrInterval) {
        float beatPerSecond = MS_IN_SECOND / (float) rrInterval;
        return (int) (beatPerSecond * SECONDS_IN_MINUTE);
    }

    private boolean isValidRRInterval(long interval) {
        return interval >= RR_INTERVAL_LOWER_BOUND && interval <= RR_INTERVAL_UPPER_BOUND;
    }

}
