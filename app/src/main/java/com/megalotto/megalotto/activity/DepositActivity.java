package com.megalotto.megalotto.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.paytm.pgsdk.PaytmConstants;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.paytm.pgsdk.TransactionManager;
import com.megalotto.megalotto.MyApplication;
import com.megalotto.megalotto.R;
import com.megalotto.megalotto.api.ApiCalling;
import com.megalotto.megalotto.helper.AppConstant;
import com.megalotto.megalotto.helper.Function;
import com.megalotto.megalotto.helper.Preferences;
import com.megalotto.megalotto.helper.ProgressBarHelper;
import com.megalotto.megalotto.model.Token;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import paytm.assist.easypay.easypay.appinvoke.BuildConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DepositActivity extends AppCompatActivity implements PaymentResultListener {
    private static final String TAG = PaymentActivity.class.getSimpleName();
    public RadioButton RazorPayRb;
    public TextView alertTv;
    private EditText amountEt;
    public String amountSt;
    private ApiCalling api;
    public TextView balanceTv;
    public String checksumSt;
    private Context context;
    private int depositBalance;
    public TextView depositTv;
    public TextView noteTv;
    public String orderIdSt;
    public RadioButton payTmRb;
    public String paymentIdSt;
    private ProgressBarHelper progressBarHelper;
    public RadioGroup radioGroup;
    public TextView signTv;
    public String tokenSt;
    public RadioButton upiRb;
    private String mopSt = "PayTm";
    final int UPI_PAYMENT = 0;
    final int GOOGLEPAY_PAYMENT = 1;
    final int PAYTM_UPI_PAYMENT = 2;
    final int PAYTM_PAYMENT = 3;


    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);
        this.context = this;
        this.api = MyApplication.getRetrofit().create(ApiCalling.class);
        this.progressBarHelper = new ProgressBarHelper(this.context, false);
        initToolbar();
        initView();
        initPreference();
        initListener();
        switch (AppConstant.MODE_OF_PAYMENT) {
            case 1:
                this.radioGroup.setVisibility(View.GONE);
                this.payTmRb.setVisibility(View.VISIBLE);
                this.upiRb.setVisibility(View.GONE);
                this.RazorPayRb.setVisibility(View.GONE);
                this.mopSt = "PayTm";
                break;
            case 2:
                this.radioGroup.setVisibility(View.GONE);
                this.payTmRb.setVisibility(View.GONE);
                this.upiRb.setVisibility(View.VISIBLE);
                this.RazorPayRb.setVisibility(View.GONE);
                this.mopSt = "UPI";
                break;
            case 3:
                this.radioGroup.setVisibility(View.GONE);
                this.payTmRb.setVisibility(View.GONE);
                this.upiRb.setVisibility(View.GONE);
                this.RazorPayRb.setVisibility(View.VISIBLE);
                this.mopSt = "RazorPay";
                break;
            default:
                this.radioGroup.setVisibility(View.VISIBLE);
                this.payTmRb.setVisibility(View.VISIBLE);
                this.upiRb.setVisibility(View.VISIBLE);
                this.RazorPayRb.setVisibility(View.VISIBLE);
                this.mopSt = "PayTm";
                break;
        }
        this.signTv.setText(AppConstant.CURRENCY_SIGN);
        this.alertTv.setText(String.format("Minimum Add Amount is %s%d", AppConstant.CURRENCY_SIGN, Integer.valueOf(AppConstant.MIN_DEPOSIT_LIMIT)));
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Deposit");
        getSupportActionBar().setElevation(0.0f);
    }

    private void initView() {
        this.balanceTv = findViewById(R.id.balanceTv);
        this.radioGroup = findViewById(R.id.radioGroup);
        this.payTmRb = findViewById(R.id.payTmRb);
        this.upiRb = findViewById(R.id.upiRb);
        this.RazorPayRb = findViewById(R.id.RazorPayRb);
        this.amountEt = findViewById(R.id.amountEt);
        this.noteTv = findViewById(R.id.noteTv);
        this.alertTv = findViewById(R.id.alertTv);
        this.signTv = findViewById(R.id.signTv);
        this.depositTv = findViewById(R.id.depositTv);
    }

    private void initPreference() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.depositBalance = extras.getInt("deposit_amount", 0);
            this.balanceTv.setText(AppConstant.CURRENCY_SIGN + this.depositBalance);
            return;
        }
        this.balanceTv.setText(AppConstant.CURRENCY_SIGN + "0");
    }

    private void initListener() {
        this.payTmRb.setOnClickListener(new View.OnClickListener() { // from class: com.ratechnoworld.megalotto.activity.DepositActivity$$ExternalSyntheticLambda0
            @Override
            public void onClick(View view) {
                DepositActivity.this.m244x5e341606(view);
            }
        });
        this.upiRb.setOnClickListener(new View.OnClickListener() { // from class: com.ratechnoworld.megalotto.activity.DepositActivity$$ExternalSyntheticLambda1
            @Override
            public void onClick(View view) {
                DepositActivity.this.m245x784f94a5(view);
            }
        });
        this.RazorPayRb.setOnClickListener(new View.OnClickListener() { // from class: com.ratechnoworld.megalotto.activity.DepositActivity$$ExternalSyntheticLambda2
            @Override
            public void onClick(View view) {
                DepositActivity.this.m246x926b1344(view);
            }
        });
        this.depositTv.setOnClickListener(new View.OnClickListener() { // from class: com.ratechnoworld.megalotto.activity.DepositActivity$$ExternalSyntheticLambda3
            @Override
            public void onClick(View view) {
                DepositActivity.this.m247xac8691e3(view);
            }
        });
    }

    
    /* renamed from: lambda$initListener$0$com-ratechnoworld-megalotto-activity-DepositActivity  reason: not valid java name */
    public void m244x5e341606(View v) {
        this.mopSt = "PayTm";
    }

    
    /* renamed from: lambda$initListener$1$com-ratechnoworld-megalotto-activity-DepositActivity  reason: not valid java name */
    public void m245x784f94a5(View v) {
        this.mopSt = "UPI";
    }

    
    /* renamed from: lambda$initListener$2$com-ratechnoworld-megalotto-activity-DepositActivity  reason: not valid java name */
    public void m246x926b1344(View v) {
        this.mopSt = "RazorPay";
    }

    

    /* renamed from: lambda$initListener$3$com-ratechnoworld-megalotto-activity-DepositActivity  reason: not valid java name */
    public void m247xac8691e3(View v) {
        char c = 0;
        this.depositTv.setEnabled(false);
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Random rand = new Random();
        int randomNum = rand.nextInt((9999 - 1000) + 1) + 1000;
        String str = (System.currentTimeMillis() + randomNum) + Preferences.getInstance(this).getString(Preferences.KEY_USER_ID);
        this.orderIdSt = str;
        this.paymentIdSt = str;
        String obj = Objects.requireNonNull(this.amountEt.getText()).toString();
        this.amountSt = obj;
        if (!obj.isEmpty()) {
            double payout = Integer.parseInt(this.amountEt.getText().toString());
            if (payout < AppConstant.MIN_DEPOSIT_LIMIT) {
                this.depositTv.setEnabled(true);
                this.alertTv.setVisibility(View.VISIBLE);
                this.alertTv.setText(String.format("Minimum Add Amount is %s%d", AppConstant.CURRENCY_SIGN, Integer.valueOf(AppConstant.MIN_DEPOSIT_LIMIT)));
                this.alertTv.setTextColor(Color.parseColor("#ff0000"));
                return;
            } else if (payout > AppConstant.MAX_DEPOSIT_LIMIT) {
                this.depositTv.setEnabled(true);
                this.alertTv.setVisibility(View.VISIBLE);
                this.alertTv.setText(String.format("Maximum Add Amount is %s%d", AppConstant.CURRENCY_SIGN, Integer.valueOf(AppConstant.MAX_DEPOSIT_LIMIT)));
                this.alertTv.setTextColor(Color.parseColor("#ff0000"));
                return;
            } else {
                this.alertTv.setVisibility(View.GONE);
                Preferences.getInstance(this.context).setInt(Preferences.KEY_DEPOSITE_AMOUNT, Integer.parseInt(this.amountSt));
                try {
                    this.depositTv.setEnabled(false);
                    String str2 = this.mopSt;
                    switch (str2.hashCode()) {
                        case -816503921:
                            if (str2.equals("GooglePay")) {
                                c = 3;
                                break;
                            }
                            c = 65535;
                            break;
                        case 84238:
                            if (str2.equals("UPI")) {
                                c = 1;
                                break;
                            }
                            c = 65535;
                            break;
                        case 76890401:
                            if (str2.equals("PayTm")) {
                                break;
                            }
                            c = 65535;
                            break;
                        case 668813978:
                            if (str2.equals("RazorPay")) {
                                c = 4;
                                break;
                            }
                            c = 65535;
                            break;
                        case 1206754703:
                            if (str2.equals("PayTm UPI")) {
                                c = 2;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
                    switch (c) {
                        case 0:
                            getPayTmToken(this.amountSt);
                            return;
                        case 1:
                            startUPIPayment();
                            return;
                        case 2:
                            startPayTmUPIPayment();
                            return;
                        case 3:
                            startGooglePayPayment();
                            return;
                        case 4:
                            startRazorPay(this.amountSt);
                            return;
                        default:
                            return;
                    }
                } catch (NullPointerException e2) {
                    this.depositTv.setEnabled(true);
                    return;
                }
            }
        }
        this.depositTv.setEnabled(true);
        this.alertTv.setVisibility(View.VISIBLE);
        this.alertTv.setText(String.format("Minimum Add Amount is %s%d", AppConstant.CURRENCY_SIGN, Integer.valueOf(AppConstant.MIN_DEPOSIT_LIMIT)));
        this.alertTv.setTextColor(Color.parseColor("#ff0000"));
    }

    public void addDepositTransaction(String paymentID) {
        this.progressBarHelper.showProgressDialog();
//        Call<CustomerModel> call = this.api.addDepositTransaction(AppConstant.PURCHASE_KEY, Preferences.getInstance(this.context).getString(Preferences.KEY_USER_ID), paymentID, String.valueOf(Preferences.getInstance(this.context).getInt(Preferences.KEY_DEPOSITE_AMOUNT)), this.mopSt);
//        call.enqueue(new Callback<CustomerModel>() { // from class: com.ratechnoworld.megalotto.activity.DepositActivity.1
//            @Override
//            public void onResponse(Call<CustomerModel> call2, Response<CustomerModel> response) {
//                CustomerModel legalData;
//                DepositActivity.this.progressBarHelper.hideProgressDialog();
//                if (response.isSuccessful() && (legalData = response.body()) != null) {
//                    List<CustomerModel.Result> res = legalData.getResult();
//                    Function.showToast(DepositActivity.this.context, res.get(0).getMsg());
//                    DepositActivity.this.onBackPressed();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<CustomerModel> call2, Throwable t) {
//                DepositActivity.this.progressBarHelper.hideProgressDialog();
//            }
//        });
    }

    private void startRazorPay(String amount) {
        Checkout co = new Checkout();
        co.setKeyID(AppConstant.RAZORPAY_API_KEY);
        try {
            JSONObject options = new JSONObject();
            options.put("name", Preferences.getInstance(this.context).getString(Preferences.KEY_USER));
            options.put("description", "Added Using RazorPay");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("currency", "INR");
            options.put("amount", Integer.parseInt(amount) * 100);
            JSONObject preFill = new JSONObject();
            preFill.put("email", Preferences.getInstance(this.context).getString(Preferences.KEY_EMAIL));
            preFill.put("contact", Preferences.getInstance(this.context).getString(Preferences.KEY_MOBILE));
            options.put("prefill", preFill);
            co.open(this, options);
        } catch (Exception e) {
            Toast.makeText(this, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override // com.razorpay.PaymentResultListener
    public void onPaymentSuccess(String paymentID) {
        try {
            addDepositTransaction(paymentID);
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentSuccess", e);
        }
    }

    @Override // com.razorpay.PaymentResultListener
    public void onPaymentError(int i, String s) {
        try {
            Toast.makeText(getApplicationContext(), "Payment failed: " + i + " " + s, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentError", e);
        }
    }

    private void getPayTmToken(String amount) {
        Log.e(TAG, " get token start");
        if (Function.checkNetworkConnection(this)) {
            Call<Token> callToken = this.api.generateTokenCall("12345", AppConstant.PAYTM_M_ID, this.orderIdSt, amount);
            callToken.enqueue(new Callback<Token>() { // from class: com.ratechnoworld.megalotto.activity.DepositActivity.2
                @Override 
                public void onResponse(Call<Token> call, Response<Token> response) {
                    Log.e(DepositActivity.TAG, " respo " + response.isSuccessful());
                    try {
                        if (response.isSuccessful() && response.body() != null) {
                            if (!response.body().getBody().getTxnToken().equals("")) {
                                Log.e(DepositActivity.TAG, " transaction token : " + response.body().getBody().getTxnToken());
                                DepositActivity.this.startPaytmPayment(response.body().getBody().getTxnToken());
                            } else {
                                Log.e(DepositActivity.TAG, " Token status false");
                            }
                        }
                    } catch (Exception e) {
                        Log.e(DepositActivity.TAG, " error in Token Res " + e.getMessage());
                    }
                }

                @Override 
                public void onFailure(Call<Token> call, Throwable t) {
                    Log.e(DepositActivity.TAG, " response error " + t.getMessage());
                }
            });
        }
    }

    public void startPaytmPayment(String token) {
        this.tokenSt = token;
        String callBackUrl = BuildConfig.BASE_URL + "theia/paytmCallback?ORDER_ID=" + this.orderIdSt;
        Log.e(TAG, " callback URL " + callBackUrl);
        PaytmOrder paytmOrder = new PaytmOrder(this.orderIdSt, AppConstant.PAYTM_M_ID, this.tokenSt, this.amountSt, callBackUrl);
        TransactionManager transactionManager = new TransactionManager(paytmOrder, new PaytmPaymentTransactionCallback() { // from class: com.ratechnoworld.megalotto.activity.DepositActivity.3
            @Override // com.paytm.pgsdk.PaytmPaymentTransactionCallback
            public void onTransactionResponse(Bundle bundle) {
                Log.e(DepositActivity.TAG, "Response (onTransactionResponse) : " + bundle.toString());
                DepositActivity.this.orderIdSt = bundle.getString(PaytmConstants.ORDER_ID);
                String status = bundle.getString(PaytmConstants.STATUS);
                DepositActivity.this.paymentIdSt = bundle.getString(PaytmConstants.TRANSACTION_ID);
                DepositActivity.this.checksumSt = bundle.getString("CHECKSUMHASH");
                if (Objects.requireNonNull(status).equalsIgnoreCase("TXN_SUCCESS")) {
                    DepositActivity depositActivity = DepositActivity.this;
                    depositActivity.addDepositTransaction(depositActivity.paymentIdSt);
                }
            }

            @Override // com.paytm.pgsdk.PaytmPaymentTransactionCallback
            public void networkNotAvailable() {
                Log.e(DepositActivity.TAG, "network not available ");
            }

            @Override // com.paytm.pgsdk.PaytmPaymentTransactionCallback
            public void onErrorProceed(String s) {
                Log.e(DepositActivity.TAG, " onErrorProcess " + s);
            }

            @Override // com.paytm.pgsdk.PaytmPaymentTransactionCallback
            public void clientAuthenticationFailed(String s) {
                Log.e(DepositActivity.TAG, "Clientauth " + s);
            }

            @Override // com.paytm.pgsdk.PaytmPaymentTransactionCallback
            public void someUIErrorOccurred(String s) {
                Log.e(DepositActivity.TAG, " UI error " + s);
            }

            @Override // com.paytm.pgsdk.PaytmPaymentTransactionCallback
            public void onErrorLoadingWebPage(int i, String s, String s1) {
                Log.e(DepositActivity.TAG, " error loading web " + s + "--" + s1);
            }

            @Override // com.paytm.pgsdk.PaytmPaymentTransactionCallback
            public void onBackPressedCancelTransaction() {
                Log.e(DepositActivity.TAG, "backPress ");
            }

            @Override // com.paytm.pgsdk.PaytmPaymentTransactionCallback
            public void onTransactionCancel(String s, Bundle bundle) {
                Log.e(DepositActivity.TAG, " transaction cancel " + s);
            }
        });
        transactionManager.setAppInvokeEnabled(false);
        transactionManager.setShowPaymentUrl(BuildConfig.BASE_URL + "theia/api/v1/showPaymentPage");
        transactionManager.startTransaction(this, 3);
    }

    void startUPIPayment() {
        Uri uri = Uri.parse("upi://pay").buildUpon().appendQueryParameter("pa", AppConstant.UPI_PA).appendQueryParameter("pn", AppConstant.UPI_PN).appendQueryParameter("mc", AppConstant.UPI_MC).appendQueryParameter("tn", AppConstant.UPI_TN).appendQueryParameter("tr", String.valueOf(System.currentTimeMillis())).appendQueryParameter("am", this.amountSt).appendQueryParameter("cu", AppConstant.UPI_CU).appendQueryParameter("refUrl", getString(R.string.app_name)).build();
        Intent upiPayIntent = new Intent("android.intent.action.VIEW");
        upiPayIntent.setData(uri);
        Intent intent = Intent.createChooser(upiPayIntent, "Pay with");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 0);
        } else {
            Toast.makeText(this, "UPI app not found, please install one to continue", Toast.LENGTH_SHORT).show();
        }
    }

    void startGooglePayPayment() {
        Uri uri = Uri.parse("upi://pay").buildUpon().appendQueryParameter("pa", AppConstant.UPI_PA).appendQueryParameter("pn", AppConstant.UPI_PN).appendQueryParameter("mc", AppConstant.UPI_MC).appendQueryParameter("tn", AppConstant.UPI_TN).appendQueryParameter("tr", String.valueOf(System.currentTimeMillis())).appendQueryParameter("am", this.amountSt).appendQueryParameter("cu", AppConstant.UPI_CU).appendQueryParameter("refUrl", getString(R.string.app_name)).build();
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(uri);
        intent.setPackage("com.google.android.apps.nbu.paisa.user");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 1);
        } else {
            Toast.makeText(this, "GooglePay app not found, please install one to continue", Toast.LENGTH_SHORT).show();
        }
    }

    void startPayTmUPIPayment() {
        Uri uri = Uri.parse("upi://pay").buildUpon().appendQueryParameter("pa", AppConstant.UPI_PA).appendQueryParameter("pn", AppConstant.UPI_PN).appendQueryParameter("mc", AppConstant.UPI_MC).appendQueryParameter("tn", AppConstant.UPI_TN).appendQueryParameter("tr", String.valueOf(System.currentTimeMillis())).appendQueryParameter("am", this.amountSt).appendQueryParameter("cu", AppConstant.UPI_CU).appendQueryParameter("refUrl", getString(R.string.app_name)).build();
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(uri);
        intent.setPackage(TransactionManager.PAYTM_APP_PACKAGE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 2);
        } else {
            Toast.makeText(this, "PAYTM app not found, please install one to continue", Toast.LENGTH_SHORT).show();
        }
    }

    private void upiPaymentDataOperation(ArrayList<String> data) {
        if (isConnectionAvailable(this)) {
            String str = data.get(0);
            Log.d("UPIPAY", "upiPaymentDataOperation: " + str);
            String paymentCancel = "";
            if (str == null) {
                str = "discard";
            }
            String status = "";
            String approvalRefNo = "";
            String[] response = str.split("&");
            for (String s : response) {
                String[] equalStr = s.split("=");
                if (equalStr.length >= 2) {
                    if (equalStr[0].equalsIgnoreCase("Status")) {
                        status = equalStr[1].toLowerCase();
                    } else if (equalStr[0].equalsIgnoreCase("ApprovalRefNo") || equalStr[0].equalsIgnoreCase("txnRef")) {
                        approvalRefNo = equalStr[1];
                    }
                } else {
                    paymentCancel = "Payment cancelled by user.";
                }
            }
            if (status.equals("success")) {
                Log.d("UPI", "responseStr: " + approvalRefNo);
                try {
                    addDepositTransaction(approvalRefNo);
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            } else if ("Payment cancelled by user.".equals(paymentCancel)) {
                Toast.makeText(this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
                return;
            } else {
                Toast.makeText(this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        Toast.makeText(this, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
    }

    public static boolean isConnectionAvailable(Context context) {
        NetworkInfo netInfo;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        return connectivityManager != null && (netInfo = connectivityManager.getActiveNetworkInfo()) != null && netInfo.isConnected() && netInfo.isConnectedOrConnecting() && netInfo.isAvailable();
    }


    @Override

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        String str = TAG;
        Log.e(str, " result code " + resultCode);
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
                if (-1 == resultCode) {
                    if (data != null) {
                        String trxt = data.getStringExtra("response");
                        Log.d("UPI", "onActivityResult: " + trxt);
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add(trxt);
                        upiPaymentDataOperation(dataList);
                        return;
                    }
                    Log.d("UPI", "onActivityResult: Return data is null");
                    ArrayList<String> dataList2 = new ArrayList<>();
                    dataList2.add("nothing");
                    upiPaymentDataOperation(dataList2);
                    return;
                }
                Log.d("UPI", "onActivityResult: Return data is null");
                ArrayList<String> dataList3 = new ArrayList<>();
                dataList3.add("nothing");
                upiPaymentDataOperation(dataList3);
                return;
            case 1:
                if (-1 == resultCode) {
                    if (data != null) {
                        String trxt2 = data.getStringExtra("response");
                        Log.d("GOOGLEPAY", "onActivityResult: " + trxt2);
                        ArrayList<String> dataList4 = new ArrayList<>();
                        dataList4.add(trxt2);
                        upiPaymentDataOperation(dataList4);
                        return;
                    }
                    Log.d("GOOGLEPAY", "onActivityResult: Return data is null");
                    ArrayList<String> dataList5 = new ArrayList<>();
                    dataList5.add("nothing");
                    upiPaymentDataOperation(dataList5);
                    return;
                }
                Log.d("GOOGLEPAY", "onActivityResult: Return data is null");
                ArrayList<String> dataList6 = new ArrayList<>();
                dataList6.add("nothing");
                upiPaymentDataOperation(dataList6);
                return;
            case 2:
                if (-1 == resultCode) {
                    if (data != null) {
                        String trxt3 = data.getStringExtra("response");
                        Log.d("PAYTM UPI", "onActivityResult: " + trxt3);
                        ArrayList<String> dataList7 = new ArrayList<>();
                        dataList7.add(trxt3);
                        upiPaymentDataOperation(dataList7);
                        return;
                    }
                    Log.d("PAYTM UPI", "onActivityResult: Return data is null");
                    ArrayList<String> dataList8 = new ArrayList<>();
                    dataList8.add("nothing");
                    upiPaymentDataOperation(dataList8);
                    return;
                }
                Log.d("PAYTM UPI", "onActivityResult: Return data is null");
                ArrayList<String> dataList9 = new ArrayList<>();
                dataList9.add("nothing");
                upiPaymentDataOperation(dataList9);
                return;
            case 3:
                if (data != null) {
                    Bundle bundle = data.getExtras();
                    if (bundle != null) {
                        for (String key : bundle.keySet()) {
                            Log.e(TAG, key + " : " + (bundle.get(key) != null ? bundle.get(key) : "NULL"));
                        }
                    }
                    try {
                        JSONObject jsonObject = new JSONObject(Objects.requireNonNull(data.getStringExtra("response")));
                        String status = jsonObject.getString(PaytmConstants.STATUS);
                        if (status.equalsIgnoreCase("TXN_SUCCESS")) {
                            this.paymentIdSt = jsonObject.getString(PaytmConstants.TRANSACTION_ID);
                            this.checksumSt = jsonObject.getString("CHECKSUMHASH");
                            this.orderIdSt = jsonObject.getString(PaytmConstants.ORDER_ID);
                            addDepositTransaction(this.paymentIdSt);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String str2 = TAG;
                    Log.e(str2, " TXNID " + this.paymentIdSt);
                    Log.e(str2, " CHECKSUMHASH " + this.checksumSt);
                    Log.e(str2, " ORDERID " + this.orderIdSt);
                    Log.e(str2, " data " + data.getStringExtra("nativeSdkForMerchantMessage"));
                    Log.e(str2, " data response - " + data.getStringExtra("response"));
                    return;
                }
                Log.e(str, " payment failed");
                return;
            default:
        }
    }
}
