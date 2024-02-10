package com.megalotto.megalotto.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.megalotto.megalotto.MyApplication;
import com.megalotto.megalotto.R;
import com.megalotto.megalotto.adapter.MyResultAdapter;
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


public class ResultsActivity extends AppCompatActivity {
    private ApiCalling api;
    private MyResultAdapter contestAdapter;
    private Context context;
    private ProgressBarHelper progressBarHelper;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        this.context = this;
        this.api = MyApplication.getRetrofit().create(ApiCalling.class);
        this.progressBarHelper = new ProgressBarHelper(this.context, false);
        initToolbar();
        initView();
        if (Function.checkNetworkConnection(this.context)) {
            getMyResults();
        }
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My Results");
        getSupportActionBar().setElevation(0.0f);
    }

    private void initView() {
        this.recyclerView = findViewById(R.id.recyclerView);
    }

    private void getMyResults() {
        this.progressBarHelper.showProgressDialog();
        Call<List<Contest>> call = this.api.getMyResults(AppConstant.PURCHASE_KEY, Preferences.getInstance(this.context).getString(Preferences.KEY_USER_ID), Preferences.getInstance(this.context).getString(Preferences.KEY_CONSTANT_ID));
        call.enqueue(new Callback<List<Contest>>() {
            @Override
            public void onResponse(Call<List<Contest>> call2, Response<List<Contest>> response) {
                List<Contest> legalData;
                ResultsActivity.this.progressBarHelper.hideProgressDialog();
                if (response.isSuccessful() && (legalData = response.body()) != null) {
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ResultsActivity.this.context);
                    ResultsActivity.this.recyclerView.setLayoutManager(linearLayoutManager);
                    ResultsActivity.this.contestAdapter = new MyResultAdapter(ResultsActivity.this.context, legalData);
                    ResultsActivity.this.contestAdapter.notifyDataSetChanged();
                    ResultsActivity.this.recyclerView.setAdapter(ResultsActivity.this.contestAdapter);
                }
            }

            @Override 
            public void onFailure(Call<List<Contest>> call2, Throwable t) {
                ResultsActivity.this.progressBarHelper.hideProgressDialog();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
