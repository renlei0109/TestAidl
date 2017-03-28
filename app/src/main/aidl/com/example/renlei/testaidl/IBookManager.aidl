// IBookManager.aidl
package com.example.renlei.testaidl;
// Declare any non-default types here with import statements
import com.example.renlei.testaidl.Book;
import com.example.renlei.testaidl.IAddBookCallBack;
interface IBookManager {

    List<Book>getBookList();
    void addBook(in Book book,IAddBookCallBack callback);

}
