package com.example.paytmgatewayjava;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    APIInterface apiInterface;
    static APIClient client;
    public APIInterface getApi() {
        return apiInterface;
    }

    private APIClient(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://us-central1-testforclfunc.cloudfunctions.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiInterface = retrofit.create(APIInterface.class);
    }

    public static APIClient getInstance() {
        if(client == null){
            client = new APIClient();
        }
        return client;
    }
}