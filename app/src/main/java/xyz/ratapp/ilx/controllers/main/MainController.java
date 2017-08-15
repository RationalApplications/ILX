package xyz.ratapp.ilx.controllers.main;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import xyz.ratapp.ilx.R;
import xyz.ratapp.ilx.controllers.Screens;
import xyz.ratapp.ilx.controllers.data.DataController;
import xyz.ratapp.ilx.controllers.interfaces.DataSettable;
import xyz.ratapp.ilx.data.dao.Request;
import xyz.ratapp.ilx.data.dao.User;
import xyz.ratapp.ilx.data.dao.Uuser;
import xyz.ratapp.ilx.ui.activities.DetailsActivity;
import xyz.ratapp.ilx.ui.activities.MainActivity;
import xyz.ratapp.ilx.ui.activities.RequestInfoActivity;
import xyz.ratapp.ilx.ui.adapters.RequestsAdapter;
import xyz.ratapp.ilx.ui.fragments.HistoryFragment;
import xyz.ratapp.ilx.ui.fragments.RecentFragment;
import xyz.ratapp.ilx.ui.fragments.RequestFragment;
import xyz.ratapp.ilx.ui.fragments.StockFragment;
import xyz.ratapp.ilx.ui.views.SlidingTabLayout;
import xyz.ratapp.ilx.ui.views.StatusSwitch;

/**
 * Created by timtim on 07/08/2017.
 */

public class MainController
        implements NavigationView.OnNavigationItemSelectedListener,
        DataSettable<Uuser> {

    private List<RequestFragment> fragments;
    private DataController data;
    private MainActivity activity;
    private DrawerLayout layout;

    public MainController(MainActivity activity) {
        //ui
        this.activity = activity;
        layout = activity.findViewById(R.id.dlMain);

        //setup status switch
        StatusSwitch status = new StatusSwitch(activity);
        status.setController(this);
        activity.setupToolbar(status);

        //data
        createFragments();
        setupData();
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
     * Method bind fragment to current screen(controller)
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
        data = DataController.getInstance();
        ViewPager container = activity.findViewById(R.id.vpContainer);
        RequestSectionsPagerAdapter adapter = new RequestSectionsPagerAdapter(
                activity.getSupportFragmentManager());
        adapter.bindFragments(fragments);
        container.setAdapter(adapter);
        SlidingTabLayout stlTabs = activity.findViewById(R.id.stlTabs);
        stlTabs.setViewPager(container);
        bindData();
    }

    /**
     * Method that bind data to all of fragments
     */
    private void bindData() {
        for (RequestFragment f : fragments) {
            data.bindRequests(f.getScreen(), f);
        }

        data.bindUser(this);
    }

    /**
     * send app to the next Screen
     *
     * @param from - screen that called next screen
     * @param data - data for next Screen
     */
    public void next(Screens from, Object data) {
        if(RequestsAdapter.screenItemMap.containsKey(from)) {
            Request r = (Request) data;
            boolean recent = from.equals(Screens.RECENT);
            String id = this.data.idOf(r, from);
            Intent next = recent ?
                    DetailsActivity.Companion.getIntent(id) :
                    RequestInfoActivity.Companion.getIntent(id, r);

            //TODO: hardcoded
            int theme = this.data.getLastState() ?
                    R.style.AppTheme_Active :
                    R.style.AppTheme_Passive;

            next.putExtra("THEME", theme);

            activity.startActivity(next);
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
        Uuser u = data.setState(state);
        activity.bind(u);
    }

    /**
     * setup User
     *
     * @param user - User data
     */
    @Override
    public void setData(Uuser user) {
        activity.bind(user);
    }

    /**
     * Process pressing to back-key
     *
     * @return true if close Drawer
     */
    public boolean back() {
        if (layout.isDrawerOpen(GravityCompat.START)) {
            layout.closeDrawer(GravityCompat.START);

            return true;
        }

        return false;
    }

    /**
     * Process pressing to item of Burger Menu
     *
     * @param item - menuItem that was pressed
     * @return true if processing was success
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        layout.closeDrawer(GravityCompat.START);

        return true;
    }

    /**
     * @return current Context
     */
    public Context getContext() {
        return activity;
    }
}
