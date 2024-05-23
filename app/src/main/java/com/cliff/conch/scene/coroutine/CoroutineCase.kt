package com.cliff.conch.scene.coroutine

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

object CoroutineCase {
    suspend fun case1(): Int {
        return withContext(Dispatchers.IO) {
            delay(20)
            10
        }
    }
}