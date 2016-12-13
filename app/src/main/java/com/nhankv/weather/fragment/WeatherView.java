package com.nhankv.weather.fragment;

import android.app.Activity;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.nhankv.weather.model.Forecast;
import com.nhankv.weather.model.ListDataWeather;

import java.util.ArrayList;

/**
 * Created by snowflower on 28/08/2016.
 */

public interface WeatherView {

    void showToast(String message);

    Activity getActivityFragment();

    void setForecastCurrent(String temperatureType, ListDataWeather listDataWeather, ImageView mImgTempCurrent, TextView mTxtTempCurrent, TextView mTextWind, TextView mTextHumidity, TextView mTextPressure);

    void setAdapterForecastDaily(String temperatureType, WeatherFragment activity, ListDataWeather listDataWeather, ViewGroup lineForecastDate, TextView txtTempLow, TextView txtTempHight);

    void setAdapterForecastHourly(String temperatureType, WeatherFragment activity, ListDataWeather listDataWeather, ViewGroup mLineForecastTime);

    ListDataWeather getListDataWeather();

    void setListDataWeather(ListDataWeather listDataWeather);

    ScrollView getmScrollMain();

    void setmScrollMain(ScrollView mScrollMain);

    LinearLayout getmMainLayout();

    void setmMainLayout(LinearLayout mMainLayout);

    ImageView getmImgTempCurrent();

    void setmImgTempCurrent(ImageView mImgTempCurrent);

    ViewGroup getmLineForecastTime();

    void setmLineForecastTime(ViewGroup mLineForecastTime);

    ViewGroup getmLineForecastDate();

    void setmLineForecastDate(ViewGroup mLineForecastDate);

    TextView getmTxtNotifile();

    void setmTxtNotifile(TextView mTxtNotifile);

    TextView getmTxtTempLow();

    void setmTxtTempLow(TextView mTxtTempLow);

    TextView getmTxtTempHight();

    void setmTxtTempHight(TextView mTxtTempHight);

    TextView getmTxtTempCurrent();

    void setmTxtTempCurrent(TextView mTxtTempCurrent);

    TextView getmTxtForecast5d();

    void setmTxtForecast5d(TextView mTxtForecast5d);

    TextView getmTxtForecast10d();

    void setmTxtForecast10d(TextView mTxtForecast10d);

    TextView getmTextWind();

    void setmTextWind(TextView mTextWind);

    TextView getmTextHumidity();

    void setmTextHumidity(TextView mTextHumidity);

    TextView getmTextPressure();

    void setmTextPressure(TextView mTextPressure);

    Animation getmAnimation();

    void setmAnimation(Animation mAnimation);

    void setBackGroundMain(String url);
}
