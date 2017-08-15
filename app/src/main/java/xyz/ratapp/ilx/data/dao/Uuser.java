package xyz.ratapp.ilx.data.dao;

/**
 * Created by timtim on 15/08/2017.
 */

public class Uuser {

    private String courierName;
    private String ava;
    private String preview;
    private String courierPhone;
    private String clientName;
    private String clientPhone;
    private String authProperty;
    private String authMd;
    private String sessionId;
    private String cid;
    private int wareHouseProperty;
    private int gps;
    private String workStatus;
    private String courierType;

    public Uuser(String courierName, String ava,
                 String preview, String courierPhone,
                 String clientName, String clientPhone,
                 String authProperty, String authMd,
                 String sessionId, String cid,
                 int wareHouseProperty, int gps,
                 String workStatus, String courierType) {
        this.courierName = courierName;
        this.ava = ava;
        this.preview = preview;
        this.courierPhone = courierPhone;
        this.clientName = clientName;
        this.clientPhone = clientPhone;
        this.authProperty = authProperty;
        this.authMd = authMd;
        this.sessionId = sessionId;
        this.cid = cid;
        this.wareHouseProperty = wareHouseProperty;
        this.gps = gps;
        this.workStatus = workStatus;
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

    public String getCourierPhone() {
        return courierPhone;
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

    public String getAuthMd() {
        return authMd;
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
