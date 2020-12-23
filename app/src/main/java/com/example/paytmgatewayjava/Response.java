package com.example.paytmgatewayjava;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("paytm_res")
    @Expose
    private String paytmRes;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getPaytmRes() {
        return paytmRes;
    }

    public void setPaytmRes(String paytmRes) {
        this.paytmRes = paytmRes;
    }

    @Override
    public String toString() {
        return "Response{" +
                "data=" + data +
                ", paytmRes='" + paytmRes + '\'' +
                '}';
    }
}