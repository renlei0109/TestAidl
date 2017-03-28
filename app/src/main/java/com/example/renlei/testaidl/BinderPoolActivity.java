package com.example.renlei.testaidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class BinderPoolActivity extends AppCompatActivity {
    Button mGetBtn;
    Button mAddBtn;
    TextView mResultTV;
    Button mBindBtn;
//    IBookManager bookManager;
    int i =10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAddBtn =  (Button) findViewById(R.id.addBtn);
        mGetBtn =  (Button) findViewById(R.id.getBtn);
        mResultTV =  (TextView) findViewById(R.id.result);
        mBindBtn = (Button) findViewById(R.id.bind);
        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
//                    bookManager.addBook();
                    IBinder bookBinder = BinderPool.getInstance(BinderPoolActivity.this).queryBinder(BinderPoolService.CODE_IBOOK_MANAGER);
                    IBookManager bookManager =  IBookManager.Stub.asInterface(bookBinder);
                    if (bookManager == null){
                        Log.d("renlei","bookManager == null"+(bookManager == null));
                        return ;
                    }
                    bookManager.addBook(new Book(i, "renlei" + i),callBack);

                    i++;
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        mGetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

//                    mResultTV.setText("Result:"+bookManager.getBookList().toString());
//                    mResultTV.setText("Result:"+bookManager.getBookList().toString());
                    IBinder bookBinder = BinderPool.getInstance(BinderPoolActivity.this).queryBinder(BinderPoolService.CODE_IBOOK_MANAGER);
                    IBookManager bookManager =  IBookManager.Stub.asInterface(bookBinder);
                    List<Book>books = bookManager.getBookList();

                    IBinder printBinder = BinderPool.getInstance(BinderPoolActivity.this).queryBinder(BinderPoolService.CODE_PRINT);
                    IPrint iPrint =  IPrint.Stub.asInterface(printBinder);
                    mResultTV.setText(iPrint.print(books));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        mBindBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BinderPool.getInstance(BinderPoolActivity.this);
            }
        });

    }



    IAddBookCallBack callBack =  new IAddBookCallBack.Stub(){
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void onSuccess(final String msg) throws RemoteException {
            Log.d("renlei","onSuccess"+msg);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(BinderPoolActivity.this,msg,Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onFail(final String msg) throws RemoteException {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(BinderPoolActivity.this,msg,Toast.LENGTH_SHORT).show();
                }
            });
        }
    };




}
