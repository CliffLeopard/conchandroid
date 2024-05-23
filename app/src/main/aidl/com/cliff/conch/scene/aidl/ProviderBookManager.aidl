// ProviderBookManager.aidl
package com.cliff.conch.scene.aidl;
import com.cliff.conch.scene.aidl.Book;
import com.cliff.conch.scene.aidl.IOnNewBookArrivedListener;
interface ProviderBookManager {
    List<Book> getBookList();
    oneway void addBook(in Book book);
    void registerListener(IOnNewBookArrivedListener listener);
    void unregisterListener(IOnNewBookArrivedListener listener);
}