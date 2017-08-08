package xyz.ratapp.ilx.data.dao;

import android.support.annotation.ColorInt;

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
    @ColorInt
    private int difficult;

    public Request(String address, String task, String comment,
                   String time, String cost, int difficult) {
        this.address = address;
        this.task = task;
        this.comment = comment;
        this.time = time;
        this.cost = cost;
        this.difficult = difficult;
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
}
