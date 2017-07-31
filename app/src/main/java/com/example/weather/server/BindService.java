package com.example.weather.server;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.text.format.Time;
import android.util.Log;

/**
 * Created by 滕磊 on 2017/7/27.
 */

public class BindService extends Service {
    private static final String TAG = "BindService";
    private NotificationManager messageNotificatioManager = null;
    private MyBinder myBinder = new MyBinder();
    public void myMethod(){
        messageNotificatioManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

    }
    @Override
    public IBinder onBind(Intent intent) {
        Log.v(TAG, "BindService-->onBind()");
        return myBinder;
    }

    public class MyBinder extends Binder {

        public BindService getService1(){
            return BindService.this;
        }
    }
    @Override
    public void onCreate() {
        Log.v(TAG, "BindService-->onCreate()");
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Log.v(TAG, "BindService-->onStart()");
        super.onStart(intent, startId);
    }

    @Override
    public void onDestroy() {
        Log.v(TAG, "BindService-->onDestroy()");
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.v(TAG, "BindService-->onUnbind()");
        return super.onUnbind(intent);
    }
}
