package com.megalotto.megalotto.ui.tickets;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.megalotto.megalotto.MyApplication;
import com.megalotto.megalotto.R;
import com.megalotto.megalotto.adapter.ContestAdapter;
import com.megalotto.megalotto.api.ApiCalling;
import com.megalotto.megalotto.helper.AppConstant;
import com.megalotto.megalotto.helper.Function;
import com.megalotto.megalotto.helper.ProgressBarHelper;
import com.megalotto.megalotto.model.CustomerModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TicketsFragment extends Fragment {
    private ApiCalling api;
    public Bundle bundle;
    private ContestAdapter contestAdapter;
    private Context context;
    String id;
    private TicketsViewModel mViewModel;
    TextView noContentTv;
    LinearLayout noDataLy;
    public ProgressBarHelper progressBarHelper;
    RecyclerView recyclerView;
    String title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mViewModel = ViewModelProviders.of(this).get(TicketsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tickets, container, false);
        this.api = MyApplication.getRetrofit().create(ApiCalling.class);
        this.context = getActivity();
        this.progressBarHelper = new ProgressBarHelper(this.context, false);
        this.recyclerView = root.findViewById(R.id.recyclerView);
        this.noContentTv = root.findViewById(R.id.noContentTv);
        this.noDataLy = root.findViewById(R.id.noDataLy);
        Bundle arguments = getArguments();
        this.bundle = arguments;
        if (arguments != null) {
            this.id = arguments.getString("PKG_ID");
            this.title = this.bundle.getString("PKG_NAME");
        }
        if (Function.checkNetworkConnection(this.context)) {
            getContestStatus();
        } else {
            Toast.makeText(this.context, "Please check your internet connectivity...", Toast.LENGTH_LONG).show();
        }
        return root;
    }
    

    public class AnonymousClass1 implements Callback<CustomerModel> {
        AnonymousClass1() {
        }

        @Override 
        public void onResponse(Call<CustomerModel> call, Response<CustomerModel> response) {
            CustomerModel legalData;
            if (response.isSuccessful() && (legalData = response.body()) != null) {
//                List<CustomerModel.Result> res = legalData.getResult();
//                if (res.get(0).getSuccess() != 1) {
//                    TicketsFragment.this.noDataLy.setVisibility(View.VISIBLE);
//                    TicketsFragment.this.recyclerView.setVisibility(View.GONE);
//                } else if (res.get(0).getLive_contest() == 1) {
//                    TicketsFragment.this.noDataLy.setVisibility(View.GONE);
//                    TicketsFragment.this.recyclerView.setVisibility(View.VISIBLE);
//                    TicketsFragment.this.mViewModel.init(Preferences.getInstance(TicketsFragment.this.context).getString(Preferences.KEY_CONTST_ID), TicketsFragment.this.id);
//                    try {
//                        TicketsFragment.this.mViewModel.getDomesticList().observe(TicketsFragment.this.getViewLifecycleOwner(), new Observer() {
//                            @Override
//                            public void onChanged(Object obj) {
//                                AnonymousClass1.this.m305x6d3520((List) obj);
//                            }
//                        });
//                    } catch (IllegalStateException e) {
//                        e.printStackTrace();
//                    }
//                } else if (res.get(0).getUpcoming_contest() == 1) {
//                    TicketsFragment.this.noContentTv.setText("Upcoming contest");
//                    TicketsFragment.this.noDataLy.setVisibility(View.VISIBLE);
//                    TicketsFragment.this.recyclerView.setVisibility(View.GONE);
//                } else if (res.get(0).getUpcoming_contest() == 0 && res.get(0).getLive_contest() == 0) {
//                    TicketsFragment.this.noContentTv.setText("No Upcoming Contest");
//                    TicketsFragment.this.noDataLy.setVisibility(View.VISIBLE);
//                    TicketsFragment.this.recyclerView.setVisibility(View.GONE);
//                }
            }
        }


        public void m305x6d3520(List invoiceModels) {
            if (invoiceModels != null) {
                if (invoiceModels.size() > 0) {
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(TicketsFragment.this.context);
                    TicketsFragment.this.recyclerView.setLayoutManager(linearLayoutManager);
                    TicketsFragment.this.contestAdapter = new ContestAdapter(TicketsFragment.this.context, invoiceModels);
                    TicketsFragment.this.contestAdapter.notifyDataSetChanged();
                    TicketsFragment.this.recyclerView.setAdapter(TicketsFragment.this.contestAdapter);
                    return;
                }
                TicketsFragment.this.noContentTv.setText("Ticket not found");
                TicketsFragment.this.noDataLy.setVisibility(View.VISIBLE);
                TicketsFragment.this.recyclerView.setVisibility(View.GONE);
            }
        }

        @Override 
        public void onFailure(Call<CustomerModel> call, Throwable t) {
        }
    }

    private void getContestStatus() {
        Call<CustomerModel> call = this.api.getContestStatus(AppConstant.PURCHASE_KEY);
        call.enqueue(new AnonymousClass1());
    }
}
