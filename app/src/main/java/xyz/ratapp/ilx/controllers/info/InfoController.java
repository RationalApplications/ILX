package xyz.ratapp.ilx.controllers.info;

import android.support.v4.view.ViewPager;
import java.util.Arrays;
import xyz.ratapp.ilx.R;
import xyz.ratapp.ilx.controllers.data.DataController;
import xyz.ratapp.ilx.controllers.interfaces.DataSettable;
import xyz.ratapp.ilx.data.dao.Button;
import xyz.ratapp.ilx.data.dao.Order;
import xyz.ratapp.ilx.data.dao.Request;
import xyz.ratapp.ilx.ui.activities.DetailsActivity;
import xyz.ratapp.ilx.ui.activities.InfoActivity;
import xyz.ratapp.ilx.ui.activities.RequestInfoActivity;
import xyz.ratapp.ilx.ui.fragments.ChatFragment;
import xyz.ratapp.ilx.ui.fragments.DetailsFragment;
import xyz.ratapp.ilx.ui.fragments.RequestInfoFragment;
import xyz.ratapp.ilx.ui.views.SlidingTabLayout;

/**
 * Created by timtim on 14/08/2017.
 *
 - Все строки отрисовать/отформатировать по макапу (шрифты чёрные/серые);
 */

public class InfoController implements DataSettable<Object> {

    private DataController data;
    private InfoActivity activity;
    private ViewPager container;
    //request id
    private String id;
    private Order order;
    private Request request;

    //fragments for details activity
    private ChatFragment chat;
    private DetailsFragment details;
    private RequestInfoFragment reqInfo;

    public InfoController(InfoActivity activity) {
        this.activity = activity;
        id = activity.getIntent().getStringExtra("id");
        data = DataController.getInstance();
        preSetupUI();
        setupData();
    }

    private void setupData() {
        data.bindInfoData(this, id);
    }

    private void preSetupUI() {
        if (activity instanceof DetailsActivity) {
            //setup tabs and fragments
            chat = new ChatFragment();
            chat.setBtnSendText(data.getNames().getSendMessageSubmit());
            details = new DetailsFragment();
            details.setBtnSendMessageText(data.getNames().getSendMessage());
            details.setRouteText(data.getNames().getRoute());

            container = activity.findViewById(R.id.vpDetailsContainer);
            InfoSectionsPagerAdapter adapter = new InfoSectionsPagerAdapter(
                    activity.getSupportFragmentManager(), data.getNames());
            adapter.bindFragments(Arrays.asList(details, chat));
            container.setAdapter(adapter);
            SlidingTabLayout stlTabs = activity.findViewById(R.id.stlDetailsTabs);
            stlTabs.setSelectedIndicatorColors(activity.getResources()
                    .getColor(R.color.primary_dark_color));
            stlTabs.setBackgroundResource(R.color.tab_bar_background_color);
            stlTabs.setCustomTabView(R.layout.tab_layout, R.id.tvTabItem);
            stlTabs.setDistributeEvenly(false);
            stlTabs.setViewPager(container);
        }
        else if(activity instanceof RequestInfoActivity &&
                id.startsWith("h")) {
            //setup tabs and fragments
            chat = new ChatFragment();
            chat.setBtnSendText(data.getNames().getSendMessageSubmit());
            reqInfo = new RequestInfoFragment();
            reqInfo.bindController(this);

            container = activity.findViewById(R.id.vpReqInfoContainer);
            InfoSectionsPagerAdapter adapter = new InfoSectionsPagerAdapter(
                    activity.getSupportFragmentManager(), data.getNames());
            adapter.bindFragments(Arrays.asList(reqInfo, chat));
            container.setAdapter(adapter);
            SlidingTabLayout stlTabs = activity.findViewById(R.id.stlReqInfoTabs);
            stlTabs.setSelectedIndicatorColors(activity.getResources()
                    .getColor(R.color.primary_dark_color));
            stlTabs.setBackgroundResource(R.color.tab_bar_background_color);
            stlTabs.setCustomTabView(R.layout.tab_layout, R.id.tvTabItem);
            stlTabs.setDistributeEvenly(false);
            stlTabs.setViewPager(container);
        }
        else if(activity instanceof RequestInfoActivity &&
                id.startsWith("s")) {
            reqInfo = new RequestInfoFragment();
            reqInfo.bindController(this);

            activity.getSupportFragmentManager().beginTransaction().
                    replace(R.id.fl_content, reqInfo).commit();
        }

        if(activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().
                    setTitle(data.getNames().getOrderView());
        }
    }

    @Override
    public void setData(Object data) {
        if (data instanceof Request && !id.startsWith("h")) {
            this.request = (Request) data;
            setupReqInfoData();
        }
        else if(data instanceof Request && id.startsWith("h")) {
            this.request = (Request) data;
            setupReqInfoHistoryData();
        }
        else if (data instanceof Order) {
            this.order = (Order) data;
            setupDetailsData();
        }
    }

    private void setupDetailsData() {
        chat.setData(order.getMessages());
        details.setData(order);
    }

    private void setupReqInfoHistoryData() {
        reqInfo.setData(request);
        reqInfo.setSwipeButtonText(null);
    }

    private void setupReqInfoData() {
        reqInfo.setData(request);
        reqInfo.setSwipeButtonText(data.getNames().getOrderTradingButton());
    }

    public void onPushButton(Button btn) {
        data.onPushButton(btn);
    }

    public void acceptRequest() {
        data.onPushButton(request.getBtn());
    }

    public void sendMessage() {
        container.setCurrentItem(1, true);
        chat.callToSendMessage();
    }
}
