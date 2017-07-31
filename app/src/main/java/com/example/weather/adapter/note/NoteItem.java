package com.example.weather.adapter.note;

import android.provider.ContactsContract;

/**
 * Created by 滕磊 on 2017/7/26.
 */

public class NoteItem {
    private String title;
    private String annotation;
    private String start_date;
    private String start_time;
    private String end_date;
    private String end_time;
    private String local;
    public NoteItem(String title,String annotation,String start_date,String start_time,String end_date,String end_time,String local){
        this.title = title;
        this.annotation = annotation;
        this.start_date = start_date;
        this.start_time = start_time;
        this.end_date = end_date;
        this.end_time = end_time;
        this.local = local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getLocal() {

        return local;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getTitle() {
        return title;
    }

    public String getAnnotation() {
        return annotation;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getStart_time() {
        return start_time;
    }

    public String getEnd_date() {
        return end_date;
    }

    public String getEnd_time() {
        return end_time;
    }
}
