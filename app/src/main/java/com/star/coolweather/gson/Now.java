package com.star.coolweather.gson;


import com.google.gson.annotations.SerializedName;

public class Now {

    @SerializedName("tmp")
    private String mTemperature;

    @SerializedName("cond")
    private More mMore;

    public String getTemperature() {
        return mTemperature;
    }

    public void setTemperature(String temperature) {
        mTemperature = temperature;
    }

    public More getMore() {
        return mMore;
    }

    public void setMore(More more) {
        mMore = more;
    }

    public class More {

        @SerializedName("txt")
        private String mInfo;

        public String getInfo() {
            return mInfo;
        }

        public void setInfo(String info) {
            mInfo = info;
        }
    }
}
