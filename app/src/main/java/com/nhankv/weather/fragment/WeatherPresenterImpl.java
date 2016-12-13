package com.nhankv.weather.fragment;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nhankv.weather.R;
import com.nhankv.weather.model.Forecast;
import com.nhankv.weather.model.ListDataWeather;

/**
 * Created by snowflower on 28/08/2016.
 */

public class WeatherPresenterImpl implements WeatherPresenter, WeatherInteractor.OnHandingListener {
    private final String TAG = getClass().getName();
    WeatherView weatherFragment;
    WeatherInteractor weatherInteractor;

    WeatherPresenterImpl(WeatherFragment weatherFragment) {
        this.weatherFragment = weatherFragment;
        this.weatherInteractor = new WeatherInteractorImpl();
    }

    @Override
    public void setFragment(WeatherView weatherFragment) {
        weatherInteractor.setWeatherFragment(weatherFragment);
    }

    @Override
    public void setArrayDataForecastHourly(WeatherFragment weatherFragment) {
        if (weatherInteractor != null) {
            weatherInteractor.getArrayDataForecastHourly(weatherFragment, this);
        }
    }

    @Override
    public void setArrayDataForecastDaily(WeatherFragment weatherFragment) {
        if (weatherInteractor != null) {
            weatherInteractor.getArrayDataForecastDaily(weatherFragment, this);
        }
    }

    @Override
    public void setForecastCurrent(String temperatureType, ListDataWeather listDataWeather, ImageView mImgTempCurrent, TextView mTxtTempCurrent, TextView mTextWind, TextView mTextHumidity, TextView mTextPressure) {
        if (weatherInteractor != null) {
            weatherInteractor.setForecastCurrent(temperatureType, listDataWeather, mImgTempCurrent, mTxtTempCurrent, mTextWind, mTextHumidity, mTextPressure);
        }
    }

    @Override
    public void setAdapterForecastDaily(String temperatureType, WeatherFragment activity, ListDataWeather listDataWeather, ViewGroup lineForecastDate, TextView txtTempLow, TextView txtTempHight) {
        if (weatherInteractor != null) {
            weatherInteractor.setAdapterForecastDaily(temperatureType, activity, listDataWeather, lineForecastDate, txtTempLow, txtTempHight);
        }
    }

    @Override
    public void setAdapterForecastHourly(String temperatureType, WeatherFragment activity, ListDataWeather listDataWeather, ViewGroup mLineForecastTime) {
        if (weatherInteractor != null) {
            weatherInteractor.setAdapterForecastHourly(temperatureType, activity, listDataWeather, mLineForecastTime);
            ;
        }
    }

    @Override
    public void onDataNotFound() {
        if (weatherFragment != null) {
            weatherFragment.showToast(weatherFragment.getActivityFragment().getResources().getString(R.string.request_data_error));
        }
    }
}
