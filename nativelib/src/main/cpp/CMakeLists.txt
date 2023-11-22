
cmake_minimum_required(VERSION 3.22.1)

project("native-activity"
        VERSION 1.0
        DESCRIPTION "a native-activity test case"
        LANGUAGES C CXX
        )

# build native_app_glue as a static lib
set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS}")
add_library(native_app_glue STATIC
        ${ANDROID_NDK}/sources/android/native_app_glue/android_native_app_glue.c)
# 添加被连接的库的可调用接口
target_include_directories(native_app_glue INTERFACE
        ${ANDROID_NDK}/sources/android/native_app_glue)

# now build app's shared lib
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=gnu++11 -Wall -Werror")
# Export ANativeActivity_onCreate(),
set(CMAKE_SHARED_LINKER_FLAGS
        "${CMAKE_SHARED_LINKER_FLAGS} -u ANativeActivity_onCreate")
add_library(${CMAKE_PROJECT_NAME} SHARED main.cpp)

# add lib dependencies
target_link_libraries(${CMAKE_PROJECT_NAME}
        android
        native_app_glue
        EGL
        GLESv1_CM
        log)