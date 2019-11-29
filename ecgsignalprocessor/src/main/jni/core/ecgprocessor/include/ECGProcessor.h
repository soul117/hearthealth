//
// Created by Alexander Molochko on 11/26/17.
//

#ifndef HEARTHEALTHMONITOR_ECGPROCESSOR_H
#define HEARTHEALTHMONITOR_ECGPROCESSOR_H


static const char *const FM_DETECTOR_CONFIG_FILE_NAME = "fm_detector_config.bin";

static const char *const PROC_SELF_FILE_CMDLINE = "/proc/self/cmdline";
static const int INVALID_CONFIG_VALUE = -1;

#include <FM_Detector.h>
#include <StdVector>
#include <firpm/pm.h>

class ECGProcessor {
private:
    vectorf calculate_first_filter(FM_Detector_params_t *params);

    vectorf calculate_second_filter(FM_Detector_params_t *params);

    bool try_to_restore_filter_configuration();

    void save_current_filter_configuration();

    void init_filters();

    FM_Detector_params_t m_fm_detector_params_;
    const size_t m_block_size_;
    std::string m_sandbox_path;
    FM_Detector_t *m_fm_detector_;

public:
    ECGProcessor(FM_Detector_params_t m_fm_detector_params_, unsigned long m_block_size_,
                 std::string sandbox_path);

    int process(const short *in, float *out);

    const FM_Detector_params_t &get_detector_params() const;

    virtual ~ECGProcessor();

    const char *const get_config_file_name();
};


#endif //HEARTHEALTHMONITOR_ECGPROCESSOR_H
