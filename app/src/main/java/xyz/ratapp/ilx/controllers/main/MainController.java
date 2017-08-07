package xyz.ratapp.ilx.controllers.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import xyz.ratapp.ilx.R;
import xyz.ratapp.ilx.view.activities.MainActivity;
import xyz.ratapp.ilx.view.SlidingTabLayout;
import xyz.ratapp.ilx.view.StatusSwitch;

/**
 * Created by timtim on 07/08/2017.
 */

public class MainController
        implements NavigationView.OnNavigationItemSelectedListener {

    private MainActivity activity;
    private DrawerLayout layout;
    private ViewPager container;
    private SectionsPagerAdapter adapter;

    public MainController(MainActivity activity) {
        this.activity = activity;
        layout = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
        adapter = new SectionsPagerAdapter(activity.getSupportFragmentManager());
        container = ((ViewPager) activity.findViewById(R.id.vp_container));
        container.setAdapter(adapter);

        setupUI();
        setupData();
    }

    private void setupUI() {
        //toolbar
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.requests);
        toolbar.addView(new StatusSwitch(activity),
                new Toolbar.LayoutParams(Gravity.END));
        activity.setSupportActionBar(toolbar);

        //tabs
        SlidingTabLayout slidingTabLayout = (SlidingTabLayout)
                activity.findViewById(R.id.stl_tabs);
        slidingTabLayout.setSelectedIndicatorColors(
                activity.getResources().getColor(R.color.tab_line));
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setViewPager(container);

        //drawer
        DrawerLayout drawer = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(activity, drawer, toolbar,
                R.string.nav_drawer_open_desc, R.string.nav_drawer_close_desc);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) activity.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setupData() {

    }

    /**
     * Process press to back-key
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_requests) {
            //TODO: send intent to requests
        }

        layout.closeDrawer(GravityCompat.START);

        return true;
    }

}
