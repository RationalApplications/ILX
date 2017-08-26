package xyz.ratapp.ilx.ui.fragments;

import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ebanx.swipebtn.OnStateChangeListener;
import com.ebanx.swipebtn.SwipeButton;

import org.w3c.dom.Text;

import xyz.ratapp.ilx.R;
import xyz.ratapp.ilx.controllers.info.InfoController;
import xyz.ratapp.ilx.controllers.interfaces.DataSettable;
import xyz.ratapp.ilx.data.dao.Request;
import xyz.ratapp.ilx.ui.activities.RequestInfoActivity;
import xyz.ratapp.ilx.ui.adapters.AddressesAdapter;
import xyz.ratapp.ilx.ui.adapters.CommentsAdapter;

/**
 * Created by timtim on 23/08/2017.
 */

public class RequestInfoFragment extends Fragment
        implements DataSettable<Request> {

    private TextView tvCost;
    private TextView tvComment;
    private TextView tvTitle;
    private View vDifficult;
    private ImageView ivMap;
    private SwipeButton swipeAccept;
    private RecyclerView rvAddresses;
    private RecyclerView rvComments;
    private InfoController controller;
    private String swipeButtonText;
    private Request request;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_request_info, container, false);
        tvCost = v.findViewById(R.id.tvCost);
        tvComment = v.findViewById(R.id.tvComment);
        tvTitle = v.findViewById(R.id.tvTitle);
        vDifficult = v.findViewById(R.id.vDifficult);
        ivMap = v.findViewById(R.id.ivMap);
        swipeAccept = v.findViewById(R.id.swipeAccept);
        rvAddresses = v.findViewById(R.id.rvAddresses);
        rvComments = v.findViewById(R.id.rvComments);
        setupUI();
        setupData();

        return v;
    }

    private void setupUI() {
        bindData();


        swipeAccept.setOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public void onStateChange(boolean active) {
                swipeAccept.setEnabled(false);
                TextView tv = new TextView(getContext());
                tv.setText(R.string.accepted);
                tv.setGravity(Gravity.CENTER);
                tv.setTextColor(getContext().getResources().getColor(R.color.text_color));
                swipeAccept.addView(tv, new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
                controller.acceptRequest();
            }
        });
    }

    public void bindController(InfoController controller) {
        this.controller = controller;
    }

    private void bindData() {
        Intent data = getActivity().getIntent();
        String title = data.getStringExtra(RequestInfoActivity.Companion.getSTR_TITLE());
        String cost = data.getStringExtra(RequestInfoActivity.Companion.getSTR_COST());
        String comment = data.getStringExtra(RequestInfoActivity.Companion.getSTR_COMMENT());
        int difficult = data.getIntExtra(RequestInfoActivity.Companion.getSTR_DIFFICULT(), -1);
        String mapUrl = data.getStringExtra(RequestInfoActivity.Companion.getSTR_MAP_URL());

        tvCost.setText(cost);
        tvComment.setText(comment);
        tvTitle.setText(title);
        if(difficult != -1) {
            vDifficult.setBackgroundColor(difficult);
        }

        if (mapUrl.equals(""))
            ivMap.setVisibility(View.GONE);
        else {
            ivMap.setVisibility(View.VISIBLE);
            Glide.with(getContext()).load(mapUrl).
                    asBitmap().into(ivMap);
        }
    }

    public void setSwipeButtonText(String text) {
        //button
        swipeButtonText = text;
    }

    @Override
    public void setData(Request data) {
        request = data;
        setupData();
    }

    private void setupData() {
        if(rvAddresses != null && rvComments != null &&
                swipeAccept != null) {
            if (swipeButtonText != null) {
                swipeAccept.setText(swipeButtonText);
            }
            else {
                swipeAccept.setVisibility(View.GONE);
            }

            if (request.getImage() != null &&
                    !request.getImage().isEmpty()) {
                Glide.with(getContext()).
                        load(request.getImage()).asBitmap().into(ivMap);
            }

            if (request.getDetails() != null) {
                //rv comments
                GridLayoutManager glm = new GridLayoutManager(getContext(), 1);
                rvComments.setLayoutManager(glm);
                rvComments.setAdapter(new CommentsAdapter(getActivity(), request.getDetails()));
            }

            if (request.getAddresses() != null) {
                //rv addresses
                GridLayoutManager glmm = new GridLayoutManager(getContext(), 1);
                rvAddresses.setLayoutManager(glmm);
                rvAddresses.setAdapter(new AddressesAdapter(getActivity(), request.getAddresses()));
            }
        }
    }

}
