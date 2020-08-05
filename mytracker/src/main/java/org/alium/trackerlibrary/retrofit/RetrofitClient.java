package org.alium.trackerlibrary.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    Gson gson = new GsonBuilder().setLenient().create();

    private static final String BASE_URL = "https://mt8wslo0ml.execute-api.ap-south-1.amazonaws.com/";
    private static RetrofitClient clientInstance;
    private Retrofit retrofit;

    private RetrofitClient(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static synchronized RetrofitClient getInstance(){
        if(clientInstance == null){
            clientInstance = new RetrofitClient();
        }

        return clientInstance;
    }

    public ApiPost getApiPost(){
        return retrofit.create(ApiPost.class);
    }
}
