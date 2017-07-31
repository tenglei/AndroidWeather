package com.example.weather.server;

/**
 * Created by 滕磊 on 2017/7/22.
 */

import java.util.List;
//
//      {
//        "results":[{
//        "location":{
//        "id":"WS0E9D8WN298", "name":"广州", "country":"CN", "path":"广州,广州,广东,中国", "timezone":
//        "Asia/Shanghai", "timezone_offset":"+08:00"
//        },"now":{
//        "text":"阴", "code":"9", "temperature":"34", "humidity":"57", "wind_direction":
//        "东南", "wind_speed":"7.92", "wind_scale":"2"
//        },"daily":[{
//        "date":"2017-07-25", "text_day":"多云", "code_day":"4", "text_night":"多云", "code_night":
//        "4", "high":"34", "low":"26"
//        },{
//        "date":"2017-07-26", "text_day":"多云", "code_day":"4", "text_night":"多云", "code_night":
//        "4", "high":"34", "low":"26"
//        },{
//        "date":"2017-07-27", "text_day":"多云", "code_day":"4", "text_night":"雷阵雨", "code_night":
//        "11", "high":"35", "low":"27"
//        },{
//        "date":"2017-07-28", "text_day":"晴", "code_day":"0", "text_night":"晴", "code_night":
//        "0", "high":"35", "low":"27"
//        },{
//        "date":"2017-07-29", "text_day":"晴", "code_day":"0", "text_night":"晴", "code_night":
//        "0", "high":"35", "low":"27"
//        }],"air":{
//        "city":{
//        "aqi":"70", "last_update":"2017-07-25T14:00:00+08:00", "quality":"良"
//        }
//        },"alarms":[{
//        "type":"高温", "level":"黄色", "description":
//        "广州市气象局于07月25日10时41分发布高温黄色预警信号，请注意防御。", "pub_date":"2017-07-25T10:41:00+08:00"
//        }],"last_update":"2017-07-25T14:50:00+08:00"
//        }]}

public class WeatherData {
    public List<Result> results;

    public static class Result {
        public Location location;
        public Now now;
        public List<Daily> daily;
        public Air air;
        public List<Alarms> alarms = null;
        public String last_update;

        public static class Alarms {
            public String title;
            public String type;
            public String level;
            public String status;
            public String description;
            public String pubdate;
        }

        public static class Location {
            public String id;
            public String name;
            public String country;
            public String path;
            public String time_zone;
            public String timezone_offset;
        }

        public static class Now {
            public String text;
            public String code;
            public String temperature;
            public String humidity;
            public String wind_direction;
            public String wind_speed;
            public String wind_scale;
        }

        public static class Daily {
            public String date;
            public String text_day;
            public String code_day;
            public String text_night;
            public String code_night;
            public String high;
            public String low;
        }

        public static class Air {
            public City city;

            public static class City {
                public String aqi;
                public String last_update;
                public String quality;
            }
        }

    }
}
