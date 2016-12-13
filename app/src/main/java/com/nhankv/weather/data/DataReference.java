package com.nhankv.weather.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nhankv.weather.model.City;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by khuat on 02/11/2016.
 */

public class DataReference {
    private static SharedPreferences mSpfStore;
    private static DataReference mDataReference = new DataReference();
    private static SharedPreferences.Editor mEdSave;
    private static String mStyleTemp;

    public static DataReference getInstance(Context context) {
        mSpfStore = context.getSharedPreferences(Constant.CITY, context.MODE_PRIVATE);
        mEdSave = mSpfStore.edit();
        return mDataReference;
    }

    public ArrayList<City> getListCity() {
        Gson gson = new Gson();
        String city = mSpfStore.getString(Constant.LIST_CITY, "");
        Type type = new TypeToken<ArrayList<City>>() {}.getType();
        ArrayList<City> listCity = gson.fromJson(city, type);
        return listCity;
    }

    public void setListCity(ArrayList<City> listCity) {
        Gson gson = new Gson();
        String city = gson.toJson(listCity);
        mEdSave.putString(Constant.LIST_CITY, city);
        mEdSave.commit();
    }

    public String getStyleTemp() {
        String styleTemp = mSpfStore.getString(Constant.STYLE_TEMP, "C");
        return styleTemp;
    }

    public void setStyleTemp(String styleTemp) {
        mEdSave.putString(Constant.STYLE_TEMP, styleTemp);
        mEdSave.commit();
    }
}
