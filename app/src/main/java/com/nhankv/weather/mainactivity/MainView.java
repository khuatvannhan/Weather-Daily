package com.nhankv.weather.mainactivity;

import android.content.Context;

import com.nhankv.weather.model.City;
import com.nhankv.weather.model.Forecast;

/**
 * Created by khuat on 24/09/2016.
 */
public interface MainView {
    City getCity();
    void onErrorConnect();
    void onErrorNotFound();
    void addForecast(Forecast forecast);
    Context getActivity();
}
