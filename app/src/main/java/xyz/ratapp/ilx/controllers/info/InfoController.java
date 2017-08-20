package xyz.ratapp.ilx.controllers.info;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ebanx.swipebtn.SwipeButton;

import java.util.List;

import xyz.ratapp.ilx.R;
import xyz.ratapp.ilx.controllers.data.DataController;
import xyz.ratapp.ilx.controllers.interfaces.DataSettable;
import xyz.ratapp.ilx.controllers.interfaces.ListSettable;
import xyz.ratapp.ilx.data.dao.Button;
import xyz.ratapp.ilx.data.dao.Order;
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

public class InfoController implements DataSettable<Object> {

    private DataController data;
    private InfoActivity activity;
    //request id
    private String id;
    private Order order;
    private Request request;

    public InfoController(InfoActivity activity) {
        this.activity = activity;
        id = activity.getIntent().getStringExtra("id");
        data = DataController.getInstance();
        setupData();
    }

    private void setupData() {
        data.bindInfoData(this, id);
    }


    @Override
    public void setData(Object data) {
        if(data instanceof Request) {
            this.request = (Request) data;
            setupReqInfoData();
        }
        else if(data instanceof Order) {
            this.order = (Order) data;
            setupDetailsData();
        }
    }

    private void setupDetailsData() {
        //setup buttons
        Order.Buttons btns = order.getBtns();
        final Button ok = btns.getOk();
        final Order.NegativeButton noup = btns.getNo();
        android.widget.Button perform =
                activity.findViewById(R.id.btnPerform);
        android.widget.Button issue =
                activity.findViewById(R.id.btnIssue);
        perform.setText(ok.getName());
        issue.setText(noup.getName());
        //setup onclicks
        perform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("MyTag", "OK");
                data.onPushButton(ok);
            }
        });
        issue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("MyTag", "NOUP");
                Dialog dialog = new Dialog(activity);
                dialog.setCanceledOnTouchOutside(true);
                dialog.setCancelable(true);
                //setup views
                LinearLayout ll = new LinearLayout(activity);
                ll.setOrientation(LinearLayout.VERTICAL);
                ll.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
                //setup data
                for (final Button b : noup.getOptions()) {
                    android.widget.Button btn =
                            new android.widget.Button(activity);
                    btn.setLayoutParams(new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
                    btn.setText(b.getName());
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            data.onPushButton(b);
                        }
                    });
                    ll.addView(btn);
                }
                dialog.setContentView(ll);

                dialog.show();
            }
        });


        //setup items
        List<Order.Item> details = order.getItems();
        RecyclerView rv = activity.findViewById(R.id.rvTasks);
        GridLayoutManager glm = new GridLayoutManager(activity, 1);
        rv.setLayoutManager(glm);
        rv.setAdapter(new DetailsAdapter(activity, details));
    }

    private void setupReqInfoData() {
        //map
        if(request.getImage() != null &&
                !request.getImage().isEmpty()) {
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
        if(request.getBtn() != null) {
            SwipeButton btn = activity.findViewById(R.id.swipeAccept);
            btn.setText(request.getBtn().getName());
        }

        if(request.getDetails() != null) {
            //rv comments
            RecyclerView rvComments = activity.findViewById(R.id.rvComments);
            GridLayoutManager glm = new GridLayoutManager(activity, 1);
            rvComments.setLayoutManager(glm);
            rvComments.setAdapter(new CommentsAdapter(activity, request.getDetails()));
        }

        if(request.getAddresses() != null) {
            //rv addresses
            RecyclerView rvAddresses = activity.findViewById(R.id.rvAddresses);
            GridLayoutManager glmm = new GridLayoutManager(activity, 1);
            rvAddresses.setLayoutManager(glmm);
            rvAddresses.setAdapter(new AddressesAdapter(activity, request.getAddresses()));
        }
    }

    public void callMap() {
        //TODO!!!
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?daddr=59.955761,30.313146"));
        activity.startActivity(intent);
    }

    public void acceptRequest() {
        data.onPushButton(request.getBtn());
    }
}
