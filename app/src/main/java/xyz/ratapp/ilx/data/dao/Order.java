package xyz.ratapp.ilx.data.dao;

import android.graphics.Color;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by timtim on 20/08/2017.
 */

public class Order implements Serializable {

    @SerializedName("h21")
    private String h21;
    @SerializedName("h22")
    private String h22;
    @SerializedName("h23")
    private String h23;
    @SerializedName("h24")
    private String h24;
    @SerializedName("color_label")
    private String color;
    @SerializedName("lat")
    private String lat;
    @SerializedName("lng")
    private String lng;
    @SerializedName("md_key")
    private String mdKey;
    @SerializedName("items")
    private Map<String, JsonObject> itemMap;
    @SerializedName("buttons")
    private Buttons btns;
    @SerializedName("messages_new")
    private long newMessages;
    @SerializedName("messages")
    private Map<String, Message> messages;

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

    public int getColor() {
        return Color.parseColor(color);
    }

    public LatLng getLocation(){
        double lat = Double.parseDouble(this.lat);
        double lng = Double.parseDouble(this.lng);

        return new LatLng(lat, lng);
    }

    public String getMdKey() {
        return mdKey;
    }

    public List<Item> getItems() {
        List<Item> result = new ArrayList<>();

        for (JsonObject dojo : itemMap.values()) {
            String text = dojo.get("text").getAsString();
            String type = dojo.get("type").getAsString();

            if(type.equals("address")) {
                String lat = dojo.get("lat").getAsString();
                String lng = dojo.get("lng").getAsString();

                result.add(new GeoItem(text, type, lat, lng));
            }
            else {
                result.add(new Item(text, type));
            }
        }

        return result;
    }

    public Buttons getBtns() {
        return btns;
    }

    public long getNewMessages() {
        return newMessages;
    }

    public Map<String, Message> getMessages() {
        return messages;
    }

    public static class Message implements Serializable {

        @SerializedName("author")
        private String author;
        @SerializedName("author_id")
        private String authorId;
        @SerializedName("message")
        private String message;
        @SerializedName("date_add")
        private String addDate;

        public String getAuthor() {
            return author;
        }

        public String getAuthorId() {
            return authorId;
        }

        public String getMessage() {
            return message;
        }

        public String getAddDate() {
            return addDate;
        }
    }

    public static class Buttons implements Serializable {

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

    public static class NegativeButton implements Serializable {

        @SerializedName("name")
        private String name;
        @SerializedName("options")
        private List<Button> options;

        public String getName() {
            return name;
        }

        public List<Button> getOptions() {
            return options;
        }
    }

    public static class Item implements Serializable {

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

    public static class GeoItem extends Item {

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
}
