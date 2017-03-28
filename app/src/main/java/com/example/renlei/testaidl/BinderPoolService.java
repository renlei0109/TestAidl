package com.example.renlei.testaidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class BinderPoolService extends Service {
    private List<Book>mBooks = new ArrayList<>();
    //专门用来删除跨进程listener的接口。根据底层binder对象删除。进程终止时有自动移除listener功能。
    private RemoteCallbackList<IAddBookCallBack> callbackList = new RemoteCallbackList<>();
    public BinderPoolService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binderPool;
    }


    public static  final int CODE_IBOOK_MANAGER = 1;
    public static  final int CODE_PRINT = 2;
    IBinderPool.Stub binderPool = new IBinderPool.Stub(){
        @Override
        public IBinder queryBinder(int binderCode) throws RemoteException {
            IBinder binder = null;
            switch (binderCode){
                case CODE_IBOOK_MANAGER:
                    binder = iBookManagerBinder;
                    break;
                case CODE_PRINT:
                    binder = printBinder;
                    break;
            }
            return binder;
        }
    };

    IPrint.Stub printBinder = new IPrint.Stub() {
        @Override
        public String print(List<Book> book) throws RemoteException {
            Log.d("renlei","printBinder*****"+book.toString());
            return book.toString();
        }
    };

    IBookManager.Stub iBookManagerBinder = new IBookManager.Stub(){
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
