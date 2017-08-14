package xyz.ratapp.ilx.controllers.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import xyz.ratapp.ilx.data.dao.Request;
import xyz.ratapp.ilx.ui.fragments.HistoryFragment;
import xyz.ratapp.ilx.ui.fragments.RecentFragment;
import xyz.ratapp.ilx.ui.fragments.StockFragment;

/**
 * Created by timtim on 12/06/2017.
 */

class SectionsPagerAdapter extends FragmentPagerAdapter {

    private StockFragment stock;
    private RecentFragment recent;
    private HistoryFragment history;

    SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
        //initialize all fragments
        stock = new StockFragment();
        recent = new RecentFragment();
        history = new HistoryFragment();
    }

    public void setData(int position, List<Request> data) {
        if(position == 0) {
            stock.setData(data);
        }
        else if(position == 1) {
            recent.setData(data);
        }
        else if(position == 2) {
            history.setData(data);
        }
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
