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
    private String lat;
    private String lng;

    public MapClickListener(Context context,
                            String lat,
                            String lng) {
        this.context = context;
        this.lat = lat;
        this.lng = lng;
    }

    @Override
    public void onClick(View view) {
        String url = String.format(
                "http://maps.google.com/maps?daddr=%s,%s",
                lat, lng);
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse(url));
        context.startActivity(intent);
    }
}
