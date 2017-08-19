package xyz.ratapp.ilx.controllers.info;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ebanx.swipebtn.SwipeButton;

import java.util.List;

import xyz.ratapp.ilx.R;
import xyz.ratapp.ilx.controllers.data.DataController;
import xyz.ratapp.ilx.controllers.interfaces.DataSettable;
import xyz.ratapp.ilx.controllers.interfaces.ListSettable;
import xyz.ratapp.ilx.data.dao.Request;
import xyz.ratapp.ilx.ui.activities.DetailsActivity;
import xyz.ratapp.ilx.ui.activities.InfoActivity;
import xyz.ratapp.ilx.ui.activities.RequestInfoActivity;
import xyz.ratapp.ilx.ui.adapters.AddressesAdapter;
import xyz.ratapp.ilx.ui.adapters.CommentsAdapter;
import xyz.ratapp.ilx.ui.adapters.DetailsAdapter;

/**
 * Created by timtim on 14/08/2017.
 */

public class InfoController implements ListSettable<String>,
        DataSettable<Request> {

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
            data.bindReqInfo(this, id);
        }
        else if(activity instanceof DetailsActivity) {
            data.bindDetails(this, id);
        }
    }

    @Override
    public void setData(List<String> data) {
        setupDetailsData(data);
    }

    @Override
    public void setData(Request data) {
        setupReqInfoData(data);
    }

    private void setupDetailsData(List<String> data) {
        RecyclerView rv = activity.findViewById(R.id.rvTasks);
        GridLayoutManager glm = new GridLayoutManager(activity, 1);
        rv.setLayoutManager(glm);
        rv.setAdapter(new DetailsAdapter(activity, data));
    }

    private void setupReqInfoData(Request request) {
        //map
        if(!request.getImage().isEmpty()) {
            ImageView map = activity.findViewById(R.id.ivMap);
            Glide.with(activity).load(request.getImage()).asBitmap().into(map);
            map.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callMap();
                }
            });
        }

        //button
        SwipeButton btn = activity.findViewById(R.id.swipeAccept);
        btn.setText(request.getBtn().getName());

        //rv comments
        RecyclerView rvComments = activity.findViewById(R.id.rvComments);
        GridLayoutManager glm = new GridLayoutManager(activity, 1);
        rvComments.setLayoutManager(glm);
        rvComments.setAdapter(new CommentsAdapter(activity, request.getDetails()));

        //rv addresses
        RecyclerView rvAddresses = activity.findViewById(R.id.rvAddresses);
        GridLayoutManager glmm = new GridLayoutManager(activity, 1);
        rvAddresses.setLayoutManager(glmm);
        rvAddresses.setAdapter(new AddressesAdapter(activity, request.getAddresses()));
    }

    private void callMap() {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?daddr=59.955761,30.313146"));
        activity.startActivity(intent);
    }
}
