package xyz.ratapp.ilx.data.dao;

import android.support.annotation.ColorInt;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by timtim on 07/08/2017.
 *
 * Simple DAO for Request object
 */

public class Request {

    private String address;
    private String task;
    private String comment;
    private String time;
    private String cost;
    private String name;
    private String phone;
    private String commission;
    @ColorInt
    private int difficult;
    private String image;
    private List<String> details;
    private List<Address> addresses;
    private Button btn;


    public Request(String address, String task, String comment,
                   String time, String cost, String commission,
                   String name, String phone, int difficult) {
        this.address = address;
        this.task = task;
        this.comment = comment;
        this.time = time;
        this.cost = cost;
        this.name = name;
        this.phone = phone;
        this.commission = commission;
        this.difficult = difficult;
    }

    public Request(String address, String comment, String cost,
                   String image, List<String> details,
                   List<Address> addresses, Button btn) {
        this.address = address;
        this.comment = comment;
        this.cost = cost;
        this.image = image;
        this.details = details;
        this.addresses = addresses;
        this.btn = btn;
    }

    public Request(String address, String comment, String cost,
                   int difficult, String image, List<String> details,
                   List<Address> addresses, Button btn) {
        this.address = address;
        this.comment = comment;
        this.cost = cost;
        this.difficult = difficult;
        this.image = image;
        this.details = details;
        this.addresses = addresses;
        this.btn = btn;
    }

    public String getAddress() {
        return address;
    }

    public String getTask() {
        return task;
    }

    public String getComment() {
        return comment;
    }

    public String getTime() {
        return time;
    }

    public String getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getCommission() {
        return commission;
    }

    public int getDifficult() {
        return difficult;
    }

    public String getImage() {
        return image;
    }

    public List<String> getDetails() {
        if(details == null) {
            String address = "Садовая ул., 54, офис 19";
            String time = "10:00-12:00";
            String cost = "Выкупить товар: -3200 руб";
            String task = "Забрать два заказа, иметь паспорт при себе";
            String name = "Имя: Ольга Ивановна";
            String phone = "Телефон: +7(905)207-22-87";

            return Arrays.asList(address, time, cost, task, name, phone);
        }

        return details;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public Button getBtn() {
        return btn;
    }
}
