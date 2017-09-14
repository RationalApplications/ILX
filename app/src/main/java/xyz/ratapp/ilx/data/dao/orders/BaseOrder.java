package xyz.ratapp.ilx.data.dao.orders;

import android.graphics.Color;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by timtim on 06/09/2017.
 */

public class BaseOrder implements Serializable {

    @SerializedName("md_key")
    private String mdKey;
    @SerializedName("color_label")
    private String difficult;
    private int difficultColor = -1;

    public String getMdKey() {
        return mdKey;
    }

    public int getDifficult() {
        if(difficultColor == 0) {
            difficultColor = Color.parseColor(difficult);
        }

        return difficultColor;
    }
}
