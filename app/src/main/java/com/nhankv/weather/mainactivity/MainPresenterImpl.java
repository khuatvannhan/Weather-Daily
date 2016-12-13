package com.nhankv.weather.mainactivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nhankv.weather.model.Forecast;

import org.json.JSONObject;

/**
 * Created by khuat on 24/09/2016.
 */
public class MainPresenterImpl implements MainPresenter, MainInteractor.OnCallBack {
    private final String TAG = getClass().getName();
    MainView mainView;
    MainInteractor mainInteractor;

    MainPresenterImpl(MainView mainView) {
        this.mainView = mainView;
        mainInteractor = new MainInteractorImpl();
    }
    @Override
    public void sucess(String data) {
        try {
            JSONObject jData = new JSONObject(data);
            Gson gsonMain = new GsonBuilder().create();
            Forecast forecast = gsonMain.fromJson(jData.toString(), Forecast.class);
            if (forecast != null) {
                if (mainView != null) {
                    mainView.addForecast(forecast);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError() {
        if (mainView != null) {
            mainView.onErrorConnect();
        }
    }

    @Override
    public void onNotFound() {
        if (mainView != null) {
            mainView.onErrorNotFound();
        }
    }

    @Override
    public void requestAPI(String url) {
        if (mainInteractor != null) {
            mainInteractor.requestAPI(url, this);
        }
    }

    @Override
    public String setNameCity(MainActivity mainActivity, double Longitude, double Latitude) {
        if (mainInteractor != null) {
            return mainInteractor.setNameCity(mainActivity, Longitude, Latitude);
        }
        return "";
    }
}
