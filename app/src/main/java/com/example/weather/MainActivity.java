package com.example.weather;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

public class MainActivity extends Activity implements GestureDetector.OnGestureListener {
    private  SwipeRefreshLayout swipeRefreshLayout;
    public Fragment[] fragments;
    private GestureDetector detector;
    private int local=0;
    private int distant=40;

    public static Context getContext() {
        return MainActivity.getContext();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        detector=new GestureDetector(this);

        ImageButton showWeather = (ImageButton) findViewById(R.id.show__weather);
        ImageButton showCalendar = (ImageButton) findViewById(R.id.show__calendar);
        //ImageButton showOutPlan = (ImageButton) findViewById(R.id.show_outplan);

        setUpFragment();

        showWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.main_content,fragments[0]).commit();
            }
        });
        showCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.main_content,fragments[1]).commit();
            }
        });
//        showOutPlan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getFragmentManager().beginTransaction().replace(R.id.main_content,fragments[2]).commit();
//            }
//        });
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        if(local==1)
        {
            if(motionEvent.getX()-motionEvent1.getX()>distant)
            {
                getFragmentManager().beginTransaction().replace(R.id.main_content,fragments[1]).commit();
                local=0;
            }
            if(motionEvent1.getX()-motionEvent.getX()>distant)
            {
                getFragmentManager().beginTransaction().replace(R.id.main_content,fragments[1]).commit();
                local=0;
            }
        }
//        else if(local==2){
//            if(motionEvent.getX()-motionEvent1.getX()>distant)
//            {
//                getFragmentManager().beginTransaction().replace(R.id.main_content,fragments[1]).commit();
//                local=0;
//            }
//            if(motionEvent1.getX()-motionEvent.getX()>distant)
//            {
//                getFragmentManager().beginTransaction().replace(R.id.main_content,fragments[0]).commit();
//                local=1;
//            }
//        }
        else if(local==0){
            if(motionEvent.getX()-motionEvent1.getX()>distant)
            {
                getFragmentManager().beginTransaction().replace(R.id.main_content,fragments[0]).commit();
                local=1;
            }
            if(motionEvent1.getX()-motionEvent.getX()>distant)
            {
                getFragmentManager().beginTransaction().replace(R.id.main_content,fragments[0]).commit();
                local=1;
            }
        }
        return false;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
            return detector.onTouchEvent(event);
       }

    private void setUpFragment(){
        fragments = new Fragment[3];
        WeatherFrag weatherfragment = new WeatherFrag();
        CalendarFrag calendarfragment = new CalendarFrag();
        NoteFrag outplanfragment = new NoteFrag();
        fragments[0] = weatherfragment;
        fragments[1] = calendarfragment;
        fragments[2] = outplanfragment;
        getFragmentManager().beginTransaction().replace(R.id.main_content,fragments[0]).commit();
    }

}
