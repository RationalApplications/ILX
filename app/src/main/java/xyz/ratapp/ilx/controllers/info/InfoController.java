package xyz.ratapp.ilx.controllers.info;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import java.util.Arrays;

import xyz.ratapp.ilx.R;
import xyz.ratapp.ilx.controllers.data.DataBinder;
import xyz.ratapp.ilx.controllers.interfaces.DataSettable;
import xyz.ratapp.ilx.data.dao.app.Button;
import xyz.ratapp.ilx.data.dao.orders.BaseOrder;
import xyz.ratapp.ilx.data.dao.orders.Order;
import xyz.ratapp.ilx.data.dao.orders.Request;
import xyz.ratapp.ilx.ui.activities.DetailsActivity;
import xyz.ratapp.ilx.ui.activities.InfoActivity;
import xyz.ratapp.ilx.ui.activities.RequestInfoActivity;
import xyz.ratapp.ilx.ui.fragments.ChatFragment;
import xyz.ratapp.ilx.ui.fragments.DetailsFragment;
import xyz.ratapp.ilx.ui.fragments.RequestInfoFragment;
import xyz.ratapp.ilx.ui.views.SlidingTabLayout;

/**
 * Created by timtim on 14/08/2017.
 * <p>
 * - Все строки отрисовать/отформатировать по макапу (шрифты чёрные/серые);
 */

public class InfoController implements DataSettable<BaseOrder> {

    private DataBinder data;
    private InfoActivity activity;
    private ViewPager container;
    //request id
    private String id;
    private Order order;
    private Request request;
    private SlidingTabLayout stlTabs;

    //fragments for details activity
    private ChatFragment chat;
    private DetailsFragment details;
    private RequestInfoFragment reqInfo;

    public InfoController(InfoActivity activity) {
        this.activity = activity;
        id = activity.getIntent().getStringExtra("id");
        data = DataBinder.getInstance(activity);
        preSetupUI();
        setupData();
    }

    private void setupData() {
        data.bindInfoData(this);
    }

    private void preSetupUI() {
        String param = activity.getIntent().getStringExtra("param");

        if (activity instanceof DetailsActivity) {
            //setup tabs and fragments
            chat = new ChatFragment();
            chat.setController(this);
            chat.setBtnSendText(data.getStrings().getSendMessageSubmit());
            details = new DetailsFragment();
            details.setBtnSendMessageText(data.getStrings().getSendMessage());
            details.setRouteText(data.getStrings().getRoute());

            container = activity.findViewById(R.id.vpDetailsContainer);
            InfoSectionsPagerAdapter adapter = new InfoSectionsPagerAdapter(
                    activity.getSupportFragmentManager(), data.getStrings());
            adapter.bindFragments(Arrays.asList(details, chat));
            container.setAdapter(adapter);
            stlTabs = activity.findViewById(R.id.stlDetailsTabs);
            stlTabs.setSelectedIndicatorColors(activity.getResources()
                    .getColor(R.color.primary_dark_color));
            stlTabs.setBackgroundResource(R.color.tab_bar_background_color);
            stlTabs.setCustomTabView(R.layout.tab_layout, R.id.tvTabItem);
            stlTabs.setDistributeEvenly(false);
            stlTabs.setViewPager(container);
            container.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    if(position == 0) {
                        View view = activity.getCurrentFocus();

                        if (view != null) {
                            InputMethodManager imm = (InputMethodManager)
                                    activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

            if(param != null && param.equals("order_chat")) {
                container.setCurrentItem(1);
            }
        } else if (activity instanceof RequestInfoActivity &&
                id.startsWith("h")) {
            //setup tabs and fragments
            chat = new ChatFragment();
            chat.setController(this);
            chat.setBtnSendText(data.getStrings().getSendMessageSubmit());
            reqInfo = new RequestInfoFragment();
            reqInfo.bindController(this);



            container = activity.findViewById(R.id.vpReqInfoContainer);
            InfoSectionsPagerAdapter adapter = new InfoSectionsPagerAdapter(
                    activity.getSupportFragmentManager(), data.getStrings());
            adapter.bindFragments(Arrays.asList(reqInfo, chat));
            container.setAdapter(adapter);
            stlTabs = activity.findViewById(R.id.stlReqInfoTabs);
            stlTabs.setSelectedIndicatorColors(activity.getResources()
                    .getColor(R.color.primary_dark_color));
            stlTabs.setBackgroundResource(R.color.tab_bar_background_color);
            stlTabs.setCustomTabView(R.layout.tab_layout, R.id.tvTabItem);
            stlTabs.setDistributeEvenly(false);
            stlTabs.setViewPager(container);
            container.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    if(position == 0) {
                        View view = activity.getCurrentFocus();

                        if (view != null) {
                            InputMethodManager imm = (InputMethodManager)
                                    activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

            if(param != null && param.equals("order_chat")) {
                container.setCurrentItem(1);
            }
        } else if (activity instanceof RequestInfoActivity &&
                id.startsWith("s")) {
            reqInfo = new RequestInfoFragment();
            reqInfo.bindController(this);

            activity.getSupportFragmentManager().beginTransaction().
                    replace(R.id.fl_content, reqInfo).commit();
        }

        if (activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().
                    setTitle(data.getStrings().getOrderView());
        }
    }

    @Override
    public void setData(BaseOrder order) {
        if (order instanceof Request && !id.startsWith("h")) {
            this.request = (Request) order;
            setupReqInfoData();
        } else if (order instanceof Request && id.startsWith("h")) {
            this.request = (Request) order;
            setupReqInfoHistoryData();
        } else if (order instanceof Order) {
            this.order = (Order) order;
            setupDetailsData();
        }
    }

    private void setupDetailsData() {
        if(order.getNewMessages() != 0) {
            ((ViewGroup) stlTabs.getChildAt(0)).getChildAt(1).
                    findViewById(R.id.vNewMessages).
                    setVisibility(View.VISIBLE);
        }

        chat.setData(order.getMessages());
        details.setData(order);
    }

    private void setupReqInfoHistoryData() {
        reqInfo.setData(request);
        reqInfo.setSwipeButtonText(null);
    }

    private void setupReqInfoData() {
        reqInfo.setData(request);
        reqInfo.setSwipeButtonText(data.getStrings().getOrderTradingButton());
    }

    public void onPushButton(Button btn) {
        data.pushButton(btn);
    }

    public void acceptRequest() {
        data.pushButton(request.getBtn());
    }

    public void sendMessage() {
        container.setCurrentItem(1, true);
        chat.callToSendMessage();
    }

    public void sendMessage(String text) {
        try {
            LocationManager lm = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
            Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location == null)
                location = lm.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            if (location == null)
                location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);


            String lat = location.getLatitude() + "";
            String lng = location.getLongitude() + "";
            String speed = location.getSpeed() + "";
            String acc = location.getAccuracy() + "";
            String time = location.getTime() + "";
            String mdKey = order == null ? request.getMdKey() : order.getMdKey();

            data.sendMessage(text, lat, lng, speed, acc, time, mdKey);
        } catch (SecurityException e) {
        }
    }
}
