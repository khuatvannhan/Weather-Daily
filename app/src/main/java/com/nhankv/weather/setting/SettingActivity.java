package com.nhankv.weather.setting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nhankv.weather.R;
import com.nhankv.weather.model.Forecast;

import java.util.ArrayList;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewGroup mLayoutSetting, mLayoutUnit, mLayoutAbout;
    private TextView mTxtUni, mTxtVersion;
    private ArrayList<Forecast> arrForecast = new ArrayList<Forecast>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        addControl();
        addEvent();
    }

    private void addEvent() {
        mLayoutAbout.setOnClickListener(this);
        mLayoutUnit.setOnClickListener(this);
        mLayoutSetting.setOnClickListener(this);
    }

    private void addControl() {
        mTxtUni = (TextView) findViewById(R.id.txtUnit);
        mTxtVersion = (TextView) findViewById(R.id.txtVersion);
        mLayoutUnit = (ViewGroup) findViewById(R.id.layoutSetting);
        mLayoutAbout = (ViewGroup) findViewById(R.id.layoutAbout);
        mLayoutSetting = (ViewGroup) findViewById(R.id.layoutSetting);
    }

    @Override
    public void onClick(View v) {

    }
}
