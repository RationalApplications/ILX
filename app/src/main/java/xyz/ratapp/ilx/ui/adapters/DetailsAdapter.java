package xyz.ratapp.ilx.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import xyz.ratapp.ilx.R;
import xyz.ratapp.ilx.data.dao.Details;
import xyz.ratapp.ilx.ui.adapters.listeners.CallerClickListener;
import xyz.ratapp.ilx.ui.adapters.listeners.MapClickListener;

/**
 * Created by timtim on 15/08/2017.
 */

public class DetailsAdapter extends
        RecyclerView.Adapter<DetailsAdapter.DetailsViewHolder> {

    private Context context;
    private List<Details> details;

    public DetailsAdapter(Context context,
                            List<Details> details) {
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
        Details d = details.get(position);
        holder.bind(d);
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

        void bind(Details d) {
            Details.Type t = d.getType();

            final String text = d.getText();
            title.setText(text);

            if(t.equals(Details.Type.TEXT)) {
                //typefont???
            }
            else if(t.equals(Details.Type.ADDRESS)) {
                //typefont???
                btnAction.setVisibility(View.VISIBLE);
                btnAction.setText(R.string.path);

                if(pather == null) {
                    pather = new MapClickListener(context);
                }

                btnAction.setOnClickListener(pather);
            }
            else if(t.equals(Details.Type.PHONE)) {
                //typefont???
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
            else if(t.equals(Details.Type.TIME)) {
                //typefont???
            }
        }
    }
}
