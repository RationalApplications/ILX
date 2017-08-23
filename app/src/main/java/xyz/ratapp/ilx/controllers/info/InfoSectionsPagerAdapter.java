package xyz.ratapp.ilx.controllers.info;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import xyz.ratapp.ilx.data.dao.Names;


/**
 * Created by timtim on 21/08/2017.
 */

class InfoSectionsPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;
    private Names names;

    InfoSectionsPagerAdapter(FragmentManager fm, Names names) {
        super(fm);
        this.names = names;
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
            return names.getOrderViewInfo();
        }
        else {
            return names.getOrderViewChat();
        }
    }

    @Override
    public int getCount() {
        return fragments.size();
    }


}
