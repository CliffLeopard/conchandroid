package com.cliff.conch.scene

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.cliff.common.BaseActivity
import com.cliff.conch.databinding.ActivityLeakBinding
import com.cliff.conch.startActivity


class LeakActivity : BaseActivity<ActivityLeakBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.start.setOnClickListener { _ ->
            startActivity(LeakBActivity::class.java)
        }

        Handler(Looper.getMainLooper()).post{};
    }

    override fun initBinding() {
        binding = ActivityLeakBinding.inflate(layoutInflater)
    }

    override fun mainView(): View {
        return binding.main
    }
}