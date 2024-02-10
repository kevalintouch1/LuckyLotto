package com.megalotto.megalotto.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyTicketFragment extends Fragment {
    Activity activity;
    private ApiCalling api;
    private MyTicketAdapter contestAdapter;
    public LinearLayout noDataLy;
    private ProgressBarHelper progressBarHelper;
    public RecyclerView recyclerView;
    View view;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = context instanceof Activity ? (Activity) context : null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_my_ticket, container, false);
        this.api = MyApplication.getRetrofit().create(ApiCalling.class);
        this.progressBarHelper = new ProgressBarHelper(this.activity, false);
        initView();
        if (Function.checkNetworkConnection(this.activity)) {
            getMyTicket();
        }
        return this.view;
    }

    private void initView() {
        this.recyclerView = this.view.findViewById(R.id.recyclerView);
        this.noDataLy = this.view.findViewById(R.id.noDataLy);
    }

    private void getMyTicket() {
        this.progressBarHelper.showProgressDialog();
        Call<List<Contest>> call = this.api.getMyTicket(AppConstant.PURCHASE_KEY, Preferences.getInstance(this.activity).getString(Preferences.KEY_USER_ID), Preferences.getInstance(this.activity).getString(Preferences.KEY_CONTST_ID));
        call.enqueue(new Callback<List<Contest>>() {
            @Override
            public void onResponse(Call<List<Contest>> call2, Response<List<Contest>> response) {
                MyTicketFragment.this.progressBarHelper.hideProgressDialog();
                if (response.isSuccessful()) {
                    List<Contest> legalData = response.body();
                    if (legalData != null) {
                        if (legalData.size() > 0) {
                            MyTicketFragment.this.recyclerView.setVisibility(View.VISIBLE);
                            MyTicketFragment.this.noDataLy.setVisibility(View.GONE);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyTicketFragment.this.activity);
                            MyTicketFragment.this.recyclerView.setLayoutManager(linearLayoutManager);
                            MyTicketFragment.this.contestAdapter = new MyTicketAdapter(MyTicketFragment.this.activity, legalData);
                            MyTicketFragment.this.contestAdapter.notifyDataSetChanged();
                            MyTicketFragment.this.recyclerView.setAdapter(MyTicketFragment.this.contestAdapter);
                            return;
                        }
                        MyTicketFragment.this.recyclerView.setVisibility(View.GONE);
                        MyTicketFragment.this.noDataLy.setVisibility(View.VISIBLE);
                        return;
                    }
                    MyTicketFragment.this.recyclerView.setVisibility(View.GONE);
                    MyTicketFragment.this.noDataLy.setVisibility(View.VISIBLE);
                    return;
                }
                MyTicketFragment.this.recyclerView.setVisibility(View.GONE);
                MyTicketFragment.this.noDataLy.setVisibility(View.VISIBLE);
            }

            @Override 
            public void onFailure(Call<List<Contest>> call2, Throwable t) {
                MyTicketFragment.this.progressBarHelper.hideProgressDialog();
                MyTicketFragment.this.recyclerView.setVisibility(View.GONE);
                MyTicketFragment.this.noDataLy.setVisibility(View.VISIBLE);
            }
        });
    }
}
