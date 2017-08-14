package xyz.ratapp.ilx.controllers.main;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
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
import xyz.ratapp.ilx.controllers.Screens;
import xyz.ratapp.ilx.data.Model;
import xyz.ratapp.ilx.data.dao.Request;
import xyz.ratapp.ilx.data.dao.User;
import xyz.ratapp.ilx.ui.activities.DetailsActivity;
import xyz.ratapp.ilx.ui.activities.MainActivity;
import xyz.ratapp.ilx.ui.activities.RequestInfoActivity;
import xyz.ratapp.ilx.ui.adapters.RequestsAdapter;
import xyz.ratapp.ilx.ui.views.SlidingTabLayout;
import xyz.ratapp.ilx.ui.views.StatusSwitch;

/**
 * Created by timtim on 07/08/2017.
 */

public class MainController
        implements NavigationView.OnNavigationItemSelectedListener {

    private MainActivity activity;
    private SectionsPagerAdapter adapter;
    private Model model;

    public MainController(MainActivity activity) {
        this.activity = activity;
        model = new Model();
        setupData();
    }

    private void setupData() {
        adapter = new SectionsPagerAdapter(activity.getSupportFragmentManager());
        ViewPager container = activity.findViewById(R.id.vpContainer);
        container.setAdapter(adapter);

        adapter.setData(0, model.getNewRequests());
        adapter.setData(1, model.getUser().getCurrentRequests());
        adapter.setData(2, model.getUser().getHistoryOfRequests());
    }

    public void next(Screens from, Object data) {
        if(RequestsAdapter.screenItemMap.containsKey(from)) {
            Request r = (Request) data;
            boolean recent = from.equals(Screens.RECENT);
            Intent next = recent ?
                    DetailsActivity.getIntent(r) :
                    RequestInfoActivity.getIntent(r);

            activity.startActivity(next);
        }
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

    public Context getContext() {
        return activity;
    }

    public void stateChanged(boolean b) {
        User u = model.getUser();
        u.setOnline(b);

        activity.bind(u);
    }
}
