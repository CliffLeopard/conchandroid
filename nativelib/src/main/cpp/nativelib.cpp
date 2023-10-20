#include <jni.h>
#include <string>
#include <android/binder_ibinder.h>

extern "C" JNIEXPORT jstring JNICALL
Java_com_cliff_nativelib_NativeLib_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}