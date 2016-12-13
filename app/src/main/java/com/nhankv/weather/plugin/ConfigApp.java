package com.nhankv.weather.plugin;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;

/**
 * Created by khuat on 06/11/2016.
 */

public class ConfigApp {
    private static ConfigApp mConfigApp = new ConfigApp();
    private static boolean onGPS = false;
    private static boolean onNet = false;

    private static Context mContext;

    public ConfigApp getInstance(Context context) {
        this.mContext = context;
        return mConfigApp;
    }

    public void checkOnGPSandNet() {
        LocationManager lm = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        try {
            onGPS = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        try {
            onNet = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }
        setOnGPS(onGPS);
        setOnNet(onNet);
    }

    public boolean showDialog(String title, String message) {
        final boolean[] isStatus = {false};
        new AlertDialog.Builder(mContext)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        isStatus[0] = true;
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
        return isStatus[0];
    }

    public void startSettingSys() {
        Intent dialogIntent = new Intent(android.provider.Settings.ACTION_SETTINGS);
        mContext.startActivity(dialogIntent);
    }

    public void startLocationSys() {
        Intent dialogIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        mContext.startActivity(dialogIntent);
    }

    public boolean isOnGPS() {
        return onGPS;
    }

    public void setOnGPS(boolean onGPS) {
        this.onGPS = onGPS;
    }

    public boolean isOnNet() {
        return onNet;
    }

    public void setOnNet(boolean onNet) {
        this.onNet = onNet;
    }
}
