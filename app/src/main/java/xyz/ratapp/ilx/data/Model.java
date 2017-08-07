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

    private ArrayList newRequests, seqRequests;

    public Model() throws IOException {
        Request requestNew = new Request("Садовая ул.", "", "3 адреса, предоплата 3500 руб.", "", "130р.", Color.GREEN);
        Request requestNew2 = new Request("Егорова ул., 102", "", "2 адреса, нужен паспорт", "", "180р.", Color.BLUE);

        Request requestSeq = new Request("Садовая ул.", "Выкупить товар -3200 руб", "3 адреса, предоплата 3500 руб.", "10:00-12:00", "180р.", Color.BLUE);
        Request requestSeq2 = new Request("Садовая ул.", "", "3 адреса, предоплата 3500 руб.", "", "180р.", Color.BLUE);
        Request requestSeq3 = new Request("Садовая ул.", "", "3 адреса, предоплата 3500 руб.", "", "", Color.BLUE);

        newRequests.add(requestNew);
        newRequests.add(requestNew2);

        seqRequests.add(requestSeq);
        seqRequests.add(requestSeq2);
        seqRequests.add(requestSeq3);


        user = new User("Олег", "Диденко", "1sq2", "https://mycodeandlife.files.wordpress.com/2013/01/384088_2317070728022_2086719259_n.jpg");
    }

    public User getUser(){
        return user;
    }

    public ArrayList<Request> getNewRequests(){
        return newRequests;
    }

    public ArrayList<Request> getSeqRequests(){
        return seqRequests;
    }

}
