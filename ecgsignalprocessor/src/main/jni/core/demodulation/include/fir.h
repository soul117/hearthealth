#ifndef _FIR_H_
#define _FIR_H_
#include <complex>
#include <assert.h>

template <class data_type, class coefs_type> class fir_t
{
    public:

    fir_t()
    {
        m_coefs = NULL; 
        m_buf  = NULL; 
        m_Lh = 0; 
        blk_sz = 0; 
    }

    fir_t(const coefs_type *h,
        int order, 
        int _blk_sz)
    {
        int i; 
        m_Lh = order;
        blk_sz = _blk_sz;
        m_coefs = new coefs_type[order];
        m_buf = new data_type[order - 1 + blk_sz]; 
        assert(m_coefs); 
        assert(m_buf); 
        

        for (i = 0; i<order; i++)
            m_coefs[i] = h[i]; 

        for (i = 0; i<order - 1 + blk_sz; i++)
            m_buf[i] = data_type(0); 
    }

    ~fir_t()
    {
        if (m_coefs) delete m_coefs; 
        if (m_buf)   delete m_buf; 
    }

    void filter(data_type *x, /* Input, blk_sz samples */
                data_type *y, /* Output, blk_sz/Kd samples */
                int Kd = 1    /* Coeficient of a decimation */)
    {
        int i;
        int j;  
        data_type *pout = y; 
        assert( blk_sz % Kd == 0);  // Размер блока должен делиться нацело на Kd 
        for (i = 0; i < blk_sz; i++)
        {   
            m_buf[i+m_Lh-1] = x[i]; 
        }
        /* Фильтрация с децимацией в Kd раз */
        for (j = 0; j < blk_sz; j += Kd)
        {
            data_type acc(0); 
            for (i = 0; i < m_Lh; i++)
            {
                acc += m_buf[j - i + m_Lh-1] * m_coefs[i];
            }
            *pout++ = acc;
        }
        /* Save last m_Lh-1 into the beginning of the buffer */
        for (i = 0; i < m_Lh-1; i++)
        {
            m_buf[i] = m_buf[blk_sz + i];
        }
    }                 

    protected: 
    int blk_sz; 
    int m_Lh;  
    coefs_type *m_coefs; 
    data_type  *m_buf; 

};
#endif //#ifndef _FIR_H_