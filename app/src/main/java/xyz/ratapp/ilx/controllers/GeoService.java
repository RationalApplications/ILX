package xyz.ratapp.ilx.controllers;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import xyz.ratapp.ilx.R;
import xyz.ratapp.ilx.controllers.data.DataBinder;
import xyz.ratapp.ilx.data.dao.users.CourierLocation;

public class GeoService extends Service {


    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private LocationCallback mLocationCallback;
    private FusedLocationProviderClient mFusedLocationClient;

    private static int INTERVAL;
    private static int FASTEST_INTERVAL;



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        INTERVAL = intent.getIntExtra("frequency", 1000) * 1000;
        FASTEST_INTERVAL = ((int) (INTERVAL * 0.9));

        buildGoogleApi();

        createLocationRequest();
        createLocationCallback();

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        startLocationUpdates();

        return super.onStartCommand(intent, flags, startId);
    }


    private void buildGoogleApi() {
        mGoogleApiClient = new GoogleApiClient.Builder(this).
                addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(@Nullable Bundle bundle) {
                        log("connected");
                    }

                    @Override
                    public void onConnectionSuspended(int i) {

                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                })
                .addApi(LocationServices.API).build();

        mGoogleApiClient.connect();
    }


    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }


    private void createLocationCallback() {
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                log("Loc: " + locationResult.getLastLocation());

                CourierLocation location = new CourierLocation(locationResult.getLastLocation().getLatitude() + "",
                        locationResult.getLastLocation().getLongitude() + "",
                        locationResult.getLastLocation().getTime() + "",
                        locationResult.getLastLocation().getSpeed() + "",
                        locationResult.getLastLocation().getAccuracy() + "");

                DataBinder controller = DataBinder.getInstance(getApplicationContext());

                //controller.courierLocation(location, getApplicationContext());
            }
        };
    }


    private void startLocationUpdates() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            log("no permission");
            return;
        }

        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                mLocationCallback, Looper.myLooper());
    }


    @Override
    public void onDestroy() {
        stopLocationUpdates();
        super.onDestroy();
    }

    private void stopLocationUpdates() {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }





    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void log(String text) {
        Log.i("MyTag", text);
    }

    public boolean checkGPSConnected(){
        LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        boolean enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!enabled) {
            Toast.makeText(getApplicationContext(), R.string.enable_geo, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

}
