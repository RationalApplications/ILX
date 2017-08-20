package xyz.ratapp.ilx.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import xyz.ratapp.ilx.R;
import xyz.ratapp.ilx.data.dao.Address;
import xyz.ratapp.ilx.data.dao.Order;

/**
 * Created by timtim on 21/08/2017.
 */

public class MessagesAdapter  extends
        RecyclerView.Adapter<MessagesAdapter.MessageViewHolder> {

    private Context context;
    private List<Order.Message> messages;

    public MessagesAdapter(Context context,
                           List<Order.Message> messages) {
        this.context = context;
        this.messages = messages;
    }

    @Override
    public MessagesAdapter.MessageViewHolder
    onCreateViewHolder(ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(context).
                inflate(R.layout.item_address, parent, false);
        return new MessagesAdapter.MessageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MessagesAdapter.MessageViewHolder holder,
                                 int position) {
        Order.Message m = messages.get(position);
        holder.bind(m);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }


    static class MessageViewHolder
            extends RecyclerView.ViewHolder {

        private TextView text;

        MessageViewHolder(View itemView) {
            super(itemView);

            text = itemView.findViewById(R.id.tvAddressTitle);
        }

        void bind(Order.Message m) {
            text.setText(m.getMessage());
        }
    }
}