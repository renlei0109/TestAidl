// IBookManager.aidl
package com.example.renlei.testaidl;

// Declare any non-default types here with import statements
import com.example.renlei.testaidl.Book;
interface IBookManager {

    List<Book>getBookList();
    void addBook(in Book book);

}
