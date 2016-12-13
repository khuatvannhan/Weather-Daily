package com.nhankv.weather.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nhankv.weather.R;
import com.nhankv.weather.model.Forecast;
import com.nhankv.weather.model.ListDataWeather;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.Date;

/**
 * Created by snowflower on 28/08/2016.
 */

public class WeatherInteractorImpl implements WeatherInteractor {
    private final String TAG = getClass().getName();
    WeatherView fragmentWeather;
    private final String RAIN = "http://s-media-cache-ak0.pinimg.com/564x/74/a6/7c/74a67c20ba6293c8ea7ba7b5befd3e1c.jpg";
    private final String CLOUDY = "http://s-media-cache-ak0.pinimg.com/564x/05/6a/45/056a4542d516759ada692ca80fc5d1f5.jpg";
    private final String PARTLY_CLOUDY_DAY = "http://s-media-cache-ak0.pinimg.com/564x/b0/7c/66/b07c663983cd64acde835390a8324a15.jpg";
    private final String PARTLY_CLOUDY_NIGHT = "http://s-media-cache-ak0.pinimg.com/564x/12/07/43/12074318deb8dcc07a3a1a2c374254a9.jpg";
    private final String CLEAR_DAY = "http://s-media-cache-ak0.pinimg.com/564x/98/61/ee/9861ee9d1306806540f9c1dd7e59d199.jpg";
    private final String CLEAR_NIGHT = "http://s-media-cache-ak0.pinimg.com/564x/1d/db/8b/1ddb8b077b5230554bbad4a1fb86f648.jpg";
    //private final String CLOUDY_DAY = "https://s-media-cache-ak0.pinimg.com/564x/d0/63/eb/d063ebb2bd75edd18549861189fe7656.jpg";
    //private final String CLOUDY_DAY = "https://s-media-cache-ak0.pinimg.com/564x/3f/cb/95/3fcb95dc9f4cd588fba1b92300f5b292.jpg";
    //private final String CLOUDY_DAY = "https://s-media-cache-ak0.pinimg.com/564x/98/61/ee/9861ee9d1306806540f9c1dd7e59d199.jpg";
    OnHandingListener listener;
    private String temperatureType;

    @Override
    public void setWeatherFragment(WeatherView weatherFragment) {
        fragmentWeather = weatherFragment;
    }

    @Override
    public void getArrayDataForecastHourly(WeatherFragment weatherFragment, OnHandingListener listener) {
        this.listener = listener;
        AsyncParserDataHour asyncParserDataHour = new AsyncParserDataHour();
        asyncParserDataHour.execute(weatherFragment);
    }

    @Override
    public void getArrayDataForecastDaily(WeatherFragment weatherFragment, OnHandingListener listener) {
        this.listener = listener;
        AsyncParserDataDaily asyncParserDataDaily = new AsyncParserDataDaily();
        asyncParserDataDaily.execute(weatherFragment);
    }

