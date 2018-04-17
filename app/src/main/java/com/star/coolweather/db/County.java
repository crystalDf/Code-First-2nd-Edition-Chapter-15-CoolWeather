package com.star.coolweather.db;

import org.litepal.crud.DataSupport;


public class County extends DataSupport {

    private int mId;
    private int mCountyId;
    private String mCountyName;
    private int mCityId;
    private String mWeatherId;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public int getCountyId() {
        return mCountyId;
    }

    public void setCountyId(int countyId) {
        mCountyId = countyId;
    }

    public String getCountyName() {
        return mCountyName;
    }

    public void setCountyName(String countyName) {
        this.mCountyName = countyName;
    }

    public int getCityId() {
        return mCityId;
    }

    public void setCityId(int cityId) {
        this.mCityId = cityId;
    }

    public String getWeatherId() {
        return mWeatherId;
    }

    public void setWeatherId(String weatherId) {
        this.mWeatherId = weatherId;
    }
}
