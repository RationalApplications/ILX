package xyz.ratapp.ilx.ui.activities;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.StyleRes;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import xyz.ratapp.ilx.R;
import xyz.ratapp.ilx.controllers.main.MainController;
import xyz.ratapp.ilx.data.dao.User;
import xyz.ratapp.ilx.ui.helpers.ThemePicker;
import xyz.ratapp.ilx.ui.views.SlidingTabLayout;
import xyz.ratapp.ilx.ui.views.StatusSwitch;

public class MainActivity extends AppCompatActivity {

    //controller
    private MainController controller;
    //ui
    private ThemePicker picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        picker = new ThemePicker(this);
        setContentView(R.layout.activity_main);
        controller = new MainController(this);
    }

    public void setupUI() {
        //find views
        DrawerLayout layout = findViewById(R.id.dlMain);
        ViewPager container = findViewById(R.id.vpContainer);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        final SlidingTabLayout slidingTabLayout = findViewById(R.id.stlTabs);
        final NavigationView navigationView = findViewById(R.id.navView);

        //toolbar
        toolbar.setTitle(R.string.requests);
        setSupportActionBar(toolbar);

        //tabs
        slidingTabLayout.setSelectedIndicatorColors(
                getResources().getColor(R.color.primary_dark_color));
        slidingTabLayout.setBackgroundResource(R.color.tab_bar_background_color);
        slidingTabLayout.setCustomTabView(R.layout.tab_layout, R.id.tvTabItem);
        slidingTabLayout.setDistributeEvenly(false);
        slidingTabLayout.setViewPager(container);

        //drawer
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, layout, toolbar,
                R.string.nav_drawer_open_desc, R.string.nav_drawer_close_desc);
        layout.addDrawerListener(toggle);
        toggle.syncState();

        //navigationView
        navigationView.setNavigationItemSelectedListener(controller);

        //status switch
        StatusSwitch status = new StatusSwitch(controller);
        toolbar.addView(status, new Toolbar.LayoutParams(Gravity.END));
    }

    public void bind(User user) {
        NavigationView navigationView = findViewById(R.id.navView);
        View layout = navigationView.getHeaderView(0);

        ImageView photo = layout.findViewById(R.id.ivNavIcon);
        TextView name = layout.findViewById(R.id.tvNavHeader);
        TextView text = layout.findViewById(R.id.tvNavText);

        Glide.with(this).load(user.getImage()).asBitmap().into(photo);
        name.setText(user.getFullName());
        text.setText(user.getStateText());

        picker.setTheme(user.isOnline());
    }

    @Override
    public void onBackPressed() {
        if (!controller.back()) {
            super.onBackPressed();
        }
    }

}