    @Override
    public void setAdapterForecastDaily(String temperatureType, WeatherFragment activity, ListDataWeather listDataWeather, ViewGroup lineForecastDate, TextView txtTempLow, TextView txtTempHight) {
        int layoutTime = R.layout.layout_forecast_day;
        this.temperatureType = temperatureType;
        if (lineForecastDate.getChildCount() > 0) {
            lineForecastDate.removeAllViews();
        }
        for (int i = 0; i < listDataWeather.getmListTimeDaily().size(); i++) {
            LayoutInflater inflater = (LayoutInflater) activity.getActivity().getApplicationContext().getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            final RelativeLayout inflate = (RelativeLayout) inflater.inflate(layoutTime, lineForecastDate, false);
            lineForecastDate.addView(inflate);
        }
        int count = lineForecastDate.getChildCount();
        for (int i = 0; i < count; i++) {
            ViewGroup viewGroup = (ViewGroup) lineForecastDate.getChildAt(i);
            if (viewGroup.getChildAt(0) instanceof TextView) {
                TextView textView = (TextView) viewGroup.getChildAt(0);
                String day = String.valueOf(convertMiliToDays(Long.parseLong(listDataWeather.getmListTimeDaily().get(i))));
                textView.setText(day);
            }
            if (viewGroup.getChildAt(1) instanceof ImageView) {
                ImageView weatherIconView = (ImageView) viewGroup.getChildAt(1);
                setIconDaily(weatherIconView, listDataWeather.getmListIconDaily().get(i), listDataWeather.getmListSummaryDaily().get(i));
            }
            ViewGroup viewGroup2 = (ViewGroup) viewGroup.getChildAt(2);
            if (viewGroup2.getChildAt(0) instanceof TextView) {
                TextView textView = (TextView) viewGroup2.getChildAt(0);
                double temp = Double.parseDouble(listDataWeather.getmListTempMax().get(i));
                String tempMax = "";
                if (temperatureType.equals("Celsius")) {
                    tempMax = convertFahrenheitToCelsius(temp);
                } else {
                    tempMax = String.valueOf((int) temp);
                }
                textView.setText(tempMax);
            }
            ViewGroup viewGroup3 = (ViewGroup) viewGroup.getChildAt(3);
            if (viewGroup3.getChildAt(0) instanceof TextView) {
                TextView textView = (TextView) viewGroup3.getChildAt(0);
                double temp = Double.parseDouble(listDataWeather.getmListTempMin().get(i));
                String tempMin = "";
                if (temperatureType.equals("Celsius")) {
                    tempMin = convertFahrenheitToCelsius(temp);
                } else {
                    tempMin = String.valueOf((int) temp);
                }
                textView.setText(tempMin);
            }
        }
        double tempMax = 0, tempMin = 0;
        if (listDataWeather.getmListTempMax().size() > 0) {
            if (listDataWeather.getmListTempMax().get(0) != null) {
                tempMax = Double.parseDouble(listDataWeather.getmListTempMax().get(0).toString());
            }
        }
        if (listDataWeather.getmListTempMin().size() > 0) {
            if (listDataWeather.getmListTempMin().get(0) != null) {
                tempMin = Double.parseDouble(listDataWeather.getmListTempMin().get(0).toString());
            }
        }
        if (temperatureType.equals("Celsius")) {
            txtTempLow.setText(convertFahrenheitToCelsius(tempMin));
            txtTempHight.setText(convertFahrenheitToCelsius(tempMax));
        } else {
            txtTempLow.setText(String.valueOf((int) tempMin));
            txtTempHight.setText(String.valueOf((int) tempMax));
        }
    }

    @Override
    public void setAdapterForecastHourly(String temperatureType, WeatherFragment activity, ListDataWeather listDataWeather, ViewGroup lineForecastTime) {
        this.temperatureType = temperatureType;
        int layoutTime = R.layout.layout_forecast_time;
        if (lineForecastTime.getChildCount() > 0) {
            lineForecastTime.removeAllViews();
        }

        for (int i = 0; i < listDataWeather.getmListTimeHourly().size(); i++) {
            LayoutInflater inflater = (LayoutInflater) activity.getActivity().getApplicationContext().getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            final LinearLayout inflate = (LinearLayout) inflater.inflate(layoutTime, lineForecastTime, false);
            lineForecastTime.addView(inflate);
        }
        int count = lineForecastTime.getChildCount();
        for (int i = 0; i < count; i++) {
            ViewGroup viewGroup = (ViewGroup) lineForecastTime.getChildAt(i);
            if (viewGroup.getChildAt(0) instanceof TextView) {
                TextView textView = (TextView) viewGroup.getChildAt(0);
                String time = String.valueOf(convertMiliToHour(Long.parseLong(listDataWeather.getmListTimeHourly().get(i)))) + ":00";
                textView.setText(time);
            }
            if (viewGroup.getChildAt(1) instanceof ImageView) {
                ImageView weatherIconView = (ImageView) viewGroup.getChildAt(1);
                setIconHour(weatherIconView, listDataWeather.getmListIconHourly().get(i), listDataWeather.getmListSummaryHourly().get(i), (int) convertMiliToHour(Long.parseLong(listDataWeather.getmListTimeHourly().get(i))));
            }
            ViewGroup viewGroup2 = (ViewGroup) viewGroup.getChildAt(2);
            if (viewGroup2.getChildAt(0) instanceof TextView) {
                TextView textView = (TextView) viewGroup2.getChildAt(0);
                double temp = Double.parseDouble(listDataWeather.getmListTempHourly().get(i));
                String tempDay = "";
                if (temperatureType.equals("Celsius")) {
                    tempDay = convertFahrenheitToCelsius(temp);
                } else {
                    tempDay = String.valueOf((int) temp);
                }
                textView.setText(tempDay);
            }
        }
    }

