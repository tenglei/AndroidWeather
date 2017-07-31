package com.example.weather.adapter.weather;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weather.R;

import java.lang.reflect.Field;
import java.util.ArrayList;


/**
 * Created by 滕磊 on 2017/7/19.
 */

public class WeatherAdapter extends BaseAdapter{
    private ArrayList<WeatherItem> items = new ArrayList<WeatherItem>();
//    private static final int sunny=0;
//    private static final int cloudy=1;
//    private static final int light_rain = 2;
//    private static final int moderate_rain = 3;
//    private static final int heavy_rain = 4;
//    private static final int thunder_storm = 5;
    private Context context;
//    private int[] weather={sunny,cloudy,light_rain,moderate_rain,heavy_rain,thunder_storm};
    private class Holder{
        private TextView date;
        private ImageView weather_icon;
        private TextView weather_detail;
        private TextView air_quality;
        private TextView temperature_range;
    }
    public WeatherAdapter(Context context,ArrayList<WeatherItem> items){
        this.items = items;
        this.context = context;
    }
    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder=null;
        String highest;
        String lowest;
        Log.v("Adapter","before judge");
        if(view==null){
            holder = new Holder();
            view = LayoutInflater.from(this.context).inflate(R.layout.weather_list_item,null);
            holder.date = (TextView)view.findViewById(R.id.today);
            holder.weather_icon = (ImageView) view.findViewById(R.id.weather_icon);
            holder.weather_detail = (TextView)view.findViewById(R.id.weather_detail);
            holder.air_quality = (TextView)view.findViewById(R.id.list_air_quality);
            holder.temperature_range=(TextView)view.findViewById(R.id.temperature_range);
            view.setTag(holder);
        }
        else{
            holder = (Holder)view.getTag();
        }
        Log.v("Adapter","after judge");
        holder.date.setText(this.items.get(i).getDate());
        holder.weather_detail.setText(this.items.get(i).getWeather());
        holder.air_quality.setText(this.items.get(i).getQuality());
        highest = this.items.get(i).getTemperature_highest();
        lowest = this.items.get(i).getGetTemperature_lowest();
        holder.temperature_range.setText(highest+"/"+lowest);
        holder.weather_icon.setBackgroundResource(getResIdByName(String.valueOf(this.items.get(i).getIcon())));
       // holder.weather_icon.setText(this.items.get(i).getIcon());//panduan
        Log.v("Adapter","finish give ");
        return view;
    }
    public int getResIdByName(String name) {
        name = "bd" + name;
        Class drawable  =  R.drawable.class;
        Field field = null;
        int id ;
        try {
            field = drawable.getField(name);
            id = field.getInt(field.getName());
        } catch (Exception e) {
            id=R.drawable.bd99;
            Log.e("ERROR", "PICTURE NOT　FOUND！");
        }
        return id;
    }
}
