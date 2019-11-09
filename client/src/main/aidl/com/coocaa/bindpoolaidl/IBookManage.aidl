// IBookManage.aidl
package com.coocaa.bindpoolaidl;
import com.coocaa.bindpoolaidl.Book;
// Declare any non-default types here with import statements

interface IBookManage {
    void addBook(inout Book book);
    void deleteBook(inout Book book);
    List<Book> getAllBook();
}
