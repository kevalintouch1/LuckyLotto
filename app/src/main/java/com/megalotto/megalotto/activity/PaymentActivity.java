package com.megalotto.megalotto.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.megalotto.megalotto.MyApplication;
import com.megalotto.megalotto.R;
import com.megalotto.megalotto.api.ApiCalling;
import com.megalotto.megalotto.helper.AppConstant;
import com.megalotto.megalotto.helper.Function;
import com.megalotto.megalotto.helper.Preferences;
import com.megalotto.megalotto.helper.ProgressBarHelper;
import com.megalotto.megalotto.model.CustomerModel;
import com.megalotto.megalotto.utils.GenerateRandomString;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PaymentActivity extends AppCompatActivity {
    TextView amountTxt;
    ApiCalling api;
    int bonusAmount;
    int bonusApplied;
    TextView bonusTxt;
    TextView buyBtn;
    Context context;
    int currentAmount;
    String entryFees;
    String feedId;
    TextView noteTxt;
    TextView payTxt;
    int payableAmount;
    ProgressBarHelper progressBarHelper;
    TextView ticketNoTxt;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        this.context = this;
        this.api = MyApplication.getRetrofit().create(ApiCalling.class);
        this.progressBarHelper = new ProgressBarHelper(this.context, false);
        initToolbar();
        initView();
        initPreference();
        initListener();
        if (AppConstant.TICKET_BONUS_USED == 0) {
            this.noteTxt.setText("No bonus amount will be used to purchase this ticket.");
        } else {
            this.noteTxt.setText("Only " + AppConstant.TICKET_BONUS_USED + "% of your bonus amount will be used to purchase this ticket.");
        }
        if (Function.checkNetworkConnection(this.context)) {
            this.ticketNoTxt.setText(GenerateRandomString.randomString(10));
            getUserWallet();
        }
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Payment");
        getSupportActionBar().setElevation(0.0f);
    }

    private void initView() {
        this.ticketNoTxt = findViewById(R.id.ticketNoTxt);
        this.amountTxt = findViewById(R.id.amountTxt);
        this.bonusTxt = findViewById(R.id.bonusTxt);
        this.payTxt = findViewById(R.id.payTxt);
        this.noteTxt = findViewById(R.id.noteTxt);
        this.buyBtn = findViewById(R.id.buyBtn);
    }

    private void initPreference() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.feedId = extras.getString("FEES_ID");
            this.entryFees = extras.getString("ENTREE_FEES");
        }
    }

    private void initListener() {
        this.buyBtn.setOnClickListener(new View.OnClickListener() { // from class: com.ratechnoworld.megalotto.activity.PaymentActivity$$ExternalSyntheticLambda0
            @Override
            public void onClick(View view) {
                PaymentActivity.this.m260x12b496e(view);
            }
        });
    }

    
    /* renamed from: lambda$initListener$0$com-ratechnoworld-megalotto-activity-PaymentActivity  reason: not valid java name */
    public void m260x12b496e(View v) {
        buyTicket();
    }

    public void buyTicket() {
        if (this.currentAmount >= this.payableAmount) {
            addTicket();
        } else {
            Toast.makeText(this.context, "You don't have enough deposit balance to buy this ticket, please add balance in your wallet.", Toast.LENGTH_SHORT).show();
        }
    }

    private void addTicket() {
        this.progressBarHelper.showProgressDialog();
        Call<CustomerModel> call = this.api.addTicket(AppConstant.PURCHASE_KEY, Preferences.getInstance(this.context).getString(Preferences.KEY_USER_ID),
                Preferences.getInstance(this.context).getString(Preferences.KEY_USER_NAME),
                Preferences.getInstance(this.context).getString(Preferences.KEY_CONTST_ID), this.feedId, this.entryFees, this.ticketNoTxt.getText().toString());
        call.enqueue(new Callback<CustomerModel>() { // from class: com.ratechnoworld.megalotto.activity.PaymentActivity.1
            @Override 
            public void onResponse(Call<CustomerModel> call2, Response<CustomerModel> response) {
//                CustomerModel legalData;
//                PaymentActivity.this.progressBarHelper.hideProgressDialog();
//                if (response.isSuccessful() && (legalData = response.body()) != null) {
//                    List<CustomerModel.Result> res = legalData.getResult();
//                    if (res.get(0).getSuccess() == 1) {
//                        Function.showToast(PaymentActivity.this.context, res.get(0).getMsg());
//                        Function.fireIntent(PaymentActivity.this.context, MyTicketActivity.class);
//                        return;
//                    }
//                    Function.showToast(PaymentActivity.this.context, res.get(0).getMsg());
//                }
            }

            @Override 
            public void onFailure(Call<CustomerModel> call2, Throwable t) {
                PaymentActivity.this.progressBarHelper.hideProgressDialog();
            }
        });
    }

    private void getUserWallet() {
        this.progressBarHelper.showProgressDialog();
        Call<CustomerModel> call = this.api.getUserWallet(AppConstant.PURCHASE_KEY, Preferences.getInstance(this.context).getString(Preferences.KEY_USER_ID));
        call.enqueue(new Callback<CustomerModel>() { // from class: com.ratechnoworld.megalotto.activity.PaymentActivity.2
            @Override 
            public void onResponse(Call<CustomerModel> call2, Response<CustomerModel> response) {
                CustomerModel legalData;
                PaymentActivity.this.progressBarHelper.hideProgressDialog();
                if (response.isSuccessful() && (legalData = response.body()) != null) {
//                    List<CustomerModel.Result> res = legalData.getResult();
//                    if (res.get(0).getSuccess() == 1) {
//                        try {
//                            PaymentActivity.this.currentAmount = Integer.parseInt(res.get(0).getCur_balance()) + Integer.parseInt(res.get(0).getWon_balance());
//                            PaymentActivity.this.bonusAmount = Integer.parseInt(res.get(0).getBonus_balance());
//                            PaymentActivity.this.bonusApplied = Math.round((AppConstant.TICKET_BONUS_USED * Float.parseFloat(Preferences.getInstance(PaymentActivity.this.context).getString(Preferences.KEY_PRICE))) / 100.0f);
//                        } catch (NumberFormatException e) {
//                            PaymentActivity.this.currentAmount = 0;
//                            PaymentActivity.this.bonusAmount = 0;
//                            PaymentActivity.this.bonusApplied = 0;
//                        }
//                        if (res.get(0).getStatus() != 1 || res.get(0).getIs_block() != 0) {
//                            Preferences.getInstance(PaymentActivity.this).setlogout();
//                        }
//                        if (PaymentActivity.this.bonusAmount >= PaymentActivity.this.bonusApplied) {
//                            PaymentActivity paymentActivity = PaymentActivity.this;
//                            paymentActivity.payableAmount = Integer.parseInt(paymentActivity.entryFees) - PaymentActivity.this.bonusApplied;
//                            PaymentActivity.this.bonusTxt.setText(String.format("- %s", AppConstant.CURRENCY_SIGN + "" + PaymentActivity.this.bonusApplied));
//                        } else {
//                            PaymentActivity paymentActivity2 = PaymentActivity.this;
//                            paymentActivity2.payableAmount = Integer.parseInt(paymentActivity2.entryFees) - PaymentActivity.this.bonusAmount;
//                            PaymentActivity.this.bonusTxt.setText(String.format("- %s", AppConstant.CURRENCY_SIGN + "" + PaymentActivity.this.bonusAmount));
//                        }
//                        PaymentActivity.this.amountTxt.setText(String.format("%s%s", AppConstant.CURRENCY_SIGN, Preferences.getInstance(PaymentActivity.this.context).getString(Preferences.KEY_PRICE)));
//                        PaymentActivity.this.payTxt.setText(String.format("%s%d", AppConstant.CURRENCY_SIGN, Integer.valueOf(PaymentActivity.this.payableAmount)));
//                    }
                }
            }

            @Override 
            public void onFailure(Call<CustomerModel> call2, Throwable t) {
                PaymentActivity.this.progressBarHelper.hideProgressDialog();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
