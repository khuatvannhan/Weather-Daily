package com.nhankv.weather.mainactivity;

/**
 * Created by khuat on 24/09/2016.
 */
public interface MainPresenter {
    void requestAPI(String url);

    String setNameCity(MainActivity mainActivity, double Longitude, double Latitude);
}
