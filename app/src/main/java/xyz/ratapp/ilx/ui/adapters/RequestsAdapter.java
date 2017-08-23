package xyz.ratapp.ilx.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.IntegerRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xyz.ratapp.ilx.R;
import xyz.ratapp.ilx.controllers.Screens;
import xyz.ratapp.ilx.controllers.main.MainController;
import xyz.ratapp.ilx.data.dao.Request;
import xyz.ratapp.ilx.ui.activities.DetailsActivity;
import xyz.ratapp.ilx.ui.activities.RequestInfoActivity;

/**
 * Created by timtim on 08/08/2017.
 */

public class RequestsAdapter extends
        RecyclerView.Adapter<RequestsAdapter.RequestsViewHolder> {

    public final static Map<Screens, Integer> screenItemMap;
    static {
        Map<Screens, Integer> tmp = new HashMap<>();
        tmp.put(Screens.RECENT, R.layout.item_recent_request);
        tmp.put(Screens.STOCK, R.layout.item_stock_request);
        tmp.put(Screens.HISTORY, R.layout.item_stock_request);

        screenItemMap = Collections.unmodifiableMap(tmp);
    }

    private MainController controller;
    private Context context;
    private List<Request> requests;
    private Screens screen;

    public RequestsAdapter(MainController controller, Screens screen,
                           List<Request> requests) {
        this.controller = controller;
        this.context = controller.getContext();
        this.screen = screen;
        this.requests = requests;
    }

    @Override
    public RequestsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).
                inflate(screenItemMap.get(screen), parent, false);
        setupDifficultHeight(v);

        return new RequestsViewHolder(v);
    }

    private void setupDifficultHeight(final View v) {
        v.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener(){

                    @Override
                    public void onGlobalLayout() {
                        int height = v.getHeight();
                        v.findViewById(R.id.vDifficult).
                                setMinimumHeight(height);
                        v.getViewTreeObserver().
                                removeGlobalOnLayoutListener(this);
                    }
                });
    }


    @Override
    public void onBindViewHolder(RequestsViewHolder holder, int position) {
        Request r = requests.get(position);
        holder.bind(r);
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }



    class RequestsViewHolder
            extends RecyclerView.ViewHolder {
        private TextView cost;
        private TextView title;
        private TextView task;
        private TextView comment;
        private TextView time;
        private View difficult;
        private View item;


        RequestsViewHolder(View itemView) {
            super(itemView);

            item = itemView;
            title = itemView.findViewById(R.id.tvTitle);
            comment = itemView.findViewById(R.id.tvComment);
            difficult = itemView.findViewById(R.id.vDifficult);

            if(screen.equals(Screens.RECENT)) {
                task = itemView.findViewById(R.id.tvTask);
                time = itemView.findViewById(R.id.tvTime);
            }
            else if(screen.equals(Screens.STOCK) ||
                    screen.equals(Screens.HISTORY)){
                cost = itemView.findViewById(R.id.tvCost);
            }
        }

        void bind(final Request r) {
            title.setText(r.getAddress());
            comment.setText(r.getComment());
            difficult.setBackgroundColor(r.getDifficult());

            if(screen.equals(Screens.RECENT)) {
                task.setText(r.getTask());
                time.setText(r.getTime());
            }
            else if(screen.equals(Screens.STOCK) ||
                    screen.equals(Screens.HISTORY)) {
                cost.setText(r.getCost());
            }

            if(controller != null) {
                item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        controller.next(screen, r);
                    }
                });
            }
        }
    }
}
