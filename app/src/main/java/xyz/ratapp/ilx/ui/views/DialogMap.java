package xyz.ratapp.ilx.ui.views;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.view.Display;
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
 *
 * Dialog that contain a map into view holder
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

    private void setupDialog() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(layout);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
    }

    private void setupLayout() {
        layout = new RelativeLayout(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layout.setLayoutParams(params);
    }

    private void setupMap() {
        Display display = ((Activity) context).getWindowManager()
                .getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        map = new MapView(context);
        ViewGroup.LayoutParams mapParams = new ViewGroup.LayoutParams(
                ((int) (width * 0.75f)),
                ((int) (height * 0.75f)));
        layout.addView(map, mapParams);
    }

    private void setupMapData(GoogleMap googleMap) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        LatLngBounds bounds;

        for(LatLng ll : data) {
            builder.include(ll);
            googleMap.addMarker(new MarkerOptions().position(ll)
                    .title("Marker in Spb"));
        }

        bounds = builder.build();
        int padding = 100;
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