    @Override
    public void setForecastCurrent(String temperatureType, ListDataWeather listDataWeather, ImageView imgTempCurrent, TextView txtTempCurrent, TextView textWind, TextView textHumidity, TextView textPressure) {
        String temp = "";
        this.temperatureType = temperatureType;
        if (temperatureType.equals("Celsius")) {
            temp = convertFahrenheitToCelsius(listDataWeather.getmCurrently().getTemperature());
        } else {
            temp = String.valueOf((int) listDataWeather.getmCurrently().getTemperature());
        }
        txtTempCurrent.setText(temp);
        setIconHour(imgTempCurrent, listDataWeather.getmCurrently().getIcon(), listDataWeather.getmCurrently().getSummary(), (int) convertMiliToHour(listDataWeather.getmCurrently().getTime()));
        AsyncSetWeatherBackground asyncSetWeatherBackground = new AsyncSetWeatherBackground();
        asyncSetWeatherBackground.execute(listDataWeather.getmCurrently().getIcon());
        textWind.setText(convertWindSpeed(listDataWeather.getmCurrently().getWindSpeed()));
        textPressure.setText(String.valueOf((int) listDataWeather.getmCurrently().getPressure()) + " mb");
        textHumidity.setText(convertHumidity(listDataWeather.getmCurrently().getHumidity()));
    }

