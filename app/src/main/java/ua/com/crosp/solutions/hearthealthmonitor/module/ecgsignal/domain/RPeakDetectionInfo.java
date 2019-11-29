package ua.com.crosp.solutions.hearthealthmonitor.module.ecgsignal.domain;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;

import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util.TimeUtils;

public class RPeakDetectionInfo {
    public long sampleDetectedAt;
    public long timeDetectedAt;

    public RPeakDetectionInfo(long sampleDetectedAt) {
        this.sampleDetectedAt = sampleDetectedAt;
        this.timeDetectedAt = TimeUtils.getCurrentTimeMillis();
    }

    public RPeakDetectionInfo(long sampleDetectedAt, long timeDetectedAt) {
        this.sampleDetectedAt = sampleDetectedAt;
        this.timeDetectedAt = timeDetectedAt;
    }

    public static class List extends ArrayList<RPeakDetectionInfo> {
        public List(int initialCapacity) {
            super(initialCapacity);
        }

        public List() {
        }

        public java.util.List<Long> convertToSamplePositionList() {
            java.util.List<Long> samplePositions = new ArrayList<>();
            for (RPeakDetectionInfo info : this) {
                samplePositions.add(info.sampleDetectedAt);
            }
            return samplePositions;
        }

        public List(@NonNull Collection<? extends RPeakDetectionInfo> c) {
            super(c);
        }
    }
}