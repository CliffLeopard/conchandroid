package com.cliff.conch.scene

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.os.Parcel
import android.os.RemoteCallbackList
import com.cliff.conch.scene.aidl.Book
import com.cliff.conch.scene.aidl.IBookManager
import com.cliff.conch.scene.aidl.IOnNewBookArrivedListener
import com.orhanobut.logger.Logger
import java.util.concurrent.CopyOnWriteArrayList

class BookManagerService : Service() {
    private val mBookList = CopyOnWriteArrayList<Book>().apply {
        add(Book(1, "数学"))
        add(Book(2, "语文"))
        add(Book(3, "英语"))
        add(Book(4, "化学"))
    }
    private val mListenerList = RemoteCallbackList<IOnNewBookArrivedListener>()

    private val mBinder: Binder by lazy {
        object : IBookManager.Stub() {
            override fun getBookList(): MutableList<Book> {
                Logger.d("server:getBookList")
                return mBookList
            }

            override fun addBook(book: Book) {
                Logger.d("server:addBook")
                mBookList.add(book)
            }

            override fun registerListener(listener: IOnNewBookArrivedListener?) {
                Logger.d("server:registerListener")
                mListenerList.register(listener)
            }

            override fun unregisterListener(listener: IOnNewBookArrivedListener?) {
                Logger.d("server:unregisterListener")
                mListenerList.unregister(listener)
            }

            override fun onTransact(code: Int, data: Parcel, reply: Parcel?, flags: Int): Boolean {
                Logger.d("server:onTransact")
                return super.onTransact(code, data, reply, flags)
            }
        }
    }

    override fun onBind(intent: Intent): IBinder {
        Logger.d("onBind")
        return mBinder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Logger.d("onUnbind")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        Logger.d("onDestroy")
        super.onDestroy()
    }
}