package xyz.ratapp.ilx.ui.views;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import xyz.ratapp.ilx.controllers.interfaces.ListSettable;

/**
 * Created by timtim on 13/08/2017.
 */

public class DialogMap extends Dialog
        implements ListSettable<LatLng> {

    private Context context;
    private RelativeLayout layout;
    private MapView map;
    private List<LatLng> data;

    public DialogMap(@NonNull Context context) {
        super(context);
        this.context = context;

        setupLayout();
        setupMap();
        setupDialog();
    }

    private void setupLayout() {
        layout = new RelativeLayout(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layout.setLayoutParams(params);
    }

    private void setupMap() {
        map = new MapView(context);
        ViewGroup.LayoutParams mapParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        layout.addView(map, mapParams);
    }

    private void setupDialog() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(layout);
        setCanceledOnTouchOutside(true);
    }

    private void setupMapData(GoogleMap googleMap) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        LatLngBounds bounds;

        for(LatLng ll : data) {
            googleMap.addMarker(new MarkerOptions().position(ll)
                    .title("Marker in Spb"));
        }

        bounds = builder.build();
        int padding = 40;
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        googleMap.moveCamera(cu);
    }

    @Override
    public void show() {
        super.show();
        MapsInitializer.initialize(context);
        map.onCreate(onSaveInstanceState());
        map.onResume();
        map.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                setupMapData(googleMap);
            }
        });
    }

    @Override
    public void setData(List<LatLng> data) {
        this.data = data;
    }

    public MapView getMap() {
        return map;
    }
}