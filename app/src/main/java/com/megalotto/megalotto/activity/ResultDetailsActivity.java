package com.megalotto.megalotto.activity;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.megalotto.megalotto.MyApplication;
import com.megalotto.megalotto.R;
import com.megalotto.megalotto.adapter.TopWinnerAdapter;
import com.megalotto.megalotto.api.ApiCalling;
import com.megalotto.megalotto.helper.AppConstant;
import com.megalotto.megalotto.helper.Function;
import com.megalotto.megalotto.helper.Preferences;
import com.megalotto.megalotto.helper.ProgressBarHelper;
import com.megalotto.megalotto.model.Contest;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ResultDetailsActivity extends AppCompatActivity {
    private ApiCalling api;
    TopWinnerAdapter contestAdapter;
    private Context context;
    TextView feeSignTxt;
    TextView feeTxt;
    private String feesId;
    TextView prizeSignTxt;
    TextView prizeTxt;
    private ProgressBarHelper progressBarHelper;
    RecyclerView recyclerView;
    TextView statusTxt;
    TextView ticketNoTxt;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_details);
        this.context = this;
        this.api = MyApplication.getRetrofit().create(ApiCalling.class);
        this.progressBarHelper = new ProgressBarHelper(this.context, false);
        initToolbar();
        initView();
        initPreference();
        this.prizeSignTxt.setText(AppConstant.CURRENCY_SIGN);
        this.feeSignTxt.setText(AppConstant.CURRENCY_SIGN);
        if (Function.checkNetworkConnection(this.context)) {
            getTopWinners();
        }
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Result Details");
        getSupportActionBar().setElevation(0.0f);
    }

    private void initView() {
        this.statusTxt = findViewById(R.id.statusTxt);
        this.feeSignTxt = findViewById(R.id.feeSignTxt);
        this.feeTxt = findViewById(R.id.feeTxt);
        this.prizeSignTxt = findViewById(R.id.prizeSignTxt);
        this.prizeTxt = findViewById(R.id.prizeTxt);
        this.ticketNoTxt = findViewById(R.id.ticketNoTxt);
        this.recyclerView = findViewById(R.id.recyclerView);
    }

    private void initPreference() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.prizeTxt.setText(extras.getString("winPrice"));
            this.feeTxt.setText(extras.getString("entryFee"));
            this.feesId = extras.getString("fees_id");
            this.ticketNoTxt.setText(extras.getString("ticket_no"));
            if (Objects.equals(extras.getString("winPrice"), "0")) {
                this.statusTxt.setText("YOU LOSS");
            } else {
                this.statusTxt.setText("YOU WON");
            }
        }
    }

    private void getTopWinners() {
        this.progressBarHelper.showProgressDialog();
        Call<List<Contest>> call = this.api.getTopWinners(AppConstant.PURCHASE_KEY, Preferences.getInstance(this.context).getString(Preferences.KEY_CONSTANT_ID), this.feesId);
        call.enqueue(new Callback<List<Contest>>() {
            @Override
            public void onResponse(Call<List<Contest>> call2, Response<List<Contest>> response) {
                List<Contest> legalData;
                ResultDetailsActivity.this.progressBarHelper.hideProgressDialog();
                if (response.isSuccessful() && (legalData = response.body()) != null) {
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ResultDetailsActivity.this.context);
                    ResultDetailsActivity.this.recyclerView.setLayoutManager(linearLayoutManager);
                    ResultDetailsActivity.this.contestAdapter = new TopWinnerAdapter(ResultDetailsActivity.this.context, legalData);
                    ResultDetailsActivity.this.contestAdapter.notifyDataSetChanged();
                    ResultDetailsActivity.this.recyclerView.setAdapter(ResultDetailsActivity.this.contestAdapter);
                }
            }

            @Override 
            public void onFailure(Call<List<Contest>> call2, Throwable t) {
                ResultDetailsActivity.this.progressBarHelper.hideProgressDialog();
            }
        });
    }
}
