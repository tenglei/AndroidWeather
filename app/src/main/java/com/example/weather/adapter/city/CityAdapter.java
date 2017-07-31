package com.example.weather.adapter.city;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.weather.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by 滕磊 on 2017/7/23.
 */

public class CityAdapter  extends BaseAdapter {
    private final static String city_tag="city";
    private HashMap<String,String> select_situation;
    private int select_index;
    private List<CityItem> items;
    private Context context;
    private SharedPreferences sharedPreferences=null;
    private class Holder{
        private ImageButton delete;
        private TextView city;
        private RadioButton select;
    }
    public CityAdapter(Context context,List<CityItem> items,SharedPreferences sharedPreferences){
        this.items = items;
        this.context = context;
        this.sharedPreferences = sharedPreferences;
    }
    public void delete_item(int index){
        items.remove(index);
        this.notifyDataSetChanged();
    }
    public void setSelectIndex(int index){
        this.select_index = index;
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
        final int delete_num = i;
        final String delete_name;
        Holder holder = null;
        if(view==null){
            holder = new Holder();
            view = LayoutInflater.from(this.context).inflate(R.layout.city_list_item,null);
            holder.delete = (ImageButton)view.findViewById(R.id.delete);
            holder.city = (TextView)view.findViewById(R.id.city_name);
            holder.select = (RadioButton)view.findViewById(R.id.select);
            view.setTag(holder);
        }
        else{
            holder = (Holder)view.getTag();
        }
        holder.delete.setBackgroundResource(R.drawable.delete_button);
        holder.city.setText(this.items.get(i).getCity());
        delete_name = this.items.get(i).getCity();
        if(i==select_index){
            holder.select.setChecked(true);
        }
        else{
            holder.select.setChecked(false);
        }
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("delete",sharedPreferences.getString(delete_name,"null"));
                if(delete_num!=select_index){
                    sharedPreferences.edit().remove(delete_name).commit();
                    delete_item(delete_num);
                }
            }
        });
        return view;
    }

}
