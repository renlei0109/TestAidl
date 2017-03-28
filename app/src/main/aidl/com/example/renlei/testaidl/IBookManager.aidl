// IBookManager.aidl
package com.example.renlei.testaidl;
// Declare any non-default types here with import statements
import com.example.renlei.testaidl.Book;
import com.example.renlei.testaidl.IAddBookCallBack;
interface IBookManager {

    List<Book>getBookList();
    //除了基本数据类型其他类型必须标上输入输出类型 ，in , out , inout
    void addBook(in Book book,IAddBookCallBack callback);
    void registerListener(IAddBookCallBack callback);
    void unRegisterListener(IAddBookCallBack callback);
}
