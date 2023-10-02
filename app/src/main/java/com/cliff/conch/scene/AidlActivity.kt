package com.cliff.conch.scene

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.cliff.conch.databinding.ActivityAidlBinding
import com.cliff.conch.scene.aidl.NormalService

class AidlActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAidlBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAidlBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.start.setOnClickListener(::mStartService)
        binding.bind.setOnClickListener(::mBindService)
        binding.end.setOnClickListener(::mEndService)
    }

    private fun mStartService(view: View) {
        val intent = Intent(this, NormalService::class.java)
        startService(intent)
    }

    private fun mBindService(view: View) {
        val intent = Intent(this, NormalService::class.java)
        bindService(intent, MyServiceConnection(), Context.BIND_EXTERNAL_SERVICE)
    }

    private fun mEndService(view: View) {
    }

    inner class MyServiceConnection : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {

        }

        override fun onServiceDisconnected(name: ComponentName?) {

        }
    }
}