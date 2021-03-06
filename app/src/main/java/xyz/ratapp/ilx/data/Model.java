package xyz.ratapp.ilx.data;

import android.graphics.Color;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import xyz.ratapp.ilx.data.dao.Request;
import xyz.ratapp.ilx.data.dao.User;

/**
 * Created by Олег on 08.08.2017.
 */

public class Model {

    private User user;

    private List<Request> newRequests, curRequests, histRequests;

    public Model() {
        curRequests = new ArrayList<>();
        histRequests = new ArrayList<>();

        Request requestNew = new Request("Садовая ул.", //address
                "", //task
                "3 адреса, предоплата 3500 руб.", //comment
                "12:00-16:00", //time
                "130р.", //cost
                "8р.", //commission
                "", //name
                "", //phone
                Color.GREEN); //difficult

        Request requestNew2 = new Request("Егорова ул., 102",
                "",
                "2 адреса, нужен паспорт",
                "12:00-13:00",
                "180р.",
                "8р.", //commission
                "",
                "",
                Color.BLUE);



        Request requestCur = new Request("Садовая ул.",
                "Выкупить товар -3200 руб",
                "Забрать два заказа, иметь паспорт при себе",
                "10:00-12:00",
                "",
                "8р.", //commission
                "Ольга Ивановна",
                "+79811421523",
                Color.BLUE);
        Request requestCur2 = new Request("Невский пр., 109, кв.122",
                "Принять оплату +1500 руб",
                "Не звонить! заказ оплачен",
                "10:00-12:00",
                "",
                "8р.", //commission
                "Олег Иванович",
                "+78255232551",
                Color.BLUE);
        Request requestCur3 = new Request("Пр. Ветеранов, 55, кв. 13",
                "Принять оплату +1700 руб",
                "",
                "10:00-12:00",
                "",
                "8р.", //commission
                "Александр Викторович",
                "+79252412512", Color.BLUE);

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

    public List<Request> getNewRequests(){
        return newRequests;
    }

    public void setNewRequests(List<Request> newRequests) {
        this.newRequests = newRequests;
    }
}
