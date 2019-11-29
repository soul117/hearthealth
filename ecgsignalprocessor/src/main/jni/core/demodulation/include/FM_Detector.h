#ifndef _FM_DETECTOR_H_
#define _FM_DETECTOR_H_

#include <StdVector>
#include <fstream>
#include <iostream>
#include "fir.h"
#include "firs_coefs.h"

using namespace std;

typedef complex<float> complex_float;
typedef std::vector<float> vectorf;

static const int DEFAULT_BLOCK_SIZE = 2048;

struct FM_Detector_params_t {
    float Fs; // Sampling frequency, Hz
    float Fc; // Carrier freq., Hz
    int Kd;   // Decimation coef
    int fir1_order;
    int fir2_order;
    vectorf fir1_coefs;
    vectorf fir2_coefs;

    int block_size;

    FM_Detector_params_t() {
        Fs = 44100.0;
        Fc = 1800.0;
        Kd = 12;
        fir1_coefs = vectorf();
        fir1_order = FIR1_ORDER;
        block_size = DEFAULT_BLOCK_SIZE;
        fir2_coefs = vectorf();
        fir2_order = FIR2_ORDER;
    };

};

class FM_Detector_t {
public:


    FM_Detector_t(FM_Detector_params_t *p = NULL) {
        FM_Detector_params_t default_params;
        if (p == NULL) {
            p = &default_params;
        }
        m_params = *p;
        int blk_sz2 = p->block_size / p->Kd;
        m_fir1 = new fir_t<complex_float, float>(&(p->fir1_coefs[0]), p->fir1_order, p->block_size);
        m_fir2 = new fir_t<float, float>(&(p->fir2_coefs[0]), p->fir2_order, blk_sz2);
        ph0 = 0;
        y_dec_end = complex_float(1.0f);
    }

    void get_out_params(
            float &Fs2,  // ������� ������������� �� ������ ���������� ��������� 
            int &blk_sz2 // ������ ����� �� ������ ���������� ���������
    ) {

        Fs2 = m_params.Fs / m_params.Kd;
        blk_sz2 = blk_sz2 / m_params.Kd;
        assert(m_params.block_size == blk_sz2 * m_params.Kd);
    }

    ~FM_Detector_t() {
        delete m_fir1;
        delete m_fir2;
    }

    int process(const short *x, float *y) {
        int i;
        const int Kd = m_params.Kd;
        int blk_sz2 = m_params.block_size / Kd;
        float phi = ph0;                              // Начальная фаза несущей
        float dfi = -2 * M_PI / m_params.Fs * m_params.Fc; // Приращение фазы несущей
        assert(m_params.block_size % Kd == 0);
        complex_float tmp0[m_params.block_size];
        complex_float tmp1[m_params.block_size];
        float tmp_float[m_params.block_size];
        // Умножаем входную последовательность на комплексную экспоненту с частотой несущей
        for (i = 0; i < m_params.block_size; i++) {
            tmp0[i] = complex_float(x[i] * cosf(phi), x[i] * sinf(phi));
            phi += dfi;
        }
        // Что избежать неограниченного возрастания ph0  и соотв. деградации вычисления комплексной экспоненты
        ph0 = fmodf(phi, 2 * M_PI);

        // ФНЧ дециматор с полосой пропускания 300 Гц, на входе blk_sz отсчетов, на выходе blk_sz2
        m_fir1->filter(tmp0, tmp1, Kd);

        complex_float prev = y_dec_end;
        for (i = 0; i < blk_sz2; i++) {
            // Вычисляем приращение фазы между отсчетами
            tmp_float[i] = arg(tmp1[i] * prev);
            prev = conj(tmp1[i]);
        }
        // Запоминаем последний отсчет
        y_dec_end = prev;
        // ФНЧ полосой примерно 10 Гц , убираем наводки 50 Гц
        m_fir2->filter(tmp_float, y);

        return blk_sz2;
    }

protected:

    fir_t<complex_float, float> *m_fir1;
    fir_t<float, float> *m_fir2;
    FM_Detector_params_t m_params;
    float ph0;
    complex_float y_dec_end;
};

#endif