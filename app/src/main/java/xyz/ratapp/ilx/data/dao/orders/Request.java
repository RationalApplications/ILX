package xyz.ratapp.ilx.data.dao.orders;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import xyz.ratapp.ilx.data.dao.orders.info.Address;
import xyz.ratapp.ilx.data.dao.app.Button;

/**
 * Created by timtim on 15/08/2017.
 *
 */

public class Request extends BaseOrder
        implements Serializable {

    @SerializedName("h1")
    private String h1;
    @SerializedName("h2")
    private String h2;
    @SerializedName("h3")
    private String h3;
    @SerializedName("address")
    private Map<String, Address> address;
    @SerializedName("url_image")
    private String image;
    @SerializedName("comments")
    private Map<String, String> comments;
    @SerializedName("button")
    private Button btn;

    public String getH1() {
        return h1;
    }

    public String getH2() {
        return h2;
    }

    public String getH3() {
        return h3;
    }

    public List<Address> getAddress() {
        return new ArrayList<>(address.values());
    }

    public String getImage() {
        return image;
    }

    public List<String> getComments() {
        return new ArrayList<>(comments.values());
    }

    public Button getBtn() {
        return btn;
    }
}
