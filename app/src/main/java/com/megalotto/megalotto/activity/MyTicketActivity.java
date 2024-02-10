package com.megalotto.megalotto.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.megalotto.megalotto.MyApplication;
import com.megalotto.megalotto.R;
import com.megalotto.megalotto.adapter.MyTicketAdapter;
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


public class MyTicketActivity extends AppCompatActivity {
    private ApiCalling api;
    private MyTicketAdapter contestAdapter;
    private Context context;
    LinearLayout noDataLyt;
    private ProgressBarHelper progressBarHelper;
    public RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ticket);
        this.context = this;
        this.api = MyApplication.getRetrofit().create(ApiCalling.class);
        this.progressBarHelper = new ProgressBarHelper(this.context, false);
        initToolbar();
        initView();
        if (Function.checkNetworkConnection(this.context)) {
            getMyTicket();
        }
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My Tickets");
        getSupportActionBar().setElevation(0.0f);
    }

    private void initView() {
        this.recyclerView = findViewById(R.id.recyclerView);
        this.noDataLyt = findViewById(R.id.noDataLyt);
    }

    private void getMyTicket() {
        this.progressBarHelper.showProgressDialog();
        Call<List<Contest>> call = this.api.getMyTicket(AppConstant.PURCHASE_KEY, Preferences.getInstance(this.context).getString(Preferences.KEY_USER_ID), Preferences.getInstance(this.context).getString(Preferences.KEY_CONTST_ID));
        call.enqueue(new Callback<List<Contest>>() { // from class: com.ratechnoworld.megalotto.activity.MyTicketActivity.1
            @Override 
            public void onResponse(Call<List<Contest>> call2, Response<List<Contest>> response) {
                MyTicketActivity.this.progressBarHelper.hideProgressDialog();
                if (response.isSuccessful()) {
                    List<Contest> legalData = response.body();
                    if (legalData != null) {
                        if (legalData.size() > 0) {
                            MyTicketActivity.this.recyclerView.setVisibility(View.VISIBLE);
                            MyTicketActivity.this.noDataLyt.setVisibility(View.GONE);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyTicketActivity.this.context);
                            MyTicketActivity.this.recyclerView.setLayoutManager(linearLayoutManager);
                            MyTicketActivity.this.contestAdapter = new MyTicketAdapter(MyTicketActivity.this.context, legalData);
                            MyTicketActivity.this.contestAdapter.notifyDataSetChanged();
                            MyTicketActivity.this.recyclerView.setAdapter(MyTicketActivity.this.contestAdapter);
                            return;
                        }
                        MyTicketActivity.this.recyclerView.setVisibility(View.GONE);
                        MyTicketActivity.this.noDataLyt.setVisibility(View.VISIBLE);
                        return;
                    }
                    MyTicketActivity.this.recyclerView.setVisibility(View.GONE);
                    MyTicketActivity.this.noDataLyt.setVisibility(View.VISIBLE);
                    return;
                }
                MyTicketActivity.this.recyclerView.setVisibility(View.GONE);
                MyTicketActivity.this.noDataLyt.setVisibility(View.VISIBLE);
            }

            @Override 
            public void onFailure(Call<List<Contest>> call2, Throwable t) {
                MyTicketActivity.this.progressBarHelper.hideProgressDialog();
                MyTicketActivity.this.recyclerView.setVisibility(View.GONE);
                MyTicketActivity.this.noDataLyt.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        Function.fireIntent(this.context, MainActivity.class);
    }
}
