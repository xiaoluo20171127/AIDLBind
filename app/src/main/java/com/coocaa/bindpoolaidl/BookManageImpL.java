package com.coocaa.bindpoolaidl;

import android.os.RemoteException;

import java.util.List;

public class BookManageImpL extends IBookManage.Stub {
    private List<Book> mBookList;
    BookManageImpL(List<Book> books){
        mBookList = books;
    }
    @Override
    public void addBook(Book book) throws RemoteException {
        if(mBookList!=null&&!mBookList.contains(book)) mBookList.add(book);
    }

    @Override
    public void deleteBook(Book book) throws RemoteException {
        if(mBookList!= null&&mBookList.contains(book)) mBookList.remove(book);
    }

    @Override
    public List<Book> getAllBook() throws RemoteException {
        return mBookList;
    }
}
