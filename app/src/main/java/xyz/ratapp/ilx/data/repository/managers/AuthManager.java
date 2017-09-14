package xyz.ratapp.ilx.data.repository.managers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import xyz.ratapp.ilx.R;
import xyz.ratapp.ilx.data.repository.callbacks.AuthCallback;
import xyz.ratapp.ilx.ui.interfaces.ErrorDisplayable;
import xyz.ratapp.ilx.data.dao.users.Courier;
import xyz.ratapp.ilx.data.dao.app.AppStrings;
import xyz.ratapp.ilx.data.repository.DataRepository;
import xyz.ratapp.ilx.data.retrofit.API;

import static xyz.ratapp.ilx.data.retrofit.API.BASE_URL;

/**
 * Created by timtim on 05/09/2017.
 */

class AuthManager {

    private static volatile AuthManager instance;

    private Retrofit retrofitBase = new Retrofit.Builder().
            addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build();
    private API apiBase = retrofitBase.create(API.class);
    private ManageableRepository repo;

    private AuthManager() {

    }

    static AuthManager getInstance(ManageableRepository repo) {
        AuthManager localInstance = instance;

        if (localInstance == null) {
            synchronized (AuthManager.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance =
                            new AuthManager();
                }
                instance.repo = repo;
            }
        }

        return localInstance;
    }

    void authAccessCode(AuthCallback callback, String code) {
        apiBase.authAccessCode(code).enqueue(callback);
    }

    void auth(AuthCallback callback, String sessionId) {
        API apiUser = repo.getApiUser();
        apiUser.auth(sessionId).enqueue(callback);
    }
}
