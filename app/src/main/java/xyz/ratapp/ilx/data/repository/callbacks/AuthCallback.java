package xyz.ratapp.ilx.data.repository.callbacks;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import xyz.ratapp.ilx.controllers.data.callbacks.BinderCallback;
import xyz.ratapp.ilx.data.dao.app.AppStrings;
import xyz.ratapp.ilx.data.dao.users.Courier;
import xyz.ratapp.ilx.data.repository.DataRepository;

/**
 * Created by timtim on 11/09/2017.
 */

public class AuthCallback extends RepositoryCallback {

    private String domainName = null;

    public AuthCallback(DataRepository repo,
                        BinderCallback binder) {
        super(repo, binder);
    }

    public AuthCallback(DataRepository repo,
                        BinderCallback binder,
                        String domainName) {
        super(repo, binder);
        this.domainName = domainName;
    }

    @Override
    void onSuccess(JsonObject obj) {
        if(domainName == null) {
            String domainName = obj.get("domain_name").getAsString();
            String sessionId = obj.get("session_id").getAsString();

            repo.setData(this, domainName, sessionId);
            repo.auth(binder);
        }
        else {
            String domain = "https://" + domainName + "/";

            String courierName = obj.get("courier_name").getAsString();
            String ava = domain + obj.get("ava_url").getAsString();
            String preview = domain + obj.get("ava_url_preview").getAsString();
            String courierPhone = obj.get("courier_phone").getAsString();
            String clientName = obj.get("client_name").getAsString();
            String clientPhone = obj.get("client_phone").getAsString();
            String authProperty = obj.get("auth_property").getAsString();
            String authMd = obj.get("auth_md").getAsString();
            String sessionId = obj.get("session_id").getAsString();
            String cid = obj.get("cid").getAsString();
            int wareHouseProperty = obj.get("warehouse_property").getAsInt();
            int gpsTimeout = obj.get("gps_timeout").getAsInt();
            String workStatus = obj.get("work_status").getAsString();
            String courierType = obj.get("courier_type").getAsString();
            int gps = obj.get("gps").getAsInt();

            Gson gson = new Gson();
            Type type = new TypeToken<AppStrings>(){}.getType();
            JsonObject orderList = obj.getAsJsonObject("elements_name");
            AppStrings strings = gson.fromJson(orderList, type);

            Courier courier = new Courier(domainName, sessionId);
            courier.onAuthSuccess(ava, preview, courierPhone, clientName,
                    clientPhone, authProperty, authMd, gps, workStatus, gpsTimeout,
                    strings, cid, wareHouseProperty, courierName, courierType);

            //savePrefs(controller.getContext());
            repo.setData(this, courier);
            binder.success(strings);
        }
    }

    @Override
    void onFailed(JsonObject obj) {
        String error = obj.get("errormessage").getAsString();
        binder.fail(error);
    }
}
