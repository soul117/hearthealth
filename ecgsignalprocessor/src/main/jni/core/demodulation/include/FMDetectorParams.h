//
// Created by Durian Odour on 11/19/17.
//

#ifndef HEARTHEALTHMONITOR_SIGNALCONFIGURATION_H
#define HEARTHEALTHMONITOR_SIGNALCONFIGURATION_H


typedef struct {
    int sample_rate;
    int kd;
    int fc;
    int fs;
    int fs2;
    int delay;
} SignalConfiguration;

#endif //HEARTHEALTHMONITOR_SIGNALCONFIGURATION_H
