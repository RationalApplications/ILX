package xyz.ratapp.ilx.view.fragments;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import xyz.ratapp.ilx.R;
import xyz.ratapp.ilx.data.dao.Request;
import xyz.ratapp.ilx.view.adapters.RequestsAdapter;
import xyz.ratapp.ilx.view.interfaces.DataSettable;

/**
 * Created by timtim on 07/08/2017.
 */

//TODO: сменить task в стоке на коммент
public class RecentFragment extends Fragment
        implements DataSettable {

    private RecyclerView recentList;
    private List<Request> requests;

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
