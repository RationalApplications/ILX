package xyz.ratapp.ilx.data.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Created by timtim on 07/08/2017.
 *
 * Simple DAO for User object
 *
 * Contain specific data for current user
 * such as session-key, name, lastName and image
 */

public class User implements Serializable {

    private String name;
    private String lastName;
    private String sessionKey;
    private String image;
    private boolean online;
    private List<Request> currentRequests;
    private List<Request> historyOfRequests;

    public User(String name, String lastName,
                String sessionKey, String image,
                List<Request> historyOfRequests,
                List<Request> currentRequests) {
        this.name = name;
        this.lastName = lastName;
        this.sessionKey = sessionKey;
        this.image = image;
        this.historyOfRequests = historyOfRequests;
        this.currentRequests = currentRequests;
        online = false;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public String getImage() {
        return image;
    }

    public boolean isOnline() {
        return online;
    }

    public List<Request> getCurrentRequests() {
        return currentRequests;
    }

    public List<Request> getHistoryOfRequests() {
        return historyOfRequests;
    }
}
