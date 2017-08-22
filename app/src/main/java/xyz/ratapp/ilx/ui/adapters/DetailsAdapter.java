package xyz.ratapp.ilx.ui.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
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
    private String routeText;

    public DetailsAdapter(Context context,
                          List<Order.Item> details,
                          String routeText) {
        this.context = context;
        this.details = details;
        this.routeText = routeText;
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

            if(item.getType().equals("text")) {
                title.setText(text);
                title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                title.setTypeface(null, Typeface.ITALIC);
            }
            else if(item.getType().equals("time")) {
                title.setText(text);
                title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            }
            else if(item.getType().equals("fio")) {
                title.setText(text);
                title.setTextColor(context.getResources().getColor(R.color.text_color));
                title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            }
            else if(item.getType().equals("address")) {
                Order.GeoItem geo = ((Order.GeoItem) item);
                title.setText(text);
                title.setTextColor(context.getResources().getColor(R.color.text_color));
                title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                btnAction.setVisibility(View.VISIBLE);
                btnAction.setText(routeText);

                if(pather == null) {
                    pather = new MapClickListener(context,
                            geo.getLat(), geo.getLng());
                }

                btnAction.setOnClickListener(pather);
            }
            else if(item.getType().equals("phone")) {
                title.setText(text);
                title.setTextColor(context.getResources().getColor(R.color.text_color));
                title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
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
