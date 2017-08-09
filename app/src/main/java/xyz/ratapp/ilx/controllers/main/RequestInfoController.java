package xyz.ratapp.ilx.controllers.main;

import android.widget.TextView;

import com.ebanx.swipebtn.OnStateChangeListener;
import com.ebanx.swipebtn.SwipeButton;

import xyz.ratapp.ilx.R;
import xyz.ratapp.ilx.view.activities.RequestInfoActivity;

/**
 * Created by Олег on 09.08.2017.
 */

public class RequestInfoController {

    RequestInfoActivity activity;

    public RequestInfoController(RequestInfoActivity activity){
        this.activity = activity;

        setUI();
        setData();
    }

    private void setData() {
        ((TextView)activity.findViewById(R.id.tv_cost)).setText(activity.getIntent().getStringExtra(RequestInfoActivity.STR_COST));
        ((TextView)activity.findViewById(R.id.tv_comission)).setText(activity.getIntent().getStringExtra(RequestInfoActivity.STR_COMMISSION));

        ((TextView) (activity.findViewById(R.id.include_hadder)).findViewById(R.id.tv_cost)).setText(
                activity.getIntent().getStringExtra(RequestInfoActivity.STR_COST)
        );

        ((TextView) (activity.findViewById(R.id.include_hadder)).findViewById(R.id.tv_comment)).setText(
                activity.getIntent().getStringExtra(RequestInfoActivity.STR_COMMENT)
        );

        ((TextView) (activity.findViewById(R.id.include_hadder)).findViewById(R.id.tv_title)).setText(
                activity.getIntent().getStringExtra(RequestInfoActivity.STR_TITLE)
        );
    }

    private void setUI() {
        ((SwipeButton)activity.findViewById(R.id.swipe_accept)).setOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public void onStateChange(boolean active) {
                //TODO: Accept order
            }
        });
    }
}
