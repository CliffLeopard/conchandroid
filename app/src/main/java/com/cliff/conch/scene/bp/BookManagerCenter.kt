package com.cliff.conch.scene.bp

import android.os.RemoteCallbackList
import com.cliff.conch.scene.aidl.Book
import com.cliff.conch.scene.aidl.IOnNewBookArrivedListener
import com.cliff.conch.scene.aidl.ProviderBookManager
import java.util.concurrent.CopyOnWriteArrayList


class BookManagerCenter : ProviderBookManager.Stub() {
    override fun getBookList(): MutableList<Book> {
        return mBookList
    }

    override fun addBook(book: Book?) {
        book?.let(mBookList::add)
        val n = mListenerList.beginBroadcast()
        for (i in 0..<n) {
            mListenerList.getBroadcastItem(i).onNewBookArrived(book)
        }
        mListenerList.finishBroadcast()
    }

    override fun registerListener(listener: IOnNewBookArrivedListener?) {
        listener?.let(mListenerList::register)
    }

    override fun unregisterListener(listener: IOnNewBookArrivedListener?) {
        listener?.let(mListenerList::unregister)
    }

    companion object {
        private val mBookList = CopyOnWriteArrayList<Book>().apply {
            add(Book(1, "数学"))
            add(Book(2, "语文"))
            add(Book(3, "英语"))
            add(Book(4, "化学"))
        }
        private val mListenerList = RemoteCallbackList<IOnNewBookArrivedListener>()
    }
}