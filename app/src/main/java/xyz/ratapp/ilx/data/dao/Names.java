package xyz.ratapp.ilx.data.dao;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by timtim on 23/08/2017.
 *
 * DAO that saves all text-data about
 * texts of buttons, titles of toolbar
 * and other
 */

public class Names implements Serializable {

    @SerializedName("orders")
    private String orders;
    @SerializedName("order_list_trading")
    private String orderListTrading;
    @SerializedName("order_list")
    private String orderList;
    @SerializedName("order_list_hostory")
    private String orderListHistory;
    @SerializedName("order_trading_button")
    private String orderTradingButton;
    @SerializedName("order_view")
    private String orderView;
    @SerializedName("order_view_info")
    private String orderViewInfo;
    @SerializedName("order_view_chat")
    private String orderViewChat;
    @SerializedName("route")
    private String route;
    @SerializedName("send_message")
    private String sendMessage;
    @SerializedName("send_message_submit")
    private String sendMessageSubmit;


    public String getOrders() {
        return orders;
    }

    public String getOrderListTrading() {
        return orderListTrading;
    }

    public String getOrderList() {
        return orderList;
    }

    public String getOrderListHistory() {
        return orderListHistory;
    }

    public String getOrderTradingButton() {
        return orderTradingButton;
    }

    public String getOrderView() {
        return orderView;
    }

    public String getOrderViewInfo() {
        return orderViewInfo;
    }

    public String getOrderViewChat() {
        return orderViewChat;
    }

    public String getRoute() {
        return route;
    }

    public String getSendMessage() {
        return sendMessage;
    }

    public String getSendMessageSubmit() {
        return sendMessageSubmit;
    }
}
