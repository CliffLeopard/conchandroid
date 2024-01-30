package com.cliff.conch.scene

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.cliff.conch.databinding.ActivityCoroutineBinding
import com.orhanobut.logger.Logger
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.createCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class CoroutineActivity : AppCompatActivity() {
    lateinit var binding: ActivityCoroutineBinding
    val kk: Int by lazy { 10 }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoroutineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val exceptionHandler = CoroutineExceptionHandler { context, throwable ->
            Logger.i("Catched Exception")

        }

        SupervisorJob()

        MainScope()

        testCoroutine()

        lifecycleScope.launch(exceptionHandler) {

            coroutineContext[CoroutineName]
            launch {
                supervisorScope {
                    launch {
                        withContext(Dispatchers.IO) {
                            while (true) {
                                delay(1000)
//                                1 / 0
                                Logger.i("Loop0")
                            }
                        }
                    }
                }
            }
            launch {
                withContext(Dispatchers.IO) {
                    while (true) {
                        delay(1000)
//                        1 / 0
                        Logger.i("Loop")
                    }
                }
            }

            launch {
                withContext(Dispatchers.IO) {
                    while (true) {
//                        ensureActive()
                        delay(1000)
                        Logger.i("Loop2")
                    }
                }
            }
        }
    }

    private fun testCoroutine() {
        val continuation = suspend {
            Logger.i("In Coroutine")
            10
        }.createCoroutine(object : Continuation<Int> {
            override val context: CoroutineContext
                get() = EmptyCoroutineContext

            override fun resumeWith(result: Result<Int>) {
                Logger.i("Coroutine End: $result")
            }
        })
        continuation.resume(Unit)

        val continuation2 = ::susp.createCoroutine(object : Continuation<Int> {
            override val context: CoroutineContext
                get() = EmptyCoroutineContext

            override fun resumeWith(result: Result<Int>) {
                Logger.i("Coroutine End: $result")
            }
        })
    }

    suspend fun s02(a: String, b: String) = suspendCoroutine<Int> { continuation ->
        Thread {
            continuation.resumeWith(Result.success(5))
        }
    }

    private suspend fun kk(k: Int): Int {
        return withContext(Dispatchers.IO) {
            Logger.i("$k ${Thread.currentThread().name}")
            Thread.sleep(10000)
            10
        }
    }

    private suspend fun susp(): Int {
        return withContext(Dispatchers.IO) {
            Logger.i(" ${Thread.currentThread().name}")
            10
        }
    }


}