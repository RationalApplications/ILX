package xyz.ratapp.ilx.controllers.main;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import xyz.ratapp.ilx.R;
import xyz.ratapp.ilx.data.Model;
import xyz.ratapp.ilx.data.dao.User;
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
    private SectionsPagerAdapter adapter;
    private Model model;

    public MainController(MainActivity activity) {
        this.activity = activity;
        model = new Model();
        setupUI();
        setupData();
    }

    private void setupUI() {
        //setup adapter, find views
        layout = (DrawerLayout) activity.findViewById(R.id.dl_main);
        adapter = new SectionsPagerAdapter(activity.getSupportFragmentManager());
        ViewPager container = (ViewPager) activity.findViewById(R.id.vp_container);
        container.setAdapter(adapter);

        //toolbar
        final Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.requests);
        toolbar.setBackgroundResource(R.color.grey);
        //status switch
        StatusSwitch status = new StatusSwitch(activity,
                new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                compoundButton.setText(b ?
                        R.string.online :
                        R.string.offline);
                toolbar.setBackgroundResource(b ?
                        R.color.colorPrimary :
                        R.color.grey);

                model.getUser().setOnline(b);
                setupUser();
            }
        });
        toolbar.addView(status,
                new Toolbar.LayoutParams(Gravity.END));
        activity.setSupportActionBar(toolbar);

        //tabs
        SlidingTabLayout slidingTabLayout = (SlidingTabLayout)
                activity.findViewById(R.id.stl_tabs);
        slidingTabLayout.setSelectedIndicatorColors(
                activity.getResources().getColor(R.color.white));
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setViewPager(container);

        //drawer
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(activity, layout, toolbar,
                R.string.nav_drawer_open_desc, R.string.nav_drawer_close_desc);
        layout.addDrawerListener(toggle);
        toggle.syncState();
        setupUser();

        NavigationView navigationView = (NavigationView) activity.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setupUser() {
        NavigationView navigationView = layout.findViewById(R.id.nav_view);
        View layout = navigationView.getHeaderView(0);

        ImageView photo = layout.findViewById(R.id.iv_nav_icon);
        TextView name = layout.findViewById(R.id.tv_nav_header);
        TextView text = layout.findViewById(R.id.tv_nav_text);

        User user = model.getUser();
        Glide.with(activity).load(user.getImage()).asBitmap().into(photo);
        name.setText(user.getName() + " " + user.getLastName());
        text.setText(user.isOnline() ? "Online" : "Offline");
    }

    private void setupData() {
        adapter.setData(0, model.getNewRequests());
        adapter.setData(1, model.getUser().getCurrentRequests());
        adapter.setData(2, model.getUser().getHistoryOfRequests());
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

            //TODO: send intent to requests?
        }

        layout.closeDrawer(GravityCompat.START);

        return true;
    }

}
