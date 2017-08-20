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
import xyz.ratapp.ilx.controllers.Screens;
import xyz.ratapp.ilx.controllers.main.MainController;
import xyz.ratapp.ilx.data.dao.Order;

/**
 * Created by timtim on 20/08/2017.
 */

public class OrdersAdapter extends
        RecyclerView.Adapter<OrdersAdapter.OrderViewHolder> {

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
    private List<Order> orders;
    private Screens screen;

    public OrdersAdapter(MainController controller, Screens screen,
                         List<Order> orders) {
        this.controller = controller;
        this.context = controller.getContext();
        this.screen = screen;
        this.orders = orders;
    }

    @Override
    public OrdersAdapter.OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).
                inflate(screenItemMap.get(screen), parent, false);
        setupDifficultHeight(v);

        return new OrdersAdapter.OrderViewHolder(v);
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
    public void onBindViewHolder(OrdersAdapter.OrderViewHolder holder, int position) {
        Order o = orders.get(position);
        holder.bind(o);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }


    class OrderViewHolder
            extends RecyclerView.ViewHolder {
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
        }

        void bind(final Order o) {

            title.setText(o.getH21());
            comment.setText(o.getH22());
            difficult.setBackgroundColor(o.getColor());

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
    }
}