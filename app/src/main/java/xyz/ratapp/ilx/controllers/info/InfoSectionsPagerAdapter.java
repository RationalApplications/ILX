package xyz.ratapp.ilx.controllers.info;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;


/**
 * Created by timtim on 21/08/2017.
 */

public class InfoSectionsPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;

    InfoSectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    void bindFragments(List<Fragment> fragments) {
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        //TODO: hardcoded
        if(position == 0) {
            return "ДЕТАЛИ";
        }
        else {
            return "ЧАТ";
        }
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
