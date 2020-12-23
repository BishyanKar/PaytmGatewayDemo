package com.example.paytmgatewayjava;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.paytm.pgsdk.TransactionManager;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText amntET,custIdET;
    ProgressBar progressBar;
    Button proceed;
    String midString;
    String txnAmountString;
    String orderIdString;
    String txnTokenString;

    Integer ActivityRequestCode = 2;

    APIInterface checksumApi;
    String callBackUrl,host;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        amntET = findViewById(R.id.amnt_et);
        custIdET = findViewById(R.id.cust_Id);
        proceed = findViewById(R.id.proceed_btn);
        progressBar = findViewById(R.id.progressbar);

        host = "https://securegw-stage.paytm.in/";
        callBackUrl  = host + "theia/paytmCallback?ORDER_ID="+orderIdString;

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDetails();
            }
        });

    }

    public void popDialog(String status,String currency,String amnt, String orderId)
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.layout_payment_status_dialog, null);
        dialogBuilder.setView(dialogView);

        TextView statusTV = dialogView.findViewById(R.id.heading_success);
        TextView orderIdTV = dialogView.findViewById(R.id.tv_orderId);
        TextView amntTV = dialogView.findViewById(R.id.tv_amnt);
        ImageView imageView = dialogView.findViewById(R.id.success_tick);

        if(status.compareTo("TXN_SUCCESS")!=0)
        {
            statusTV.setText("Payment Failed");
            imageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.failure,null));
            orderIdTV.setVisibility(View.GONE);
            amntTV.setVisibility(View.GONE);
        }
        else {
            orderIdTV.setText("Order ID : "+orderId);
            amntTV.setText("Amount : "+amnt +"  "+currency);
        }

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        if(alertDialog.getWindow()!=null)
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
    public void getDetails()
    {
        //orderIdString = "ORDERID_" + orderIdET.getText().toString();
        progressBar.setVisibility(View.VISIBLE);
        String amnt = amntET.getText().toString();
        String custId = custIdET.getText().toString();

        checksumApi = APIClient.getInstance().getApi();
        checksumApi.getTransDet(amnt,custId).enqueue(new Callback<com.example.paytmgatewayjava.Response>() {
            @Override
            public void onResponse(Call<com.example.paytmgatewayjava.Response> call, Response<com.example.paytmgatewayjava.Response> response) {
                Log.d(TAG, "onResponse: RES : "+response.body());
                if(response.body()!=null)
                {
                    try {
                        com.example.paytmgatewayjava.Response res = response.body();
                        JSONObject paytmRes = new JSONObject(res.getPaytmRes());
                        Body body = res.getData().getBody();
                        orderIdString = body.getOrderId();
                        midString = body.getMid();
                        txnAmountString = body.getTxnAmount().getValue();
                        txnTokenString = paytmRes.optJSONObject("body").optString("txnToken");
                        String orderDetails = "MID: " + midString + ", OrderId: " + orderIdString + ", TxnToken: " + txnTokenString + ", Amount: " + txnAmountString;
                        Toast.makeText(MainActivity.this, orderDetails, Toast.LENGTH_SHORT).show();
                        startPayment();

                    }catch (Exception err){
                        Log.d(TAG, err.toString());
                        progressBar.setVisibility(View.GONE);
                    }
                }
                else {
                    Log.d(TAG, "onResponse: NULL ERROR");
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<com.example.paytmgatewayjava.Response> call, Throwable t) {
                Log.d(TAG, "onFailure: ERROR : "+t.getMessage());
                progressBar.setVisibility(View.GONE);
            }
        });

    }
    public void startPayment(){
            PaytmOrder paytmOrder = new PaytmOrder(orderIdString, midString, txnTokenString, txnAmountString, callBackUrl);
            TransactionManager transactionManager = new TransactionManager(paytmOrder, new PaytmPaymentTransactionCallback(){

                @Override
                public void onTransactionResponse(Bundle bundle) {

                    progressBar.setVisibility(View.GONE);
                    Log.d(TAG, "onTransactionResponse: "+bundle.toString());
                    //Toast.makeText(MainActivity.this, "Response (onTransactionResponse) : "+bundle.toString(), Toast.LENGTH_LONG).show();
                    String status = bundle.getString("STATUS");
                    String amnt = bundle.getString("TXNAMOUNT","");
                    String orderId = bundle.getString("ORDERID","");
                    String currency = bundle.getString("CURRENCY","");

                    popDialog(status,currency,amnt,orderId);
                }

                @Override
                public void networkNotAvailable() {
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onErrorProceed(String s) {
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void clientAuthenticationFailed(String s) {
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void someUIErrorOccurred(String s) {
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onErrorLoadingWebPage(int i, String s, String s1) {
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onBackPressedCancelTransaction() {
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onTransactionCancel(String s, Bundle bundle) {
                    progressBar.setVisibility(View.GONE);
                }
            });
            transactionManager.setAppInvokeEnabled(false);
            transactionManager.setShowPaymentUrl(host + "theia/api/v1/showPaymentPage");
            transactionManager.startTransaction(this, ActivityRequestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ActivityRequestCode && data != null) {
            progressBar.setVisibility(View.GONE);
            Log.d(TAG, "onActivityResult: "+data.getStringExtra("nativeSdkForMerchantMessage") + data.getStringExtra("response"));
            Toast.makeText(this, data.getStringExtra("nativeSdkForMerchantMessage") + data.getStringExtra("response"), Toast.LENGTH_LONG).show();
        }
    }
}