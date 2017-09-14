package xyz.ratapp.ilx.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xyz.ratapp.ilx.R;
import xyz.ratapp.ilx.controllers.routing.Screens;
import xyz.ratapp.ilx.controllers.main.MainController;
import xyz.ratapp.ilx.data.dao.orders.BaseOrder;
import xyz.ratapp.ilx.data.dao.orders.Order;
import xyz.ratapp.ilx.data.dao.orders.Request;

/**
 * Created by timtim on 20/08/2017.
 */

public class BaseOrdersAdapter extends
        RecyclerView.Adapter<BaseOrdersAdapter.OrderViewHolder> {

    public final static Map<Screens, Integer> screenItemMap;

    static {
        Map<Screens, Integer> tmp = new HashMap<>();
        tmp.put(Screens.RECENT, R.layout.item_recent_request);
        tmp.put(Screens.STOCK, R.layout.item_stock_request);
        tmp.put(Screens.HISTORY, R.layout.item_recent_request);

        screenItemMap = Collections.unmodifiableMap(tmp);
    }

    private MainController controller;
    private Context context;
    private List<BaseOrder> orders;
    private Screens screen;

    public BaseOrdersAdapter(MainController controller, Screens screen,
                             List<BaseOrder> orders) {
        this.controller = controller;
        this.context = controller.getContext();
        this.screen = screen;
        this.orders = orders;
    }

    @Override
    public BaseOrdersAdapter.OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).
                inflate(screenItemMap.get(screen), parent, false);
        setupDifficultHeight(v);

        return new BaseOrdersAdapter.OrderViewHolder(v);
    }

    private void setupDifficultHeight(final View v) {
        v.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {

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
    public void onBindViewHolder(BaseOrdersAdapter.OrderViewHolder holder, int position) {
        BaseOrder order = orders.get(position);

        if(order instanceof Order) {
            Order o = ((Order) order);
            holder.bind(o);
        }
        else if(order instanceof Request) {
            Request r = ((Request) order);
            holder.bind(r);
        }
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }


    class OrderViewHolder
            extends RecyclerView.ViewHolder {
        private TextView cost;
        private TextView title;
        private TextView task;
        private TextView comment;
        private TextView time;
        private View difficult;
        private View item;


        OrderViewHolder(View itemView) {
            super(itemView);

            item = itemView;
            title = itemView.findViewById(R.id.tvTitle);
            comment = itemView.findViewById(R.id.tvComment);
            difficult = itemView.findViewById(R.id.vDifficult);
            task = itemView.findViewById(R.id.tvTask);
            time = itemView.findViewById(R.id.tvTime);

            if(screen.equals(Screens.RECENT)) {
                task = itemView.findViewById(R.id.tvTask);
                time = itemView.findViewById(R.id.tvTime);
            }
            else if(screen.equals(Screens.STOCK) ||
                    screen.equals(Screens.HISTORY)){
                cost = itemView.findViewById(R.id.tvCost);
            }
        }

        void bind(final Order o) {

            title.setText(o.getH21());
            comment.setText(o.getH22());
            difficult.setBackgroundColor(o.getDifficult());

            task.setText(o.getH23());
            time.setText(o.getH24());

            if (controller != null) {
                item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        controller.next(screen, o);
                    }
                });
            }
        }

        void bind(final Request r) {
            title.setText(r.getH2());
            comment.setText(r.getH3());
            difficult.setBackgroundColor(r.getDifficult());
            cost.setText(r.getH1());

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