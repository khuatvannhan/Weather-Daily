package com.nhankv.weather.mainactivity;

/**
 * Created by khuat on 24/09/2016.
 */
public interface MainInteractor {
    interface OnCallBack {
        void sucess(String data);

        void onError();

        void onNotFound();
    }

    void requestAPI(String url, OnCallBack OnCallBack);

    String setNameCity(MainActivity activity, double Longitude, double Latitude);
}
