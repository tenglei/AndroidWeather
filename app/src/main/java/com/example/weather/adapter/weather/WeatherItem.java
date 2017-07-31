package com.example.weather.adapter.weather;

/**
 * Created by 滕磊 on 2017/7/19.
 */

public class WeatherItem {
    private String date;
    private int icon;
    private String weather;
    private String quality;
    private String temperature_highest;
    private String getTemperature_lowest;
    public WeatherItem(String date,int icon,String weather,String quality,String temperature_highest,String getTemperature_lowest){
        this.date = date;
        this.icon = icon;
        this.weather = weather;
        this.quality = quality;
        this.temperature_highest = temperature_highest;
        this.getTemperature_lowest = getTemperature_lowest;
    }
    public String getDate(){
        return this.date;
    }
    public int getIcon(){
        return this.icon;
    }
    public String getWeather(){
        return this.weather;
    }
    public String getQuality(){
        return this.quality;
    }
    public String getTemperature_highest(){
        return this.temperature_highest;
    }
    public String getGetTemperature_lowest(){
        return this.getTemperature_lowest;
    }


}
