package com.cliff.conch.box.dp;

import com.cliff.conch.scene.aidl.Book;
import com.orhanobut.logger.Logger;

public class ManagerImpl implements IManager, IManager2 {
    @Override
    public void addBook(Book book) {
        Logger.i("ManagerImpl:addBook");
    }
    @Override
    public void lendBook() {
        Logger.i("ManagerImpl:lendBook");
    }
}
