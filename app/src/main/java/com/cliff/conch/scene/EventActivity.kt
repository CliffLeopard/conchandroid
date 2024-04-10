package com.cliff.conch.scene

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import androidx.appcompat.app.AppCompatActivity
import com.cliff.conch.databinding.ActivityEventBinding
import com.orhanobut.logger.Logger

class EventActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEventBinding
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

    override fun onContentChanged() {
        super.onContentChanged()
    }
}