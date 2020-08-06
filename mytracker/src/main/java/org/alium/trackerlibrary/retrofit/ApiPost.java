package org.alium.trackerlibrary.retrofit;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiPost {

//    @Headers("content-type: application/json")
    @POST("default/logger-LoggerFunction-DF5DMWQXW20A")
    Call<ResponseBody> PostData(@HeaderMap Map<String, String> headers, @Body JSONObject jsonObject);





//    @Headers("Content-Type: application/json")
//    @POST("/post")
//    Call<AliumData> PostData(@Body String jsonObject);
//    @POST("path")
//    Call<ResponseBody> getStringRequestBody(@Body RequestBody body);


}
