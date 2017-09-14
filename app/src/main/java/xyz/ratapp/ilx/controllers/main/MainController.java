package xyz.ratapp.ilx.controllers.main;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import xyz.ratapp.ilx.R;
import xyz.ratapp.ilx.controllers.routing.Screens;
import xyz.ratapp.ilx.controllers.data.DataBinder;
import xyz.ratapp.ilx.controllers.interfaces.DataSettable;
import xyz.ratapp.ilx.data.dao.app.AppStrings;
import xyz.ratapp.ilx.data.dao.orders.BaseOrder;
import xyz.ratapp.ilx.data.dao.orders.Request;
import xyz.ratapp.ilx.data.dao.users.Courier;
import xyz.ratapp.ilx.ui.activities.MainActivity;
import xyz.ratapp.ilx.ui.adapters.BaseOrdersAdapter;
import xyz.ratapp.ilx.ui.fragments.HistoryFragment;
import xyz.ratapp.ilx.ui.fragments.RecentFragment;
import xyz.ratapp.ilx.ui.fragments.RequestFragment;
import xyz.ratapp.ilx.ui.fragments.StockFragment;
import xyz.ratapp.ilx.ui.views.SlidingTabLayout;
import xyz.ratapp.ilx.ui.views.StatusSwitch;

/**
 * Created by timtim on 07/08/2017.
 */

