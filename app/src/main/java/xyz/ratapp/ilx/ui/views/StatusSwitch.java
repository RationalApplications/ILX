package xyz.ratapp.ilx.ui.views;

import android.content.Context;
import android.util.TypedValue;
import android.widget.CompoundButton;
import android.widget.Switch;

import xyz.ratapp.ilx.R;
import xyz.ratapp.ilx.controllers.main.MainController;

/**
 * Created by timtim on 07/08/2017.
 *
 * View that change theme of current context
 * depending on the state of switch
 */

public class StatusSwitch extends Switch {

    //TODO: вынести в integers
    public static final int SWITCH_PADDING = 12;

    private Context context;
    private MainController controller;

    public StatusSwitch(Context context) {
        super(context);
        this.context = context;
        setText(R.string.offline);
        setTextAppearance(context, R.style.TextAppearance_AppCompat_Widget_ActionBar_Title_Inverse);
        float scale = this.getResources().getDisplayMetrics().density;
        int switchPadding = (int) (SWITCH_PADDING * scale);
        setSwitchPadding(switchPadding);
        //TODO: вынести захардкоженное
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);

        setOnCheckedChangeListener(new StatusListener());
    }

    public void setController(MainController controller) {
        this.controller = controller;
    }

    /**
     * Class that provides change theme of app
     * depends on state of Switch
     */
    private class StatusListener implements OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton,
                                     boolean b) {
            setText(b ?
                    R.string.online :
                    R.string.offline);
            controller.setStateChanged(b);
        }
    }
}
