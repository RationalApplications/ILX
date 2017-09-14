package xyz.ratapp.ilx.data.dao.orders;


import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import xyz.ratapp.ilx.data.dao.app.Buttons;
import xyz.ratapp.ilx.data.dao.orders.info.GeoItem;
import xyz.ratapp.ilx.data.dao.orders.info.Item;
import xyz.ratapp.ilx.data.dao.orders.info.Message;

/**
 * Created by timtim on 20/08/2017.
 */

public class Order extends BaseOrder
        implements Serializable {

    @SerializedName("h21")
    private String h21;
    @SerializedName("h22")
    private String h22;
    @SerializedName("h23")
    private String h23;
    @SerializedName("h24")
    private String h24;
    @SerializedName("lat")
    private String lat;
    @SerializedName("lng")
    private String lng;
    @SerializedName("items")
    private Map<String, JsonObject> itemMap;
    @SerializedName("buttons")
    private Buttons btns;
    @SerializedName("messages_new")
    private long newMessages;
    @SerializedName("messages")
    private Map<String, Message> messagesMap;

    private transient List<Item> items;
    private transient List<Message> messages;


    public String getH21() {
        return h21;
    }

    public String getH22() {
        return h22;
    }

    public String getH23() {
        return h23;
    }

    public String getH24() {
        return h24;
    }

    public LatLng getLocation(){
        double lat = Double.parseDouble(this.lat);
        double lng = Double.parseDouble(this.lng);

        return new LatLng(lat, lng);
    }

    public List<Item> getItems() {
        if(items == null) {
            items = new ArrayList<>();

            for (JsonObject dojo : itemMap.values()) {
                String text = dojo.get("text").getAsString();
                String type = dojo.get("type").getAsString();

                if(type.equals("address")) {
                    String lat = dojo.get("lat").getAsString();
                    String lng = dojo.get("lng").getAsString();

                    items.add(new GeoItem(text, type, lat, lng));
                }
                else {
                    items.add(new Item(text, type));
                }
            }
        }

        return items;
    }

    public Buttons getBtns() {
        return btns;
    }

    public long getNewMessages() {
        return newMessages;
    }

    public List<Message> getMessages() {
        if(messages == null) {
            messages = new ArrayList<>(messagesMap.values());
        }

        return messages;
    }

}