    public void setIconDaily(ImageView view, String icon, String summary) {
        switch (icon) {
            case "rain":
                if (summary.equals("Breezy throughout the day and rain starting in the afternoon.")) {
                    view.setImageResource(R.drawable.rain);
                }
                if (summary.equals("Drizzle until afternoon, starting again overnight.")) {
                    view.setImageResource(R.drawable.drizzle);
                }
                if (summary.equals("Drizzle throughout the day.")) {
                    view.setImageResource(R.drawable.drizzle);
                }
                if (summary.equals("Drizzle in the evening.")) {
                    view.setImageResource(R.drawable.drizzle);
                }
                if (summary.equals("Drizzle starting in the evening.")) {
                    view.setImageResource(R.drawable.drizzle);
                }
                if (summary.equals("Light rain starting in the afternoon, continuing until evening.")) {
                    view.setImageResource(R.drawable.small_rain_evening);
                }
                if (summary.equals("Rain until afternoon.")) {
                    view.setImageResource(R.drawable.rain);
                }
                if (summary.equals("Drizzle overnight.")) {
                    view.setImageResource(R.drawable.drizzle);
                }
                if (summary.equals("Rain until evening.")) {
                    view.setImageResource(R.drawable.rain);
                }
                if (summary.equals("Rain until evening, starting again overnight.")) {
                    view.setImageResource(R.drawable.rain);
                }
                if (summary.equals("Rain starting in the afternoon.")) {
                    view.setImageResource(R.drawable.rain);
                }
                if (summary.equals("Light rain overnight.")) {
                    view.setImageResource(R.drawable.drizzle);
                }
                if (summary.equals("Drizzle until evening.")) {
                    view.setImageResource(R.drawable.drizzle);
                }
                if (summary.equals("Drizzle starting in the afternoon.")) {
                    view.setImageResource(R.drawable.rain);
                }
                if (summary.equals("Light rain starting in the afternoon.")) {
                    view.setImageResource(R.drawable.rain);
                }
                if (summary.equals("Light rain until afternoon, starting again in the evening.")) {
                    view.setImageResource(R.drawable.rain);
                }
                if (summary.equals("Light rain in the morning and overnight.")) {
                    view.setImageResource(R.drawable.drizzle);
                }
                if (summary.equals("Drizzle until afternoon.")) {
                    view.setImageResource(R.drawable.drizzle);
                }
                if (summary.equals("Light rain until afternoon, starting again overnight.")) {
                    view.setImageResource(R.drawable.rain);
                }
                if (summary.equals("Rain overnight.")) {
                    view.setImageResource(R.drawable.rain);
                }
                if (summary.equals("Light rain starting in the evening.")) {
                    view.setImageResource(R.drawable.day_small_rainy);
                }
                if (summary.equals("Light rain throughout the day.")) {
                    view.setImageResource(R.drawable.day_small_rainy);
                }
                if (summary.equals("Rain in the morning and afternoon.")) {
                    view.setImageResource(R.drawable.day_small_rainy);
                }
                if (summary.equals("Rain in the morning and evening.")) {
                    view.setImageResource(R.drawable.rain);
                }
                if (summary.equals("Light rain until evening, starting again overnight.")) {
                    view.setImageResource(R.drawable.rain);
                }
                if (summary.equals("Light rain in the morning and afternoon.")) {
                    view.setImageResource(R.drawable.drizzle);
                }
                if (summary.equals("Light rain until afternoon.")) {
                    view.setImageResource(R.drawable.day_small_rainy);
                }
                if (summary.equals("Rain until afternoon, starting again in the evening.")) {
                    view.setImageResource(R.drawable.rain);
                }
                if (summary.equals("Light rain in the morning.")) {
                    view.setImageResource(R.drawable.day_small_rainy);
                }
                if (summary.equals("Rain throughout the day.")) {
                    view.setImageResource(R.drawable.rain);
                }
                if (summary.equals("Drizzle in the morning and evening.")) {
                    view.setImageResource(R.drawable.drizzle);
                }
                if (summary.equals("Drizzle starting in the afternoon, continuing until evening.")) {
                    view.setImageResource(R.drawable.drizzle);
                }
                if (summary.equals("Light rain until evening.")) {
                    view.setImageResource(R.drawable.day_small_rainy);
                }
                if (summary.equals("Drizzle in the morning.")) {
                    view.setImageResource(R.drawable.drizzle);
                }
                break;
            case "partly-cloudy-day":
                if (summary.equals("Partly cloudy until evening.")) {
                    view.setImageResource(R.drawable.partly_cloudy_day);
                }
                if (summary.equals("Partly cloudy in the morning.")) {
                    view.setImageResource(R.drawable.partly_cloudy_day);
                }
                if (summary.equals("Partly cloudy until afternoon.")) {
                    view.setImageResource(R.drawable.partly_cloudy_day);
                }
                if (summary.equals("Mostly cloudy throughout the day.")) {
                    view.setImageResource(R.drawable.cloudy);
                }
                if (summary.equals("Partly cloudy throughout the day.")) {
                    view.setImageResource(R.drawable.partly_cloudy_day);
                }
                if (summary.equals("Mostly cloudy until evening.")) {
                    view.setImageResource(R.drawable.cloudy);
                }
                break;
            case "partly-cloudy-night":
                if (summary.equals("Mostly cloudy starting in the afternoon.")) {
                    view.setImageResource(R.drawable.cloudy);
                }
                if (summary.equals("Partly cloudy overnight.")) {
                    view.setImageResource(R.drawable.cloudy_night);
                }
                if (summary.equals("Partly cloudy starting in the evening.")) {
                    view.setImageResource(R.drawable.cloudy_night);
                }
                if (summary.equals("Partly cloudy in the morning.")) {
                    view.setImageResource(R.drawable.cloudy_night);
                }
                if (summary.equals("Mostly cloudy starting in the evening.")) {
                    view.setImageResource(R.drawable.cloudy);
                }
                break;
            case "cloudy":
                if (summary.equals("Overcast throughout the day.")) {
                    view.setImageResource(R.drawable.cloud);
                }
                break;
            case "wind":
                if (summary.equals("Breezy starting in the afternoon, continuing until evening, and partly cloudy starting in the evening.")) {
                    view.setImageResource(R.drawable.wind);
                }
                if (summary.equals("Breezy in the afternoon and partly cloudy starting in the evening.")) {
                    view.setImageResource(R.drawable.wind);
                }
                break;
            case "clear-day":
                if (summary.equals("Clear throughout the day.")) {
                    view.setImageResource(R.drawable.clear_day);
                }
        }
    }

