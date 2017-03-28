package com.example.renlei.testaidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MyService extends Service {
    private List<Book>mBooks = new ArrayList<>();
    //专门用来删除跨进程listener的接口。根据底层binder对象删除。进程终止时有自动移除listener功能。
    private RemoteCallbackList<IAddBookCallBack>callbackList = new RemoteCallbackList<>();

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
            int N = callbackList.beginBroadcast();
            for (int i = 0;i<N;i++){
                IAddBookCallBack addBookCallBack = callbackList.getBroadcastItem(i);
                if (addBookCallBack != null){
                    addBookCallBack.onSuccess("成功2222"+book.toString());
                }
            }
            callbackList.finishBroadcast();
        }

        @Override
        public void registerListener(IAddBookCallBack callback) throws RemoteException {
            if (callback != null){
                callbackList.register(callback);
            }
            int N = callbackList.beginBroadcast();
            callbackList.finishBroadcast();
            Log.d("renlei","registerListener---"+N);
        }

        @Override
        public void unRegisterListener(IAddBookCallBack callback) throws RemoteException {
            if (callback != null){
                callbackList.unregister(callback);
            }
            int N = callbackList.beginBroadcast();
            callbackList.finishBroadcast();
            Log.d("renlei","unRegisterListener---"+N);
        }
    };
}
