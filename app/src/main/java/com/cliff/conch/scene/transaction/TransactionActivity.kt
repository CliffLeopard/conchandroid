package com.cliff.conch.scene.transaction

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.cliff.common.BaseActivity
import com.cliff.conch.databinding.ActivityTransactionBinding
import com.orhanobut.logger.Logger
import top.canyie.pine.Pine
import top.canyie.pine.callback.MethodHook

class TransactionActivity : BaseActivity<ActivityTransactionBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAction()
    }

    override fun initBinding() {
        binding = ActivityTransactionBinding.inflate(layoutInflater)
    }

    override fun mainView(): View {
        return binding.main
    }

    private fun initAction() {
        val clzTransactionExecutor =
            Class.forName("android.app.servertransaction.TransactionExecutor")
        val clzClientTransaction = Class.forName("android.app.servertransaction.ClientTransaction")
        val clzLaunchActivityItem =
            Class.forName("android.app.servertransaction.LaunchActivityItem")

//        val execute = clzTransactionExecutor.getDeclaredMethod("execute",clzClientTransaction)
//        val executeTransactionItems = clzTransactionExecutor.getDeclaredMethod("executeTransactionItems",clzClientTransaction)
        binding.hook.setOnClickListener {
            Logger.i("Click")
            val executeNonLifecycleItem =
                clzTransactionExecutor.getDeclaredMethod(
                    "executeNonLifecycleItem",
                    clzClientTransaction,
                    clzLaunchActivityItem,
                    Boolean::class.java
                )
            Pine.hook(executeNonLifecycleItem, Hooker())
        }
        binding.start.setOnClickListener {
            startActivity(Intent(this, TransactDemoActivity::class.java))
        }
    }

    private class Hooker : MethodHook() {
        override fun beforeCall(callFrame: Pine.CallFrame?) {
            Logger.i("beforeCall:" + callFrame?.method?.name)
            super.beforeCall(callFrame)
        }

        override fun afterCall(callFrame: Pine.CallFrame?) {
            Logger.i("afterCall:" + callFrame?.method?.name)
            super.afterCall(callFrame)
        }
    }
}