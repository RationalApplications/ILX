package xyz.ratapp.ilx.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import xyz.ratapp.ilx.R;
import xyz.ratapp.ilx.data.dao.Order;
import xyz.ratapp.ilx.ui.adapters.listeners.CallerClickListener;
import xyz.ratapp.ilx.ui.adapters.listeners.MapClickListener;

/**
 * Created by timtim on 15/08/2017.
 */

public class DetailsAdapter extends
        RecyclerView.Adapter<DetailsAdapter.DetailsViewHolder> {

    private Context context;
    private List<Order.Item> details;

    public DetailsAdapter(Context context,
                            List<Order.Item> details) {
        this.context = context;
        this.details = details;
    }

    @Override
    public DetailsAdapter.DetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(context).
                inflate(R.layout.item_details, parent, false);
        return new DetailsAdapter.DetailsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(DetailsAdapter.DetailsViewHolder holder, int position) {
        Order.Item item = details.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return details.size();
    }


    class DetailsViewHolder
            extends RecyclerView.ViewHolder {

        private TextView title;
        private Button btnAction;
        private ImageView ivAction;
        private CallerClickListener caller;
        private MapClickListener pather;


        DetailsViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.tvDetailsTitle);
            btnAction = itemView.findViewById(R.id.btnDetailsAction);
            ivAction = itemView.findViewById(R.id.ivDetailsAction);
        }

        void bind(Order.Item item) {

            String text = item.getText();

            if(item.getType().equals("text") ||
                    item.getType().equals("time") ||
                    item.getType().equals("fio")) {
                title.setText(text);
            }
            else if(item.getType().equals("address")) {
                Order.GeoItem geo = ((Order.GeoItem) item);
                title.setText(text);
                btnAction.setVisibility(View.VISIBLE);
                btnAction.setText(R.string.path);

                if(pather == null) {
                    pather = new MapClickListener(context,
                            geo.getLat(), geo.getLng());
                }

                btnAction.setOnClickListener(pather);
            }
            else if(item.getType().equals("phone")) {
                title.setText(text);
                ivAction.setVisibility(View.VISIBLE);
                ivAction.setImageResource(R.drawable.phone);

                if(caller == null) {
                    caller = new CallerClickListener(context, text);
                }
                else {
                    caller.setText(text);
                }
                ivAction.setOnClickListener(caller);
            }
        }
    }
}
