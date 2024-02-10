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
import com.megalotto.megalotto.adapter.NotificationAdapter;
import com.megalotto.megalotto.api.ApiCalling;
import com.megalotto.megalotto.helper.AppConstant;
import com.megalotto.megalotto.helper.Function;
import com.megalotto.megalotto.helper.ProgressBarHelper;
import com.megalotto.megalotto.model.NotificationModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NotificationActivity extends AppCompatActivity {
    private ApiCalling api;
    private Context context;
    private NotificationAdapter dataAdapter;
    private final List<NotificationModel> dataArrayList = new ArrayList();
    LinearLayout noDataLyt;
    private NotificationModel notificationModel;
    private ProgressBarHelper progressBarHelper;
    RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        this.context = this;
        this.api = MyApplication.getRetrofit().create(ApiCalling.class);
        this.progressBarHelper = new ProgressBarHelper(this.context, false);
        initToolbar();
        initView();
        if (Function.checkNetworkConnection(this.context)) {
            getNotification();
        }
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Notifications");
        getSupportActionBar().setElevation(0.0f);
    }

    private void initView() {
        this.recyclerView = findViewById(R.id.recyclerView);
        this.noDataLyt = findViewById(R.id.noDataLyt);
    }

    private void getNotification() {
        this.progressBarHelper.showProgressDialog();
        Call<List<NotificationModel>> call = this.api.getNotification(AppConstant.PURCHASE_KEY);
        call.enqueue(new Callback<List<NotificationModel>>() { // from class: com.ratechnoworld.megalotto.activity.NotificationActivity.1
            @Override 
            public void onResponse(Call<List<NotificationModel>> call2, Response<List<NotificationModel>> response) {
                NotificationActivity.this.progressBarHelper.hideProgressDialog();
                if (response.isSuccessful()) {
                    List<NotificationModel> legalData = response.body();
                    if (legalData != null && legalData.size() > 0) {
                        for (NotificationModel notificationModel1 : legalData) {
                            NotificationActivity.this.notificationModel = new NotificationModel();
                            NotificationActivity.this.dataArrayList.add(NotificationActivity.this.notificationModel);
                        }
                        NotificationActivity.this.recyclerView.setVisibility(View.VISIBLE);
                        NotificationActivity.this.noDataLyt.setVisibility(View.GONE);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(NotificationActivity.this.context);
                        NotificationActivity.this.dataAdapter = new NotificationAdapter(NotificationActivity.this.getApplicationContext(), NotificationActivity.this.dataArrayList);
                        NotificationActivity.this.dataAdapter.notifyDataSetChanged();
                        NotificationActivity.this.recyclerView.setLayoutManager(linearLayoutManager);
                        NotificationActivity.this.recyclerView.setAdapter(NotificationActivity.this.dataAdapter);
                        return;
                    }
                    NotificationActivity.this.recyclerView.setVisibility(View.GONE);
                    NotificationActivity.this.noDataLyt.setVisibility(View.VISIBLE);
                    return;
                }
                NotificationActivity.this.recyclerView.setVisibility(View.GONE);
                NotificationActivity.this.noDataLyt.setVisibility(View.VISIBLE);
            }

            @Override 
            public void onFailure(Call<List<NotificationModel>> call2, Throwable t) {
                NotificationActivity.this.progressBarHelper.hideProgressDialog();
                NotificationActivity.this.recyclerView.setVisibility(View.GONE);
                NotificationActivity.this.noDataLyt.setVisibility(View.VISIBLE);
            }
        });
    }

    Date d1;
    String ans;
    public String findDifference(String start_date, String end_date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            d1 = sdf.parse(start_date);
        } catch (ParseException e) {
        }
        try {
            Date d2 = sdf.parse(end_date);
            long difference_In_Time = Objects.requireNonNull(d2).getTime() - Objects.requireNonNull(d1).getTime();
            long difference_In_Seconds = TimeUnit.MILLISECONDS.toSeconds(difference_In_Time) % 60;
            long difference_In_Minutes = TimeUnit.MILLISECONDS.toMinutes(difference_In_Time);
            long difference_In_Hours = TimeUnit.MILLISECONDS.toHours(difference_In_Time) % 24;
            long difference_In_Days = TimeUnit.MILLISECONDS.toDays(difference_In_Time) % 365;
            try {
                if (difference_In_Minutes == 0) {
                    ans = difference_In_Seconds + " seconds";
                } else if (difference_In_Hours == 0) {
                    ans = difference_In_Minutes + " minutes";
                } else if (difference_In_Days == 0) {
                    ans = difference_In_Hours + " hours";
                } else if (difference_In_Days > 0 && difference_In_Days < 7) {
                    ans = difference_In_Days + " days";
                } else if (difference_In_Days <= 6) {
                    return "";
                } else {
                    return start_date;
                }
                return ans;
            } catch (Exception e2) {
                return "";
            }
        } catch (ParseException e3) {
            return "";
        }
    }
}
