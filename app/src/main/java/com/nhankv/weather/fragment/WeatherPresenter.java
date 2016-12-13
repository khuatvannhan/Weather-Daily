package com.nhankv.weather.fragment;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nhankv.weather.model.Forecast;
import com.nhankv.weather.model.ListDataWeather;

/**
 * Created by snowflower on 28/08/2016.
 */

public interface WeatherPresenter {
    void setFragment(WeatherView weatherFragment);

    void setArrayDataForecastHourly(WeatherFragment weatherFragment);

    void setArrayDataForecastDaily(WeatherFragment weatherFragment);

    void setForecastCurrent(String temperatureType, ListDataWeather listDataWeather, ImageView mImgTempCurrent, TextView mTxtTempCurrent, TextView mTextWind, TextView mTextHumidity, TextView mTextPressure);

    void setAdapterForecastDaily(String temperatureType, WeatherFragment activity, ListDataWeather listDataWeather, ViewGroup lineForecastDate, TextView txtTempLow, TextView txtTempHight);

    void setAdapterForecastHourly(String temperatureType, WeatherFragment activity, ListDataWeather listDataWeather, ViewGroup mLineForecastTime);
}
