package xyz.ratapp.ilx.view.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
