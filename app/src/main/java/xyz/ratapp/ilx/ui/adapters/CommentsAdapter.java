package xyz.ratapp.ilx.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by timtim on 19/08/2017.
 *
 * TODO: use DetailsAdapter?
 */

public class CommentsAdapter extends
        RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder> {

    private Context context;
    private List<String> comments;

    public CommentsAdapter(Context context,
                            List<String> comments) {
        this.context = context;
        this.comments = comments;
    }

    @Override
    public CommentsAdapter.CommentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView text = new TextView(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        text.setLayoutParams(params);

        return new CommentsAdapter.CommentsViewHolder(text);
    }

    @Override
    public void onBindViewHolder(CommentsAdapter.CommentsViewHolder holder,
                                 int position) {
        holder.bind(comments.get(position));
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }


    static class CommentsViewHolder
            extends RecyclerView.ViewHolder {

        private TextView text;

        CommentsViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView;
        }

        void bind(String data) {
            text.setText(data);
        }
    }
}