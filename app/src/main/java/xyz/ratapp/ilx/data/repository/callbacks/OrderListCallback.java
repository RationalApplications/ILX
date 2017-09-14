package xyz.ratapp.ilx.data.repository.callbacks;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import xyz.ratapp.ilx.controllers.data.callbacks.BinderCallback;
import xyz.ratapp.ilx.data.dao.orders.Order;
import xyz.ratapp.ilx.data.repository.DataRepository;

/**
 * Created by timtim on 11/09/2017.
 */

public class OrderListCallback extends RepositoryCallback {

    public OrderListCallback(DataRepository repo,
                             BinderCallback binder) {
        super(repo, binder);
    }

    @Override
    void onSuccess(JsonObject obj) {
        List<Order> recent = new ArrayList<>();

        if(obj.has("orders")) {
            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, Order>>() {}.getType();
            JsonObject orderList = obj.getAsJsonObject("orders");
            Map<String, Order> orderMap = gson.fromJson(orderList, type);

            recent.addAll(orderMap.values());
        }

        repo.setData(this, recent, binder);
    }

    @Override
    void onFailed(JsonObject obj) {
        String error = obj.get("errormessage").getAsString();
        binder.fail(error);
    }
}
