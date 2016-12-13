package com.nhankv.weather.mainactivity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nhankv.weather.Constants;
import com.nhankv.weather.R;
import com.nhankv.weather.fragment.WeatherFragment;
import com.nhankv.weather.model.City;
import com.nhankv.weather.model.Forecast;
import com.nhankv.weather.plugin.ConfigApp;
//import com.nhankv.weather.plugin.MyPlaceAutocomplete;

import java.util.ArrayList;

import im.delight.android.location.SimpleLocation;

public class MainActivity extends AppCompatActivity implements MainView, View.OnClickListener, PopupMenu.OnMenuItemClickListener {
    public final String TAG = getClass().getName();
    private ArrayAdapter mAdapterCity;
    public City city = new City();
    private ArrayList<City> mListCity = new ArrayList<City>();
    private ArrayList<Forecast> mArrForecast = new ArrayList<Forecast>();
    private ArrayList<WeatherFragment> mListFragment = new ArrayList<WeatherFragment>();
    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private MainPresenter mMainPresenter;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private WeatherFragment mWeatherFragment = new WeatherFragment(this);
    private ConfigApp mConfigApp;
    /*private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;*/
    private TextView mTxtTitle;
    private ImageView mImgMenu, mImgAdd;
    private RelativeLayout mLayoutToolbar;
    public LinearLayout mainContent;
    public String temperatureType = "Celsius";
    public double currentLatitude;
    public double currentLongitude;
    private MenuItem addItem;

