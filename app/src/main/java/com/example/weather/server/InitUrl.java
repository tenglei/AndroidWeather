package com.example.weather.server;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.SignatureException;
import java.util.Date;
import java.net.URLEncoder;
import java.io.UnsupportedEncodingException;

public class InitUrl {
    //http://www.thinkpage.cn/weather/api.svc/getWeather?city=beijing&language=zh-cn&provider=CMA&unit=C&aqi=city
    private String TIANQI_DAILY_WEATHER_URL = "http://www.thinkpage.cn/weather/api.svc/getWeather";

    private String TIANQI_API_SECRET_KEY = "pn9u46kj3vrch7pb";

    private String TIANQI_API_USER_ID = "UCFB8E9C2E";
    private String generateSignature(String data, String key) throws SignatureException {
        String result;
        try {
            // get an hmac_sha1 key from the raw key bytes
            SecretKeySpec signingKey = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA1");
            // get an hmac_sha1 Mac instance and initialize with the signing key
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);
            // compute the hmac on input data bytes
            byte[] rawHmac = mac.doFinal(data.getBytes("UTF-8"));
            result = new String(rawHmac,"UTF-8");
        }
        catch (Exception e) {
            throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
        }
        return result;
    }
    public String generateGetDiaryWeatherURL(
            String location,
            String language,
            String unit,
            String start,
            String days,
            boolean code_location
    )  throws SignatureException, UnsupportedEncodingException {
        String timestamp = String.valueOf(new Date().getTime());
        String params = "ts=" + timestamp + "&ttl=30&uid=" + TIANQI_API_USER_ID;
        Integer ts=(int)(System.currentTimeMillis()/1000);
        //String signature = URLEncoder.encode(generateSignature(params, TIANQI_API_SECRET_KEY), "UTF-8");
        if(!code_location){
            return TIANQI_DAILY_WEATHER_URL + "?"+ "key="+TIANQI_API_SECRET_KEY+"&city=" + location + "&language=" + language + "&provider=CMA"+"&unit=" + unit + "&aqi=city"+"&start=" + start + "&days=" + days;
        }
        else{
            return TIANQI_DAILY_WEATHER_URL + "?"+ "key="+TIANQI_API_SECRET_KEY + "&language=" + language + "&provider=CMA"+"&unit=" + unit + "&aqi=city"+"&start=" + start + "&days=" + days;

        }
    }
}
