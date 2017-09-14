package xyz.ratapp.ilx.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import xyz.ratapp.ilx.R;
import xyz.ratapp.ilx.data.dao.orders.info.Address;

/**
 * Created by timtim on 10/08/2017.
 */

public class AddressesAdapter extends
        RecyclerView.Adapter<AddressesAdapter.AddressessViewHolder> {

    private Context context;
    private List<Address> addresses;

    public AddressesAdapter(Context context,
                            List<Address> addresses) {
        this.context = context;
        this.addresses = addresses;
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
        Address a = addresses.get(position);
        holder.bind(a, position + 1);
    }

    @Override
    public int getItemCount() {
        return addresses.size();
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

        void bind(Address a, int pos) {
            title.setText(pos + ". " + a.getH10());
            time.setText(a.getH11());
            task.setText(a.getH12());

            if(!a.getH13().isEmpty()) {
                comment.setText(a.getH13());
            }
            else {
                comment.setVisibility(View.GONE);
            }
        }
    }
}
