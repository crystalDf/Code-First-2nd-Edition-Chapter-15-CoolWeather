package com.star.coolweather.db;


import org.litepal.crud.LitePalSupport;

public class Province extends LitePalSupport {

    private int mId;
    private int mProvinceId;
    private String mProvinceName;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getProvinceId() {
        return mProvinceId;
    }

    public void setProvinceId(int provinceId) {
        mProvinceId = provinceId;
    }

    public String getProvinceName() {
        return mProvinceName;
    }

    public void setProvinceName(String provinceName) {
        mProvinceName = provinceName;
    }
}
