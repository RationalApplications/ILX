package xyz.ratapp.ilx.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

public class StockFragment extends Fragment
        implements DataSettable {

    private RecyclerView stockList;
    private List<Request> requests;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupUI(view);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stock, container, false);
    }

    private void setupUI(View view) {
        stockList = view.findViewById(R.id.rv_stock);

        if(requests != null) {
            setData(requests);
        }
    }

    @Override
    public void setData(List<Request> requests) {
        this.requests = requests;

        if(stockList != null) {
            GridLayoutManager glm = new GridLayoutManager(getActivity(), 1);
            stockList.setLayoutManager(glm);
            stockList.setAdapter(new RequestsAdapter(getActivity(), false, requests));
        }
    }

}
