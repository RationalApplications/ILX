package xyz.ratapp.ilx.data.dao;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by timtim on 15/08/2017.
 *
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
    @SerializedName("address")
    private Map<String, Address> address;
    @SerializedName("url_image")
    private String image;
    @SerializedName("comments")
    private Map<String, String> comments;
    @SerializedName("button")
    private Button btn;


    public Rerequest(String h1, String h2, String h3,
                     String difficult, String mdKey,

                     Map<String, Address> address,
                     String image,
                     Map<String, String> comments, Button btn) {
        this.h1 = h1;
        this.h2 = h2;
        this.h3 = h3;
        this.difficult = difficult;
        this.mdKey = mdKey;
        this.address = address;
        this.image = image;
        this.comments = comments;
        this.btn = btn;
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

    public String getMdKey() {
        return mdKey;
    }

    public Map<String, Address> getAddress() {
        return address;
    }

    public String getImage() {
        return image;
    }

    public Map<String, String> getComments() {
        return comments;
    }

    public Button getBtn() {
        return btn;
    }
}