    public void setIconHour(ImageView view, String icon, String summary, int time) {
        switch (icon) {
            case "clear-day":
                if (summary.equals("Clear")) {
                    view.setImageResource(R.drawable.clear_day);
                }
                break;
            case "partly-cloudy-day":
                if (summary.equals("Partly Cloudy")) {
                    view.setImageResource(R.drawable.partly_cloudy_day);
                }
                if (summary.equals("Mostly Cloudy")) {
                    view.setImageResource(R.drawable.cloudy);
                }
                break;

            case "rain":
                if (summary.equals("Drizzle")) {
                    view.setImageResource(R.drawable.rain);
                }
                if (summary.equals("Light Rain")) {
                    view.setImageResource(R.drawable.day_small_rainy);
                }
                if (summary.equals("Rain")) {
                    view.setImageResource(R.drawable.rain);
                }
                break;
            case "cloudy":
                if (summary.equals("Overcast")) {
                    view.setImageResource(R.drawable.cloud);
                }
                break;
            case "wind":
                if (summary.equals("Breezy")) {
                    view.setImageResource(R.drawable.wind);
                }
                break;

            case "clear-night":
                if (summary.equals("Clear")) {
                    view.setImageResource(R.drawable.clear_night);
                }
                break;
            case "partly-cloudy-night":
                if (summary.equals("Partly Cloudy")) {
                    view.setImageResource(R.drawable.cloudy_night);
                }
                if (summary.equals("Mostly Cloudy")) {
                    view.setImageResource(R.drawable.cloudy);
                }
                break;
        }
    }

    private String convertWindSpeed(double wind) {
        return ((int) (wind * 1.609)) + " km/h";
    }

    private String convertHumidity(double humidity) {
        return ((int) (humidity * 100)) + " %";
    }

    private String convertFahrenheitToCelsius(double fahrenheit) {
        int celsius = (int) ((fahrenheit - 32) / 1.8);
        return String.valueOf(celsius);
    }

    private long convertMiliToHour(long milliseconds) {
        milliseconds = milliseconds * 1000;
        Date date = new Date(milliseconds);
        return date.getHours();
    }

    public String convertMiliToDays(long milliseconds) {
        milliseconds = milliseconds * 1000;
        Date date = new Date(milliseconds);
        switch (date.getDay()) {
            case 1:
                return fragmentWeather.getActivityFragment().getResources().getString(R.string.monday);

            case 2:
                return fragmentWeather.getActivityFragment().getResources().getString(R.string.tuesday);

            case 3:
                return fragmentWeather.getActivityFragment().getResources().getString(R.string.wednesday);

            case 4:
                return fragmentWeather.getActivityFragment().getResources().getString(R.string.thursday);

            case 5:
                return fragmentWeather.getActivityFragment().getResources().getString(R.string.friday);

            case 6:
                return fragmentWeather.getActivityFragment().getResources().getString(R.string.saturday);

            case 0:
                return fragmentWeather.getActivityFragment().getResources().getString(R.string.sunday);
        }
        return "";
    }

