package com.cliff.conch.scene

import android.os.Bundle
import android.view.View
import com.cliff.common.BaseActivity
import com.cliff.conch.databinding.ActivitySelfDefineViewBinding

class SelfDefineViewActivity : BaseActivity<ActivitySelfDefineViewBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initBinding() {
        binding = ActivitySelfDefineViewBinding.inflate(layoutInflater)
    }

    override fun mainView(): View {
        return binding.main
    }
}