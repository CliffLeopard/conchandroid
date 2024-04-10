package com.cliff.conch.scene

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.IBinder.DeathRecipient
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.cliff.conch.databinding.ActivityBookManagerBinding
import com.cliff.conch.scene.aidl.Book
import com.cliff.conch.scene.aidl.IBookManager
import com.orhanobut.logger.Logger

class BookManagerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookManagerBinding
    private var bookManager: IBookManager? = null

    private val recipient: DeathRecipient by lazy {
        DeathRecipient {
            Logger.d("DeathRecipient")
            if (bookManager != null) {
                bookManager?.asBinder()?.unlinkToDeath(recipient, 0)
                bookManager = null
                bindService(
                    Intent(this, BookManagerService::class.java),
                    connection,
                    Context.BIND_AUTO_CREATE
                )
            }
        }
    }

    private val connection by lazy {
        object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                Logger.d("onServiceConnected")
                bookManager = IBookManager.Stub.asInterface(service)
                bookManager?.asBinder()?.linkToDeath(recipient, 0)
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                Logger.d("onServiceDisconnected")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookManagerBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun clickBindService(view: View) {
        bindService(
            Intent(this, BookManagerService::class.java),
            connection,
            Context.BIND_AUTO_CREATE
        )
    }

    fun clickUnbindService(view: View) {
        unbindService(connection)
    }

    fun clickAddBook(view: View) {
        Logger.d("Begin AddBook")
        Logger.i(baseContext.toString())
        bookManager?.addBook(Book(10, "物理"))
        Logger.d("Finish AddBook")
    }

    fun clickStartActivity(view: View) {
        startActivity(Intent(this, BookManagerActivity::class.java))
    }
}