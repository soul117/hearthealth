firpm library (double precision version)
=========================================

## Installation instructions (double precision version)##
This code has been tested **only** on Linux installations. In order to compile and use it, a recent version of g++ with
C++11 support is necessary (a version >= 4.8 should work nicely). Some external utilities and libraries must also be installed
and available on your system search paths:
* CMake version 2.8 or newer
* Eigen version 3 or newer
* (optional) Google gtest framework for generating the test executables
* (optional) doxygen version 1.8.3 or newer in order to generate the accompanying documentation

Assuming these prerequisites are taken care of and you are located at the base folder containing the source code and test files,
building the library can be done using the following commands (with the appropriate user privileges):

        mkdir build
        cd build
        cmake ..
        make all
        make install

This series of steps will install the library on your system. Usually the default location is /usr/local, but this behavior can
be changed when calling CMake by specifying an explicit install prefix:

        cmake -DCMAKE_INSTALL_PREFIX=/your/install/path/here ..


If gtest is installed on your system, the *make all* command should have generated two test executables
* firpmlib_scaling_test : should contain code to generate all the example filters used in the article
* firpmlib_extensive_test : contains a more extensive set of over 50 different filters, giving an iteration count comparison for
uniform initialization and reference scaling.

We must make the precision that both the gtest library header files and the static and shared versions of the library *must* be installed
in order to generate the test files. In the case of an Ubuntu installation of gtest from their official repositories, the static and shared
versions of gtest are not installed. Ways of solving this problem are described at the
following link: http://askubuntu.com/questions/145887/why-no-library-files-installed-for-google-test


The make all target also generates the documentation if Doxygen was found on your system when running cmake. It can also be
generated individually by running the command

        make doc

after cmake was called.


Executing these files will print out information regarding the final reference error (i.e. final delta value) obtained when
executing the Parks-McClellan exchange algorithm, along with iteration count information. Depending on your machine, these tests
can take a some time to finish. For example, on my quad core Intel Xeon(R) E5-1620, it took several minutes to finish running
the firpmlib_extensive_test file.
An example output for one test case from firpm_scaling_test looks like this:

        [ RUN      ] firpm_scaling_test.combfir
        START Parks-McClellan with uniform initialization
        Final Delta     = 1.6066103765385452407e-07
        Iteration count = 12
        FINISH Parks-McClellan with uniform initialization
        START Parks-McClellan with reference scaling
        Final Delta     = 1.6066983887677986099e-07
        Iteration count = 3
        FINISH Parks-McClellan with reference scaling
        Iteration count reduction for final filter: 0.75
        [       OK ] firpm_scaling_test.combfir (263 ms)

It shows information when using both uniform initialization and reference scaling to design that particular filter_complex. If uniform initialization
fails to converge to a result, then the output can look like this (test case extracted from the firpm_extensive_test file):

        [ RUN      ] firpm_extensive_test.extensive12
        START Parks-McClellan with uniform initialization
        The exchange algorithm did not converge.
        TRIGGER: numerical instability
        POSSIBLE CAUSES: poor starting reference and/or a too small value for Nmax.
        Iteration count = NC
        FINISH Parks-McClellan with uniform initialization
        START Parks-McClellan with reference scaling
        Final delta     = 3.0071500558839879242e-13
        Iteration count = 7
        FINISH Parks-McClellan with reference scaling
        [       OK ] firpm_extensive_test.extensive12 (24 ms)
