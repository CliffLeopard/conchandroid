package com.cliff.conch.scene

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cliff.conch.databinding.ActivityEventBusBinding
import com.cliff.eventbuskotlin.MyEventBusIndex
import com.orhanobut.logger.Logger
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class EventBusActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEventBusBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initEventBus()
        binding = ActivityEventBusBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button.setOnClickListener {
            EventBus.getDefault().post(MessageEvent("事件A"))
        }
    }

    // 这里是EventBus3.0改版之后必须要设置的步骤，只有这样EventBus注解生成的SubscriberInfoIndex才能起作用
    // 一般在Application种初始化的时候调用
    private fun initEventBus() {
        EventBus.builder()
            .addIndex(MyEventBusIndex())
            .installDefaultEventBus()
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    @Suppress("unused")
    fun onMessageEvent(event: MessageEvent?) {
        Logger.d("接收到事件:${event?.value}")
    }

    data class MessageEvent(var value: String)
}