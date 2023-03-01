package com.soli.coolweatherwells.util;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.soli.coolweatherwells.db.City;
import com.soli.coolweatherwells.db.County;
import com.soli.coolweatherwells.db.Province;
import com.soli.coolweatherwells.gson.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Utility {
    public static boolean handleProvinceResponse(String response){
        if(!TextUtils.isEmpty(response)){
            try {
                JSONArray allProvinces = new JSONArray(response);
                for (int i = 0; i < allProvinces.length(); i++) {
                    JSONObject provinceObject = allProvinces.getJSONObject(i);
                    Province province = new Province();
                    province.setProvinceCode(provinceObject.getInt("id"));
                    province.setProvinceName(provinceObject.getString("name"));
                    province.save();
                }
                return true;
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    public static boolean handleCityResponse(String response, int provinceId){
        // sometimes due to the school network have not permitted, so the response have some wrong, it's not a JSON datatype cause a wrong exception in code.
        Log.d("Utiltiy.handleCityResponse", "response is " + response);
        if(!TextUtils.isEmpty(response)){
            try {
                JSONArray allCities = new JSONArray(response);
                for (int i = 0; i < allCities.length(); i++) {
                    JSONObject cityObject = allCities.getJSONObject(i);
                    City city = new City();
                    city.setProvinceId(provinceId);
                    city.setCityCode(cityObject.getInt("id"));
                    city.setCityName(cityObject.getString("name"));
                    city.save();
                }
                return true;
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    public static boolean handleCountyResponse(String response, int cityId){
        if(!TextUtils.isEmpty(response)){
            try {
                JSONArray allCounty = new JSONArray(response);
                for (int i = 0; i < allCounty.length(); i++) {
                    JSONObject countyObject = allCounty.getJSONObject(i);
                    County county = new County();
                    county.setCityId(cityId);
                    county.setWeatherId(countyObject.getString("weather_id"));
                    county.setCountyName(countyObject.getString("name"));
                    county.save();
                }
                return true;
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    /**
     * 将返回的JSON数据解析成Weather实体类
     */
    public static Weather handleWeatherResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather");
            String weatherContent = jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(weatherContent, Weather.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
