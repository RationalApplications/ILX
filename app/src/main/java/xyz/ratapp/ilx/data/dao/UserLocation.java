package xyz.ratapp.ilx.data.dao;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Олег on 15.08.2017.
 */

public class UserLocation {

    private String latitude;
    private String longitude;
    private String time;
    private String speed;
    private String acc;

    public UserLocation(String latitude, String longitude, String time, String speed, String acc) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.time = time;
        this.speed = speed;
        this.acc = acc;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getTime() {
        return time;
    }

    public String getSpeed() {
        return speed;
    }

    public String getAcc() {
        return acc;
    }


}