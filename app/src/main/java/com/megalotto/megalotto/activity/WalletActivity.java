package com.megalotto.megalotto.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.megalotto.megalotto.MyApplication;
import com.megalotto.megalotto.R;
import com.megalotto.megalotto.adapter.TransactionAdapter;
import com.megalotto.megalotto.api.ApiCalling;
import com.megalotto.megalotto.helper.AppConstant;
import com.megalotto.megalotto.helper.Function;
import com.megalotto.megalotto.helper.Preferences;
import com.megalotto.megalotto.helper.ProgressBarHelper;
import com.megalotto.megalotto.model.Contest;
import com.megalotto.megalotto.model.CustomerModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WalletActivity extends AppCompatActivity {
    private int amount;
    private ApiCalling api;
    private int bonus;
    TextView bonusTxt;
    LinearLayout bottomLyt;
    private Contest contest;
    private TransactionAdapter contestAdapter;
    private Context context;
    private int deposit;
    TextView depositTxt;
    private final List<Contest> legalDataFinal = new ArrayList<>();
    LinearLayout noDataLyt;
    private ProgressBarHelper progressBarHelper;
    RecyclerView recyclerView;
    TextView totalTxt;
    TextView transactionTxt;
    TextView winningTxt;
    TextView withdrawTxt;
    private int won;

    
    @Override 
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        this.context = this;
        this.api = MyApplication.getRetrofit().create(ApiCalling.class);
        this.progressBarHelper = new ProgressBarHelper(this.context, false);
        initToolbar();
        initView();
        initListener();
        if (AppConstant.WALLET_MODE == 0) {
            this.withdrawTxt.setVisibility(View.VISIBLE);
        } else {
            this.withdrawTxt.setVisibility(View.GONE);
        }
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Wallet");
        getSupportActionBar().setElevation(0.0f);
    }

    private void initView() {
        this.winningTxt = findViewById(R.id.winningTxt);
        this.bonusTxt = findViewById(R.id.bonusTxt);
        this.totalTxt = findViewById(R.id.totalTxt);
        this.withdrawTxt = findViewById(R.id.withdrawTxt);
        this.depositTxt = findViewById(R.id.depositTxt);
        this.transactionTxt = findViewById(R.id.transactionTxt);
        this.bottomLyt = findViewById(R.id.bottomLyt);
        this.noDataLyt = findViewById(R.id.noDataLyt);
        this.recyclerView = findViewById(R.id.recyclerView);
    }

    private void initListener() {
        this.withdrawTxt.setOnClickListener(new View.OnClickListener() { // from class: com.ratechnoworld.megalotto.activity.WalletActivity$$ExternalSyntheticLambda1
            @Override 
            public void onClick(View view) {
                WalletActivity.this.m291xed6350cf(view);
            }
        });
        this.depositTxt.setOnClickListener(new View.OnClickListener() { // from class: com.ratechnoworld.megalotto.activity.WalletActivity$$ExternalSyntheticLambda2
            @Override 
            public void onClick(View view) {
                WalletActivity.this.m292x7a9e0250(view);
            }
        });
    }

    
    /* renamed from: lambda$initListener$0$com-ratechnoworld-megalotto-activity-WalletActivity  reason: not valid java name */
    public void m291xed6350cf(View v) {
        withdrawBalance();
    }

    
    /* renamed from: lambda$initListener$1$com-ratechnoworld-megalotto-activity-WalletActivity  reason: not valid java name */
    public void m292x7a9e0250(View v) {
        depositBalance();
    }

    private void getUserWallet() {
        this.progressBarHelper.showProgressDialog();
        Call<CustomerModel> call = this.api.getUserWallet(AppConstant.PURCHASE_KEY, Preferences.getInstance(this.context).getString(Preferences.KEY_USER_ID));
        call.enqueue(new Callback<CustomerModel>() { // from class: com.ratechnoworld.megalotto.activity.WalletActivity.1
            @Override 
            public void onResponse(Call<CustomerModel> call2, Response<CustomerModel> response) {
                CustomerModel legalData;
                WalletActivity.this.progressBarHelper.hideProgressDialog();
                if (response.isSuccessful() && (legalData = response.body()) != null) {
//                    List<CustomerModel.Result> res = legalData.getResult();
//                    if (res.get(0).getSuccess() == 1) {
//                        try {
//                            WalletActivity.this.deposit = Integer.parseInt(res.get(0).getCur_balance());
//                            WalletActivity.this.won = Integer.parseInt(res.get(0).getWon_balance());
//                            WalletActivity.this.bonus = Integer.parseInt(res.get(0).getBonus_balance());
//                            WalletActivity walletActivity = WalletActivity.this;
//                            walletActivity.amount = walletActivity.deposit + WalletActivity.this.won;
//                        } catch (NumberFormatException e) {
//                            WalletActivity.this.deposit = 0;
//                            WalletActivity.this.won = 0;
//                            WalletActivity.this.bonus = 0;
//                            WalletActivity.this.amount = 0;
//                        }
//                        WalletActivity.this.totalTxt.setText(AppConstant.CURRENCY_SIGN + "" + WalletActivity.this.amount);
//                        WalletActivity.this.winningTxt.setText(String.format("Won: %s", AppConstant.CURRENCY_SIGN + "" + WalletActivity.this.won));
//                        WalletActivity.this.bonusTxt.setText(String.format("Bonus: %s", AppConstant.CURRENCY_SIGN + "" + WalletActivity.this.bonus));
//                        if (res.get(0).getStatus() != 1 || res.get(0).getIs_block() != 0) {
//                            Preferences.getInstance(WalletActivity.this).setlogout();
//                        }
//                    }
                }
            }

            @Override 
            public void onFailure(Call<CustomerModel> call2, Throwable t) {
                WalletActivity.this.progressBarHelper.hideProgressDialog();
            }
        });
    }

    private void getTransactions() {
        this.progressBarHelper.showProgressDialog();
        Call<List<Contest>> call = this.api.getTransactions(AppConstant.PURCHASE_KEY, Preferences.getInstance(this.context).getString(Preferences.KEY_USER_ID));
        call.enqueue(new Callback<List<Contest>>() { // from class: com.ratechnoworld.megalotto.activity.WalletActivity.2
            @Override 
            public void onResponse(Call<List<Contest>> call2, Response<List<Contest>> response) {
                List<Contest> legalData;
                WalletActivity.this.progressBarHelper.hideProgressDialog();
                if (response.isSuccessful() && (legalData = response.body()) != null) {
                    if (legalData.size() > 0) {
                        WalletActivity.this.legalDataFinal.clear();
                        for (Contest contestfinal : legalData) {
                            WalletActivity.this.contest = new Contest();
                            WalletActivity.this.contest.setDate(contestfinal.getDate());
                            WalletActivity.this.contest.setReq_amount(contestfinal.getReq_amount());
                            WalletActivity.this.contest.setOrder_id(contestfinal.getOrder_id());
                            WalletActivity.this.contest.setRemark(contestfinal.getRemark());
                            WalletActivity.this.contest.setStatus(contestfinal.getStatus());
                            WalletActivity.this.legalDataFinal.add(WalletActivity.this.contest);
                        }
                        WalletActivity.this.transactionTxt.setVisibility(View.VISIBLE);
                        WalletActivity.this.recyclerView.setVisibility(View.VISIBLE);
                        WalletActivity.this.noDataLyt.setVisibility(View.GONE);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(WalletActivity.this.context);
                        WalletActivity.this.recyclerView.setLayoutManager(linearLayoutManager);
                        WalletActivity.this.contestAdapter = new TransactionAdapter(WalletActivity.this.context, WalletActivity.this.legalDataFinal);
                        WalletActivity.this.contestAdapter.notifyDataSetChanged();
                        WalletActivity.this.recyclerView.setAdapter(WalletActivity.this.contestAdapter);
                        return;
                    }
                    WalletActivity.this.transactionTxt.setVisibility(View.GONE);
                    WalletActivity.this.recyclerView.setVisibility(View.GONE);
                    WalletActivity.this.noDataLyt.setVisibility(View.VISIBLE);
                }
            }

            @Override 
            public void onFailure(Call<List<Contest>> call2, Throwable t) {
                WalletActivity.this.progressBarHelper.hideProgressDialog();
                WalletActivity.this.transactionTxt.setVisibility(View.GONE);
                WalletActivity.this.recyclerView.setVisibility(View.GONE);
                WalletActivity.this.noDataLyt.setVisibility(View.VISIBLE);
            }
        });
    }

    private AlertDialog alertDialog() {
        return new AlertDialog.Builder(this).setTitle("Withdraw limit").setMessage("Minimum withdraw amount is limited to " + AppConstant.CURRENCY_SIGN + "" + AppConstant.MIN_WITHDRAW_LIMIT).setNegativeButton("Close", new DialogInterface.OnClickListener() { // from class: com.ratechnoworld.megalotto.activity.WalletActivity$$ExternalSyntheticLambda0
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).create();
    }

    public void withdrawBalance() {
        try {
            if (this.amount < AppConstant.MIN_WITHDRAW_LIMIT) {
                alertDialog().show();
            } else {
                Intent intent = new Intent(this.context, WithdrawalActivity.class);
                intent.putExtra("won_amount", this.won);
                Function.fireIntentWithData(this.context, intent);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public void depositBalance() {
        try {
            Intent intent = new Intent(this.context, DepositActivity.class);
            intent.putExtra("deposit_amount", this.deposit);
            Function.fireIntentWithData(this.context, intent);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        if (Function.checkNetworkConnection(this.context)) {
            getUserWallet();
            getTransactions();
        }
    }
}
