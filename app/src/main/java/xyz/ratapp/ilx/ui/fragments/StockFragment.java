package xyz.ratapp.ilx.ui.fragments;

import xyz.ratapp.ilx.R;
import xyz.ratapp.ilx.controllers.Screens;

/**
 * Created by timtim on 07/08/2017.
 */

public class StockFragment extends RequestFragment {

    @Override
    public String getTitle() {
        return controller.getContext().getString(R.string.stock);
    }

    @Override
    public Screens getScreen() {
        return Screens.STOCK;
    }
}
