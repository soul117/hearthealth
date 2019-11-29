# Cmake Minimum Version
cmake_minimum_required(VERSION 3.4.1)

project(EcgProcessing)
set (CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11")
# Some useful macros
MACRO(ADD_SUBLIB libname source_ext)
    #Compute required sources
    set(sublib_path "${LIBS_DIRECTORY}/${libname}")
    file(GLOB_RECURSE sublib_sources "${sublib_path}/src/*.${source_ext}")
    #Create library
    IF( sublib_sources )
        #add_executable(${libname} ${sublib_sources})
        ADD_LIBRARY(${libname} SHARED ${sublib_sources})
    ENDIF()
    #add this library's header folder to the global include set
    INCLUDE_DIRECTORIES("${sublib_path}/include")
    INCLUDE_DIRECTORIES("${sublib_path}/")
    link_directories(${sublib_path})
ENDMACRO(ADD_SUBLIB)

# Add nested cmake files
include(libs/CMakeLists.txt)
include(core/CMakeLists.txt)
include_directories(${CMAKE_CURRENT_SOURCE_DIR})


# ECG Audio Processor library
add_library(ecg-signal-processor SHARED
            ecgprocessorproxy.cpp)

find_library( # Sets the name of the path variable.
        log-lib
        # Specifies the name of the NDK library that
        # you want CMake to locate.
        log )

# Link
target_link_libraries(
        demodulation
        firpm_d)

target_link_libraries(
            demodulation
            log
            android
            firpm_d)

target_link_libraries(
        ecgprocessor
        demodulation)

target_link_libraries(
            ecg-signal-processor
            log
            android
            firpm_d
            demodulation
            qrsdetection
            ecgprocessor
            )



