package com.example.renlei.testaidl;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    Button mGetBtn;
    Button mAddBtn;
    TextView mResultTV;
    Button mBindBtn;
    IBookManager bookManager;
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
                    mResultTV.setText("Result:"+bookManager.getBookList().toString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        mBindBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MyService.class);
                bindService(intent,connection, Context.BIND_AUTO_CREATE);
            }
        });

    }

    private IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            if (bookManager == null)
                return;
            bookManager.asBinder().unlinkToDeath(deathRecipient,0);
            bookManager = null;
            //// TODO: 2017/3/28  重新绑定 或者其他逻辑
        }
    };
    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("renlei","onServiceConnected");
            try {
                bookManager = IBookManager.Stub.asInterface(service);
                bookManager.registerListener(callBack);
                service.linkToDeath(deathRecipient,0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

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
                    Toast.makeText(MainActivity.this,msg,Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onFail(final String msg) throws RemoteException {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this,msg,Toast.LENGTH_SHORT).show();
                }
            });
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bookManager != null){
            try {
                bookManager.unRegisterListener(callBack);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }



}
