package xyz.ratapp.ilx.ui.views;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.SwitchCompat;
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

public class StatusSwitch extends SwitchCompat {

    public static int SWITCH_PADDING;
    public static int TEXT_SIZE;
    public static int WAIT_TIME;
    public static float SCALE;

    private Context context;
    private MainController controller;
    private final StatusListener listener =
            new StatusListener();

    public StatusSwitch(Context context) {
        super(context);
        this.context = context;

        setupSizes();
        setupScales();
        setupColors();

        setText(R.string.offline);
        setOnCheckedChangeListener(listener);
    }

    private void setupSizes() {
        if(WAIT_TIME == 0) {
            WAIT_TIME = getResources().
                    getInteger(R.integer.switch_wait_time);
        }

        if (SWITCH_PADDING == 0) {
            SWITCH_PADDING = getResources().
                    getInteger(R.integer.switch_padding);
        }

        if (TEXT_SIZE == 0) {
            TEXT_SIZE = getResources().
                    getInteger(R.integer.switch_text_size);
        }

        if(SCALE == 0) {
            SCALE = getResources().
                    getDisplayMetrics().density;
        }
    }

    private void setupScales() {
        //TODO: do something - deprecated
        //setup text style
        setTextAppearance(context, R.style.TextAppearance_AppCompat_Widget_ActionBar_Title_Inverse);
        setTextSize(TypedValue.COMPLEX_UNIT_SP, TEXT_SIZE);
        //setup padding
        int switchPadding = (int) (SWITCH_PADDING * SCALE);
        setSwitchPadding(switchPadding);
    }

    private void setupColors() {
        int[][] states = new int[][] {
                new int[] {-android.R.attr.state_checked},
                new int[] {android.R.attr.state_checked},
        };

        int[] thumbColors = new int[] {
                getResources().
                        getColor(R.color.status_switch_background_color),
                getResources().
                        getColor(R.color.status_switch_background_color)
        };

        int[] trackColors = new int[] {
                context.getResources().
                        getColor(R.color.passive_accent_color),
                context.getResources().
                        getColor(R.color.accent_color),
        };

        SwitchCompat switchCompat = this;
        DrawableCompat.setTintList(DrawableCompat.wrap(
                switchCompat.getThumbDrawable()), new ColorStateList(states, thumbColors));
        DrawableCompat.setTintList(DrawableCompat.wrap(
                switchCompat.getTrackDrawable()), new ColorStateList(states, trackColors));

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
            setEnabled(false);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    setEnabled(true);
                }
            }, WAIT_TIME * 1000);

            controller.setStateChanged(b);
        }
    }
}