    public void delay(int milisecond) {
        try {
            Thread.sleep(milisecond);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class AsyncParserDataHour extends AsyncTask<WeatherFragment, WeatherFragment, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onProgressUpdate(WeatherFragment... fragments) {
            super.onProgressUpdate(fragments);
            WeatherFragment weatherFragment = fragments[0];
            //setAdapterForecastDaily();
            int layoutTime = R.layout.layout_forecast_time;
            if (weatherFragment.getmLineForecastTime().getChildCount() > 0) {
                weatherFragment.getmLineForecastTime().removeAllViews();
            }

            for (int i = 0; i < weatherFragment.getListDataWeather().getmListTimeHourly().size(); i++) {
                LayoutInflater inflater = (LayoutInflater) fragmentWeather.getActivityFragment().getApplicationContext().getSystemService
                        (Context.LAYOUT_INFLATER_SERVICE);
                final LinearLayout inflate = (LinearLayout) inflater.inflate(layoutTime, weatherFragment.getmLineForecastTime(), false);
                weatherFragment.getmLineForecastTime().addView(inflate);
            }
            int count = weatherFragment.getmLineForecastTime().getChildCount();
            for (int i = 0; i < count; i++) {
                ViewGroup viewGroup = (ViewGroup) weatherFragment.getmLineForecastTime().getChildAt(i);
                if (viewGroup.getChildAt(0) instanceof TextView) {
                    TextView textView = (TextView) viewGroup.getChildAt(0);
                    String time = String.valueOf(convertMiliToHour(Long.parseLong(weatherFragment.getListDataWeather().getmListTimeHourly().get(i)))) + ":00";
                    textView.setText(time);
                }
                if (viewGroup.getChildAt(1) instanceof ImageView) {
                    ImageView weatherIconView = (ImageView) viewGroup.getChildAt(1);
                    setIconHour(weatherIconView, weatherFragment.getListDataWeather().getmListIconHourly().get(i), weatherFragment.getListDataWeather().getmListSummaryHourly().get(i), (int) convertMiliToHour(Long.parseLong(weatherFragment.getListDataWeather().getmListTimeHourly().get(i))));
                }
                ViewGroup viewGroup2 = (ViewGroup) viewGroup.getChildAt(2);
                if (viewGroup2.getChildAt(0) instanceof TextView) {
                    TextView textView = (TextView) viewGroup2.getChildAt(0);
                    double temp = Double.parseDouble(weatherFragment.getListDataWeather().getmListTempHourly().get(i));
                    String tempDay = "";
                    if (temperatureType.equals("Celsius")) {
                        tempDay = convertFahrenheitToCelsius(temp);
                    } else {
                        tempDay = String.valueOf((int) temp);
                    }
                    textView.setText(tempDay);
                }
            }
        }

        @Override
        protected Void doInBackground(WeatherFragment... fragments) {
            WeatherFragment weatherFragment = fragments[0];
            weatherFragment.getListDataWeather().getmListTimeHourly().clear();
            weatherFragment.getListDataWeather().getmListIconHourly().clear();
            weatherFragment.getListDataWeather().getmListTempHourly().clear();
            weatherFragment.getListDataWeather().getmListSummaryHourly().clear();

            if (weatherFragment.getListDataWeather().getmForecastHourly() != null) {
                for (int i = 0; i < weatherFragment.getListDataWeather().getmForecastHourly().getData().size(); i++) {
                    if (weatherFragment.getListDataWeather().getmForecastHourly().getData().get(i).getTime() != 0) {
                        weatherFragment.getListDataWeather().getmListTimeHourly().add(String.valueOf(weatherFragment.getListDataWeather().getmForecastHourly().getData().get(i).getTime()));
                    }
                    if (weatherFragment.getListDataWeather().getmForecastHourly().getData().get(i).getIcon() != null) {
                        weatherFragment.getListDataWeather().getmListIconHourly().add(weatherFragment.getListDataWeather().getmForecastHourly().getData().get(i).getIcon());
                    }
                    if (weatherFragment.getListDataWeather().getmForecastHourly().getData().get(i).getTemperature() != 0) {
                        weatherFragment.getListDataWeather().getmListTempHourly().add(String.valueOf(weatherFragment.getListDataWeather().getmForecastHourly().getData().get(i).getTemperature()));
                    }
                    if (weatherFragment.getListDataWeather().getmForecastHourly().getData().get(i).getSummary() != null) {
                        weatherFragment.getListDataWeather().getmListSummaryHourly().add(weatherFragment.getListDataWeather().getmForecastHourly().getData().get(i).getSummary());
                    }
                }
                delay(100);
                publishProgress(weatherFragment);
                //listener.onSetListArrayHourlyFinish();
            }
            return null;
        }
    }

    class AsyncParserDataDaily extends AsyncTask<WeatherFragment, WeatherFragment, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onProgressUpdate(WeatherFragment... fragments) {
            super.onProgressUpdate(fragments);
            WeatherFragment weatherFragment = fragments[0];
            int layoutTime = R.layout.layout_forecast_day;
            if (weatherFragment.getmLineForecastDate().getChildCount() > 0) {
                weatherFragment.getmLineForecastDate().removeAllViews();
            }
            for (int i = 0; i < weatherFragment.getListDataWeather().getmListTimeDaily().size(); i++) {
                LayoutInflater inflater = (LayoutInflater) fragmentWeather.getActivityFragment().getApplicationContext().getSystemService
                        (Context.LAYOUT_INFLATER_SERVICE);
                final RelativeLayout inflate = (RelativeLayout) inflater.inflate(layoutTime, weatherFragment.getmLineForecastDate(), false);
                weatherFragment.getmLineForecastDate().addView(inflate);
            }
            int count = weatherFragment.getmLineForecastDate().getChildCount();
            for (int i = 0; i < count; i++) {
                ViewGroup viewGroup = (ViewGroup) weatherFragment.getmLineForecastDate().getChildAt(i);
                if (viewGroup.getChildAt(0) instanceof TextView) {
                    TextView textView = (TextView) viewGroup.getChildAt(0);
                    String day = String.valueOf(convertMiliToDays(Long.parseLong(weatherFragment.getListDataWeather().getmListTimeDaily().get(i))));
                    textView.setText(day);
                }
                if (viewGroup.getChildAt(1) instanceof ImageView) {
                    ImageView weatherIconView = (ImageView) viewGroup.getChildAt(1);
                    setIconDaily(weatherIconView, weatherFragment.getListDataWeather().getmListIconDaily().get(i), weatherFragment.getListDataWeather().getmListSummaryDaily().get(i));
                }
                ViewGroup viewGroup2 = (ViewGroup) viewGroup.getChildAt(2);
                if (viewGroup2.getChildAt(0) instanceof TextView) {
                    TextView textView = (TextView) viewGroup2.getChildAt(0);
                    double temp = Double.parseDouble(weatherFragment.getListDataWeather().getmListTempMax().get(i));
                    String tempMax = "";
                    if (temperatureType.equals("Celsius")) {
                        tempMax = convertFahrenheitToCelsius(temp);
                    } else {
                        tempMax = String.valueOf((int) temp);
                    }
                    textView.setText(tempMax);
                }
                ViewGroup viewGroup3 = (ViewGroup) viewGroup.getChildAt(3);
                if (viewGroup3.getChildAt(0) instanceof TextView) {
                    TextView textView = (TextView) viewGroup3.getChildAt(0);
                    double temp = Double.parseDouble(weatherFragment.getListDataWeather().getmListTempMin().get(i));
                    String tempMin = "";
                    if (temperatureType.equals("Celsius")) {
                        tempMin = convertFahrenheitToCelsius(temp);
                    } else {
                        tempMin = String.valueOf((int) temp);
                    }
                    textView.setText(tempMin);
                }
            }
            double tempMax = 0, tempMin = 0;
            if (weatherFragment.getListDataWeather().getmListTempMax().size() > 0) {
                if (weatherFragment.getListDataWeather().getmListTempMax().get(0) != null) {
                    tempMax = Double.parseDouble(weatherFragment.getListDataWeather().getmListTempMax().get(0).toString());
                }
            }
            if (weatherFragment.getListDataWeather().getmListTempMin().size() > 0) {
                if (weatherFragment.getListDataWeather().getmListTempMin().get(0) != null) {
                    tempMin = Double.parseDouble(weatherFragment.getListDataWeather().getmListTempMin().get(0).toString());
                }
            }
            if (temperatureType.equals("Celsius")) {
                weatherFragment.getmTxtTempLow().setText(convertFahrenheitToCelsius(tempMin));
                weatherFragment.getmTxtTempHight().setText(convertFahrenheitToCelsius(tempMax));
            } else {
                weatherFragment.getmTxtTempLow().setText(String.valueOf((int) tempMin));
                fragmentWeather.getmTxtTempHight().setText(String.valueOf((int) tempMax));
            }
            //listener.onSetListArrayDailyFinish();
        }

        @Override
        protected Void doInBackground(WeatherFragment... fragments) {
            WeatherFragment weatherFragment = fragments[0];
            weatherFragment.getListDataWeather().getmListTempMax().clear();
            weatherFragment.getListDataWeather().getmListTempMin().clear();
            weatherFragment.getListDataWeather().getmListTempDaily().clear();
            weatherFragment.getListDataWeather().getmListIconDaily().clear();
            weatherFragment.getListDataWeather().getmListTempDaily().clear();
            weatherFragment.getListDataWeather().getmListSummaryDaily().clear();

            if (weatherFragment.getListDataWeather().getmForecastDaily() != null) {
                for (int i = 0; i < weatherFragment.getListDataWeather().getmForecastDaily().getData().size(); i++) {
                    if (weatherFragment.getListDataWeather().getmForecastDaily().getData().get(i).getTime() != 0) {
                        weatherFragment.getListDataWeather().getmListTimeDaily().add(String.valueOf(weatherFragment.getListDataWeather().getmForecastDaily().getData().get(i).getTime()));
                    }
                    if (weatherFragment.getListDataWeather().getmForecastDaily().getData().get(i).getIcon() != null) {
                        weatherFragment.getListDataWeather().getmListIconDaily().add(weatherFragment.getListDataWeather().getmForecastDaily().getData().get(i).getIcon());
                    }
                    if (weatherFragment.getListDataWeather().getmForecastDaily().getData().get(i).getTemperature() != 0) {
                        weatherFragment.getListDataWeather().getmListTempDaily().add(String.valueOf(weatherFragment.getListDataWeather().getmForecastDaily().getData().get(i).getTemperature()));
                    }
                    if (weatherFragment.getListDataWeather().getmForecastDaily().getData().get(i).getTemperatureMin() != 0) {
                        weatherFragment.getListDataWeather().getmListTempMin().add(String.valueOf(weatherFragment.getListDataWeather().getmForecastDaily().getData().get(i).getTemperatureMin()));
                    }
                    if (weatherFragment.getListDataWeather().getmForecastDaily().getData().get(i).getTemperatureMax() != 0) {
                        weatherFragment.getListDataWeather().getmListTempMax().add(String.valueOf(weatherFragment.getListDataWeather().getmForecastDaily().getData().get(i).getTemperatureMax()));
                    }
                    if (weatherFragment.getListDataWeather().getmForecastDaily().getData().get(i).getSummary() != null) {
                        weatherFragment.getListDataWeather().getmListSummaryDaily().add(weatherFragment.getListDataWeather().getmForecastDaily().getData().get(i).getSummary());
                    }
                }
                delay(100);
                publishProgress(weatherFragment);
            }
            return null;
        }
    }

