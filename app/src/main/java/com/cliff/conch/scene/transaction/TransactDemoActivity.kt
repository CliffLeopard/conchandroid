package com.cliff.conch.scene.transaction

import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cliff.conch.R
import com.orhanobut.logger.Logger

class TransactDemoActivity : AppCompatActivity() {
    override fun attachBaseContext(newBase: Context?) {
        Logger.i("attachBaseContext")
        super.attachBaseContext(newBase)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Logger.i("onCreate")
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_transact_demo)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onResume() {
        Logger.i("onResume")
        super.onResume()
    }

    override fun onRestart() {
        Logger.i("onRestart")
        super.onRestart()
    }

    override fun onStart() {
        Logger.i("onStart")
        super.onStart()
    }

    override fun onStop() {
        Logger.i("onStop")
        super.onStop()
    }

    override fun onDestroy() {
        Logger.i("onDestroy")
        super.onDestroy()
    }

}