public class MainController implements
        NavigationView.OnNavigationItemSelectedListener,
        DataSettable<Courier> {

    private List<RequestFragment> fragments;
    private DataBinder data;
    private MainActivity activity;
    private DrawerLayout layout;
    private StatusSwitch status;
    //костыль
    private boolean exit = false;
    private int selected = -1;

    public MainController(MainActivity activity) {
        //ui
        this.activity = activity;
        data = DataBinder.getInstance(activity);
        layout = activity.findViewById(R.id.dlMain);
        checkIntent();
    }

    private void checkIntent() {
        Intent intent = activity.getIntent();

        String type = intent.getStringExtra("type");

        if(type != null) {
            String mdKey = null;
            if (type.equals("order_info") ||
                    type.equals("order_chat")) {
                mdKey = intent.getStringExtra("md_key");
            }

            if(!type.isEmpty() || (!type.isEmpty() &&
                    mdKey != null && !mdKey.isEmpty())) {

                if(type.equals("order_list_trading")) {
                    selected = 0;
                }
                else if(type.equals("order_list")) {
                    selected = 1;
                }
                else if(type.equals("order_list_history")) {
                    selected = 2;
                }
                else if(type.equals("order_info")) {
                    //data.sendNextByMdKey(mdKey, "order_info");
                }
                else if(type.equals("order_chat")) {
                }
            }
        }
    }

    /**
     * Method create fragments for main screen
     */
    private void createFragments() {
        fragments = new ArrayList<>();
        StockFragment stock = new StockFragment();
        RecentFragment recent = new RecentFragment();
        HistoryFragment history = new HistoryFragment();
        fragments.add(stock);
        fragments.add(recent);
        fragments.add(history);
        bindFragments();
    }

    /**
     * Method bindCourier fragment to current screen(controller)
     */
    private void bindFragments() {
        for (RequestFragment f : fragments) {
            f.bindController(this);
        }
    }

    /**
     * Method that setup data to fragments
     */
    private void setupData() {
        ViewPager container = activity.findViewById(R.id.vpContainer);
        RequestSectionsPagerAdapter adapter = new RequestSectionsPagerAdapter(
                activity.getSupportFragmentManager());
        adapter.bindFragments(fragments);
        container.setAdapter(adapter);
        SlidingTabLayout stlTabs = activity.findViewById(R.id.stlTabs);
        stlTabs.setViewPager(container);
        data.loadAllData(this);

        if(selected != -1) {
            container.setCurrentItem(selected);
        }
    }

    public void setSwitch(StatusSwitch statusSwitch) {
        this.status = statusSwitch;
        activity.setupToolbar(getStrings().getOrders(), status);
        activity.setupUI();

        //data
        createFragments();
        setupData();
    }

    private RequestFragment getFragment(Screens screen) {
        for (RequestFragment rf : fragments) {
            if(rf.getScreen().equals(screen)) {
                return rf;
            }
        }

        return null;
    }

    public void bindCourier(Courier courier) {
        toggleUpdatingGPS(courier.getWorkStatus());
        activity.bindCourier(courier);

        if(exit) {
            activity.finish();
        }
    }

    /**
     * send app to the next Screen
     *
     * @param from - screen that called next screen
     * @param data - data for next Screen
     */
    public void next(Screens from, Object data) {
        if(BaseOrdersAdapter.screenItemMap.containsKey(from)) {
            boolean recent = from.equals(Screens.RECENT);
            /*String id = this.data.idOf(data, from);
            Intent next = recent ?
                    DetailsActivity.Companion.getIntent(id) :
                    RequestInfoActivity.Companion.getIntent(id, ((Request) data));
            next = from.equals(Screens.HISTORY) ?
                    RequestInfoActivity.Companion.getIntent(id, ((Request) data)) :
                    next;

            //TODO: hardcoded
            int theme = this.data.getState() ?
                    R.style.AppTheme_Active :
                    R.style.AppTheme_Passive;

            next.putExtra("THEME", theme);

            activity.startActivity(next);*/
        }
    }

    public void next(Screens from, Object data, String param) {
        if(BaseOrdersAdapter.screenItemMap.containsKey(from)) {
            /*boolean recent = from.equals(Screens.RECENT);
            String id = this.data.idOf(data, from);
            Intent next = recent ?
                    DetailsActivity.Companion.getIntent(id) :
                    RequestInfoActivity.Companion.getIntent(id, ((Request) data));
            next = from.equals(Screens.HISTORY) ?
                    RequestInfoActivity.Companion.getIntent(id, ((Request) data)) :
                    next;

            //TODO: hardcoded
            int theme = this.data.getState() ?
                    R.style.AppTheme_Active :
                    R.style.AppTheme_Passive;

            next.putExtra("THEME", theme);
            next.putExtra("param", param);

            activity.startActivity(next);*/
        }
    }

    /**
     * set current state - resetup ui,
     * change user state and othe...
     *
     * @param state - true if online,
     *              false if offline
     */
    public void setStateChanged(boolean state) {
        data.setState(this, state);
        data.loadAllData(this);
    }

    private void toggleUpdatingGPS(boolean state) {
        /*Intent i = new Intent(activity, GeoService.class);
        i.putExtra("frequency", data.getFrequency());

        if (state) {
            activity.startService(i);
        }
        else{
            activity.stopService(i);
        }*/
    }

    /**
     * setup User
     *
     * @param courier - User data
     */
    @Override
    public void setData(Courier courier) {
        activity.bindCourier(courier);
        status.setChecked(courier.isOnline());
    }

    /**
     * Process pressing to item of Burger Menu
     *
     * @param item - menuItem that was pressed
     * @return true if processing was success
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.nav_exit) {
            exit = true;
            data.exit();
        }

        layout.closeDrawer(GravityCompat.START);

        return true;
    }

    /**
     * @return current Context
     */
    public Context getContext() {
        return activity;
    }

    public void refresh(Screens screen) {
        if(screen.equals(Screens.STOCK)) {
            data.loadOrderListTrading(this);
        }
        else if(screen.equals(Screens.RECENT)) {
            data.loadOrderList(this);
        }
        else if(screen.equals(Screens.HISTORY)) {
            data.loadOrderListHistory(this);
        }
    }

    public AppStrings getStrings() {
        return data.getStrings();
    }

    public void bindScreens() {
        for(RequestFragment f : fragments) {
            data.bindOrders(f.getScreen(), f);
        }
    }

    public void bindScreen(Screens screen) {
        RequestFragment fr = getFragment(screen);

        if(fr != null) {
            data.bindOrders(fr.getScreen(), fr);
        }
    }
}
