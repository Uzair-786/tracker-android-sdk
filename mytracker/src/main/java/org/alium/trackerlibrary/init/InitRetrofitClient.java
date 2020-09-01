package org.alium.trackerlibrary.init;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InitRetrofitClient {

    Gson gson = new GsonBuilder().setLenient().create();

    private static final String BASE_URL = "https://dev-webpush.alium.co.in/";
    private static InitRetrofitClient clientInstance;
    private Retrofit retrofit;

    private InitRetrofitClient(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static synchronized InitRetrofitClient getInstance(){
        if(clientInstance == null){
            clientInstance = new InitRetrofitClient();
        }

        return clientInstance;
    }

    public InitPostApi getApiPost(){
        return retrofit.create(InitPostApi.class);
    }
}
