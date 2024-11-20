package com.cliff.conch.scene

import android.os.Bundle
import android.view.View
import com.cliff.common.BaseActivity
import com.cliff.conch.databinding.ActivityRetrofitBinding

class RetrofitActivity : BaseActivity<ActivityRetrofitBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
    }

    override fun initBinding() {
        binding = ActivityRetrofitBinding.inflate(layoutInflater)
    }

    override fun mainView(): View {
        return binding.main
    }
}