package xyz.ratapp.ilx.controllers.info;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import xyz.ratapp.ilx.data.dao.app.AppStrings;


/**
 * Created by timtim on 21/08/2017.
 */

class InfoSectionsPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;
    private AppStrings strings;

    InfoSectionsPagerAdapter(FragmentManager fm, AppStrings strings) {
        super(fm);
        this.strings = strings;
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
        if(position == 0) {
            return strings.getOrderViewInfo();
        }
        else {
            return strings.getOrderViewChat();
        }
    }

    @Override
    public int getCount() {
        return fragments.size();
    }


}
