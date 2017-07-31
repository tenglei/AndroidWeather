package com.example.weather;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.weather.adapter.weather.WeatherAdapter;
import com.example.weather.adapter.weather.WeatherItem;
import com.example.weather.server.WeatherData;
import com.example.weather.server.InitUrl;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;

import android.os.Handler;

import static java.lang.Math.exp;


/**
 * Created by 滕磊 on 2017/7/19.
 */

public class WeatherFrag extends Fragment {
    private SharedPreferences sharedPreferences ;
    public final static int REQUESTCODE = 1;
    public InitUrl service;
    protected ListView recent_view = null;
    protected WeatherAdapter adapter = null;
    protected View view = null;
    protected String url = null;
    private final static int day = 0;
    private final static int night = 1;
    private final static int dew_point = 15;
    protected ArrayList<WeatherItem> items = new ArrayList<WeatherItem>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private ImageButton edit_citys;
    public String default_city;
    public String show_city = "no_city";
    DecimalFormat df = new DecimalFormat("######0.0");
    public String weather_share;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    items.clear();
                    weather_share = "";
                    Gson gson = new Gson();
                    Log.v("WeatherFrag", msg.obj.toString());
                    WeatherData data = gson.fromJson(msg.obj.toString(), WeatherData.class);
                    double body_temperature;
                    double temperature_double = Double.parseDouble(data.results.get(0).now.temperature);
                    // double humidity = Double.parseDouble(data.results.get(0).now.humidity);
                    body_temperature = (temperature_double) - 0.55 * (1 - (exp((17.269 * (dew_point)) / ((dew_point) + 237.3)) / exp((17.269 * (temperature_double)) / ((temperature_double) + 237.3)))) * (temperature_double - 14);
                    //   THI = (温度) - 0.55* (1 - (EXP(( 17.269*(露点))╱((露点) + 237.3 ))╱EXP (( 17.269* (温度))╱((温度) + 237.3)))) * ( 温度 - 14)


                    TextView city = view.findViewById(R.id.city);
                    TextView area = view.findViewById(R.id.area);
                    TextView temperature = view.findViewById(R.id.temperature);
                    TextView weather = view.findViewById(R.id.weather);
                    TextView quality = view.findViewById(R.id.air_quality);
                    TextView wind = view.findViewById(R.id.wind);
                    TextView wind_level = view.findViewById(R.id.wind_level);
                    TextView humidity = view.findViewById(R.id.relative_humidity_level);
                    TextView temperature_feel = view.findViewById(R.id.temperature_feel_level);
                    RelativeLayout relativelayout = view.findViewById(R.id.today_weather);
                    relativelayout.setBackgroundResource(getResIdByName(selectBackGround(data.results.get(0).daily.get(0).code_night)));
                    TextView alarm = view.findViewById(R.id.alarms);



                    String[] area_tmp = data.results.get(0).location.path.split(",");

                    // java.lang.reflect.Type type = new TypeToken<WeatherData>() {}.getType();
                    Log.v("WeatherFrag", msg.obj.toString());
                    WeatherItem today = new WeatherItem("今天", Integer.parseInt(data.results.get(0).daily.get(0).code_night), "早:" + data.results.get(0).daily.get(0).text_day, "晚:" + data.results.get(0).daily.get(0).text_night, data.results.get(0).daily.get(0).high, data.results.get(0).daily.get(0).low);
                    WeatherItem tomorrow = new WeatherItem("明天", Integer.parseInt(data.results.get(0).daily.get(1).code_night), "早:" + data.results.get(0).daily.get(1).text_day, "晚:" + data.results.get(0).daily.get(1).text_night, data.results.get(0).daily.get(1).high, data.results.get(0).daily.get(1).low);
                    WeatherItem day_after_tomorrow = new WeatherItem("后天", Integer.parseInt(data.results.get(0).daily.get(2).code_night), "早:" + data.results.get(0).daily.get(2).text_day, "晚:" + data.results.get(0).daily.get(2).text_night, data.results.get(0).daily.get(2).high, data.results.get(0).daily.get(2).low);
                    items.add(today);
                    items.add(tomorrow);
                    items.add(day_after_tomorrow);

