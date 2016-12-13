package com.nhankv.weather.fragment;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.nhankv.weather.mainactivity.MainActivity;
import com.nhankv.weather.R;
import com.nhankv.weather.model.City;
import com.nhankv.weather.model.Forecast;
import com.nhankv.weather.model.ListDataWeather;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Created by snowflower on 28/08/2016.
 */

@SuppressLint("ValidFragment")
public class WeatherFragment extends Fragment implements WeatherView {
    private final String TAG = getClass().getName();
    private static Forecast mForecast = new Forecast();
    private WeatherPresenter mWeatherPresenter;
    public ListDataWeather listDataWeather = new ListDataWeather();
    private ScrollView mScrollMain;
    //private RelativeLayout mMainLayout;
    private ImageView mImgTempCurrent;
    private ViewGroup mLineForecastTime;
    private ViewGroup mLineForecastDate;
    private TextView mTxtNotifile;
    private TextView mTxtTempLow;
    private TextView mTxtTempHight;
    private TextView mTxtTempCurrent;
    private TextView mTxtForecast5d;
    private TextView mTxtForecast10d;
    private TextView mTextWind;
    private TextView mTextHumidity;
    private TextView mTextPressure;

    public Animation mAnimation;
    public static MainActivity activity;
    public static String typeCity = "";

    public static double currentLatitude, currentLongitude;

    private static final String ARG_SECTION_NUMBER = "section_number";

    @SuppressLint("ValidFragment")
    public WeatherFragment(MainActivity activity) {
        super();
        this.activity = activity;
    }

    public static WeatherFragment newInstance(int sectionNumber, Forecast forecast, City city) {
        WeatherFragment fragment = new WeatherFragment(getMainActivity());
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        mForecast = forecast;
        currentLatitude = city.getLatitude();
        currentLongitude = city.getLongitude();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mWeatherPresenter = new WeatherPresenterImpl(WeatherFragment.this);
        init(rootView);
        mWeatherPresenter.setFragment(this);
        parserForecast();
        return rootView;
    }

    private void init(View view) {
        mScrollMain = (ScrollView) view.findViewById(R.id.scrollMain);
        //mMainLayout = (RelativeLayout) view.findViewById(R.id.mainLayout);
        mLineForecastDate = (LinearLayout) view.findViewById(R.id.lineForecastDate);
        mLineForecastTime = (LinearLayout) view.findViewById(R.id.lineForecastTime);
        mImgTempCurrent = (ImageView) view.findViewById(R.id.imgTempCurrent);

        mTextWind = (TextView) view.findViewById(R.id.textWind);
        mTxtTempLow = (TextView) view.findViewById(R.id.txTempLow);

        mTextHumidity = (TextView) view.findViewById(R.id.textHumidity);
        mTextPressure = (TextView) view.findViewById(R.id.textPressure);
        //mTxtNotifile = (TextView) view.findViewById(R.id.txtNotifile);
        mTxtTempHight = (TextView) view.findViewById(R.id.txTempHight);
       /* mTxtForecast5d = (TextView) view.findViewById(R.id.txForecast5d);
        mTxtForecast10d = (TextView) view.findViewById(R.id.txForecast10d);*/
        mTxtTempCurrent = (TextView) view.findViewById(R.id.txtTempCurrent);
    }

    public void parserForecast() {
        setmCurrently(mForecast.getCurrently());
        setmForecastDaily(mForecast.getDaily(), this);
        setmForecastHourly(mForecast.getHourly(), this);
    }

