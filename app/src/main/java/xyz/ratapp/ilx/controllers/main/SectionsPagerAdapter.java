package xyz.ratapp.ilx.controllers.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import xyz.ratapp.ilx.view.fragments.HistoryFragment;
import xyz.ratapp.ilx.view.fragments.RecentFragment;
import xyz.ratapp.ilx.view.fragments.StockFragment;

/**
 * Created by timtim on 12/06/2017.
 */

class SectionsPagerAdapter extends FragmentPagerAdapter {

    private StockFragment stock = new StockFragment();
    private RecentFragment recent = new RecentFragment();
    private HistoryFragment history = new HistoryFragment();

    SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {

        if(position == 0) {
            return stock;
        }
        else if(position == 1) {
            return recent;
        }
        else if(position == 2) {
            return history;
        }

        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        //TODO: change to @strings
        switch (position) {
            case 0:
                return "Биржа";
            case 1:
                return "Текущие";
            case 2:
                return "История";
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
