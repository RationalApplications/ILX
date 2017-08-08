package xyz.ratapp.ilx.data;

import android.graphics.Color;

import java.io.IOException;
import java.util.ArrayList;

import xyz.ratapp.ilx.data.dao.Request;
import xyz.ratapp.ilx.data.dao.User;

/**
 * Created by Олег on 08.08.2017.
 */

public class Model {

    private User user;

    private ArrayList<Request> newRequests, curRequests, histRequests;

    public Model() {
        newRequests = new ArrayList<>();
        curRequests = new ArrayList<>();
        histRequests = new ArrayList<>();

        Request requestNew = new Request("Садовая ул.", "", "3 адреса, предоплата 3500 руб.", "", "130р.", "", "", Color.GREEN);
        Request requestNew2 = new Request("Егорова ул., 102", "", "2 адреса, нужен паспорт", "", "180р.", "", "", Color.BLUE);

        Request requestCur = new Request("Садовая ул.", "Выкупить товар -3200 руб", "3 адреса, предоплата 3500 руб.", "10:00-12:00", "180р.", "", "", Color.BLUE);
        Request requestCur2 = new Request("Садовая ул.", "", "3 адреса, предоплата 3500 руб.", "", "180р.", "", "", Color.BLUE);
        Request requestCur3 = new Request("Садовая ул.", "", "3 адреса, предоплата 3500 руб.", "", "", "", "", Color.BLUE);

        newRequests.add(requestNew);
        newRequests.add(requestNew2);

        curRequests.add(requestCur);
        curRequests.add(requestCur2);
        curRequests.add(requestCur3);

        histRequests.add(requestCur);
        histRequests.add(requestCur2);
        histRequests.add(requestCur3);

        user = new User("Олег", "Диденко", "1sq2", "https://pbs.twimg.com/media/CYtAGQPWMAAbUYH.jpg", histRequests, curRequests);
    }

    public User getUser(){
        return user;
    }

    public ArrayList<Request> getNewRequests(){
        return newRequests;
    }
}
