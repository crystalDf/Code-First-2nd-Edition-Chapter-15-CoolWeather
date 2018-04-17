package com.star.coolweather.gson;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Weather {

    @SerializedName("status")
    private String mStatus;

    @SerializedName("basic")
    private Basic mBasic;

    @SerializedName("aqi")
    private AQI mAQI;

    @SerializedName("now")
    private Now mNow;

    @SerializedName("suggestion")
    private Suggestion mSuggestion;

    @SerializedName("daily_forecast")
    private List<Forecast> mForecastList;

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public Basic getBasic() {
        return mBasic;
    }

    public void setBasic(Basic basic) {
        mBasic = basic;
    }

    public AQI getAQI() {
        return mAQI;
    }

    public void setAQI(AQI AQI) {
        mAQI = AQI;
    }

    public Now getNow() {
        return mNow;
    }

    public void setNow(Now now) {
        mNow = now;
    }

    public Suggestion getSuggestion() {
        return mSuggestion;
    }

    public void setSuggestion(Suggestion suggestion) {
        mSuggestion = suggestion;
    }

    public List<Forecast> getForecastList() {
        return mForecastList;
    }

    public void setForecastList(List<Forecast> forecastList) {
        mForecastList = forecastList;
    }
}
