package xyz.ratapp.ilx.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import xyz.ratapp.ilx.controllers.routing.Screens;
import xyz.ratapp.ilx.controllers.interfaces.ListSettable;
import xyz.ratapp.ilx.controllers.main.MainController;
import xyz.ratapp.ilx.data.dao.orders.BaseOrder;
import xyz.ratapp.ilx.data.dao.orders.Order;
import xyz.ratapp.ilx.data.dao.orders.Request;
import xyz.ratapp.ilx.ui.adapters.BaseOrdersAdapter;

/**
 * Created by timtim on 12/08/2017.
 *
 * Parent fragment for all fragments that
 * use Request as dao for output it into
 * RecyclerView
 *
 * -Screen
 * --LinearLayout
 * ---SwipeRefreshLayout
 * ----RecyclerView
 * ---/SwipeRefreshLayout
 * --/LinearLayout
 * -/Screen
 */

public abstract class RequestFragment extends Fragment
        implements ListSettable<BaseOrder> {

    protected RecyclerView requestList;
    protected SwipeRefreshLayout refreshLayout;
    protected MainController controller;
    protected List<Request> data;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Context context = getContext();

        //setup main layout
        LinearLayout mainLayout = new LinearLayout(context);
        LinearLayout.LayoutParams mainParams =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
        mainLayout.setLayoutParams(mainParams);
        mainLayout.setOrientation(LinearLayout.VERTICAL);

        //setup refresh layout
        refreshLayout = new SwipeRefreshLayout(context);
        SwipeRefreshLayout.LayoutParams srlParams = new SwipeRefreshLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mainLayout.addView(refreshLayout, srlParams);
        preSetupRefreshLayout();

        //setup rv
        requestList = new RecyclerView(context);
        RecyclerView.LayoutParams rvParams = new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        addViewToRefreshLayout(requestList, rvParams);

        return mainLayout;
    }

    @Override
    public void onViewCreated(View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupUI();
    }

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

        if(data != null) {
            setData(new ArrayList<BaseOrder>(data));
        }
    }

    /**
     * If you need put smtg into RefreshLayout
     * override this method
     */
    protected void preSetupRefreshLayout() {

    }

    /**
     * If you need to add view into RefreshLayout
     * with some other logic - override this method
     *
     * @param view - view that will added to refreshLayout
     * @param params - layout params for view
     */
    protected void addViewToRefreshLayout(View view,
                                          ViewGroup.LayoutParams params) {
        refreshLayout.addView(view, params);
    }

    /**
     * Method that called when screen refresh
     *
     * @return true if refresh was success
     * false in other case
     */
    protected boolean refresh() {
        controller.refresh(getScreen());

        return true;
    }

    /**
     * Method that binds fragment
     * to controller
     */
    public void bindController(MainController controller) {
        this.controller = controller;
    }

    /**
     * In this method you need to
     * setup data as fragment-data.
     * Also you need to update RV
     *
     * @param data - List of dao for RV
     */
    @Override
    public void setData(List<BaseOrder> data) {
        this.data = new ArrayList<>();
        for (BaseOrder bo : data) {
            this.data.add((Request) bo);
        }

        if(requestList != null) {
            GridLayoutManager glm = new GridLayoutManager(getActivity(), 1);
            requestList.setLayoutManager(glm);
            requestList.setAdapter(new BaseOrdersAdapter(controller,
                    getScreen(), new ArrayList<BaseOrder>(this.data)));
        }
    }

    /**
     * @return title of fragment
     */
    public abstract String getTitle();

    /**
     * @return screen of fragment
     */
    public abstract Screens getScreen();
}
