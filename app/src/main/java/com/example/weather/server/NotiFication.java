package com.example.weather.server;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.text.format.Time;

/**
 * Created by 滕磊 on 2017/7/27.
 */

public class NotiFication extends Thread{
    private String title;
    private String context;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    Time time;
    private Notification messageNotification = null;
    private NotificationManager messageNotificatioManager = null;

    public NotiFication(String title, String context, int year, int month, int day, int hour, int minute,NotificationManager messageNotificatioManager) {
        this.title = title;
        this.context = context;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.messageNotificatioManager = messageNotificatioManager;
        messageNotification = new Notification();
        messageNotification.tickerText = "新消息";
        messageNotification.defaults = Notification.DEFAULT_SOUND;
    }

    public void isTimeUp() {
       new Thread(new Runnable() {
            @Override
            public void run() {
                int timer = 0;
                while (true) {
                    try {
                        Thread.currentThread().sleep(3000);
                        time.setToNow();
                        if (year == time.year && month == time.month && day == time.monthDay && hour == time.hour && minute == minute) {
                            //notification
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
