package xyz.ratapp.ilx.ui.fragments;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import xyz.ratapp.ilx.R;
import xyz.ratapp.ilx.controllers.Screens;
import xyz.ratapp.ilx.controllers.main.MainController;
import xyz.ratapp.ilx.data.dao.Order;
import xyz.ratapp.ilx.data.dao.Request;
import xyz.ratapp.ilx.ui.adapters.OrdersAdapter;
import xyz.ratapp.ilx.ui.adapters.RequestsAdapter;
import xyz.ratapp.ilx.ui.views.DialogMap;

/**
 * Created by timtim on 07/08/2017.
 */

public class RecentFragment extends RequestFragment {

    private RelativeLayout container;
    private List<Order> orders;
    private String title;

    @Override
    public void bindController(MainController controller) {
        super.bindController(controller);
        title = controller.getNames().getOrderList();
    }


    @Override
    protected void setupUI() {
        //refresh
        SwipeRefreshLayout.OnRefreshListener refresh =
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        refreshLayout.setRefreshing(!refresh());
                    }
                };
        refreshLayout.setOnRefreshListener(refresh);

        Context context = getContext();

        //create button
        Button showMap = new Button(context);

        //setup button:
        //sizes
        final float SCALE = getContext().getResources().
                getDisplayMetrics().density;
        final float ON_THE_MAP_WIDTH = getResources().getDimension(R.dimen.button_on_the_map_width);
        RelativeLayout.LayoutParams params =
                new RelativeLayout.LayoutParams(
                        (int) (ON_THE_MAP_WIDTH * SCALE + 0.5f),
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        //margins
        final float LEFT_MARGIN = getResources().getDimension(R.dimen.default_screen_margin);
        final float BOTTOM_MARGIN = getResources().getDimension(R.dimen.default_large_margin);
        params.leftMargin = (int) (LEFT_MARGIN * SCALE + 0.5f);
        params.bottomMargin = (int) (BOTTOM_MARGIN * SCALE + 0.5f);
        //align
        params.addRule(RelativeLayout.ALIGN_LEFT, requestList.getId());
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        //button values
        showMap.setBackgroundResource(R.color.accent_color);
        showMap.setText(R.string.on_the_map);

        //setup click listener
        showMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showOnMap();
            }
        });
        addViewToRefreshLayout(showMap, params);

        if(orders != null) {
            setData(orders);
        }
    }

    private void showOnMap() {
        List<LatLng> locations = new ArrayList<>();
        for (Order o : orders) {
            locations.add(o.getLocation());
        }

        DialogMap dialog = new DialogMap(getContext());
        dialog.setData(locations);
        dialog.show();
    }

    @Override
    public void setData(List data) {
        this.orders = (List<Order>) data;

        if(requestList != null) {
            GridLayoutManager glm = new GridLayoutManager(getActivity(), 1);
            requestList.setLayoutManager(glm);
            requestList.setAdapter(new OrdersAdapter(controller, getScreen(), orders));
        }
    }

    @Override
    protected void preSetupRefreshLayout() {
        Context context = getContext();

        container = new RelativeLayout(context);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        refreshLayout.addView(container, params);
    }

    @Override
    protected void addViewToRefreshLayout(View view,
                                          ViewGroup.LayoutParams params) {
        container.addView(view, params);
    }

    @Override
    public Screens getScreen() {
        return Screens.RECENT;
    }

    @Override
    public String getTitle() {
        return title;
    }
}