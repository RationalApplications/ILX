package xyz.ratapp.ilx.view;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import xyz.ratapp.ilx.R;
import xyz.ratapp.ilx.controllers.main.MainController;

public class MainActivity extends AppCompatActivity {

    private MainController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        controller = new MainController(this);
    }

    @Override
    public void onBackPressed() {
        if(!controller.back()) {
            super.onBackPressed();
        }
    }
}
