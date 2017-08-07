package xyz.ratapp.ilx.view;

import android.content.Context;
import android.widget.CompoundButton;
import android.widget.Switch;

import xyz.ratapp.ilx.R;

/**
 * Created by timtim on 07/08/2017.
 */

public class StatusSwitch extends Switch {

    private boolean online;

    public StatusSwitch(Context context) {
        super(context);
        setText();
        setOnCheckedChangeListener(new ChangeStatusListener());
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
