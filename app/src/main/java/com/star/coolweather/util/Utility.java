package com.star.coolweather.util;


import android.text.TextUtils;

import com.google.gson.Gson;
import com.star.coolweather.db.City;
import com.star.coolweather.db.County;
import com.star.coolweather.db.Province;
import com.star.coolweather.gson.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Utility {

    private static final String COLUMN_NAME_PROVINCE_ID_IN_JSON = "id";
    private static final String COLUMN_NAME_PROVINCE_NAME_IN_JSON = "name";
    private static final String COLUMN_NAME_CITY_ID_IN_JSON = "id";
    private static final String COLUMN_NAME_CITY_NAME_IN_JSON = "name";
    private static final String COLUMN_NAME_COUNTY_ID_IN_JSON = "id";
    private static final String COLUMN_NAME_COUNTY_NAME_IN_JSON = "name";
    private static final String COLUMN_NAME_COUNTY_WEATHER_ID_IN_JSON = "weather_id";

    private static final String HE_WEATHER = "HeWeather";

    public static boolean handleProvinceResponse(String response) {

        if (!TextUtils.isEmpty(response)) {

            try {

                JSONArray allProvinces = new JSONArray(response);

                for (int i = 0; i < allProvinces.length(); i++) {

                    JSONObject provinceObject = allProvinces.getJSONObject(i);

                    Province province = new Province();
                    province.setProvinceId(provinceObject.getInt(
                            COLUMN_NAME_PROVINCE_ID_IN_JSON));
                    province.setProvinceName(provinceObject.getString(
                            COLUMN_NAME_PROVINCE_NAME_IN_JSON));
                    province.save();
                }

                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public static boolean handleCityResponse(String response, int provinceId) {

        if (!TextUtils.isEmpty(response)) {

            try {

                JSONArray allCities = new JSONArray(response);

                for (int i = 0; i < allCities.length(); i++) {

                    JSONObject cityObject = allCities.getJSONObject(i);

                    City city = new City();
                    city.setCityId(cityObject.getInt(
                            COLUMN_NAME_CITY_ID_IN_JSON ));
                    city.setCityName(cityObject.getString(
                            COLUMN_NAME_CITY_NAME_IN_JSON ));
                    city.setProvinceId(provinceId);
                    city.save();
                }

                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public static boolean handleCountyResponse(String response, int cityId) {

        if (!TextUtils.isEmpty(response)) {

            try {

                JSONArray allCounties = new JSONArray(response);

                for (int i = 0; i < allCounties.length(); i++) {

                    JSONObject countyObject = allCounties.getJSONObject(i);

                    County county = new County();
                    county.setCountyId(countyObject.getInt(
                            COLUMN_NAME_COUNTY_ID_IN_JSON));
                    county.setCountyName(countyObject.getString(
                            COLUMN_NAME_COUNTY_NAME_IN_JSON));
                    county.setCityId(cityId);
                    county.setWeatherId(countyObject.getString(
                            COLUMN_NAME_COUNTY_WEATHER_ID_IN_JSON));
                    county.save();
                }

                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public static Weather handleWeatherResponse(String response) {

        if (!TextUtils.isEmpty(response)) {

            try {

                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray(HE_WEATHER);

                String weatherContent = jsonArray.getJSONObject(0).toString();

                return new Gson().fromJson(weatherContent, Weather.class);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
