package xyz.ratapp.ilx.data.dao;

/**
 * Created by timtim on 15/08/2017.
 */

public class Uuser {

    private String ava;
    private String preview;
    private String clientName;
    private String clientPhone;
    private String authProperty;
    private String sessionId;
    private int gps;
    private String workStatus;
    private String gpsTimeout;
    private String cid;
    private int wareHouseProperty;
    private String courierName;
    private String courierType;


    public Uuser(String ava, String preview, String clientName, String clientPhone,
                 String authProperty, String sessionId, int gps, String workStatus,
                 String gpsTimeout, String cid, int wareHouseProperty,
                 String courierName, String courierType) {
        this.ava = ava;
        this.preview = preview;
        this.clientName = clientName;
        this.clientPhone = clientPhone;
        this.authProperty = authProperty;
        this.sessionId = sessionId;
        this.gps = gps;
        this.workStatus = workStatus;
        this.gpsTimeout = gpsTimeout;
        this.cid = cid;
        this.wareHouseProperty = wareHouseProperty;
        this.courierName = courierName;
        this.courierType = courierType;
    }

    public String getCourierName() {
        return courierName;
    }

    public String getAva() {
        return ava;
    }

    public String getPreview() {
        return preview;
    }

    public String getClientName() {
        return clientName;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public String getAuthProperty() {
        return authProperty;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getCid() {
        return cid;
    }

    public int getWareHouseProperty() {
        return wareHouseProperty;
    }

    public int getGps() {
        return gps;
    }

    public String getGpsTimeout() {
        return gpsTimeout;
    }

    public String getWorkStatus() {
        //TODO: hardcoded
        return workStatus.equals("1") ? "Онлайн" : "Оффлайн";
    }

    public boolean isOnline() {
        return workStatus.equals("1");
    }

    public String getCourierType() {
        return courierType;
    }

    public void setOnline(boolean online) {
        this.workStatus = online ? "1" : "0";
    }
}
