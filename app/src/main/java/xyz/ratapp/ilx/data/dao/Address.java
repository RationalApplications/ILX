package xyz.ratapp.ilx.data.dao;

import com.google.gson.annotations.SerializedName;

/**
 * Created by timtim on 19/08/2017.
 *
 * "1":
 {
 "h10":"Егорова ул, 12",
 "h11":"09:00-20:00",
 "h12":"Выкуп: 2900₽ ",
 "h13":""
 },
 */

public class Address {

    @SerializedName("h10")
    private String h10;
    @SerializedName("h11")
    private String h11;
    @SerializedName("h12")
    private String h12;
    @SerializedName("h13")
    private String h13;

    public Address(String h10, String h11, String h12, String h13) {
        this.h10 = h10;
        this.h11 = h11;
        this.h12 = h12;
        this.h13 = h13;
    }

    public String getH10() {
        return h10;
    }

    public String getH11() {
        return h11;
    }

    public String getH12() {
        return h12;
    }

    public String getH13() {
        return h13;
    }
}
