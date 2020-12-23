package com.example.paytmgatewayjava;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Body {

    @SerializedName("requestType")
    @Expose
    private String requestType;
    @SerializedName("mid")
    @Expose
    private String mid;
    @SerializedName("websiteName")
    @Expose
    private String websiteName;
    @SerializedName("orderId")
    @Expose
    private String orderId;
    @SerializedName("callbackUrl")
    @Expose
    private String callbackUrl;
    @SerializedName("txnAmount")
    @Expose
    private TxnAmount txnAmount;
    @SerializedName("userInfo")
    @Expose
    private UserInfo userInfo;

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getWebsiteName() {
        return websiteName;
    }

    public void setWebsiteName(String websiteName) {
        this.websiteName = websiteName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public TxnAmount getTxnAmount() {
        return txnAmount;
    }

    public void setTxnAmount(TxnAmount txnAmount) {
        this.txnAmount = txnAmount;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public String toString() {
        return "Body{" +
                "requestType='" + requestType + '\'' +
                ", mid='" + mid + '\'' +
                ", websiteName='" + websiteName + '\'' +
                ", orderId='" + orderId + '\'' +
                ", callbackUrl='" + callbackUrl + '\'' +
                ", txnAmount=" + txnAmount +
                ", userInfo=" + userInfo +
                '}';
    }
}