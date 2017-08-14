package xyz.ratapp.ilx.controllers.info;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import xyz.ratapp.ilx.R;
import xyz.ratapp.ilx.data.Model;
import xyz.ratapp.ilx.ui.activities.DetailsActivity;
import xyz.ratapp.ilx.ui.activities.InfoActivity;
import xyz.ratapp.ilx.ui.activities.RequestInfoActivity;
import xyz.ratapp.ilx.ui.adapters.AddressesAdapter;

/**
 * Created by timtim on 14/08/2017.
 */

public class InfoController {

    private InfoActivity activity;

    public InfoController(InfoActivity activity) {
        this.activity = activity;
        setupData();
    }

    private void setupData() {
        if(activity instanceof RequestInfoActivity) {
            setupReqInfoData();
        }
        else if(activity instanceof DetailsActivity) {
            setupDetailsData();
        }
    }

    private void setupDetailsData() {
        /*
        ((TextView)activity.findViewById(R.id.tv_address)).setText(activity.getIntent().getStringExtra(DetailsActivity.STR_ADDRESS));
        ((TextView)activity.findViewById(R.id.tv_time)).setText(activity.getIntent().getStringExtra(DetailsActivity.STR_TIME));
        ((TextView)activity.findViewById(R.id.tv_task)).setText(activity.getIntent().getStringExtra(DetailsActivity.STR_TASK));
        ((TextView)activity.findViewById(R.id.tv_description)).setText(activity.getIntent().getStringExtra(DetailsActivity.STR_DESCRIPTION));

        ((TextView)activity.findViewById(R.id.tv_name)).setText("Имя: " + activity.getIntent().getStringExtra(DetailsActivity.STR_NAME));
        ((TextView)activity.findViewById(R.id.tv_telephone)).setText("Телефон: " + activity.getIntent().getStringExtra(DetailsActivity.STR_PHONE));
        */
    }

    private void setupReqInfoData() {
        activity.findViewById(R.id.ivMap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callMap();
            }
        });

        //rv
        Model model = new Model();
        RecyclerView rvAddresses = activity.findViewById(R.id.rvAddresses);
        GridLayoutManager glm = new GridLayoutManager(activity, 1);
        rvAddresses.setLayoutManager(glm);
        rvAddresses.setAdapter(new AddressesAdapter(activity, model.getNewRequests()));
    }

    private void callMap() {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?daddr=59.955761,30.313146"));
        activity.startActivity(intent);
    }
}
