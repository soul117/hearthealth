# Cmake Minimum Version
cmake_minimum_required(VERSION 3.4.1)
set(LIBS_DIRECTORY ${CMAKE_CURRENT_LIST_DIR})

ADD_SUBLIB(demodulation "cpp")
ADD_SUBLIB(qrsdetection "cpp")
ADD_SUBLIB(ecgprocessor "cpp")