    class AsyncAnimation extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            //mImgTurbine.startAnimation(mAnimation);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            fragmentWeather.setmAnimation(AnimationUtils.loadAnimation(fragmentWeather.getActivityFragment().getApplicationContext(), R.anim.clockwise));
            publishProgress();
            return null;
        }
    }

    class AsyncSetWeatherBackground extends AsyncTask<String, String, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            String url = values[0];
            fragmentWeather.setBackGroundMain(url);
            /*Picasso.with(fragmentWeather.getActivityFragment()).load(url).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    fragmentWeather.getmMainLayout().setBackground(new BitmapDrawable(fragmentWeather.getActivityFragment().getApplicationContext().getResources(), bitmap));
                }

                @Override
                public void onBitmapFailed(final Drawable errorDrawable) {
                    Log.d("TAG", "FAILED");
                }

                @Override
                public void onPrepareLoad(final Drawable placeHolderDrawable) {
                    Log.d("TAG", "Prepare Load");
                }
            });*/
        }

        @Override
        protected Void doInBackground(String... values) {
            String icon = values[0];
            String url = "";
            switch (icon) {
                case "rain":
                    url = RAIN;
                    break;
                case "cloudy":
                    url = CLOUDY;
                    break;
                case "partly-cloudy-day":
                    url = PARTLY_CLOUDY_DAY;
                    break;
                case "partly-cloudy-night":
                    url = PARTLY_CLOUDY_NIGHT;
                    break;
                case "clear-night":
                    url = CLEAR_NIGHT;
                    break;
                case "clear-day":
                    url = CLEAR_DAY;
                    break;
            }
            if (url != null) {
                publishProgress(url);
            }
            return null;
        }
    }
}
