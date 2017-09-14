package xyz.ratapp.ilx.data.dao.app;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by timtim on 06/09/2017.
 */

public class Buttons implements Serializable {

    @SerializedName("ok")
    private Button ok;
    @SerializedName("no")
    private NegativeButton no;

    public Button getOk() {
        return ok;
    }

    public NegativeButton getNo() {
        return no;
    }
}
