package xyz.ratapp.ilx.view.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import xyz.ratapp.ilx.R;

/**
 * Created by timtim on 07/08/2017.
 */

public class StockFragment extends Fragment {

    private RecyclerView stockList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stock, container, false);
        setupUI(view);

        return view;
    }

    private void setupUI(View view) {
        stockList = view.findViewById(R.id.rv_stock);
    }

}