    public void showToast(final String message) {
        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setmCurrently(Forecast.Currently mCurrently) {
        this.listDataWeather.setmCurrently(mCurrently);
        setForecastCurrent(activity.temperatureType, listDataWeather, mImgTempCurrent, mTxtTempCurrent, mTextWind, mTextHumidity, mTextPressure);
    }

    public void setmForecastDaily(Forecast.ForecastDaily mForecastDaily, WeatherFragment weatherFragment) {
        this.listDataWeather.setmForecastDaily(mForecastDaily);
        mWeatherPresenter.setArrayDataForecastDaily(weatherFragment);
    }

    public void setmForecastHourly(Forecast.ForecastHourly mForecastHourly, WeatherFragment weatherFragment) {
        this.listDataWeather.setmForecastHourly(mForecastHourly);
        mWeatherPresenter.setArrayDataForecastHourly(weatherFragment);
    }

    @Override
    public void setForecastCurrent(String temperatureType, ListDataWeather listDataWeather, ImageView mImgTempCurrent, TextView mTxtTempCurrent, TextView mTextWind, TextView mTextHumidity, TextView mTextPressure) {
        if (mWeatherPresenter != null) {
            mWeatherPresenter.setForecastCurrent(temperatureType, listDataWeather, mImgTempCurrent, mTxtTempCurrent, mTextWind, mTextHumidity, mTextPressure);
        }
    }

    @Override
    public void setAdapterForecastDaily(String temperatureType, WeatherFragment activity, ListDataWeather listDataWeather, ViewGroup lineForecastDate, TextView txtTempLow, TextView txtTempHight) {
        if (mWeatherPresenter != null) {
            mWeatherPresenter.setAdapterForecastDaily(temperatureType, activity, listDataWeather, lineForecastDate, txtTempLow, txtTempHight);
        }
    }

    @Override
    public void setAdapterForecastHourly(String temperatureType, WeatherFragment activity, ListDataWeather listDataWeather, ViewGroup mLineForecastTime) {
        if (mWeatherPresenter != null) {
            mWeatherPresenter.setAdapterForecastHourly(temperatureType, activity, listDataWeather, mLineForecastTime);
        }
    }

    @Override
    public FragmentActivity getActivityFragment() {
        return this.getActivity();
    }

    public static MainActivity getMainActivity() {
        return activity;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.v(this.getClass().getSimpleName(), "onPause()");
    }

    @Override
    public ListDataWeather getListDataWeather() {
        return listDataWeather;
    }

    @Override
    public void setListDataWeather(ListDataWeather listDataWeather) {
        this.listDataWeather = listDataWeather;
    }

    @Override
    public ScrollView getmScrollMain() {
        return mScrollMain;
    }

    @Override
    public void setmScrollMain(ScrollView mScrollMain) {
        this.mScrollMain = mScrollMain;
    }

    @Override
    public LinearLayout getmMainLayout() {
        return activity.mainContent;
    }

    @Override
    public void setmMainLayout(LinearLayout mMainLayout) {
        activity.mainContent = mMainLayout;
    }

    @Override
    public ImageView getmImgTempCurrent() {
        return mImgTempCurrent;
    }

    @Override
    public void setmImgTempCurrent(ImageView mImgTempCurrent) {
        this.mImgTempCurrent = mImgTempCurrent;
    }

    @Override
    public ViewGroup getmLineForecastTime() {
        return mLineForecastTime;
    }

    @Override
    public void setmLineForecastTime(ViewGroup mLineForecastTime) {
        this.mLineForecastTime = mLineForecastTime;
    }

    @Override
    public ViewGroup getmLineForecastDate() {
        return mLineForecastDate;
    }

    @Override
    public void setmLineForecastDate(ViewGroup mLineForecastDate) {
        this.mLineForecastDate = mLineForecastDate;
    }

    @Override
    public TextView getmTxtNotifile() {
        return mTxtNotifile;
    }

    @Override
    public void setmTxtNotifile(TextView mTxtNotifile) {
        this.mTxtNotifile = mTxtNotifile;
    }

    @Override
    public TextView getmTxtTempLow() {
        return mTxtTempLow;
    }

    @Override
    public void setmTxtTempLow(TextView mTxtTempLow) {
        this.mTxtTempLow = mTxtTempLow;
    }

    @Override
    public TextView getmTxtTempHight() {
        return mTxtTempHight;
    }

    @Override
    public void setmTxtTempHight(TextView mTxtTempHight) {
        this.mTxtTempHight = mTxtTempHight;
    }

    @Override
    public TextView getmTxtTempCurrent() {
        return mTxtTempCurrent;
    }

    @Override
    public void setmTxtTempCurrent(TextView mTxtTempCurrent) {
        this.mTxtTempCurrent = mTxtTempCurrent;
    }

    @Override
    public TextView getmTxtForecast5d() {
        return mTxtForecast5d;
    }

    @Override
    public void setmTxtForecast5d(TextView mTxtForecast5d) {
        this.mTxtForecast5d = mTxtForecast5d;
    }

    @Override
    public TextView getmTxtForecast10d() {
        return mTxtForecast10d;
    }

    @Override
    public void setmTxtForecast10d(TextView mTxtForecast10d) {
        this.mTxtForecast10d = mTxtForecast10d;
    }

    @Override
    public TextView getmTextWind() {
        return mTextWind;
    }

    @Override
    public void setmTextWind(TextView mTextWind) {
        this.mTextWind = mTextWind;
    }

    @Override
    public TextView getmTextHumidity() {
        return mTextHumidity;
    }

    @Override
    public void setmTextHumidity(TextView mTextHumidity) {
        this.mTextHumidity = mTextHumidity;
    }

    @Override
    public TextView getmTextPressure() {
        return mTextPressure;
    }

    @Override
    public void setmTextPressure(TextView mTextPressure) {
        this.mTextPressure = mTextPressure;
    }

    @Override
    public Animation getmAnimation() {
        return mAnimation;
    }

    @Override
    public void setmAnimation(Animation mAnimation) {
        this.mAnimation = mAnimation;
    }

    @Override
    public void setBackGroundMain(String url) {
        ImageView imageView = new ImageView(getActivity());
        Glide.with(getActivity()).load(url)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                        // Do something with bitmap here.
                        activity.mainContent.setBackground(new BitmapDrawable(getActivity().getApplicationContext().getResources(), bitmap));
                    }
                });

        /*Picasso.with(getActivity()).load(url).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                activity.mainContent.setBackground(new BitmapDrawable(getActivity().getApplicationContext().getResources(), bitmap));
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
}
