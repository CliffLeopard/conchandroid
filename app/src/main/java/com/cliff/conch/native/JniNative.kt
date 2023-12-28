package com.cliff.conch.native

import android.os.MessageQueue


object JniNative {
    init {
        System.loadLibrary("native-message")
    }
    
    private external fun nativeInit(queue: MessageQueue)
    fun initNative(queue: MessageQueue) {
        nativeInit(queue)
    }

    private val mJniNativeListeners: MutableList<JniNativeListener> = ArrayList()

    interface JniNativeListener {
        fun notifyNativeCall(msg: String?)
    }

    fun setNativeListen(mJniNativeListener: JniNativeListener) {
        mJniNativeListeners.add(mJniNativeListener)
    }

    fun removeNativeListen(mJniNativeListener: JniNativeListener) {
        mJniNativeListeners.remove(mJniNativeListener)
    }

    fun notifyNativeCall(msg: String?) {
        for (lis in mJniNativeListeners) {
            lis.notifyNativeCall(msg)
        }
    }
}


