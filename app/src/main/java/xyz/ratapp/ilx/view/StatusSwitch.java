package xyz.ratapp.ilx.view;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import xyz.ratapp.ilx.R;

import static android.support.design.R.attr.theme;

/**
 * Created by timtim on 07/08/2017.
 */

public class StatusSwitch extends Switch {

    private boolean online;

    public StatusSwitch(Context context, OnCheckedChangeListener listener) {
        super(context);
        setText(R.string.offline);
        setOnCheckedChangeListener(listener);
        setTextAppearance(context, R.style.TextAppearance_AppCompat_Widget_ActionBar_Title_Inverse);

        final float scale = this.getResources().getDisplayMetrics().density;
        setSwitchPadding(((int) (12 * scale)));
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
    }

    private void setText() {
        setText(online ?
                R.string.online :
                R.string.offline);
    }

    public boolean isOnline() {
        return online;
    }

    private class ChangeStatusListener
            implements OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton compoundButton,
                                     boolean state) {
            online = state;
            setText();
        }
    }
}
