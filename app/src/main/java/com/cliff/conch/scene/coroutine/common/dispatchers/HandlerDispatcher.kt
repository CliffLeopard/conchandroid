package com.cliff.conch.scene.coroutine.common.dispatchers

import android.os.Handler

object HandlerDispatcher:
    Dispatcher {
    private val handler = Handler()

    override fun dispatch(block: () -> Unit) {
        handler.post(block)
    }
}