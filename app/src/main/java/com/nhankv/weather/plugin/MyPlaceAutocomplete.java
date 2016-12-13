//package com.nhankv.weather.plugin;
//
//import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
//
//import android.annotation.TargetApi;
//import android.app.Activity;
//import android.app.Fragment;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.View.OnClickListener;
//import android.widget.EditText;
//
//import com.google.android.gms.R.id;
//import com.google.android.gms.R.layout;
//import com.google.android.gms.common.GoogleApiAvailability;
//import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
//import com.google.android.gms.common.GooglePlayServicesRepairableException;
//import com.google.android.gms.common.api.Status;
//import com.google.android.gms.location.places.AutocompleteFilter;
//import com.google.android.gms.location.places.Place;
//import com.google.android.gms.location.places.ui.PlaceAutocomplete;
//import com.google.android.gms.location.places.ui.PlaceSelectionListener;
//import com.google.android.gms.location.places.ui.PlaceAutocomplete.IntentBuilder;
//import com.google.android.gms.maps.model.LatLngBounds;
//import com.nhankv.weather.R;
//
//@TargetApi(12)
//public class MyPlaceAutocomplete extends Activity {
//    //private View zzaYx;
//    private View zzaYy;
//    private EditText zzaYz;
//    @Nullable
//    private LatLngBounds zzaYA;
//    @Nullable
//    private AutocompleteFilter zzaYB;
//    @Nullable
//    private PlaceSelectionListener zzaYC;
//
//    public MyPlaceAutocomplete() {
//    }
//
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_search);
//        //this.zzaYx = findViewById(id.place_autocomplete_search_button);
//        this.zzaYy = findViewById(id.place_autocomplete_clear_button);
//        this.zzaYz = (EditText) findViewById(id.place_autocomplete_search_input);
//        OnClickListener var5 = new OnClickListener() {
//            public void onClick(View var1) {
//                MyPlaceAutocomplete.this.zzDp();
//            }
//        };
//        //this.zzaYx.setOnClickListener(var5);
//        this.zzaYz.setOnClickListener(var5);
//        this.zzaYy.setOnClickListener(new OnClickListener() {
//            public void onClick(View var1) {
//                MyPlaceAutocomplete.this.setText("");
//            }
//        });
//        this.zzDo();
//    }
//
//    @Override
//    public void onDestroy() {
//        //this.zzaYx = null;
//        this.zzaYy = null;
//        this.zzaYz = null;
//        super.onDestroy();
//    }
//
//    public void setBoundsBias(@Nullable LatLngBounds var1) {
//        this.zzaYA = var1;
//    }
//
//    public void setFilter(@Nullable AutocompleteFilter var1) {
//        this.zzaYB = var1;
//    }
//
//    public void setText(CharSequence var1) {
//        this.zzaYz.setText(var1);
//        this.zzDo();
//    }
//
//    public void setHint(CharSequence var1) {
//        this.zzaYz.setHint(var1);
//        //this.zzaYx.setContentDescription(var1);
//    }
//
//    public void setOnPlaceSelectedListener(PlaceSelectionListener var1) {
//        this.zzaYC = var1;
//    }
//
//    private void zzDo() {
//        boolean var1 = !this.zzaYz.getText().toString().isEmpty();
//        this.zzaYy.setVisibility(var1 ? View.VISIBLE : View.GONE);
//    }
//
//    private void zzDp() {
//        int var1 = -1;
//
//        try {
//            Intent var2 = (new IntentBuilder(2)).setBoundsBias(this.zzaYA).setFilter(this.zzaYB).zzeR(this.zzaYz.getText().toString()).zziH(1).build(this);
//            this.startActivityForResult(var2, 30421);
//        } catch (GooglePlayServicesRepairableException var3) {
//            var1 = var3.getConnectionStatusCode();
//            Log.e("Places", "Could not open autocomplete activity", var3);
//        } catch (GooglePlayServicesNotAvailableException var4) {
//            var1 = var4.errorCode;
//            Log.e("Places", "Could not open autocomplete activity", var4);
//        }
//
//        if (var1 != -1) {
//            GoogleApiAvailability var5 = GoogleApiAvailability.getInstance();
//            var5.showErrorDialogFragment(this, var1, 30422);
//        }
//
//    }
//
//    public void onActivityResult(int var1, int var2, Intent var3) {
//        if (var1 == 30421) {
//            if (var2 == -1) {
//                Place var4 = PlaceAutocomplete.getPlace(this, var3);
//                if (this.zzaYC != null) {
//                    this.zzaYC.onPlaceSelected(var4);
//                }
//
//                this.setText(var4.getName().toString());
//            } else if (var2 == 2) {
//                Status var5 = PlaceAutocomplete.getStatus(this, var3);
//                if (this.zzaYC != null) {
//                    this.zzaYC.onError(var5);
//                }
//            }
//        }
//
//        super.onActivityResult(var1, var2, var3);
//    }
//}
