package com.cliff.conch.scene.bp

import android.os.IInterface
import android.os.RemoteCallbackList
import com.cliff.conch.scene.aidl.Book
import com.cliff.conch.scene.aidl.IOnNewBookArrivedListener
import com.cliff.conch.scene.aidl.ProviderBookManager
import com.orhanobut.logger.Logger
import java.util.concurrent.CopyOnWriteArrayList


class BookManagerCenter : ProviderBookManager.Stub() {
    override fun getBookList(): CopyOnWriteArrayList<Book> {
        return mBookList
    }

    override fun addBook(book: Book?) {
        book?.let(mBookList::add)
        val n = mListenerList.beginBroadcast()
        for (i in 0..<n) {
            mListenerList.getBroadcastItem(i).apply {
                onNewBookArrived(book)
                refreshBookCount(mBookList.size)
            }
        }
        mListenerList.finishBroadcast()
    }

    override fun registerListener(listener: IOnNewBookArrivedListener?) {
        listener?.let(mListenerList::register)
        listener?.refreshBookCount(mBookList.size)
        Logger.d("Server端成功注册Listener")
    }

    override fun unregisterListener(listener: IOnNewBookArrivedListener?) {
        listener?.let(mListenerList::unregister)
        Logger.d("Server端成功解除Listener注册")
    }

    companion object {
        private val mBookList = CopyOnWriteArrayList<Book>().apply {
            add(Book(1, "数学"))
            add(Book(2, "语文"))
            add(Book(3, "英语"))
            add(Book(4, "化学"))
        }
        private val mListenerList = MyCallBackList<IOnNewBookArrivedListener> {
            Logger.d("在Server端注册的listener:Listener Died")
        }
    }

    internal class MyCallBackList<E>(private val block: (E) -> Unit) :
        RemoteCallbackList<E>() where E : IInterface {
        override fun onCallbackDied(callback: E) {
            block(callback)
        }
    }
}