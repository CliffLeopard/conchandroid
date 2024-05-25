package com.cliff.conch.scene.coroutine

import kotlinx.coroutines.delay
import kotlin.concurrent.thread
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.RestrictsSuspension
import kotlin.coroutines.createCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.startCoroutine
import kotlin.coroutines.suspendCoroutine

object CoroutineCase {
    fun basicCreate() {
        val continuation = suspend {
            println("In Create continuation")
            5
        }.createCoroutine(object : Continuation<Int> {
            override val context: CoroutineContext
                get() = EmptyCoroutineContext

            override fun resumeWith(result: Result<Int>) {
                println("Coroutine End: $result")
            }
        })
        continuation.resume(Unit)
    }

    /**
     * 这里使用的函数是:
     * fun <T> (suspend() -> T).startCoroutine(completion:Continuation<T>)
     * 即(suspend() -> T)函数类型的拓展函数
     */
    fun basicStart() {
        suspend {
            println("In Start continuation")
            5
        }.startCoroutine(object : Continuation<Int> {
            override val context: CoroutineContext
                get() = EmptyCoroutineContext

            override fun resumeWith(result: Result<Int>) {
                println("Coroutine End: $result")
            }
        })
    }

    /**
     * 这里使用的函数是:
     * fun <T> (suspend R.() -> T).startCoroutine(receiver:R,completion:Continuation<T>)
     * 即R类型的拓展函数(suspend() -> T)，的拓展函数
     * 这里假如receiver是为了规范作用域，当R类型添加了 @RestrictsSuspension 注解的时候，就无法访问作用域外的刮起函数了
     */
    private fun <R, T> launchCoroutine(receiver: R, block: suspend R.() -> T) {
        block.startCoroutine(receiver, object : Continuation<T> {
            override val context: CoroutineContext
                get() = EmptyCoroutineContext

            override fun resumeWith(result: Result<T>) {
                println("Coroutine End: $result")
            }
        })
    }

    fun basicReceiverCreate() {
        val book = Bookie()
        launchCoroutine(book) {
            println("In Start continuation: $this")
//            delay(5000) //当Bookie添加了注解 @RestrictsSuspension 时，不能在调用此挂起函数
            bDelay()
        }
    }

    @RestrictsSuspension
    data class Bookie(val name: String = "Cliff", val age: Int = 12) {
        suspend fun bDelay() {
            delay(5000)
            println("endDelay: $this")
        }
    }

    suspend fun suspendFunction02(a:String,b:String)  =
        suspendCoroutine { continuation ->
            thread {
                continuation.resumeWith(Result.success(5))
            }
        }
}