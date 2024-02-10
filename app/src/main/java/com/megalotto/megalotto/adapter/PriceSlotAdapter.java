package com.megalotto.megalotto.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.megalotto.megalotto.R;
import com.megalotto.megalotto.helper.AppConstant;
import com.megalotto.megalotto.model.Contest;

import java.util.List;


public class PriceSlotAdapter extends RecyclerView.Adapter<PriceSlotAdapter.ViewHolder> {
    Context context;
    List<Contest> dataArrayList;

    public PriceSlotAdapter(Context applicationContext, List<Contest> dataArrayList) {
        this.context = applicationContext;
        this.dataArrayList = dataArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_prize_pool, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.amount.setText(AppConstant.CURRENCY_SIGN + "" + this.dataArrayList.get(position).getPrize());
        holder.rank.setText(this.dataArrayList.get(position).getRank());
    }

    @Override
    public int getItemCount() {
        return this.dataArrayList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView amount;
        TextView rank;

        public ViewHolder(View itemView) {
            super(itemView);
            this.amount = itemView.findViewById(R.id.amount);
            this.rank = itemView.findViewById(R.id.rank);
        }
    }
}
