package xyz.ratapp.ilx.controllers.info;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import xyz.ratapp.ilx.R;
import xyz.ratapp.ilx.controllers.data.DataController;
import xyz.ratapp.ilx.controllers.interfaces.ListSettable;
import xyz.ratapp.ilx.data.Model;
import xyz.ratapp.ilx.data.dao.Details;
import xyz.ratapp.ilx.ui.activities.DetailsActivity;
import xyz.ratapp.ilx.ui.activities.InfoActivity;
import xyz.ratapp.ilx.ui.activities.RequestInfoActivity;
import xyz.ratapp.ilx.ui.adapters.AddressesAdapter;
import xyz.ratapp.ilx.ui.adapters.DetailsAdapter;
import xyz.ratapp.ilx.ui.adapters.RequestsAdapter;

/**
 * Created by timtim on 14/08/2017.
 */

public class InfoController implements ListSettable<Details> {

    private DataController data;
    private InfoActivity activity;
    //request id
    private String id;

    public InfoController(InfoActivity activity) {
        this.activity = activity;
        id = activity.getIntent().getStringExtra("id");
        data = DataController.getInstance();
        setupData();
    }

    private void setupData() {
        if(activity instanceof RequestInfoActivity) {
            setupReqInfoData();
        }
        else if(activity instanceof DetailsActivity) {
            data.bindDetails(this, id);
        }
    }

    @Override
    public void setData(List<Details> data) {
        setupDetailsData(data);
    }

    private void setupDetailsData(List<Details> data) {
        RecyclerView rv = activity.findViewById(R.id.rvTasks);
        GridLayoutManager glm = new GridLayoutManager(activity, 1);
        rv.setLayoutManager(glm);
        rv.setAdapter(new DetailsAdapter(activity, data));
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
