package com.example.renlei.testaidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

/**
 * Time 2017/3/28.
 * User renlei
 * Email renlei@xiaomi.com
 */

public class BinderPool {
    Context context;
    private IBinderPool mBinderPool;
    private static volatile BinderPool INSTANCE = null;

    private BinderPool(Context context) {
        this.context = context;
    }

    public static BinderPool getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (BinderPool.class) {
                INSTANCE = new BinderPool(context);
                INSTANCE.init();
            }
        }
        return INSTANCE;
    }

    private void init() {
        Log.d("renlei", "binderpool--" + "init");
        Intent intent = new Intent(context, BinderPoolService.class);
        context.bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("renlei", "binderpool--" + "onServiceConnected");
            mBinderPool = IBinderPool.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    public IBinder queryBinder(int binderCode) {
        try {
            if (mBinderPool == null) {
                Log.d("renlei", "binderpool--" + "mBinderPool == null");
                return null;
            }
            return mBinderPool.queryBinder(binderCode);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }
}
