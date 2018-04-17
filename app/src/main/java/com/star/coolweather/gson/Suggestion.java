package com.star.coolweather.gson;


import com.google.gson.annotations.SerializedName;

public class Suggestion {

    @SerializedName("comf")
    private Comfort mComfort;

    @SerializedName("cw")
    private CarWash mCarWash;

    @SerializedName("sport")
    private Sport mSport;

    public Comfort getComfort() {
        return mComfort;
    }

    public void setComfort(Comfort comfort) {
        mComfort = comfort;
    }

    public CarWash getCarWash() {
        return mCarWash;
    }

    public void setCarWash(CarWash carWash) {
        mCarWash = carWash;
    }

    public Sport getSport() {
        return mSport;
    }

    public void setSport(Sport sport) {
        mSport = sport;
    }

    public class Comfort {

        @SerializedName("txt")
        private String mInfo;

        public String getInfo() {
            return mInfo;
        }

        public void setInfo(String info) {
            mInfo = info;
        }
    }

    public class CarWash {

        @SerializedName("txt")
        private String mInfo;

        public String getInfo() {
            return mInfo;
        }

        public void setInfo(String info) {
            mInfo = info;
        }
    }

    public class Sport {

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
