package xyz.ratapp.ilx.ui.adapters.listeners;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;

/**
 * Created by timtim on 17/08/2017.
 *
 * Class that call phone-manager to
 * call phone number(starts with +)
 * from text,
 *
 * f.e.: with text "hello, call +7 (911) 160-64-42"
 * listener will call number "+7 (911) 160-64-42"
 * - from character plus to end of string
 */

public class CallerClickListener
        implements View.OnClickListener {

    private Context context;
    private String text;

    public CallerClickListener(Context context,
                               String text) {
        this.context = context;
        this.text = text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void onClick(View view) {
        String phone = text;
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phone));

        try {
            context.startActivity(intent);
        } catch (SecurityException e) {
            Log.e("MyTag", "Ошибка звонка");
        }
    }
}
