package xyz.ratapp.ilx.view.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Map;

import xyz.ratapp.ilx.R;
import xyz.ratapp.ilx.controllers.main.DetailsController;
import xyz.ratapp.ilx.data.dao.Request;

public class DetailsActivity extends AppCompatActivity {

    public static String SHOW_DETAILS_OF_REQUEST_ACTION =
            "xyz.ratapp.ilx.SHOW_DETAILS_OF_REQUEST_ACTION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        DetailsController controller = new DetailsController(this);
    }

    public static Intent getIntent(Request request){
        Intent intent = new Intent();
        intent.setAction(SHOW_DETAILS_OF_REQUEST_ACTION);
        intent.putExtra(DetailsController.STR_ADDRESS, request.getAddress());
        intent.putExtra(DetailsController.STR_TIME, request.getTime());
        intent.putExtra(DetailsController.STR_TASK, request.getTask());
        intent.putExtra(DetailsController.STR_DESCRIPTION, request.getComment());
        intent.putExtra(DetailsController.STR_NAME, request.getName());
        intent.putExtra(DetailsController.STR_PHONE, request.getPhone());

        return intent;
    }


}
