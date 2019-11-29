//
// Created by Alexander Molochko on 11/26/17.
//
#include "ECGProcessor.h"

vectorf ECGProcessor::calculate_first_filter(FM_Detector_params_t *params) {
    float fs = params->Fs;
    double fs_half = fs / 2;
    std::vector<double> f = {0, FIR1_ORDER, 600, fs_half};
    const std::vector<double> a = {1, 1, 0, 0};
    const std::vector<double> w = {0.1, 1.0};
    std::transform(f.begin(), f.end(), f.begin(),
                   std::bind2nd(std::divides<double>(), fs_half));
    PMOutput coefficients = firpm(FIR1_ORDER, f, a, w);
    vectorf coeff_result;
    for (auto const &value: coefficients.h) {
        coeff_result.push_back((float &&) value);
    }
    return coeff_result;

}

vectorf ECGProcessor::calculate_second_filter(FM_Detector_params_t *params) {
    float fs2 = params->Fs / params->Kd;
    double fs_half = fs2 / 2;
    std::vector<double> f = {0, 10, 50, fs_half};
    const std::vector<double> a = {1, 1, 0, 0};
    const std::vector<double> w = {0.1, 1.0};
    std::transform(f.begin(), f.end(), f.begin(),
                   std::bind2nd(std::divides<double>(), fs_half));
    PMOutput coefficients = firpm(FIR2_ORDER, f, a, w);
    vectorf coeff_result;
    for (auto const &value: coefficients.h) {
        coeff_result.push_back((float &&) value);
    }
    return coeff_result;
}


void ECGProcessor::init_filters() {
    this->m_fm_detector_ = new FM_Detector_t(&this->m_fm_detector_params_);
}

void print_vector(vectorf &vector, std::string m_sandbox_path) {
/*    std::cout << "Printing VECTOR ";
    std::cout << "{ ";
    for (auto i = vector.begin(); i != vector.end(); ++i)
        std::cout << *i << ", ";
    std::cout << " }";*/
    stringstream ss2;
    ss2 << vector.size();
    const char *buffer = (m_sandbox_path + '/' + "fir_" + ss2.str()).c_str();
    std::cout << "FILE NAME - " << buffer << std::endl;
    std::ofstream output_file(buffer);
    output_file << "{ ";
    for (auto i = vector.begin(); i != vector.end(); ++i)
        output_file << *i << ", ";
    output_file << " }";
}

ECGProcessor::ECGProcessor(FM_Detector_params_t
                           m_fm_detector_params_, unsigned long
                           m_block_size_,
                           std::string
                           sandbox_path)
        : m_fm_detector_params_(m_fm_detector_params_), m_block_size_(m_block_size_),
          m_sandbox_path(sandbox_path) {
    // Try to restore previously saved values
//    if (!this->try_to_restore_filter_configuration()) {
//        this->m_fm_detector_params_.fir1_coefs = calculate_first_filter(
//                &this->m_fm_detector_params_);
//        this->m_fm_detector_params_.fir2_coefs = calculate_second_filter(
//                &this->m_fm_detector_params_);
//        this->save_current_filter_configuration();
//    }
    this->m_fm_detector_params_.fir1_coefs = fir1_coeffs_vector;
    this->m_fm_detector_params_.fir2_coefs = fir2_coeffs_vector;
    this->init_filters();

}

ECGProcessor::~ECGProcessor() {
    if (m_fm_detector_ != nullptr) {
        delete (m_fm_detector_);
        m_fm_detector_ = nullptr;
    }
}

int ECGProcessor::process(const short *in, float *out) {
    if (this->m_fm_detector_ != nullptr) {
        return this->m_fm_detector_->process(in, out);
    } else {
        return -1;
    }
}

const FM_Detector_params_t &ECGProcessor::get_detector_params() const {
    return m_fm_detector_params_;
}

bool ECGProcessor::try_to_restore_filter_configuration() {
    std::ifstream in;
    FM_Detector_params_t restored;
    // Set invalid values to check later
    restored.Kd = INVALID_CONFIG_VALUE;
    restored.Fs = INVALID_CONFIG_VALUE;
    in.open(get_config_file_name(), std::ios::binary);
/*    if (in.good()) {*/
    in.read(reinterpret_cast<char *>(&restored.Fs),
            sizeof(restored.Fs));
    in.read(reinterpret_cast<char *>(&restored.Fc),
            sizeof(restored.Fc));
    in.read(reinterpret_cast<char *>(&restored.Kd),
            sizeof(restored.Kd));
    in.read(reinterpret_cast<char *>(&restored.fir1_order),
            sizeof(restored.fir1_order));
    in.read(reinterpret_cast<char *>(&restored.fir2_order),
            sizeof(restored.fir2_order));
    vectorf fir_1_coeffs;
    // Fir 1

    fir_1_coeffs.resize((unsigned long) restored.fir1_order);
    in.read((char *) &fir_1_coeffs[0], restored.fir1_order * sizeof(fir_1_coeffs[0]));
    restored.fir1_coefs = fir_1_coeffs;
    // Fir 2
    vectorf fir_2_coeffs;
    fir_2_coeffs.resize((unsigned long) restored.fir2_order);
    in.read((char *) &fir_2_coeffs[0], restored.fir2_order * sizeof(fir_2_coeffs[0]));
    restored.fir2_coefs = fir_2_coeffs;
    in.close();

    bool zeros_fir_1 = std::all_of(fir_1_coeffs.begin(), fir_1_coeffs.end(),
                                   [](float i) { return i == 0; });
    bool zeros_fir_2 = std::all_of(fir_2_coeffs.begin(), fir_2_coeffs.end(),
                                   [](float i) { return i == 0; });
    if (restored.Kd > 0 && restored.Fs > 0 && !zeros_fir_1 && !zeros_fir_2) {
        m_fm_detector_params_ = restored;
        return true;
    }
    return false;
}

void ECGProcessor::save_current_filter_configuration() {
    std::ofstream out;
    out.open(get_config_file_name(), std::ios::binary);
    out.write(reinterpret_cast<char *>(&m_fm_detector_params_.Fs),
              sizeof(m_fm_detector_params_.Fs));
    out.write(reinterpret_cast<char *>(&m_fm_detector_params_.Fc),
              sizeof(m_fm_detector_params_.Fc));
    out.write(reinterpret_cast<char *>(&m_fm_detector_params_.Kd),
              sizeof(m_fm_detector_params_.Kd));
    out.write(reinterpret_cast<char *>(&m_fm_detector_params_.fir1_order),
              sizeof(m_fm_detector_params_.fir1_order));
    out.write(reinterpret_cast<char *>(&m_fm_detector_params_.fir2_order),
              sizeof(m_fm_detector_params_.fir2_order));
    auto &fir_1_vec_first = m_fm_detector_params_.fir1_coefs[0];
    out.write(reinterpret_cast<const char *>(&fir_1_vec_first),
              m_fm_detector_params_.fir1_order * sizeof(fir_1_vec_first));

    // Fir2
    auto &fir_2_vec_first = m_fm_detector_params_.fir2_coefs[0];
    out.write(reinterpret_cast<const char *>(&fir_2_vec_first),
              m_fm_detector_params_.fir2_order * sizeof(fir_2_vec_first));
    out.flush();
    out.close();
}

const char *const ECGProcessor::get_config_file_name() {
    return (m_sandbox_path + '/' + FM_DETECTOR_CONFIG_FILE_NAME).c_str();
}


