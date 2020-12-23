package com.example.paytmgatewayjava;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("body")
    @Expose
    private Body body;
    @SerializedName("head")
    @Expose
    private Head head;

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public Head getHead() {
        return head;
    }

    public void setHead(Head head) {
        this.head = head;
    }

    @Override
    public String toString() {
        return "Data{" +
                "body=" + body +
                ", head=" + head +
                '}';
    }
}
