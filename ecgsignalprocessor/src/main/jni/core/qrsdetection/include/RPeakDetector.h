#ifndef _RPEAK_DETECTOR_H_
#define _RPEAK_DETECTOR_H_


static const int DELAY_COEFFICIENT = 12;

#include <iostream>

struct Rpeak_Detector_params_t {
    int PD_DELAY;
    float alpha;
    float threshold;
    int blk_size;

    Rpeak_Detector_params_t() {
        blk_size = 2048;
        PD_DELAY = 735;  // Задержка срабатывания пикового детектора, samples
        alpha = 0.0006f; // Коэф. сглаживания оценок дисперсии и матожидания
        threshold = 6;   // Порог срабатывания (во сколько раз выброс R зубца должен превысить оценку дисперсии
    }
};

struct Rpeak_Detector_status_t {
    int pos;       // Номер отчета где найден R зубец
    float *z;      // Выход ECG z[blk_sz]
    float *stat2;  // Оценка дисперсии stat2[blk_sz]
};

class RPeakDetector {
public:
    RPeakDetector(int block_size, int sampling_rate, Rpeak_Detector_params_t *params = nullptr) {
        int i;

        if (params == nullptr) {
            Rpeak_Detector_params_t params_default;
            m_params = params_default;
        } else {
            m_params = *params;
        }
        m_params.blk_size = block_size;
        m_params.PD_DELAY = calculate_pd_delay(sampling_rate);
        std::cout << "------ R Peak DELAY  --------" << m_params.PD_DELAY << std::endl;
        std::cout << "------ R Peak BUFFER SIZE  --------" << m_params.blk_size << std::endl;
        mean_z = new float[m_params.blk_size];
        var_z = new float[m_params.blk_size];
        mean_z_local = 0;
        var_z_local = 0;
        count = 0;
        prev_peak_val = 0;
        prev_peak_pos = 0;
        peak_val = 0;
        peak_pos = 0;
        local_max_z = 0;
        count = 0;
        prev_peak_pos = 0;
        peak_val = 0;
        peak_pos = 0;
        global_count = 0;
        var_at_max_pos = 0;

        for (i = 0; i < m_params.blk_size; i++) {
            mean_z[i] = var_z[i] = 0;
        }
    }

    int calculate_pd_delay(int sampling_rate) {
        return sampling_rate / DELAY_COEFFICIENT;
    }

    int process(float *z, Rpeak_Detector_status_t *pstatus) {
        int i;
        int pos = -1;


        float alpha = m_params.alpha;


        for (i = 0; i < m_params.blk_size; i++) {
            float d = z[i] - mean_z_local;
            mean_z_local = alpha * d + mean_z_local;
            var_z_local = alpha * d * d + (1 - alpha) * var_z_local;
            mean_z[i] = mean_z_local;
            var_z[i] = var_z_local;
            // Remove DC
            z[i] -= mean_z_local;
        }
        for (i = 0; i < m_params.blk_size; i++) {
            if (z[i] > local_max_z) {
                count = 0;
                // Set temporary max variance and wait if it will remain the same for delay
                local_max_z = z[i];
                var_at_max_pos = var_z[i];
            } else {
                count++;
                if (count > m_params.PD_DELAY) {
                    // Check if
                    if (local_max_z * local_max_z > m_params.threshold * var_at_max_pos) {
                        pos = global_count + i - count;
                    }
                    count = 0;
                    local_max_z = 0;
                }
            }
        }
        global_count += m_params.blk_size;
        pstatus->stat2 = var_z;
        pstatus->z = nullptr;
        pstatus->pos = pos;

        return pos;
    }

    virtual ~RPeakDetector() {
        if (mean_z != nullptr) {
            delete mean_z;
            mean_z = nullptr;
        }
        if (var_z != nullptr) {
            delete var_z;
            var_z = nullptr;
        }
    }

protected:
    float mean_z_local = 0;
    float var_z_local = 0;
    int count = 0;
    float prev_peak_val = 0;
    int prev_peak_pos = 0;
    float peak_val = 0;
    int peak_pos = 0;
    float local_max_z = 0;
    int global_count = 0;
    Rpeak_Detector_params_t m_params;
    float var_at_max_pos;
    float *z_cleaned;
    float *mean_z;
    float *var_z;
};

#endif