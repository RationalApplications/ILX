package xyz.ratapp.ilx.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.List;
import xyz.ratapp.ilx.R;
import xyz.ratapp.ilx.data.dao.Request;
import xyz.ratapp.ilx.view.activities.DetailsActivity;

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
        final Request r = requests.get(position);

        holder.title.setText(r.getAddress());
        holder.comment.setText(r.getComment());

        //setup difficult
        int height = holder.requests.getHeight();
        holder.difficult.setMinimumHeight(height);
        holder.difficult.setBackgroundColor(r.getDifficult());

        if(recent) {
            holder.task.setText(r.getTask());
            holder.time.setText(r.getTime());
        }
        else {
            holder.cost.setText(r.getCost());
        }

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next = DetailsActivity.getIntent(r);
                context.startActivity(next);
            }
        });
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }

    static class RequestsViewHolder
            extends RecyclerView.ViewHolder {

        private RelativeLayout requests;
        private TextView cost;
        private TextView title;
        private TextView task;
        private TextView comment;
        private TextView time;
        private View difficult;
        private View item;


        RequestsViewHolder(View itemView, boolean isRecent) {
            super(itemView);

            item = itemView;
            requests = itemView.findViewById(R.id.rl_requests);
            title = itemView.findViewById(R.id.tv_title);
            comment = itemView.findViewById(R.id.tv_comment);
            difficult = itemView.findViewById(R.id.v_difficult);

            if(isRecent) {
                task = itemView.findViewById(R.id.tv_task);
                time = itemView.findViewById(R.id.tv_time);
            }
            else {
                cost = itemView.findViewById(R.id.tv_cost);
            }
        }
    }
}
