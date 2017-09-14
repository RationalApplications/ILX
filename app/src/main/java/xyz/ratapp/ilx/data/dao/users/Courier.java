package xyz.ratapp.ilx.data.dao.users;

import android.content.Context;

import java.util.List;

import xyz.ratapp.ilx.R;
import xyz.ratapp.ilx.data.dao.app.AppStrings;
import xyz.ratapp.ilx.data.dao.orders.BaseOrder;

/**
 * Created by timtim on 01/09/2017.
 *
 * DAO-class for Courier object
 */

public class Courier extends User {

    //auth info
    private String ava;
    private String preview;
    private String courierPhone;
    private String clientName;
    private String clientPhone;
    private String authMd;
    private String authProperty;
    private int gps;
    private String workStatus;
    private int gpsTimeout;
    //Strings for app localization
    private AppStrings elementsName;
    //=== end :) ===
    private String cid;
    private int wareHouseProperty;
    private String courierName;
    private String courierType;
    //list of recent orders and history of orders
    private List<BaseOrder> recent;
    private List<BaseOrder> history;

    public Courier(String domainName, String sessionId) {
        super(domainName, sessionId);
    }

    //init all user data here!
    public void onAuthSuccess(String ava, String preview,
                              String courierPhone, String clientName,
                              String clientPhone, String authProperty,
                              String authMd, int gps,
                              String workStatus, int gpsTimeout,
                              AppStrings elementsName,
                              String cid, int wareHouseProperty,
                              String courierName, String courierType) {
        this.ava = String.format("https://%s/%s", getDomainName(), ava);
        this.preview = String.format("https://%s/%s", getDomainName(), preview);
        this.courierPhone = courierPhone;
        this.clientName = clientName;
        this.clientPhone = clientPhone;
        this.authMd = authMd;
        this.authProperty = authProperty;
        this.gps = gps;
        this.workStatus = workStatus;
        this.gpsTimeout = gpsTimeout;
        this.elementsName = elementsName;
        this.cid = cid;
        this.wareHouseProperty = wareHouseProperty;
        this.courierName = courierName;
        this.courierType = courierType;
    }

    //some setters
    public void setRecentOrders(List<BaseOrder> recent) {
        this.recent = recent;
    }

    public void setHistoryOfOrders(List<BaseOrder> history) {
        this.history = history;
    }

    public void setWorkStatus(boolean workStatus) {
        this.workStatus = workStatus ? "1" : "0";
    }

    //ton of getters :)

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

    public int getGps() {
        return gps;
    }

    public boolean getWorkStatus() {
        return workStatus.equals("1");
    }

    public int getWorkStatusStringId() {
        return workStatus.equals("1") ?
                R.string.online :
                R.string.offline;
    }

    public String getWorkStatus(Context context) {
        return workStatus.equals("1") ?
                context.getString(R.string.online) :
                context.getString(R.string.offline);
    }

    public boolean isOnline() {
        return workStatus.equals("1");
    }

    public int getGpsTimeout() {
        return gpsTimeout;
    }

    public AppStrings getElementsName() {
        return elementsName;
    }

    public String getCid() {
        return cid;
    }

    public int getWareHouseProperty() {
        return wareHouseProperty;
    }

    public String getCourierName() {
        return courierName;
    }

    public String getCourierType() {
        return courierType;
    }

    public List<BaseOrder> getRecent() {
        return recent;
    }

    public List<BaseOrder> getHistory() {
        return history;
    }

    public String getCourierPhone() {
        return courierPhone;
    }

    public String getAuthMd() {
        return authMd;
    }
}
