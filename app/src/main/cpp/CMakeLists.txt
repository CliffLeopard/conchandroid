cmake_minimum_required(VERSION 3.22.1)

project("native-message"
        VERSION 1.0
        DESCRIPTION "a native-message test case"
        LANGUAGES C CXX
        )
set(NATIVE_MESSAGE "native-message")
add_library(${NATIVE_MESSAGE} SHARED native_message.cpp)
target_link_libraries(${NATIVE_MESSAGE}
        android
        )