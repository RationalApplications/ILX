package xyz.ratapp.ilx.data.dao.orders.info;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by timtim on 06/09/2017.
 */

public class Item implements Serializable {

    @SerializedName("text")
    private String text;
    @SerializedName("type")
    private String type;

    public Item(String text, String type) {
        this.text = text;
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public String getType() {
        return type;
    }
}