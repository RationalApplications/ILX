package xyz.ratapp.ilx.data.retrofit;

import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by timtim on 15/08/2017.
 *
 * Метод		auth_access_code
 * URL		    https://apps.itlogist.ru/api/courier/v2/?data_type=json&access_code={access_code}
 *
 * Метод		auth
 * URL		    https://{domain_name}/api/courier/v2/?method=auth&session_id={session_id}
 *
 * Метод		courier_location
 * URL		    https://{domain_name}/api/courier/v2/?method=courier_location&session_id={session_id}& lat={lat}&lng={lng}&speed={speed}&acc={acc}&time={time}
 *
 * Метод		work_status_update
 * URL		    https://{domain_name}/api/courier/v2/?method=work_status_update&session_id= {session_id}&work_status={work_status}
 *
 * Метод		order_list_trading
 * URL		    https://{domain_name}/api/courier/v2/?method=order_list_trading&session_id= {session_id}
 */

public interface API {

    String BASE_URL = "https://apps.itlogist.ru/api/courier/v2/";
    String USER_URL_MASK = "https://%s/api/courier/v2/";


    @GET("?data_type=json")
    Call<JsonObject> authAccessCode(@Query("access_code") String accessCode);

    @GET("?method=auth")
    Call<JsonObject> auth(@Query("session_id") String sessionId);

    @GET("?method=courier_location")
    Call<JsonObject> courierLocation(@Query("session_id") String sessionId,
                                     @Query("lat") String lat,
                                     @Query("lng") String lng,
                                     @Query("speed") String speed,
                                     @Query("acc") String acc,
                                     @Query("time") String time);

    @GET("?method=work_status_update")
    Call<JsonObject> workStatusUpdate(@Query("session_id") String sessionId,
                                      @Query("work_status") String workStatus);

    @GET("?method=order_list_trading")
    Call<JsonObject> orderListTrading(@Query("session_id") String sessionId);

    @GET("?method=register_gcm")
    Call<JsonObject> registerFCM(@Query("session_id") String sessionId,
                                 @Query("registration_id\t") String regId);
}
