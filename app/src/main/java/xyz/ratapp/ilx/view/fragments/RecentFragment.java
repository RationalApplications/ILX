package xyz.ratapp.ilx.view.fragments;

import android.app.Dialog;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import xyz.ratapp.ilx.R;
import xyz.ratapp.ilx.data.dao.Request;
import xyz.ratapp.ilx.view.adapters.RequestsAdapter;
import xyz.ratapp.ilx.view.interfaces.ListSettable;

/**
 * Created by timtim on 07/08/2017.
 */

public class RecentFragment extends Fragment
        implements ListSettable<Request> {

    private RecyclerView recentList;
    private List<Request> requests;
    private Button showMap;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupUI(view);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recent, container, false);
    }

    private void setupUI(View view) {
        recentList = view.findViewById(R.id.rv_recent);
        showMap = view.findViewById(R.id.btn_on_the_map);
        showMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_map);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();

                MapView mMapView = dialog.findViewById(R.id.mv_dialog);
                MapsInitializer.initialize(getActivity());
                mMapView.onCreate(dialog.onSaveInstanceState());
                mMapView.onResume();
                mMapView.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        LatLng spb = new LatLng(59.955761, 30.313146);
                        googleMap.addMarker(new MarkerOptions().position(spb)
                                .title("Marker in Spb"));
                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(spb));
                    }
                });
            }
        });

        //refresh
        final SwipeRefreshLayout srl = view.findViewById(R.id.srl_recent);
        SwipeRefreshLayout.OnRefreshListener refresh =
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Toast.makeText(getActivity(), "Вот так будет работать обновление",
                                Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                               srl.setRefreshing(false);
                            }
                        }, 2000);
                    }
                };
        srl.setOnRefreshListener(refresh);

        if(requests != null) {
            setData(requests);
        }
    }

    @Override
    public void setData(List<Request> requests) {
        this.requests = requests;

        if(recentList != null) {
            GridLayoutManager glm = new GridLayoutManager(getActivity(), 1);
            recentList.setLayoutManager(glm);
            recentList.setAdapter(new RequestsAdapter(getActivity(), true, requests));
        }
    }

}
