package com.cliff.conch.scene

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.cliff.common.BaseActivity
import com.cliff.conch.databinding.ActivityAnractivityBinding

class ANRActivity : BaseActivity<ActivityAnractivityBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initBinding() {
        binding = ActivityAnractivityBinding.inflate(layoutInflater)
    }

    override fun mainView(): View {
        return binding.main
    }

    override fun onResume() {
        super.onResume()
        Handler(Looper.getMainLooper()).postDelayed({
            println("begin run")
            Thread.sleep(300000)
            println("end run")
        }, 1500)
    }
}