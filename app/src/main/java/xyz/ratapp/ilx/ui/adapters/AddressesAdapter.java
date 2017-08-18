package xyz.ratapp.ilx.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import xyz.ratapp.ilx.R;
import xyz.ratapp.ilx.data.dao.Request;

/**
 * Created by timtim on 10/08/2017.
 */

public class AddressesAdapter extends
        RecyclerView.Adapter<AddressesAdapter.AddressessViewHolder> {

    private Context context;
    private List<Request> requests;

    public AddressesAdapter(Context context,
                            List<Request> requests) {
        this.context = context;
        this.requests = requests;
    }

    @Override
    public AddressesAdapter.AddressessViewHolder
    onCreateViewHolder(ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(context).
                inflate(R.layout.item_address, parent, false);
        return new AddressesAdapter.AddressessViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AddressesAdapter.AddressessViewHolder holder,
                                 int position) {
        Request r = requests.get(position);
        holder.bind(r);
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

            title = itemView.findViewById(R.id.tvAddressTitle);
            comment = itemView.findViewById(R.id.tvAddressComment);
            task = itemView.findViewById(R.id.tvAddressTask);
            time = itemView.findViewById(R.id.tvAddressTime);
        }

        void bind(Request r) {
            title.setText(r.getAddress());
            comment.setText(r.getComment());
            time.setText(r.getTime());

            if(!r.getTask().isEmpty()) {
                task.setText(r.getTask());
            }
            else {
                task.setVisibility(View.GONE);
            }
        }
    }
}
