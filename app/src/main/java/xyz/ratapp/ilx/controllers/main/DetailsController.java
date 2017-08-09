package xyz.ratapp.ilx.controllers.main;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import xyz.ratapp.ilx.R;
import xyz.ratapp.ilx.view.activities.DetailsActivity;

/**
 * Created by Олег on 08.08.2017.
 */

public class DetailsController {

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
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?daddr=20.5666,45.345"));
                activity.startActivity(intent);
            }
        });

        (activity.findViewById(R.id.btn_write_logist)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: dialog logistic chat
            }
        });

        (activity.findViewById(R.id.btn_start_order)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Start order
            }
        });

        (activity.findViewById(R.id.btn_issue)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: ISSUE ????
            }
        });

        (activity.findViewById(R.id.img_phone)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:" + ((TextView)activity.findViewById(R.id.tv_telephone)).getText().toString()));
                activity.startActivity(intent);
            }
        });
    }

    private void setData() {
        ((TextView)activity.findViewById(R.id.tv_address)).setText(activity.getIntent().getStringExtra(DetailsActivity.STR_ADDRESS));
        ((TextView)activity.findViewById(R.id.tv_time)).setText(activity.getIntent().getStringExtra(DetailsActivity.STR_TIME));
        ((TextView)activity.findViewById(R.id.tv_task)).setText(activity.getIntent().getStringExtra(DetailsActivity.STR_TASK));
        ((TextView)activity.findViewById(R.id.tv_description)).setText(activity.getIntent().getStringExtra(DetailsActivity.STR_DESCRIPTION));

        ((TextView)activity.findViewById(R.id.tv_name)).setText(activity.getIntent().getStringExtra(DetailsActivity.STR_NAME));
        ((TextView)activity.findViewById(R.id.tv_telephone)).setText(activity.getIntent().getStringExtra(DetailsActivity.STR_PHONE));
    }
}
