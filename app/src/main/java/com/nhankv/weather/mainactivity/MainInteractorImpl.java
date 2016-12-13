package com.nhankv.weather.mainactivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.github.kittinunf.fuel.Fuel;
import com.github.kittinunf.fuel.core.FuelError;
import com.github.kittinunf.fuel.core.Handler;
import com.github.kittinunf.fuel.core.Request;
import com.github.kittinunf.fuel.core.Response;

import java.util.List;
import java.util.Locale;

/**
 * Created by khuat on 24/09/2016.
 */
public class MainInteractorImpl implements MainInteractor {
    private final String TAG = getClass().getName();
    private MainView activity;


    @Override
    public void requestAPI(String url, final OnCallBack OnCallBack) {
        Log.d(TAG, " url " + url);
        try {
            Fuel.get(url).responseString(new Handler<String>() {
                @Override
                public void failure(Request request, Response response, FuelError error) {
                    Log.d(TAG, " Can't connect API ");
                    OnCallBack.onError();
                }

                @Override
                public void success(Request request, Response response, String data) {
                    if ((data != null) && (!data.equals(""))) {
                        if (data.equals("{}")) {
                            OnCallBack.onNotFound();
                            Log.d(TAG, " Not Found Data From API ");
                        } else {
                            try {
                                Log.d(TAG, " on Messenger ");
                                Toast.makeText(activity.getActivity().getApplicationContext(), "onReceive Data Finish", Toast.LENGTH_LONG).show();
                                OnCallBack.sucess(data);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String setNameCity(MainActivity activity, double Longitude, double Latitude) {
        this.activity = activity;
        String city = "";
        try {
            Geocoder gcd = new Geocoder(activity.getApplicationContext(), Locale.getDefault());
            List<Address> addresses = gcd.getFromLocation(Latitude, Longitude, 1);
            if (addresses.size() > 0) {
                if (addresses.size() > 1) {
                    if (addresses.get(1).getFeatureName() != null) {
                        city = addresses.get(1).getFeatureName();
                    } else {
                        if (addresses.size() > 2) {
                            if (addresses.get(2).getFeatureName() != null) {
                                city = addresses.get(2).getFeatureName();
                            }
                        }
                    }
                } else {
                    city = addresses.get(0).getFeatureName();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return city;
        /*AsyncGetNameCity asyncGetNameCity = new AsyncGetNameCity();
        asyncGetNameCity.execute(Longitude, Latitude);*/
    }

    class AsyncGetNameCity extends AsyncTask<Double, String, Void> {
        @Override
        protected Void doInBackground(Double... values) {
            double longitude = values[0];
            double latitude = values[1];
            String city = "";
            try {
                Geocoder gcd = new Geocoder(activity.getActivity().getApplicationContext(), Locale.getDefault());
                List<Address> addresses = gcd.getFromLocation(latitude, longitude, 1);
                if (addresses.size() > 0) {
                    if (addresses.size() > 1) {
                        if (addresses.get(1).getFeatureName() != null) {
                            city = addresses.get(1).getFeatureName();
                        } else {
                            if (addresses.size() > 2) {
                                if (addresses.get(2).getFeatureName() != null) {
                                    city = addresses.get(2).getFeatureName();
                                }
                            }
                        }
                    } else {
                        city = addresses.get(0).getFeatureName();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            publishProgress(city);
            return null;
        }

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
            String city = values[0];
            activity.getCity().setCityName(city);
        }
    }

}
