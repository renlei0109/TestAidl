// IPrint.aidl
package com.example.renlei.testaidl;

// Declare any non-default types here with import statements
import com.example.renlei.testaidl.Book;
interface IPrint {
    String print(in List<Book> book);
}
