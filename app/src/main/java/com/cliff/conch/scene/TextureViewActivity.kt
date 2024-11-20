package com.cliff.conch.scene

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.cliff.common.BaseActivity
import com.cliff.conch.R
import com.cliff.conch.databinding.ActivityTextureViewBinding

class TextureViewActivity : BaseActivity<ActivityTextureViewBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val surfaceView = binding.hdrSurface
    }

    override fun initBinding() {
        binding = ActivityTextureViewBinding.inflate(layoutInflater)
    }

    override fun mainView(): View {
       return binding.main
    }
}