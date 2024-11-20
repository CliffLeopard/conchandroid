package com.cliff.conch.scene

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.cliff.common.BaseActivity
import com.cliff.conch.databinding.ActivityEventBinding

class EventActivity : BaseActivity<ActivityEventBinding>() {
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        binding.eventText.setOnClickListener{
//            Logger.i("ClickText")
//        }
//
//        binding.eventLayout.setOnClickListener{
//            Logger.i("ClickLayout")
//        }

//        binding.eventText.setOnTouchListener { v, event ->
//            Logger.i("ClickText")
//            true
//        }
//
//        binding.eventLayout.setOnTouchListener { v, event ->
//            Logger.i("ClickLayout")
//            true
//        }
    }

    override fun initBinding() {
        binding = ActivityEventBinding.inflate(layoutInflater)
    }

    override fun mainView(): View {
        return binding.main
    }


    override fun onContentChanged() {
        super.onContentChanged()
    }
}