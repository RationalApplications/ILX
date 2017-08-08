package xyz.ratapp.ilx.controllers.main;

import android.view.View;
import android.widget.TextView;

import xyz.ratapp.ilx.R;
import xyz.ratapp.ilx.view.activities.DetailsActivity;

/**
 * Created by Олег on 08.08.2017.
 */

public class DetailsController {
    public final static String STR_ADDRESS = "address";
    public final static String STR_TIME = "time";
    public final static String STR_TASK = "task";
    public final static String SIR_DESCRIPTION = "description";
    public final static String STR_NAME = "name";
    public final static String STR_PHONE = "phone";

    private DetailsActivity activity;

    public DetailsController(DetailsActivity activity){
        this.activity = activity;

        setData();
        setUI();
    }

    private void setUI() {
        (activity.findViewById(R.id.btn_way)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        (activity.findViewById(R.id.btn_write_logist)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        (activity.findViewById(R.id.btn_start_order)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        (activity.findViewById(R.id.btn_issue)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        (activity.findViewById(R.id.img_phone)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void setData() {
        ((TextView)activity.findViewById(R.id.tv_address)).setText(activity.getIntent().getStringExtra(STR_ADDRESS));
        ((TextView)activity.findViewById(R.id.tv_time)).setText(activity.getIntent().getStringExtra(STR_TIME));
        ((TextView)activity.findViewById(R.id.tv_task)).setText(activity.getIntent().getStringExtra(STR_TASK));
        ((TextView)activity.findViewById(R.id.tv_description)).setText(activity.getIntent().getStringExtra(SIR_DESCRIPTION));

        ((TextView)activity.findViewById(R.id.tv_name)).setText(activity.getIntent().getStringExtra(STR_NAME));
        ((TextView)activity.findViewById(R.id.tv_telephone)).setText(activity.getIntent().getStringExtra(STR_PHONE));
    }
}
