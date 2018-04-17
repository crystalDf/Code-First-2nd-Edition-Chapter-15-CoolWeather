package com.star.coolweather.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.star.coolweather.gson.Weather;
import com.star.coolweather.util.HttpUtil;
import com.star.coolweather.util.Utility;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.star.coolweather.WeatherActivity.BING_PIC;
import static com.star.coolweather.WeatherActivity.BING_PIC_URL;
import static com.star.coolweather.WeatherActivity.KEY;
import static com.star.coolweather.WeatherActivity.OK;
import static com.star.coolweather.WeatherActivity.QUERY_CITY_ID;
import static com.star.coolweather.WeatherActivity.QUERY_KEY;
import static com.star.coolweather.WeatherActivity.WEATHER;
import static com.star.coolweather.WeatherActivity.WEATHER_URL;

public class AutoUpdateService extends Service {

    private static final long AN_HOUR_INTERVAL = TimeUnit.HOURS.toMillis(1);

    private AlarmManager mAlarmManager;

    private String mWeatherId;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        updateWeather();
        updateBingPic();

        mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        long triggerAtTime = SystemClock.elapsedRealtime() + AN_HOUR_INTERVAL * 8;

        Intent intent1 = new Intent(this, AutoUpdateService.class);

        PendingIntent pendingIntent = PendingIntent.getService(
                this, 0, intent1, 0);

        mAlarmManager.cancel(pendingIntent);
        mAlarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                triggerAtTime, pendingIntent);

        return super.onStartCommand(intent, flags, startId);
    }

    private void updateWeather() {

        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);

        String weatherString = sharedPreferences.getString(WEATHER, null);

        if (!TextUtils.isEmpty(weatherString)) {

            Weather weather = Utility.handleWeatherResponse(weatherString);

            mWeatherId = weather.getBasic().getWeatherId();

            String weatherUrl = WEATHER_URL + QUERY_CITY_ID + mWeatherId +
                    QUERY_KEY + KEY;

            HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    final String responseText = response.body().string();
                    final Weather weather = Utility.handleWeatherResponse(responseText);

                    if (weather != null && OK.equals(weather.getStatus())) {

                        SharedPreferences.Editor editor =
                                PreferenceManager.getDefaultSharedPreferences(
                                        AutoUpdateService.this).edit();
                        editor.putString(WEATHER, responseText);
                        editor.apply();
                    }
                }
            });
        }
    }

    private void updateBingPic() {

        String requestBingPic = BING_PIC_URL;

        HttpUtil.sendOkHttpRequest(requestBingPic, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String bingPic = response.body().string();

                SharedPreferences.Editor editor =
                        PreferenceManager.getDefaultSharedPreferences(
                                AutoUpdateService.this).edit();
                editor.putString(BING_PIC, bingPic);
                editor.apply();
            }
        });
    }
}
