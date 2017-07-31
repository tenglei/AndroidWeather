package com.example.weather;

import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.weather.adapter.city.CityItem;
import com.example.weather.adapter.note.NoteAdapter;
import com.example.weather.adapter.note.NoteItem;
import com.example.weather.server.BindService;
import com.example.weather.server.TestService;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by 滕磊 on 2017/7/19.
 */

public class CalendarFrag extends Fragment {
    private final static int REQUEST = 2;
    private View view;
    private CalendarView calendarView;
    private ImageButton add_plan;
    private Intent intent;
    private Integer current_select;
    private SharedPreferences sharedPreferences;
    private ListView plan_list;
    private NoteAdapter noteAdapter;
    private ArrayList<NoteItem> items = new ArrayList<>();
    private Integer items_size;
    private boolean flag;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_calendar, container, false);
        sharedPreferences = this.getActivity().getSharedPreferences("plan", Context.MODE_PRIVATE);
        items_size = sharedPreferences.getInt("items_size",0);
        if(items_size>0){
            initData();
        }
        calendarView = view.findViewById(R.id.calendarView);
        add_plan = view.findViewById(R.id.add_note);
        plan_list = (ListView) view.findViewById(R.id.note_list);
        noteAdapter = new NoteAdapter(getActivity(), items);
        plan_list.setAdapter(noteAdapter);
        current_select = sharedPreferences.getInt("current_select", -1);
        add_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getActivity(), NoteEditActivity.class);
                current_select = -1; //啥都没选
                startActivityForResult(intent, REQUEST);
            }
        });
        plan_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                intent = new Intent(getActivity(), NoteEditActivity.class);
                String title = items.get(i).getTitle();
                String start_date = items.get(i).getStart_date();
                String start_time = items.get(i).getStart_time();
                String end_date = items.get(i).getEnd_date();
                String end_time = items.get(i).getEnd_time();
                String local = items.get(i).getLocal();
                String annotation = items.get(i).getAnnotation();

                intent.putExtra("title", title);
                intent.putExtra("start_date", start_date);
                intent.putExtra("start_time", start_time);
                intent.putExtra("end_date", end_date);
                intent.putExtra("end_time", end_time);
                intent.putExtra("local", local);
                intent.putExtra("annotation", annotation);

                current_select = i;
                sharedPreferences.edit().putInt("current_select", i).commit();   //存取序号从0开始

                startActivityForResult(intent, REQUEST);
            }
        });
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Toast.makeText(getActivity(), "点击了" + year + "年" + month + "月" + dayOfMonth + "日", Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0) {
            if (requestCode == REQUEST) {
                String title = data.getStringExtra("title");
                String start_date = data.getStringExtra("start_date");
                String start_time = data.getStringExtra("start_time");
                String end_date = data.getStringExtra("end_date");
                String end_time = data.getStringExtra("end_time");
                String local = data.getStringExtra("local");
                String annotation = data.getStringExtra("annotation");
                if(current_select==-1){
                    items.add(new NoteItem(title,start_date,start_time,end_date,end_time,local,annotation));
                    noteAdapter.notifyDataSetChanged();
                    plan_list.setSelection(items.size()-1);
                    storeNote(items_size,title,start_date,start_time,end_date,end_time,local,annotation);
                    items_size++;
                    sharedPreferences.edit().putInt("items_size",items_size).commit();
                }
                else{
                    items.get(current_select).setTitle(title);
                    items.get(current_select).setStart_date(start_date);
                    items.get(current_select).setStart_time(start_time);
                    items.get(current_select).setEnd_date(end_date);
                    items.get(current_select).setEnd_time(end_time);
                    items.get(current_select).setLocal(local);
                    items.get(current_select).setAnnotation(annotation);
                    storeNote(current_select,title,start_date,start_time,end_date,end_time,local,annotation);
                    noteAdapter.notifyDataSetChanged();
                }
//                Toast.makeText(getActivity(),data.getStringExtra("test"),Toast.LENGTH_SHORT);
                Log.i("test", data.getStringExtra("test"));//ok
//                this.show_city = data.getStringExtra("show_city");
//                sharedPreferences.edit().putString("send_city",this.show_city).commit();
            }
        } else if (resultCode == 1) {
            if (requestCode == REQUEST) {
                Log.i("test", data.getStringExtra("test"));//cancel
            }
        }
    }
    public void storeNote(int i,String title,String start_date,String start_time,String end_date,String end_time,String local,String annotation){
        String storeName = "note" + i;
        if(start_date==null){
            start_date ="null";
        }
        if(start_time==null){
            start_time="null";
        }
        if(end_date==null){
            end_date = "null";
        }
        if(end_time==null){
            end_time = "null";
        }
        if(local.equals("")){
            local = "null";
        }
        if(annotation.equals("")){
            annotation = "null";
        }
        String contain =title +"/"+start_date+"/"+start_time+"/"+end_date+"/"+end_time+"/"+local+"/"+annotation;
        Log.i("print contain",contain);
        sharedPreferences.edit().putString(storeName,contain).commit();
    }
    public void initData(){
        Map<String,?> allContent = sharedPreferences.getAll();
        for(String i:allContent.keySet()){
            Log.i("pring i",allContent.get(i).toString());
            if(!i.equals("current_select")&&!i.equals("items_size")){
                String note_from_store=allContent.get(i).toString();
                String[] res;
                res = note_from_store.split("/");
                String title = res[0];
                String start_date = res[1];
                String start_time = res[2];
                String end_date = res[3];
                String end_time = res[4];
                String local = res[5];
                String annotation = res[6];
                items.add(new NoteItem(title,start_date,start_time,end_date,end_time,local,annotation));
            }
        }
    //    new TestService().addNotification(1000,"test","test","test");
    }
}
