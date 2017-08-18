package xyz.ratapp.ilx.data.dao;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by timtim on 15/08/2017.
 */

public class Rerequest implements Serializable {

    @SerializedName("h1")
    private String h1;
    @SerializedName("h2")
    private String h2;
    @SerializedName("h3")
    private String h3;
    @SerializedName("color_label")
    private String difficult;
    @SerializedName("md_key")
    private String mdKey;

    public Rerequest(String h1, String h2,
                     String h3, String mdKey) {
        this.h1 = h1;
        this.h2 = h2;
        this.h3 = h3;
        this.mdKey = mdKey;
    }

    public String getH1() {
        return h1;
    }

    public String getH2() {
        return h2;
    }

    public String getH3() {
        return h3;
    }

    public String getDifficult() {
        return difficult;
    }
}
