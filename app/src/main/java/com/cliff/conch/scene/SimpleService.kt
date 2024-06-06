package com.cliff.conch.scene

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import com.cliff.conch.scene.aidl.Book
import com.cliff.conch.scene.aidl.IBookManager
import com.cliff.conch.scene.aidl.IOnNewBookArrivedListener
import com.orhanobut.logger.Logger

class SimpleService : Service() {
    companion object {
        val handler = Handler()
    }

    override fun onCreate() {
        Logger.i("SimpleService:OnCreate")
        handler.postDelayed(kotlinx.coroutines.Runnable {
            stopSelf()
        }, 5000)
        super.onCreate()
    }

    override fun onBind(intent: Intent): IBinder {
        Logger.i("SimpleService:onBind", intent)
        return bookManager
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Logger.i("SimpleService:onStartCommand:$intent  $flags  $startId")
        return START_REDELIVER_INTENT
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Logger.i("SimpleService:onUnbind", intent)
        return super.onUnbind(intent)
    }

    override fun onRebind(intent: Intent?) {
        Logger.i("SimpleService:onRebind", intent)
        super.onRebind(intent)
    }

    override fun onDestroy() {
        Logger.i("SimpleService:onDestroy")
        super.onDestroy()
    }

    private val bookManager by lazy {
        object : IBookManager.Stub() {
            override fun getBookList(): MutableList<Book> = mutableListOf()
            override fun addBook(book: Book?) {}
            override fun registerListener(listener: IOnNewBookArrivedListener?) {}
            override fun unregisterListener(listener: IOnNewBookArrivedListener?) {}
        }
    }
}