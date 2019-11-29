#include <jni.h>
#include <firpm/pm.h>
#include <FM_Detector.h>
#include <ECGProcessor.h>
#include <RPeakDetector.h>

#define  LOG_TAG    "ECG_DEMODULATOR"

#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)
#define  LOGW(...)  __android_log_print(ANDROID_LOG_WARN,LOG_TAG,__VA_ARGS__)
#define  LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG,LOG_TAG,__VA_ARGS__)
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
#define MAX_SHORT_VALUE 32768
static const int CARRIER_FREQUENCY = 1700;
ECGProcessor *ecg_processor;
RPeakDetector *r_peak_detector;
Rpeak_Detector_status_t r_status;
bool isDisposed = false;
extern "C" {
JNIEXPORT jshortArray JNICALL
Java_net_crosp_android_library_ecgaudioprocessor_NativeEcgSignalProcessor_demodulate(
        JNIEnv *env, jobject instance, jshortArray readBuffer_);
}

void segfault_sigaction(int signal, siginfo_t *si, void *arg) {
    printf("Caught segfault at address %p\n", si->si_addr);
    exit(0);
}

void set_sigsegv_handler() {
    struct sigaction sa;

    memset(&sa, 0, sizeof(struct sigaction));
    sigemptyset(&sa.sa_mask);
    sa.sa_sigaction = segfault_sigaction;
    sa.sa_flags = SA_SIGINFO;

    sigaction(SIGSEGV, &sa, NULL);
}

extern "C"
JNIEXPORT void JNICALL
Java_net_crosp_android_library_ecgaudioprocessor_NativeEcgSignalProcessor_init(JNIEnv *env,
                                                                               jobject instance,
                                                                               jint samplingRate,
                                                                               jint readBufferSize,
                                                                               jint decimationCoefficient,
                                                                               jstring sandboxPath_) {
    set_sigsegv_handler();
    FM_Detector_params_t fm_params;
    fm_params.Kd = decimationCoefficient;
    fm_params.Fc = CARRIER_FREQUENCY;
    fm_params.Fs = samplingRate;
    fm_params.fir1_order = FIR1_ORDER;
    fm_params.fir2_order = FIR2_ORDER;
    fm_params.block_size = readBufferSize;
    std::cout << "------ Decimation coefficient  --------" << fm_params.Kd << std::endl;
    std::cout << "------ Fs coefficient  --------" << fm_params.Fs << std::endl;
    std::cout << "------ block_size coefficient  --------" << fm_params.block_size << std::endl;
    // Get sandbox path to restore configuration
    auto path_pointer = env->GetStringUTFChars(sandboxPath_, 0);
    std::cout << "------ Sandbox path  --------" << path_pointer << std::endl;
    std::string sandbox_location = std::string(path_pointer);
    env->ReleaseStringUTFChars(sandboxPath_, path_pointer);
    ecg_processor = new ECGProcessor(fm_params, (size_t) readBufferSize, sandbox_location);
    // Init R peak detector
    r_peak_detector = new RPeakDetector(readBufferSize / decimationCoefficient, samplingRate);
    isDisposed = false;
}

extern "C"
JNIEXPORT void JNICALL
Java_net_crosp_android_library_ecgaudioprocessor_NativeEcgSignalProcessor_release(JNIEnv *env,
                                                                                  jobject instance) {

    try {
        if (ecg_processor != nullptr) {
            delete ecg_processor;
            ecg_processor = nullptr;
        }
        if (r_peak_detector != nullptr) {
            delete r_peak_detector;
            r_peak_detector = nullptr;
        }
        isDisposed = true;
    }
    catch (std::exception &e) {
        std::cout << e.what();
    }
}


JNIEXPORT jshortArray JNICALL
Java_net_crosp_android_library_ecgaudioprocessor_NativeEcgSignalProcessor_demodulate(
        JNIEnv *env, jobject instance, jshortArray readBuffer_) {
    jshortArray result;
    try {
        if (!isDisposed) {
            int input_size = env->GetArrayLength(readBuffer_);
            float fd_out[input_size];
            // Convert to native array
            jshort *input_array = env->GetShortArrayElements(readBuffer_, JNI_FALSE);

            int out_samples_count = ecg_processor->process(input_array, fd_out);

            auto &detector_params = ecg_processor->get_detector_params();
            assert(out_samples_count == detector_params.block_size / detector_params.Kd);
            jshort temp_result[out_samples_count];
            for (int i = 0; i < out_samples_count; i++) {
                temp_result[i] = (jshort) (fd_out[i] *
                                           MAX_SHORT_VALUE); // put whatever logic you want to populate the values here.
            }

            env->ReleaseShortArrayElements(readBuffer_, input_array, JNI_FALSE);
            result = env->NewShortArray(out_samples_count);
            env->SetShortArrayRegion(result, 0, out_samples_count, temp_result);
            return result;
        }
    }
    catch (std::exception &e) {
        std::cout << e.what();
    }
    return 0;
}

extern "C"
JNIEXPORT jint JNICALL
Java_net_crosp_android_library_ecgaudioprocessor_NativeEcgSignalProcessor_detectRPeaks(JNIEnv *env,
                                                                                       jobject instance,
                                                                                       jshortArray input_) {
    try {
        if (!isDisposed) {
            jshort *input = env->GetShortArrayElements(input_, NULL);
            int length = env->GetArrayLength(input_);
            float samples[length];
            for (int i = 0; i < length; i++) {
                samples[i] = (float) (input[i] / (float) MAX_SHORT_VALUE);
            }
            env->ReleaseShortArrayElements(input_, input, 0);
            int detected = r_peak_detector->process(samples, &r_status);
            return detected;
        }
    }
    catch (std::exception &e) {
        std::cout << e.what();
    }
    return 0;
}