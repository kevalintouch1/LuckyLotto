package com.megalotto.megalotto.ui.ticketsold;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.megalotto.megalotto.R;
import com.megalotto.megalotto.adapter.TicketSoldAdapter;
import com.megalotto.megalotto.helper.Function;
import com.megalotto.megalotto.helper.Preferences;
import com.megalotto.megalotto.helper.ProgressBarHelper;

import java.util.List;


public class TicketsSoldFragment extends Fragment {
    public Bundle bundle;
    private Context context;
    String feedId;
    public TicketsSoldViewModel mViewModel;
    private ProgressBarHelper progressBarHelper;
    RecyclerView recyclerView;
    TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mViewModel = ViewModelProviders.of(this).get(TicketsSoldViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tickets_sold, container, false);
        FragmentActivity activity = getActivity();
        this.context = activity;
        this.progressBarHelper = new ProgressBarHelper(activity, false);
        this.recyclerView = root.findViewById(R.id.recyclerView);
        this.textView = root.findViewById(R.id.textView);
        loadBundle();
        if (Function.checkNetworkConnection(this.context)) {
            this.progressBarHelper.showProgressDialog();
            this.mViewModel.init(Preferences.getInstance(this.context).getString(Preferences.KEY_CONTST_ID), this.feedId);
            this.mViewModel.getDomesticList().observe(getViewLifecycleOwner(), (Observer) obj -> TicketsSoldFragment.this.m306x187fa67c((List) obj));
        } else {
            Toast.makeText(this.context, "Please check your internet connectivity...", Toast.LENGTH_LONG).show();
        }
        return root;
    }

    public void m306x187fa67c(List invoiceModels) {
        if (invoiceModels != null) {
            if (invoiceModels.size() > 0) {
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.context);
                this.recyclerView.setLayoutManager(linearLayoutManager);
                TicketSoldAdapter ticketSoldAdapter = new TicketSoldAdapter(this.context, invoiceModels);
                ticketSoldAdapter.notifyDataSetChanged();
                this.recyclerView.setAdapter(ticketSoldAdapter);
            } else {
                this.recyclerView.setVisibility(View.GONE);
                this.textView.setVisibility(View.VISIBLE);
            }
        }
        this.progressBarHelper.hideProgressDialog();
    }

    private void loadBundle() {
        Bundle arguments = getArguments();
        this.bundle = arguments;
        if (arguments != null) {
            this.feedId = arguments.getString("FEES_ID");
        }
    }
}
