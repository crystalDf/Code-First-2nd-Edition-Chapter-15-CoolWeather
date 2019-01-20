package com.star.coolweather;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.star.coolweather.db.City;
import com.star.coolweather.db.County;
import com.star.coolweather.db.Province;
import com.star.coolweather.util.HttpUtil;
import com.star.coolweather.util.Utility;

import org.litepal.LitePal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ChooseAreaFragment extends Fragment {

    private static final String SERVER_ADDRESS = "http://guolin.tech/api/china";
    private static final String QUERY_PROVINCE = "province";
    private static final String QUERY_CITY = "city";
    private static final String QUERY_COUNTY = "county";

    private static final int LEVEL_PROVINCE = 0;
    private static final int LEVEL_CITY = 1;
    private static final int LEVEL_COUNTY = 2;

    private static final String COLUMN_NAME_PROVINCE_ID = "mProvinceId";
    private static final String COLUMN_NAME_CITY_ID = "mCityId";

    private ProgressBar mProgressBar;
    private TextView mTitleText;
    private Button mBackButton;
    private ListView mListView;
    private ArrayAdapter<String> mAdapter;
    private List<String> mDataList;

    private List<Province> mProvinceList;
    private List<City> mCityList;
    private List<County> mCountyList;

    private Province mSelectedProvince;
    private City mSelectedCity;

    private int mCurrentLevel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_choose_area, container, false);

        mTitleText = view.findViewById(R.id.title_text);
        mBackButton = view.findViewById(R.id.back_button);
        mListView = view.findViewById(R.id.list_view);
        mProgressBar = view.findViewById(R.id.progress_bar);

        mDataList = new ArrayList<>();
        mAdapter = new ArrayAdapter<>(
                getContext(), android.R.layout.simple_list_item_1, mDataList);
        mListView.setAdapter(mAdapter);

        closeProgressBar();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mListView.setOnItemClickListener((parent, view, position, id) -> {
            switch (mCurrentLevel) {
                case LEVEL_PROVINCE:
                    mSelectedProvince = mProvinceList.get(position);
                    queryCities();
                    break;
                case LEVEL_CITY:
                    mSelectedCity = mCityList.get(position);
                    queryCounties();
                    break;
                case LEVEL_COUNTY:
                    String weatherId = mCountyList.get(position).getWeatherId();

                    if (getActivity() instanceof MainActivity) {
                        Intent intent = new Intent(getActivity(), WeatherActivity.class);
                        intent.putExtra(WeatherActivity.WEATHER_ID, weatherId);
                        startActivity(intent);
                        getActivity().finish();
                    } else if (getActivity() instanceof WeatherActivity) {
                        WeatherActivity weatherActivity =
                                (WeatherActivity) getActivity();
                        weatherActivity.getDrawerLayout().closeDrawers();
                        weatherActivity.getSwipeRefreshLayout().setRefreshing(true);
                        weatherActivity.requestWeather(weatherId);
                    }

                    break;
                default:
            }
        });

        mBackButton.setOnClickListener(v -> {
            switch (mCurrentLevel) {
                case LEVEL_COUNTY:
                    queryCities();
                    break;
                case LEVEL_CITY:
                    queryProvinces();
                    break;
                default:
            }
        });

        queryProvinces();
    }

    private void queryProvinces() {
        mTitleText.setText(R.string.china);
        mBackButton.setVisibility(View.GONE);

        mProvinceList = LitePal.findAll(Province.class);

        if (mProvinceList.size() > 0) {
            mDataList.clear();

            for (Province province : mProvinceList) {
                mDataList.add(province.getProvinceName());
            }

            mAdapter.notifyDataSetChanged();
            mListView.setSelection(0);

            mCurrentLevel = LEVEL_PROVINCE;
        } else {
            queryFromServer(SERVER_ADDRESS, QUERY_PROVINCE);
        }
    }

    private void queryCities() {
        mTitleText.setText(mSelectedProvince.getProvinceName());
        mBackButton.setVisibility(View.VISIBLE);

        mCityList = LitePal.where(COLUMN_NAME_PROVINCE_ID + " = ?",
                String.valueOf(mSelectedProvince.getProvinceId())).find(City.class);

        if (mCityList.size() > 0) {
            mDataList.clear();

            for (City city: mCityList) {
                mDataList.add(city.getCityName());
            }

            mAdapter.notifyDataSetChanged();
            mListView.setSelection(0);

            mCurrentLevel = LEVEL_CITY;
        } else {
            int provinceId = mSelectedProvince.getProvinceId();

            String address = SERVER_ADDRESS + "/" + provinceId;
            queryFromServer(address, QUERY_CITY);
        }
    }

    private void queryCounties() {
        mTitleText.setText(mSelectedCity.getCityName());
        mBackButton.setVisibility(View.VISIBLE);

        mCountyList = LitePal.where(COLUMN_NAME_CITY_ID + " = ?",
                String.valueOf(mSelectedCity.getCityId())).find(County.class);

        if (mCountyList.size() > 0) {
            mDataList.clear();

            for (County county: mCountyList) {
                mDataList.add(county.getCountyName());
            }

            mAdapter.notifyDataSetChanged();
            mListView.setSelection(0);

            mCurrentLevel = LEVEL_COUNTY;
        } else {
            int provinceId = mSelectedProvince.getProvinceId();
            int cityId = mSelectedCity.getCityId();

            String address = SERVER_ADDRESS + "/" + provinceId + "/" + cityId;
            queryFromServer(address, QUERY_COUNTY);
        }
    }

    private void queryFromServer(String address, final String queryParam) {

        showProgressBar();

        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                getActivity().runOnUiThread(() -> {

                    closeProgressBar();
                    Toast.makeText(getContext(), "加载失败", Toast.LENGTH_LONG).show();
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                String responseText = response.body().string();
                boolean result = false;

                switch (queryParam) {
                    case QUERY_PROVINCE:
                        result = Utility.handleProvinceResponse(responseText);
                        break;
                    case QUERY_CITY:
                        result = Utility.handleCityResponse(responseText,
                                mSelectedProvince.getProvinceId());
                        break;
                    case QUERY_COUNTY:
                        result = Utility.handleCountyResponse(responseText,
                                mSelectedCity.getCityId());
                        break;
                    default:
                }

                if (result) {
                    getActivity().runOnUiThread(() -> {
                        closeProgressBar();

                        switch (queryParam) {
                            case QUERY_PROVINCE:
                                queryProvinces();
                                break;
                            case QUERY_CITY:
                                queryCities();
                                break;
                            case QUERY_COUNTY:
                                queryCounties();
                                break;
                            default:
                        }
                    });
                }
            }
        });
    }

    private void showProgressBar() {

        if (mProgressBar == null) {
            mProgressBar = new ProgressBar(getActivity());
            mProgressBar.setIndeterminate(true);
        }

        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void closeProgressBar() {

        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.GONE);
            mProgressBar = null;
        }
    }
}
