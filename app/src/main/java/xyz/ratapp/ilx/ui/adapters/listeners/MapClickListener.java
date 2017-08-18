package xyz.ratapp.ilx.ui.adapters.listeners;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

/**
 * Created by timtim on 17/08/2017.
 */

public class MapClickListener
        implements View.OnClickListener {

    private Context context;

    public MapClickListener(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View view) {
        //TODO: change intent
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?daddr=59.955761,30.313146"));
        context.startActivity(intent);
    }
}
