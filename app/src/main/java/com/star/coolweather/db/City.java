package com.star.coolweather.db;

import org.litepal.crud.LitePalSupport;


public class City extends LitePalSupport {

    private int mId;
    private int mCityId;
    private String mCityName;
    private int mProvinceId;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getCityId() {
        return mCityId;
    }

    public void setCityId(int cityId) {
        mCityId = cityId;
    }

    public String getCityName() {
        return mCityName;
    }

    public void setCityName(String cityName) {
        mCityName = cityName;
    }

    public int getProvinceId() {
        return mProvinceId;
    }

    public void setProvinceId(int provinceId) {
        mProvinceId = provinceId;
    }
}
