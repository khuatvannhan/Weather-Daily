package com.nhankv.weather.model;

import java.util.ArrayList;

/**
 * Created by khuat on 13/12/2016.
 */

public class ListDataWeather {
    private Forecast.Currently mCurrently = new Forecast.Currently();
    private Forecast.ForecastDaily mForecastDaily = new Forecast.ForecastDaily();
    private Forecast.ForecastHourly mForecastHourly = new Forecast.ForecastHourly();
    private ArrayList<String> mListTempHourly = new ArrayList<String>();
    private ArrayList<String> mListSummaryHourly = new ArrayList<String>();
    private ArrayList<String> mListTimeHourly = new ArrayList<String>();
    private ArrayList<String> mListIconHourly = new ArrayList<String>();

    private ArrayList<String> mListTempMin = new ArrayList<String>();
    private ArrayList<String> mListTempMax = new ArrayList<String>();
    private ArrayList<String> mListIconDaily = new ArrayList<String>();
    private ArrayList<String> mListTempDaily = new ArrayList<String>();
    private ArrayList<String> mListSummaryDaily = new ArrayList<String>();
    private ArrayList<String> mListTimeDaily = new ArrayList<String>();

    public Forecast.Currently getmCurrently() {
        return mCurrently;
    }

    public void setmCurrently(Forecast.Currently mCurrently) {
        this.mCurrently = mCurrently;
    }

    public Forecast.ForecastDaily getmForecastDaily() {
        return mForecastDaily;
    }

    public void setmForecastDaily(Forecast.ForecastDaily mForecastDaily) {
        this.mForecastDaily = mForecastDaily;
    }

    public Forecast.ForecastHourly getmForecastHourly() {
        return mForecastHourly;
    }

    public void setmForecastHourly(Forecast.ForecastHourly mForecastHourly) {
        this.mForecastHourly = mForecastHourly;
    }

    public ArrayList<String> getmListTempHourly() {
        return mListTempHourly;
    }

    public void setmListTempHourly(ArrayList<String> mListTempHourly) {
        this.mListTempHourly = mListTempHourly;
    }

    public ArrayList<String> getmListSummaryHourly() {
        return mListSummaryHourly;
    }

    public void setmListSummaryHourly(ArrayList<String> mListSummaryHourly) {
        this.mListSummaryHourly = mListSummaryHourly;
    }

    public ArrayList<String> getmListTimeHourly() {
        return mListTimeHourly;
    }

    public void setmListTimeHourly(ArrayList<String> mListTimeHourly) {
        this.mListTimeHourly = mListTimeHourly;
    }

    public ArrayList<String> getmListIconHourly() {
        return mListIconHourly;
    }

    public void setmListIconHourly(ArrayList<String> mListIconHourly) {
        this.mListIconHourly = mListIconHourly;
    }

    public ArrayList<String> getmListTempMin() {
        return mListTempMin;
    }

    public void setmListTempMin(ArrayList<String> mListTempMin) {
        this.mListTempMin = mListTempMin;
    }

    public ArrayList<String> getmListTempMax() {
        return mListTempMax;
    }

    public void setmListTempMax(ArrayList<String> mListTempMax) {
        this.mListTempMax = mListTempMax;
    }

    public ArrayList<String> getmListIconDaily() {
        return mListIconDaily;
    }

    public void setmListIconDaily(ArrayList<String> mListIconDaily) {
        this.mListIconDaily = mListIconDaily;
    }

    public ArrayList<String> getmListTempDaily() {
        return mListTempDaily;
    }

    public void setmListTempDaily(ArrayList<String> mListTempDaily) {
        this.mListTempDaily = mListTempDaily;
    }

    public ArrayList<String> getmListSummaryDaily() {
        return mListSummaryDaily;
    }

    public void setmListSummaryDaily(ArrayList<String> mListSummaryDaily) {
        this.mListSummaryDaily = mListSummaryDaily;
    }

    public ArrayList<String> getmListTimeDaily() {
        return mListTimeDaily;
    }

    public void setmListTimeDaily(ArrayList<String> mListTimeDaily) {
        this.mListTimeDaily = mListTimeDaily;
    }
}
