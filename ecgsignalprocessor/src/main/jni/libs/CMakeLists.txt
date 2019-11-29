# Cmake Minimum Version
cmake_minimum_required(VERSION 3.4.1)
set(LIBS_DIRECTORY ${CMAKE_CURRENT_LIST_DIR})
#include_directories(${CMAKE_CURRENT_SOURCE_DIR}/eigen/)

ADD_SUBLIB(firpm_d "cpp")
ADD_SUBLIB(eigen "cpp")