//
// Created by CliffLeopard on 2023/12/28.
//
#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <sys/stat.h>
#include <android/log.h>
#include <jni.h>
#include <assert.h>
#include <poll.h>
#include <android/looper.h>


#define TAG "JniNative"
#define LOGE(TAG, ...) __android_log_print(ANDROID_LOG_INFO,TAG,__VA_ARGS__)

extern "C" JNIEXPORT void JNICALL Java_com_cliff_conch_native_JniNative_nativeInit
        (JNIEnv *env, jobject object, jobject messageQueue) {

    return;
}
