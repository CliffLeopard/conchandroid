package com.cliff.conch.scene

import android.os.Bundle
import android.view.View
import com.cliff.common.BaseActivity
import com.cliff.conch.databinding.ActivityPineBinding

class PineActivity : BaseActivity<ActivityPineBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initBinding() {
        binding = ActivityPineBinding.inflate(layoutInflater)
    }

    override fun mainView(): View {
        return binding.main
    }
}