package xyz.ratapp.ilx.controllers.data;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import xyz.ratapp.ilx.controllers.Screens;
import xyz.ratapp.ilx.controllers.interfaces.ListSettable;
import xyz.ratapp.ilx.controllers.main.MainController;
import xyz.ratapp.ilx.data.Model;
import xyz.ratapp.ilx.data.dao.Request;
import xyz.ratapp.ilx.data.dao.User;

/**
 * Created by timtim on 14/08/2017.
 */

public class DataController {

    private boolean state = false;
    private final static Model model = new Model();
    private final static Map<Screens, List<Request>> screenRequestsMap;

    static {
        Map<Screens, List<Request>> tmp = new HashMap<>();
        tmp.put(Screens.RECENT, model.getUser().getCurrentRequests());
        tmp.put(Screens.STOCK, model.getNewRequests());
        tmp.put(Screens.HISTORY, model.getUser().getHistoryOfRequests());

        screenRequestsMap = Collections.unmodifiableMap(tmp);
    }

    public User setState(boolean state) {
        User u = model.getUser();
        u.setOnline(state);
        this.state = state;

        return u;
    }

    public void bindRequests(Screens screen,
                             ListSettable<Request> settable) {
        settable.setData(screenRequestsMap.get(screen));
    }

    public void bindUser(MainController controller) {
        User u = model.getUser();
        state = u.isOnline();
        controller.setData(u);
    }

    public boolean getLastState() {
        return state;
    }
}
