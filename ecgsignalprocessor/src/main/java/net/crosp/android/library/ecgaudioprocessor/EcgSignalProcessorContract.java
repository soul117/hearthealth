package net.crosp.android.library.ecgaudioprocessor;

/**
 * Created by Alexander Molochko on 11/26/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

interface EcgSignalProcessorContract {
    short[] demodulate(short[] readBuffer);

    int detectRPeaks(short[] result);
}
