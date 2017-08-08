package xyz.ratapp.ilx.view.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import xyz.ratapp.ilx.R;
import xyz.ratapp.ilx.controllers.main.DetailsController;

public class DetailsActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        DetailsController controller = new DetailsController(this);
    }


}
