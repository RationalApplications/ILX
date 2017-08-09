package xyz.ratapp.ilx.data.dao;

import android.support.annotation.ColorInt;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

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
    private ArrayList listCoords;


    public Request(String address, String task, String comment, String time,
                   String cost, String commission, String name, String phone, int difficult) {
        this.address = address;
        this.task = task;
        this.comment = comment;
        this.time = time;
        this.cost = cost;
        this.name = name;
        this.phone = phone;
        this.difficult = difficult;
        this.commission = commission;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
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

    public int getDifficult() {
        return difficult;
    }

    public String getCommission() {
        return commission;
    }

    public ArrayList getCoords(){
        LatLng latlng1 = new LatLng(59.9251684, 30.3118085);
        LatLng latlng2 = new LatLng(59.9323387, 30.3481368);
        LatLng latlng3 = new LatLng(59.833029, 30.1891884);

        ArrayList list = new ArrayList();
        list.add(latlng1);
        list.add(latlng2);
        list.add(latlng3);

        return list;
    }
}
