package com.example.paytmgatewayjava;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("paytmCheckSum")
    Call<Response> getTransDet(@Query("amnt") String amnt, @Query("cID") String cID);
}