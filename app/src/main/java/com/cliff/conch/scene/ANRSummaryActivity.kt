package com.cliff.conch.scene

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.os.MessageQueue
import android.os.SystemClock
import android.view.View
import com.cliff.common.BaseActivity
import com.cliff.conch.BuildConfig
import com.cliff.conch.databinding.ActivityAnractivityBinding
import com.orhanobut.logger.Logger
import top.canyie.pine.Pine
import top.canyie.pine.PineConfig
import top.canyie.pine.callback.MethodHook

class ANRSummaryActivity : BaseActivity<ActivityAnractivityBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setActions()
    }

    override fun initBinding() {
        binding = ActivityAnractivityBinding.inflate(layoutInflater)
    }

    override fun mainView(): View {
        TODO("Not yet implemented")
    }

    private fun setActions() {
        hook2()
        Looper.getMainLooper().setMessageLogging {
            println("IDLE:$it")
        }

        binding.longWorkUi.setOnClickListener {
            Logger.i("Sleep Begin")
            Thread.sleep(20 * 1000)
            Logger.i("Sleep End:20s")
        }

        Looper.getMainLooper().queue.addIdleHandler {
            getQueueMessage()
            true
        }
    }

    @SuppressLint("DiscouragedPrivateApi")
    private fun getQueueMessage(): Message? {
        val mq = Looper.getMainLooper().queue
        val clz = Class.forName("android.os.MessageQueue")
        val filed = clz.getDeclaredField("mMessages")
        filed.isAccessible = true
        val message = filed.get(mq) as? Message
        val now = SystemClock.uptimeMillis()
        if (message != null) {

            println("IDLE Message Not Null: ${message.`when` - now}")
        } else {
            println("IDLE Message IS Null: ${message}")
        }
        return message
    }

    @SuppressLint("DiscouragedPrivateApi")
    private fun hook2() {
        PineConfig.debug = true; // 是否debug，true会输出较详细log
        PineConfig.debuggable = BuildConfig.DEBUG; // 该应用是否可调试，建议和配置文件中的值保持一致，否则会出现问题
        // 修改mMessages地方
        Pine.hook(
            MessageQueue::class.java.getDeclaredMethod("postSyncBarrier"),
            object : MethodHook() {
                override fun beforeCall(callFrame: Pine.CallFrame?) {
                    super.beforeCall(callFrame)
                    println("IDLE: beforeCall:postSyncBarrier")
                }

                override fun afterCall(callFrame: Pine.CallFrame?) {
                    super.afterCall(callFrame)
                    println("IDLE: afterCall:postSyncBarrier")
                }
            }
        )

        Pine.hook(
            MessageQueue::class.java.getDeclaredMethod("removeSyncBarrier", Int::class.java),
            object : MethodHook() {
                override fun beforeCall(callFrame: Pine.CallFrame?) {
                    super.beforeCall(callFrame)
                    println("IDLE: beforeCall:removeSyncBarrier")
                }

                override fun afterCall(callFrame: Pine.CallFrame?) {
                    super.afterCall(callFrame)
                    println("IDLE: afterCall:removeSyncBarrier")
                }
            }
        )

        Pine.hook(
            MessageQueue::class.java.getDeclaredMethod(
                "enqueueMessage",
                Message::class.java,
                Long::class.java
            ),
            object : MethodHook() {
                override fun beforeCall(callFrame: Pine.CallFrame?) {
                    super.beforeCall(callFrame)
                    println("IDLE: beforeCall:enqueueMessage")
                }

                override fun afterCall(callFrame: Pine.CallFrame?) {
                    super.afterCall(callFrame)
                    println("IDLE: afterCall:enqueueMessage")
                }
            }
        )

        Pine.hook(
            MessageQueue::class.java.getDeclaredMethod(
                "removeMessages",
                Handler::class.java,
                Int::class.java,
                Object::class.java
            ),
            object : MethodHook() {
                override fun beforeCall(callFrame: Pine.CallFrame?) {
                    super.beforeCall(callFrame)
                    Logger.i(
                        "IDLE: beforeCall:removeMessages:${Thread.currentThread().name}  ${
                            callFrame?.args?.get(
                                0
                            )
                        } ${callFrame?.args?.get(1)} ${callFrame?.args?.get(2)}"
                    )
                }

                override fun afterCall(callFrame: Pine.CallFrame?) {
                    super.afterCall(callFrame)
                    Logger.i(
                        "IDLE: afterCall:removeMessages:${Thread.currentThread().name} ${
                            callFrame?.args?.get(
                                0
                            )
                        } ${callFrame?.args?.get(1)} ${callFrame?.args?.get(2)}"
                    )
                }
            }
        )
    }

}

object CHandler : Handler(Looper.getMainLooper()) {

}