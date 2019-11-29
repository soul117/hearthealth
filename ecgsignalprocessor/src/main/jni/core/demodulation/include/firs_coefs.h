
#ifndef _FIR_COEFS_H_
#define _FIR_COEFS_H_

#include <vector>

#define FIR2_ORDER 401
#define FIR1_ORDER 301
/*
    MATLAB:
    Fs = 44100
    fir1_coefs = firpm(300, [0, 300, 600, Fs/2]/(Fs/2), [1, 1, 0, 0], [0.1, 1]);
    fir2_coefs = firpm(400, [0, 10, 50, Fs2/2]/(Fs2/2), [1, 1, 0, 0], [0.1, 1]);
*/
extern const float fir2_coefs_tab[FIR2_ORDER];
extern const float fir1_coefs_tab[FIR1_ORDER];
extern const std::vector<float> fir1_coeffs_vector;
extern const std::vector<float> fir2_coeffs_vector;

#endif