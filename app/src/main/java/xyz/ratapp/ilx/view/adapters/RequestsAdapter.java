package xyz.ratapp.ilx.view.adapters;

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
 * Created by timtim on 08/08/2017.
 */

public class RequestsAdapter extends
        RecyclerView.Adapter<RequestsAdapter.RequestsViewHolder> {

    private Context context;
    private List<Request> requests;
    private boolean recent;

    public RequestsAdapter(Context context, boolean recent,
                           List<Request> requests) {
        this.context = context;
        this.recent = recent;
        this.requests = requests;
    }

    @Override
    public RequestsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(
                recent ? R.layout.item_recent_request : R.layout.item_stock_request,
                parent, false);

        return new RequestsViewHolder(v, recent);
    }

    @Override
    public void onBindViewHolder(RequestsViewHolder holder, int position) {
        Request r = requests.get(position);

        holder.title.setText(r.getAddress());
        holder.task.setText(r.getTask());
        holder.difficult.setBackgroundColor(r.getDifficult());

        if(recent) {
            holder.comment.setText(r.getComment());
            holder.time.setText(r.getTime());
        }
        else {
            holder.cost.setText(r.getCost());
        }
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }

    static class RequestsViewHolder
            extends RecyclerView.ViewHolder {

        private TextView cost;
        private TextView title;
        private TextView task;
        private TextView comment;
        private TextView time;
        private View difficult;


        public RequestsViewHolder(View itemView, boolean isRecent) {
            super(itemView);

            title = itemView.findViewById(R.id.tv_title);
            task = itemView.findViewById(R.id.tv_task);
            difficult = itemView.findViewById(R.id.v_difficult);

            if(isRecent) {
                comment = itemView.findViewById(R.id.tv_comment);
                time = itemView.findViewById(R.id.tv_time);
            }
            else {
                cost = itemView.findViewById(R.id.tv_cost);
            }
        }
    }
}
