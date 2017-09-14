package xyz.ratapp.ilx.ui.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

import xyz.ratapp.ilx.R;
import xyz.ratapp.ilx.controllers.interfaces.DataSettable;
import xyz.ratapp.ilx.data.dao.app.Buttons;
import xyz.ratapp.ilx.data.dao.app.NegativeButton;
import xyz.ratapp.ilx.data.dao.orders.Order;
import xyz.ratapp.ilx.data.dao.orders.Request;
import xyz.ratapp.ilx.data.dao.orders.info.Item;
import xyz.ratapp.ilx.ui.activities.DetailsActivity;
import xyz.ratapp.ilx.ui.adapters.DetailsAdapter;


public class DetailsFragment extends Fragment
        implements DataSettable<Object> {

    private DetailsActivity activity;
    private RecyclerView rvDetails;
    private Button btnPerform;
    private Button btnIssue;
    private Button btnSendMessage;
    private String btnSendMessageText;
    private String routeText;
    private Order order;
    private Request request;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(order != null) {
            setupOrderData();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_details, container, false);
        rvDetails = v.findViewById(R.id.rvTasks);
        btnPerform = v.findViewById(R.id.btnPerform);
        btnIssue = v.findViewById(R.id.btnIssue);
        btnSendMessage = v.findViewById(R.id.btnSendMessage);
        activity = ((DetailsActivity) getActivity());

        return v;
    }

    @Override
    public void setData(Object data) {
        if(data instanceof Order) {
            order = (Order) data;
            setupOrderData();
        }
        else if(data instanceof Request) {
            request = (Request) data;
            setupRequestData();
        }
    }

    public void setBtnSendMessageText(String btnSendMessageText) {
        this.btnSendMessageText = btnSendMessageText;

        if(btnSendMessage != null) {
            btnSendMessage.setText(btnSendMessageText);
        }
    }

    public void setRouteText(String routeText) {
        this.routeText = routeText;
    }

    private void setupRequestData() {
        if(request != null && rvDetails != null) {
            //setup buttons
            Buttons btns = order.getBtns();
            final xyz.ratapp.ilx.data.dao.app.Button ok = btns.getOk();
            final NegativeButton noup = btns.getNo();
            btnPerform.setText(ok.getName());
            btnIssue.setText(noup.getName());
            if(btnSendMessage != null) {
                btnSendMessage.setText(btnSendMessageText);
                //setup onclicks
                btnSendMessage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        activity.getController().sendMessage();
                    }
                });
            }
            btnPerform.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("MyTag", "OK");
                    activity.onPushButton(ok);
                    Toast.makeText(getContext(), "Успешно!", Toast.LENGTH_SHORT).show();
                }
            });
            btnIssue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("MyTag", "NOUP");
                    final Dialog dialog = new Dialog(activity);
                    dialog.setTitle("Выберите причину: ");
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.setCancelable(true);
                    //setup views
                    LinearLayout ll = new LinearLayout(activity);
                    ll.setOrientation(LinearLayout.VERTICAL);
                    ll.setLayoutParams(new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT));
                    //setup data
                    for (final xyz.ratapp.ilx.data.dao.app.Button b : noup.getOptions()) {
                        android.widget.Button btn =
                                new android.widget.Button(activity);
                        //===
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT);
                        float scale = activity.getResources().
                                getDisplayMetrics().density;
                        int margin = (int) (10 * scale);
                        params.leftMargin = margin;
                        params.rightMargin = margin;
                        params.topMargin = margin;
                        params.bottomMargin = margin;
                        //===
                        btn.setText(b.getName());
                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                activity.onPushButton(b);
                                dialog.cancel();
                                Toast.makeText(getContext(), "Успешно!", Toast.LENGTH_SHORT).show();
                            }
                        });
                        ll.addView(btn, params);
                    }
                    dialog.setContentView(ll);

                    dialog.show();
                }
            });


            //setup items
            List<Item> details = order.getItems();
            GridLayoutManager glm = new GridLayoutManager(getContext(), 1);
            rvDetails.setLayoutManager(glm);
            rvDetails.setAdapter(new DetailsAdapter(getActivity(), details, routeText));
        }
    }

    private void setupOrderData() {
        if(order != null && rvDetails != null) {
            //setup buttons
            Buttons btns = order.getBtns();
            final xyz.ratapp.ilx.data.dao.app.Button ok = btns.getOk();
            final NegativeButton noup = btns.getNo();
            btnPerform.setText(ok.getName());
            btnIssue.setText(noup.getName());
            if(btnSendMessage != null) {
                btnSendMessage.setText(btnSendMessageText);
                //setup onclicks
                btnSendMessage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        activity.getController().sendMessage();
                    }
                });
            }
            btnPerform.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("MyTag", "OK");
                    activity.onPushButton(ok);
                    Toast.makeText(getContext(), "Успешно!", Toast.LENGTH_SHORT).show();
                }
            });
            btnIssue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("MyTag", "NOUP");
                    final Dialog dialog = new Dialog(activity);
                    dialog.setTitle("Выберите причину: ");
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.setCancelable(true);
                    //setup views
                    LinearLayout ll = new LinearLayout(activity);
                    ll.setOrientation(LinearLayout.VERTICAL);
                    ll.setLayoutParams(new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT));
                    //setup data
                    for (final xyz.ratapp.ilx.data.dao.app.Button b : noup.getOptions()) {
                        android.widget.Button btn =
                                new android.widget.Button(activity);
                        //===
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT);
                        float scale = activity.getResources().
                                getDisplayMetrics().density;
                        int margin = (int) (10 * scale);
                        params.leftMargin = margin;
                        params.rightMargin = margin;
                        params.topMargin = margin;
                        params.bottomMargin = margin;
                        //===
                        btn.setText(b.getName());
                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                activity.onPushButton(b);
                                dialog.cancel();
                                Toast.makeText(getContext(), "Успешно!", Toast.LENGTH_SHORT).show();
                            }
                        });
                        ll.addView(btn, params);
                    }
                    dialog.setContentView(ll);

                    dialog.show();
                }
            });


            //setup items
            List<Item> details = order.getItems();
            GridLayoutManager glm = new GridLayoutManager(getContext(), 1);
            rvDetails.setLayoutManager(glm);
            rvDetails.setAdapter(new DetailsAdapter(getActivity(), details, routeText));
        }
    }
}
