package xyz.ratapp.ilx.controllers.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import xyz.ratapp.ilx.data.dao.Request;
import xyz.ratapp.ilx.ui.fragments.HistoryFragment;
import xyz.ratapp.ilx.ui.fragments.RecentFragment;
import xyz.ratapp.ilx.ui.fragments.RequestFragment;
import xyz.ratapp.ilx.ui.fragments.StockFragment;

/**
 * Created by timtim on 12/06/2017.
 */

class RequestSectionsPagerAdapter extends FragmentPagerAdapter {

    private List<RequestFragment> fragments;

    RequestSectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    void bindFragments(List<RequestFragment> fragments) {
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragments.get(position).getTitle();
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
