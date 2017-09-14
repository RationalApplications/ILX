package xyz.ratapp.ilx.ui.fragments;

import xyz.ratapp.ilx.controllers.routing.Screens;
import xyz.ratapp.ilx.controllers.main.MainController;

/**
 * Created by timtim on 07/08/2017.
 */

public class StockFragment extends RequestFragment {

    private String title;

    @Override
    public void bindController(MainController controller) {
        super.bindController(controller);
        title = controller.getStrings().getOrderListTrading();
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public Screens getScreen() {
        return Screens.STOCK;
    }

}
