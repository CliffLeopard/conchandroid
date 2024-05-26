package com.cliff.conch.scene.aidl;
import com.cliff.conch.scene.aidl.Book;

interface IOnNewBookArrivedListener {
    void onNewBookArrived(in Book newBook);
    void refreshBookCount(in int cout);
}