                    city.setText(data.results.get(0).location.name);
                    weather_share+=data.results.get(0).location.name;
                    area.setText(area_tmp[area_tmp.length - 2] + "," + area_tmp[area_tmp.length - 1]);
                    temperature.setText(data.results.get(0).now.temperature);
                    weather_share+=" "+data.results.get(0).now.temperature+"度";
                    if (dayOrNight() == day) {
                        weather.setText(data.results.get(0).daily.get(0).text_day);
                    } else {
                        weather.setText(data.results.get(0).daily.get(0).text_night);
                    }
                    quality.setText("空气" + data.results.get(0).air.city.quality + " " + data.results.get(0).air.city.aqi);
                    weather_share +="空气" + data.results.get(0).air.city.quality + " " + data.results.get(0).air.city.aqi;
                    wind.setText(data.results.get(0).now.wind_direction + "风");
                    wind_level.setText(data.results.get(0).now.wind_scale + "级");
                    humidity.setText(data.results.get(0).now.humidity + "%");
                    temperature_feel.setText(String.valueOf(df.format(body_temperature)));
                    if(data.results.get(0).alarms.size()!=0){
                        alarm.setText(data.results.get(0).alarms.get(0).type+data.results.get(0).alarms.get(0).level+"预警:"+data.results.get(0).alarms.get(0).description);
                    }
                    else{
                        alarm.setText("");
                    }
                    adapter = new WeatherAdapter(getActivity(), items);
                    recent_view = (ListView) view.findViewById(R.id.recent_weather);
                    recent_view.setAdapter(adapter);
                    break;
                case 1:
                    Gson gson1 = new Gson();
                    Log.v("WeatherFrag", msg.obj.toString());
                    WeatherData data1 = gson1.fromJson(msg.obj.toString(), WeatherData.class);
                    default_city = data1.results.get(0).location.name;
                    Log.i("reserror",default_city);
                    break;


            }

        }

    };
    private void sendHttp(final int tag) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (tag == 1) {
                    try {
                        String local_url = service.generateGetDiaryWeatherURL(
                                "宁明",
                                "zh-Hans",
                                "c",
                                "0",
                                "2",
                                true
                        );
                        HttpURLConnection connection = null;
                        try {
                            URL Url = new URL(local_url);
                            connection = (HttpURLConnection) Url.openConnection();
                            connection.setRequestMethod("GET");
                            connection.setConnectTimeout(8000);
                            connection.setReadTimeout(8000);
                            InputStream in = connection.getInputStream();
                            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                            StringBuilder response = new StringBuilder();
                            String line;
                            while ((line = reader.readLine()) != null) {
                                response.append(line);
                            }
                            Log.v("RUN", response.toString());
                            Message message = new Message();
                            message.what = 1;
                            message.obj = response.toString();
                            handler.sendMessage(message);
                        } catch (Exception e) {

                        }
                    } catch (Exception e) {
                        System.out.println("Exception:" + e);
                    }
                } else {
                    HttpURLConnection connection = null;
                    try {
                        URL Url = new URL(url);
                        connection = (HttpURLConnection) Url.openConnection();
                        connection.setRequestMethod("GET");
                        connection.setConnectTimeout(8000);
                        connection.setReadTimeout(8000);
                        InputStream in = connection.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                        Log.v("RUN", response.toString());
                        Message message = new Message();
                        message.what = 0;
                        message.obj = response.toString();
                        handler.sendMessage(message);
                    } catch (Exception e) {

                    }
                }


            }
        }).start();
    }
