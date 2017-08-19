package xyz.ratapp.ilx.data.dao;

import com.google.gson.annotations.SerializedName;

/**
 * Created by timtim on 19/08/2017.
 */

public class Button {

    @SerializedName("name")
    private String name;
    @SerializedName("url")
    private String url;

    public Button(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
