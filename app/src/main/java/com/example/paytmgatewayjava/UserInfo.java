package com.example.paytmgatewayjava;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserInfo {

    @SerializedName("custId")
    @Expose
    private String custId;

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "custId='" + custId + '\'' +
                '}';
    }
}