//    private String readStream(InputStream inputStream) {
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        byte[] buffer = new byte[1024];
//        int length = 0;
//        try {
//            while ((length = inputStream.read(buffer)) != -1) {
//                byteArrayOutputStream.write(buffer, 0, length);
//            }
//            String result = byteArrayOutputStream.toString();
//            inputStream.close();
//            byteArrayOutputStream.close();
//            return result;
//        } catch (Exception e) {
//            return null;
//
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_weather, container, false);
        sharedPreferences = this.getActivity().getSharedPreferences("cities", Context.MODE_PRIVATE);
        this.show_city = sharedPreferences.getString("send_city","no_city");

        Log.i("getshow",this.show_city);
        service = new InitUrl();
        if (show_city.equals("no_city")) {
            startGetData(this.show_city, true);  //首次，默认定位
        } else {
            startGetData(this.show_city, false);
            sendHttp(1);
         //   startGetData(this.show_city,true);   //选择，使用返回,重新获取默认定位
        }
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.load_bar);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(show_city.equals("no_city")){
                    startGetData(show_city,true);
                }else{
                    startGetData(show_city,false);
                }
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);//设置不刷新
                }
            }
        });
        edit_citys = (ImageButton) view.findViewById(R.id.to_city);
        edit_citys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CityActivity.class);
                Log.v("default_city", default_city);
                intent.putExtra("default_city", default_city);
                startActivityForResult(intent, REQUESTCODE);
                // startActivity(intent);
            }
        });
        final TextView sendSMS= view.findViewById(R.id.city);
        sendSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSMS(weather_share);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0) {
            if (requestCode == REQUESTCODE) {
                this.show_city = data.getStringExtra("show_city");
                Log.i("reserror",this.show_city);
                startGetData(this.show_city,false);
                sharedPreferences.edit().putString("send_city",this.show_city).commit();
            }
        }

    }

    public int dayOrNight() {
        Time time = new Time();
        time.setToNow();
        int hour = time.hour;
        if (hour > 0 && hour < 18) {
            return day;
        } else {
            return night;
        }
    }

    public String selectBackGround(String name) {
        String[] sunny = {"0", "2", "38"};
        String[] night = {"1", "3", "6", "8"};
        String[] fog = {"26", "27", "28", "29", "30", "31"};
        String[] cloudy = {"4", "9", "7", "5"};
        for (String i : sunny) {
            if (i.equals(name)) {
                return "bdsunny";
            }
        }
        for (String j : night) {
            if (j.equals(name)) {
                return "bdnight";
            }
        }
        for (String k : fog) {
            if (k.equals(name)) {
                return "bdfog";
            }
        }
        for (String l : cloudy) {
            if (l.equals(name)) {
                return "bdcloudy";
            }
        }
        return "bdrain";
    }

    public int getResIdByName(String name) {
        Class drawable = R.drawable.class;
        Field field = null;
        int id;
        try {
            field = drawable.getField(name);
            id = field.getInt(field.getName());
        } catch (Exception e) {
            id = R.drawable.bd99;
            Log.e("ERROR", "PICTURE NOT　FOUND！");
        }
        return id;
    }

    public void startGetData(String localtion, boolean useDefaultLocaltion) {
        service = new InitUrl();
        try {
            this.url = service.generateGetDiaryWeatherURL(
                    localtion,
                    "zh-Hans",
                    "c",
                    "0",
                    "2",
                    useDefaultLocaltion
            );
            if(useDefaultLocaltion){
                sendHttp(1);
                sendHttp(0);
            }
            else{
                sendHttp(0);
            }
        } catch (Exception e) {
            System.out.println("Exception:" + e);
        }
    }
    private   void  sendSMS(String weatherShare){
        Uri smsToUri = Uri.parse( "smsto:" );
        Intent sendIntent =  new  Intent(Intent.ACTION_VIEW, smsToUri);
        //短信内容
        sendIntent.putExtra( "sms_body", weatherShare);
        sendIntent.setType( "vnd.android-dir/mms-sms" );
        startActivityForResult(sendIntent, 1002 );
    }




}
