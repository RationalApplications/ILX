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
        final View v = LayoutInflater.from(context).inflate(R.layout.item_details, parent, false);
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
        private Caller caller;
        private Pather pather;


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
                    pather = new Pather();
                }

                btnAction.setOnClickListener(pather);
            }
            else if(t.equals(Details.Type.PHONE)) {
                //typefont???
                ivAction.setVisibility(View.VISIBLE);
                ivAction.setImageResource(R.drawable.phone);

                if(caller == null) {
                    caller = new Caller(text);
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


        private class Caller implements View.OnClickListener {
            private String text;

            Caller(String text) {
                this.text = text;
            }

            public void setText(String text) {
                this.text = text;
            }

            @Override
            public void onClick(View view) {
                String phone = text.substring(text.indexOf('+'));
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("tel:" + phone));
                context.startActivity(intent);
            }
        }

        private class Pather implements View.OnClickListener {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?daddr=59.955761,30.313146"));
                context.startActivity(intent);
            }
        }
    }
}
