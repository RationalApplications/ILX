package xyz.ratapp.ilx.view.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import xyz.ratapp.ilx.R;

/**
 * Created by timtim on 07/08/2017.
 */

public class RecentFragment extends Fragment {

    private RecyclerView recentList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recent, container, false);
        setupUI(view);

        return view;
    }

    private void setupUI(View view) {
        recentList = view.findViewById(R.id.rv_recent);
    }

}