    private SimpleLocation mLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mConfigApp = new ConfigApp().getInstance(this);
        mLocation = new SimpleLocation(this);
        setupActionBar();
        //mConfigApp.checkOnGPSandNet();
        //checkGPSandNet();
        mMainPresenter = new MainPresenterImpl(this);
        getLocation();
        //mGoogleApiClient.connect();
    }

    public void getLocation() {
        mLocation.setBlurRadius(5000);
        // if we can't access the location yet
        if (!mLocation.hasLocationEnabled()) {
            // ask the user to enable location access
            SimpleLocation.openSettings(this);
        }

        currentLatitude = mLocation.getLatitude();
        currentLongitude = mLocation.getLongitude();
        Toast.makeText(this, "Latitude: = " + currentLatitude + " Longitude: = " + currentLongitude, Toast.LENGTH_LONG).show();
        City city = new City();
        city.setLongitude(currentLongitude);
        city.setLatitude(currentLatitude);
        city.setCityName(mMainPresenter.setNameCity(MainActivity.this, city.longitude, city.latitude));
        mListCity.add(city);
        if (city.getCityName() != null) {
            mTxtTitle.setText(city.getCityName());
        }
        if (((int) currentLatitude != 0) && ((int) currentLongitude != 0)) {
            mMainPresenter.requestAPI(Constants.URL_WEATHER + currentLatitude + "," + currentLongitude);
        }
    }

    public void checkGPSandNet() {
        if (!mConfigApp.isOnGPS()) {
            if (mConfigApp.showDialog(getResources().getString(R.string.title_access_location),
                    getResources().getString(R.string.off_gps))) {
                mConfigApp.startSettingSys();
            }
        }
        if (!mConfigApp.isOnNet()) {
            if (mConfigApp.showDialog(getResources().getString(R.string.title_network_error),
                    getResources().getString(R.string.off_network))) {
                mConfigApp.startLocationSys();
            }
        }
    }

    public void setupActionBar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mLayoutToolbar = (RelativeLayout) mToolbar.findViewById(R.id.layoutToolbar);
        mImgAdd = (ImageView) mLayoutToolbar.findViewById(R.id.imgAdd);
        mImgMenu = (ImageView) mLayoutToolbar.findViewById(R.id.imgMenu);
        mTxtTitle = (TextView) mLayoutToolbar.findViewById(R.id.txtTitle);
        mainContent = (LinearLayout) findViewById(R.id.mainContent);
        mImgMenu.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        menuClick();
                    }
                }
        );
        mImgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addClick();
            }
        });
    }

    public void setAdapterForListViewCity() {
        ArrayList<String> listNameCity = new ArrayList<String>();
        for (int i = 0; i < mListCity.size(); i++) {
            listNameCity.add(mListCity.get(i).getCityName());
        }
        mAdapterCity = new ArrayAdapter(this, R.layout.layout_item_listcity, listNameCity);
        //mLvCityForecast.setAdapter(mAdapterCity);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        mLocation.endUpdates();
        super.onPause();
    }

    @Override
    public void onStop() {
        //mGoogleApiClient.disconnect();
        super.onStop();
    }

   /* public void getLocation() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                // The next two lines tell the new client that “this” current class will handle connection stuff
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                //fourth line adds the LocationServices API endpoint from GooglePlayServices
                .addApi(LocationServices.API)
                .build();

        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds
    }*/

    public void setUpViewPage() {
        mSectionsPagerAdapter.addFragment(mWeatherFragment);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    public void setDataForFragment() {
        Log.d(TAG, " Add ----------- ");
        mListCity.add(city);
        WeatherFragment weatherFragment = new WeatherFragment(this);
        mSectionsPagerAdapter.addFragment(weatherFragment);
        mSectionsPagerAdapter.notifyDataSetChanged();
    }

   /* @Override
    public void onConnected(@Nullable Bundle bundle) {
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (location == null) {
           *//* if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            } else {*//*
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            //}

        } else {
            //If everything went fine lets get latitude and longitude
            currentLatitude = location.getLatitude();
            currentLongitude = location.getLongitude();
            Toast.makeText(this, "Latitude: = " + currentLatitude + " Longitude: = " + currentLongitude, Toast.LENGTH_LONG).show();
            City city = new City();
            city.setLongitude(currentLongitude);
            city.setLatitude(currentLatitude);
            city.setCityName(mMainPresenter.setNameCity(MainActivity.this, city.longitude, city.latitude));
            mListCity.add(city);
            mTxtTitle.setText(city.getCityName());
            if (((int) currentLatitude != 0) && ((int) currentLongitude != 0)) {
                mMainPresenter.requestAPI(Constants.URL_WEATHER + currentLatitude + "," + currentLongitude);
            }
        }
    }*/

    public void initFragment() {
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        setUpViewPage();
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (mMainPresenter != null) {
                    if (mListCity != null) {
                        if (mListCity.get(position) != null) {
                            mTxtTitle.setText(mListCity.get(position).getCityName());
                        }
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d(TAG, "On Back ");
    }

    /*@Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, Constants.CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("Error", "Location services connection failed with code " + connectionResult.getErrorCode());
            showToast(getResources().getString(R.string.error_get_location));
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        currentLatitude = location.getLatitude();
        currentLongitude = location.getLongitude();
        Toast.makeText(this, currentLatitude + " WORKS " + currentLongitude + "", Toast.LENGTH_LONG).show();
    }
*/
    @Override
    public City getCity() {
        return city;
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void onErrorConnect() {
        showToast(getResources().getString(R.string.error_connect_api));
    }

    @Override
    public void onErrorNotFound() {
        showToast(getResources().getString(R.string.error_not_found));
    }

    @Override
    public void addForecast(Forecast forecast) {
        mArrForecast.add(forecast);
        if (mSectionsPagerAdapter == null && mViewPager == null) {
            initFragment();
        } else {
            setDataForFragment();
        }
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG);
    }

    public void onClick(View view) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //autocompleteFragment.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == this.RESULT_OK) {
                Log.d(TAG, " onActivityResult onActivityResult ");
                String cityName = data.getStringExtra("cityName");
                double longitude = data.getDoubleExtra("longitude", 0);
                double latitude = data.getDoubleExtra("latitude", 0);
                city = new City();
                city.setCityName(cityName);
                city.setLongitude(longitude);
                city.setLatitude(latitude);
                Log.d(TAG, " long -----  " + longitude + " laaaaaaaa  == " + latitude);
                mMainPresenter.requestAPI(Constants.URL_WEATHER + latitude + "," + longitude);
            }
        }
    }

    private void menuClick() {
        View vAddItem = findViewById(R.id.imgMenu);
        PopupMenu popupMenu = new PopupMenu(this, vAddItem);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.menu_main);
        popupMenu.show();
    }

    private void addClick() {
        Intent intent = new Intent(this, PlacesAutoCompleteActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                finish();
                startActivity(getIntent());
                break;

            case R.id.setting:

                break;
        }
        return false;
    }

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mListFragment.get(position).newInstance(position, mArrForecast.get(position), mListCity.get(position));
        }

        @Override
        public int getCount() {
            return mListFragment.size();
        }

        public void addFragment(WeatherFragment fragment) {
            mListFragment.add(fragment);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mListCity.get(position).getCityName();
        }
    }
}
