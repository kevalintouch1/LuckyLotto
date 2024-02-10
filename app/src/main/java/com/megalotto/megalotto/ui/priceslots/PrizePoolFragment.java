package com.megalotto.megalotto.ui.priceslots;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.megalotto.megalotto.R;
import com.megalotto.megalotto.adapter.PriceSlotAdapter;
import com.megalotto.megalotto.helper.Function;
import com.megalotto.megalotto.helper.ProgressBarHelper;

import java.util.List;


public class PrizePoolFragment extends Fragment {
    public Bundle bundle;
    PriceSlotAdapter contestAdapter;
    Context context;
    String feedId;
    public PrizePoolViewModel mViewModel;
    ProgressBarHelper progressBarHelper;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mViewModel = ViewModelProviders.of(this).get(PrizePoolViewModel.class);
        View root = inflater.inflate(R.layout.fragment_prize_pool, container, false);
        this.context = getActivity();
        this.progressBarHelper = new ProgressBarHelper(this.context, false);
        this.recyclerView = root.findViewById(R.id.recyclerView);
        loadBundle();
        if (Function.checkNetworkConnection(this.context)) {
            this.progressBarHelper.showProgressDialog();
            this.mViewModel.init(this.feedId);
            this.mViewModel.getDomesticList().observe(getViewLifecycleOwner(), new Observer() {
                @Override
                public void onChanged(Object obj) {
                    PrizePoolFragment.this.m304xde278c5b((List) obj);
                }
            });
        }
        return root;
    }


    public void m304xde278c5b(List invoiceModels) {
        if (invoiceModels != null && invoiceModels.size() > 0) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.context);
            this.recyclerView.setLayoutManager(linearLayoutManager);
            PriceSlotAdapter priceSlotAdapter = new PriceSlotAdapter(this.context, invoiceModels);
            this.contestAdapter = priceSlotAdapter;
            priceSlotAdapter.notifyDataSetChanged();
            this.recyclerView.setAdapter(this.contestAdapter);
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
