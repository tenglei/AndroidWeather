package com.example.weather.adapter.city;

import com.example.weather.server.WeatherData;

/**
 * Created by 滕磊 on 2017/7/23.
 */

public class CityItem {
    private String city;
    private boolean select;
    public CityItem(String city){
        this.city = city;
    }
    public void setCity(String city){
        this.city = city;
    }
    public String getCity(){
        return this.city;
    }
    public void setSelect(boolean select){
        this.select = select;
    }
    public boolean getSelect(){
        return this.select;
    }
}
