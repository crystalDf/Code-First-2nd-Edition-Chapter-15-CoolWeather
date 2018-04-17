package com.star.coolweather.gson;


import com.google.gson.annotations.SerializedName;

public class Forecast {

    @SerializedName("date")
    private String mDate;

    @SerializedName("tmp")
    private Temperature mTemprature;

    @SerializedName("cond")
    private More mMore;

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public Temperature getTemprature() {
        return mTemprature;
    }

    public void setTemprature(Temperature temprature) {
        mTemprature = temprature;
    }

    public More getMore() {
        return mMore;
    }

    public void setMore(More more) {
        mMore = more;
    }

    public class Temperature {

        @SerializedName("max")
        private String mMax;

        @SerializedName("min")
        private String mMin;

        public String getMax() {
            return mMax;
        }

        public void setMax(String max) {
            mMax = max;
        }

        public String getMin() {
            return mMin;
        }

        public void setMin(String min) {
            mMin = min;
        }
    }

    public class More {

        @SerializedName("txt_d")
        private String mInfo;

        public String getInfo() {
            return mInfo;
        }

        public void setInfo(String info) {
            mInfo = info;
        }
    }
}
