package xyz.ratapp.ilx.ui.helpers;

import android.content.res.Resources;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.StyleRes;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;

import xyz.ratapp.ilx.R;
import xyz.ratapp.ilx.ui.activities.MainActivity;

/**
 * Created by timtim on 14/08/2017.
 */


public class ThemePicker {

    @StyleRes
    private static final int STATE_ACTIVE_THEME =
            R.style.AppTheme_Active_NoActionBar;
    @StyleRes
    private static final int STATE_PASSIVE_THEME =
            R.style.AppTheme_Passive_NoActionBar;
    @StyleRes
    private int currentTheme;
    private MainActivity activity;
    private NavigationView navigationView;
    private Toolbar toolbar;

    public ThemePicker(MainActivity activity) {
        this.activity = activity;
        navigationView = activity.findViewById(R.id.navView);
        toolbar = activity.findViewById(R.id.toolbar);
    }

    public void setTheme(boolean state) {
        currentTheme = state ?
                STATE_ACTIVE_THEME :
                STATE_PASSIVE_THEME;

        activity.setTheme(currentTheme);

        //setup views
        navigationView.getHeaderView(0).
                setBackgroundResource(getSideNavBar());
        toolbar.setBackgroundResource(getPrimaryColor());
    }

    @StyleRes
    public int getCurrentTheme() {
        return currentTheme;
    }


    public int getPrimaryColor() {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = activity.getTheme();
        theme.resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.resourceId;
    }


    public int getPrimaryDarkColor() {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = activity.getTheme();
        theme.resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
        return typedValue.resourceId;
    }


    public int getAccentColor() {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = activity.getTheme();
        theme.resolveAttribute(R.attr.colorAccent, typedValue, true);
        return typedValue.resourceId;
    }


    @DrawableRes
    public int getSideNavBar() {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = activity.getTheme();
        theme.resolveAttribute(R.attr.sideNavBar, typedValue, true);
        return typedValue.resourceId;
    }
}