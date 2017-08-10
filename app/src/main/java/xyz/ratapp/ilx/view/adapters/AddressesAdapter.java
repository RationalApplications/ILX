package xyz.ratapp.ilx.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import xyz.ratapp.ilx.R;
import xyz.ratapp.ilx.data.dao.Request;
import xyz.ratapp.ilx.view.activities.DetailsActivity;
import xyz.ratapp.ilx.view.activities.RequestInfoActivity;

/**
 * Created by timtim on 10/08/2017.
 */

public class AddressesAdapter extends
        RecyclerView.Adapter<AddressesAdapter.AddressessViewHolder> {

    private Context context;
    private List<Request> requests;

    public AddressesAdapter(Context context, List<Request> requests) {
        this.context = context;
        this.requests = requests;
    }

    @Override
    public AddressesAdapter.AddressessViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(context).inflate(
                R.layout.item_address,
                parent, false);

        return new AddressesAdapter.AddressessViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AddressesAdapter.AddressessViewHolder holder, int position) {
        final Request r = requests.get(position);

        holder.title.setText(r.getAddress());
        holder.comment.setText(r.getComment());
        holder.task.setText(r.getTask());
        holder.time.setText(r.getTime());
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }

    static class AddressessViewHolder
            extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView task;
        private TextView comment;
        private TextView time;


        AddressessViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.tv_address_title);
            comment = itemView.findViewById(R.id.tv_address_comment);
            task = itemView.findViewById(R.id.tv_address_task);
            time = itemView.findViewById(R.id.tv_address_time);
        }
    }
}
