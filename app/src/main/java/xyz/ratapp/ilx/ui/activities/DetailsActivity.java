package xyz.ratapp.ilx.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import xyz.ratapp.ilx.R;
import xyz.ratapp.ilx.controllers.info.DetailsController;
import xyz.ratapp.ilx.data.dao.Request;

public class DetailsActivity extends AppCompatActivity {

    public static String SHOW_DETAILS_OF_REQUEST_ACTION =
            "xyz.ratapp.logo.SHOW_DETAILS_OF_REQUEST_ACTION";

    public final static String STR_ADDRESS = "address";
    public final static String STR_TIME = "time";
    public final static String STR_TASK = "task";
    public final static String STR_DESCRIPTION = "description";
    public final static String STR_NAME = "name";
    public final static String STR_PHONE = "phone";

    public static Intent getIntent(Request request){
        Intent intent = new Intent();
        intent.setAction(SHOW_DETAILS_OF_REQUEST_ACTION);
        intent.putExtra(STR_ADDRESS, request.getAddress());
        intent.putExtra(STR_TIME, request.getTime());
        intent.putExtra(STR_TASK, request.getTask());
        intent.putExtra(STR_DESCRIPTION, request.getComment());
        intent.putExtra(STR_NAME, request.getName());
        intent.putExtra(STR_PHONE, request.getPhone());

        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        new DetailsController(this);
    }

    //TODO: поднять в рефакторинге и вызвать с контроллера
    public void enableToolbarBack() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
