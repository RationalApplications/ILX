package xyz.ratapp.ilx.ui.helpers;

import android.content.res.Resources;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.StyleRes;
import android.util.TypedValue;

import xyz.ratapp.ilx.R;
import xyz.ratapp.ilx.ui.activities.MainActivity;

/**
 * Created by timtim on 14/08/2017.
 */

public class ThemePicker {
    @StyleRes
    private static final int STATE_ACTIVE_THEME = R.style.AppTheme_Active;
    @StyleRes
    private static final int STATE_PASSIVE_THEME = R.style.AppTheme_Passive;
    @StyleRes
    private int currentTheme;
    private MainActivity activity;

    public ThemePicker(MainActivity activity) {
        this.activity = activity;
    }

    public void setTheme(boolean state) {
        currentTheme = state ?
                STATE_ACTIVE_THEME :
                STATE_PASSIVE_THEME;

        activity.setTheme(currentTheme);

        /*views
        navigationView.getHeaderView(0).
                setBackgroundResource(getSideNavBar());*/
    }

    @StyleRes
    public int getCurrentTheme() {
        return currentTheme;
    }

    @ColorInt
    public int getPrimaryColor() {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = activity.getTheme();
        theme.resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }

    @ColorInt
    public int getPrimaryDarkColor() {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = activity.getTheme();
        theme.resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
        return typedValue.data;
    }

    @ColorInt
    public int getAccentColor() {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = activity.getTheme();
        theme.resolveAttribute(R.attr.colorAccent, typedValue, true);
        return typedValue.data;
    }

    @DrawableRes
    public int getSideNavBar() {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = activity.getTheme();
        theme.resolveAttribute(R.attr.sideNavBar, typedValue, true);
        return typedValue.data;
    }
}