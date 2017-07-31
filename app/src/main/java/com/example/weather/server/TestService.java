package com.example.weather.server;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.weather.MainActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 滕磊 on 2017/7/27.
 */

public class TestService extends Service {

    static Timer timer = null;

    // 清除通知
    public static void cleanAllNotification() {
        NotificationManager mn = (NotificationManager) MainActivity
                .getContext().getSystemService(NOTIFICATION_SERVICE);
        mn.cancelAll();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    // 添加通知
    public static void addNotification(int delayTime, String tickerText,
                                       String contentTitle, String contentText) {
        Intent intent = new Intent(MainActivity.getContext(),
                TestService.class);
        intent.putExtra("delayTime", delayTime);
        intent.putExtra("tickerText", tickerText);
        intent.putExtra("contentTitle", contentTitle);
        intent.putExtra("contentText", contentText);
        MainActivity.getContext().startService(intent);
    }

    public void onCreate() {
        Log.e("addNotification", "===========create=======");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(final Intent intent, int flags, int startId) {

        long period = 24 * 60 * 60 * 1000; // 24小时一个周期
        int delay = intent.getIntExtra("delayTime", 0);
        if (null == timer) {
            timer = new Timer();
        }
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                NotificationManager mn = (NotificationManager) TestService.this
                        .getSystemService(NOTIFICATION_SERVICE);
                Notification.Builder builder = new Notification.Builder(
                        TestService.this);

                Intent notificationIntent = new Intent(TestService.this,
                        MainActivity.class);// 点击跳转位置
                PendingIntent contentIntent = PendingIntent.getActivity(
                        TestService.this, 0, notificationIntent, 0);
                builder.setContentIntent(contentIntent);
             //   builder.setSmallIcon(R.drawable.ic_launcher);// 设置通知图标
                builder.setTicker(intent.getStringExtra("tickerText")); // 测试通知栏标题
                builder.setContentText(intent.getStringExtra("contentText")); // 下拉通知啦内容
                builder.setContentTitle(intent.getStringExtra("contentTitle"));// 下拉通知栏标题
                builder.setAutoCancel(true);// 点击弹出的通知后,让通知将自动取消
               // builder.setVibrate(new long[] { 0, 2000, 1000, 4000 }); // 震动需要真机测试-延迟0秒震动2秒延迟1秒震动4秒
                // builder.setSound(Uri.withAppendedPath(Audio.Media.INTERNAL_CONTENT_URI,
                // "5"));//获取Android多媒体库内的铃声
                // builder.setSound(Uri.parse("file:///sdcard/xx/xx.mp3"))
                // ;//自定义铃声
                builder.setDefaults(Notification.DEFAULT_ALL);// 设置使用系统默认声音
                // builder.addAction("图标", title, intent); //此处可设置点击后 打开某个页面
                Notification notification = builder.build();
              //  notification.flags = notification.FLAG_INSISTENT;// 声音无限循环
                mn.notify((int) System.currentTimeMillis(), notification);
            }
        }, delay, period);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.e("addNotification", "===========destroy=======");
        super.onDestroy();
    }
}