package com.star.coolweather.gson;


import com.google.gson.annotations.SerializedName;

public class Basic {

    @SerializedName("city")
    private String mCityName;

    @SerializedName("id")
    private String mWeatherId;

    @SerializedName("update")
    private Update mUpdate;

    public String getCityName() {
        return mCityName;
    }

    public void setCityName(String cityName) {
        mCityName = cityName;
    }

    public String getWeatherId() {
        return mWeatherId;
    }

    public void setWeatherId(String weatherId) {
        mWeatherId = weatherId;
    }

    public Update getUpdate() {
        return mUpdate;
    }

    public void setUpdate(Update update) {
        mUpdate = update;
    }

    public class Update {

        @SerializedName("loc")
        private String mUpdateTime;

        public String getUpdateTime() {
            return mUpdateTime;
        }

        public void setUpdateTime(String updateTime) {
            mUpdateTime = updateTime;
        }
    }
}
