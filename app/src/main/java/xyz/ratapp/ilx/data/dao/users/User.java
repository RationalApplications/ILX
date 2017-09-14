package xyz.ratapp.ilx.data.dao.users;

import java.io.Serializable;

/**
 * Created by timtim on 01/09/2017.
 *
 * Simple class for user - contain only
 * session id and domain name
 *
 * ***
 * Use this class for serializing
 * user data, Luck
 * ***
 */

public class User implements Serializable {

    private String domainName;
    private String sessionId;

    public User(String domainName, String sessionId) {
        this.domainName = domainName;
        this.sessionId = sessionId;
    }

    public String getDomainName() {
        return domainName;
    }

    public String getSessionId() {
        return sessionId;
    }
}
