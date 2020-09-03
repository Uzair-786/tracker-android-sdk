package org.alium.trackerlibrary.init;

import org.json.JSONObject;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface InitPostApi {

//    @Headers("Content-Type: application/json")//,Content-Length: 1439,Host: https://dev-webpush.alium.co.in")
//    @Headers("Content-Length: 1439")
//    @Headers("Host: https://dev-webpush.alium.co.in")
    @POST("/node/api/dashboard/auth/init-client")
    Call<Init> PostData(@Body Init jsonObject);

//    @HeaderMap Map<String, String> headers,
}
