package xyz.ratapp.ilx.data.dao.orders.info;

import com.google.gson.annotations.SerializedName;

/**
 * Created by timtim on 06/09/2017.
 */

public class GeoItem extends Item {

    public GeoItem(String text, String type,
                   String lat, String lng) {
        super(text, type);
        this.lat = lat;
        this.lng = lng;
    }

    @SerializedName("lat")
    private String lat;
    @SerializedName("lng")
    private String lng;

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }
}