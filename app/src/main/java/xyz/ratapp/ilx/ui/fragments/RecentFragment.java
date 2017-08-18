package xyz.ratapp.ilx.ui.fragments;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.model.LatLng;

import java.util.Arrays;

import xyz.ratapp.ilx.R;
import xyz.ratapp.ilx.controllers.Screens;
import xyz.ratapp.ilx.ui.views.DialogMap;

/**
 * Created by timtim on 07/08/2017.
 */

public class RecentFragment extends RequestFragment {

    private RelativeLayout container;

    @Override
    protected void setupUI() {
        super.setupUI();

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
    }

    private void showOnMap() {
        DialogMap dialog = new DialogMap(getContext());
        dialog.setData(Arrays.asList(new LatLng(59.955761, 30.313146)));
        dialog.show();
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
        return controller.getContext().getString(R.string.recent);
    }
}
