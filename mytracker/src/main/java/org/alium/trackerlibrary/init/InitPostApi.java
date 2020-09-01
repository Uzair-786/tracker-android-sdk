package org.alium.trackerlibrary.init;

import org.json.JSONObject;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

public interface InitPostApi {

    @POST("node/api/dashboard/auth/init-client")
    Call<ResponseBody> PostData(@HeaderMap Map<String, String> headers, @Body JSONObject jsonObject);

}
