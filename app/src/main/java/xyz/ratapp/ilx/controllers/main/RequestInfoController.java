package xyz.ratapp.ilx.controllers.main;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ebanx.swipebtn.OnStateChangeListener;
import com.ebanx.swipebtn.SwipeButton;

import xyz.ratapp.ilx.R;
import xyz.ratapp.ilx.data.Model;
import xyz.ratapp.ilx.view.activities.RequestInfoActivity;
import xyz.ratapp.ilx.view.adapters.AddressesAdapter;
import xyz.ratapp.ilx.view.adapters.RequestsAdapter;

/**
 * Created by Олег on 09.08.2017.
 */

public class RequestInfoController {

    RequestInfoActivity activity;

    public RequestInfoController(RequestInfoActivity activity){
        this.activity = activity;

        setUI();
        setData();
    }

    private void setData() {
        ((TextView) activity.findViewById(R.id.tv_delivery_cost)).setText("За доставку: " + activity.getIntent().getStringExtra(RequestInfoActivity.STR_COST));
        ((TextView) activity.findViewById(R.id.tv_comission)).setText("Коммисия: " + activity.getIntent().getStringExtra(RequestInfoActivity.STR_COMMISSION));

        ((TextView) (activity.findViewById(R.id.inc_header)).findViewById(R.id.tv_cost)).setText(
                activity.getIntent().getStringExtra(RequestInfoActivity.STR_COST)
        );

        ((TextView) (activity.findViewById(R.id.inc_header)).findViewById(R.id.tv_comment)).setText(
                activity.getIntent().getStringExtra(RequestInfoActivity.STR_COMMENT)
        );

        ((TextView) (activity.findViewById(R.id.inc_header)).findViewById(R.id.tv_title)).setText(
                activity.getIntent().getStringExtra(RequestInfoActivity.STR_TITLE)
        );

        activity.findViewById(R.id.iv_map_holder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?daddr=59.955761,30.313146"));
                activity.startActivity(intent);
            }
        });

        //TODO: color line

        //rv
        Model model = new Model();

        RecyclerView rvAddresses = activity.findViewById(R.id.rv_addresses);
        GridLayoutManager glm = new GridLayoutManager(activity, 1);
        rvAddresses.setLayoutManager(glm);
        rvAddresses.setAdapter(new AddressesAdapter(activity, model.getNewRequests()));
    }

    private void setUI() {
        ((SwipeButton)activity.findViewById(R.id.swipe_accept)).setOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public void onStateChange(boolean active) {
                //TODO: Accept order
            }
        });
    }
}
