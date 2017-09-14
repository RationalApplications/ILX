package xyz.ratapp.ilx.data.dao.users;

/**
 * Created by Олег on 15.08.2017.
 */

public class CourierLocation {

    private String latitude;
    private String longitude;
    private String time;
    private String speed;
    private String acc;

    public CourierLocation(String latitude, String longitude,
                           String time, String speed, String acc) {
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