package com.example.renlei.testaidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MyService extends Service {
    private List<Book>mBooks = new ArrayList<>();
    public MyService() {
        Log.d("renlei","MyService");
        mBooks.add(new Book(1,"renlei"));
        mBooks.add(new Book(2,"renlei2"));
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return binder;
    }

    IBookManager.Stub binder = new IBookManager.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBooks;
        }

        @Override
        public void addBook(Book book,IAddBookCallBack callBack) throws RemoteException {
            mBooks.add(book);
            Log.d("renlei","callback"+(callBack==null));
            if (callBack != null){
                callBack.onSuccess("成功"+ book.toString());
            }
        }
    };
}
