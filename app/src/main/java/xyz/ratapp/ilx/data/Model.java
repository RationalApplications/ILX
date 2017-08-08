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

        Request requestNew = new Request("Садовая ул.", //address
                "", //task
                "3 адреса, предоплата 3500 руб.", //comment
                "", //time
                "130р.", //cost
                "", //name
                "", //phone
                Color.GREEN); //difficult

        Request requestNew2 = new Request("Егорова ул., 102",
                "",
                "2 адреса, нужен паспорт",
                "",
                "180р.",
                "",
                "",
                Color.BLUE);



        Request requestCur = new Request("Садовая ул.",
                "Выкупить товар -3200 руб",
                "Забрать два заказа, иметь паспорт при себе",
                "10:00-12:00",
                "",
                "Ольга Ивановна",
                "+79811421523",
                Color.BLUE);
        Request requestCur2 = new Request("Невский пр., 109, кв.122",
                "Принять оплату +1500 руб",
                "Не звонить! заказ оплачен",
                "10:00-12:00",
                "",
                "Олег Иванович",
                "+78255232551",
                Color.BLUE);
        Request requestCur3 = new Request("Пр. Ветеранов, 55, кв. 13",
                "Принять оплату +1700 руб",
                "",
                "10:00-12:00",
                "",
                "Александр Викторович",
                "+79252412512", Color.BLUE);

        newRequests.add(requestNew);
        newRequests.add(requestNew2);

        curRequests.add(requestCur);
        curRequests.add(requestCur2);
        curRequests.add(requestCur3);

        histRequests.add(requestCur);
        histRequests.add(requestCur2);
        histRequests.add(requestCur3);

        user = new User("Олег", "Диденко", "1sq2", "https://mycodeandlife.files.wordpress.com/2013/01/384088_2317070728022_2086719259_n.jpg", histRequests, curRequests);
    }

    public User getUser(){
        return user;
    }

    public ArrayList<Request> getNewRequests(){
        return newRequests;
    }
}
