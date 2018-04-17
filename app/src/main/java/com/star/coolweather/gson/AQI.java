package com.star.coolweather.gson;


import com.google.gson.annotations.SerializedName;

public class AQI {

    @SerializedName("city")
    private AQICity mCity;

    public AQICity getCity() {
        return mCity;
    }

    public void setCity(AQICity city) {
        mCity = city;
    }

    public class AQICity {

        @SerializedName("aqi")
        private String mAQI;

        @SerializedName("pm25")
        private String mPM25;

        public String getAQI() {
            return mAQI;
        }

        public void setAQI(String AQI) {
            mAQI = AQI;
        }

        public String getPM25() {
            return mPM25;
        }

        public void setPM25(String PM25) {
            mPM25 = PM25;
        }
    }
}
