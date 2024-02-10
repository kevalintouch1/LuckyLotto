package com.megalotto.megalotto.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.megalotto.megalotto.R;
import com.megalotto.megalotto.activity.ResultDetailsActivity;
import com.megalotto.megalotto.helper.AppConstant;
import com.megalotto.megalotto.helper.Function;
import com.megalotto.megalotto.model.Contest;

import java.util.List;


public class MyResultAdapter extends RecyclerView.Adapter<MyResultAdapter.ViewHolder> {
    Context context;
    List<Contest> dataArrayList;

    public MyResultAdapter(Context applicationContext, List<Contest> dataArrayList) {
        this.context = applicationContext;
        this.dataArrayList = dataArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_winner, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.currencyFeeTv.setText(String.format("Paid: %s", AppConstant.CURRENCY_SIGN));
        holder.entryFeeTv.setText(this.dataArrayList.get(position).getEntry_fee());
        holder.currencyPrizeTv.setText(String.format("Won: %s", AppConstant.CURRENCY_SIGN));
        holder.winPriceTv.setText(this.dataArrayList.get(position).getWin_prize());
        holder.typeTv.setText(String.format("Ticket Type: %s", this.dataArrayList.get(position).getPkg_name()));
        holder.itemView.setOnClickListener(view -> MyResultAdapter.this.m300xea538efa(position, view));
    }

    public void m300xea538efa(int position, View view) {
        Intent intent = new Intent(this.context, ResultDetailsActivity.class);
        intent.putExtra("winPrice", this.dataArrayList.get(position).getWin_prize());
        intent.putExtra("entryFee", this.dataArrayList.get(position).getEntry_fee());
        intent.putExtra("fees_id", this.dataArrayList.get(position).getFees_id());
        intent.putExtra("ticket_no", this.dataArrayList.get(position).getTicket_no());
        Function.fireIntentWithData(this.context, intent);
    }

    @Override
    public int getItemCount() {
        return this.dataArrayList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView currencyFeeTv;
        TextView currencyPrizeTv;
        TextView entryFeeTv;
        TextView typeTv;
        TextView winPriceTv;

        public ViewHolder(View itemView) {
            super(itemView);
            this.currencyFeeTv = itemView.findViewById(R.id.currencyFeeTv);
            this.currencyPrizeTv = itemView.findViewById(R.id.currencyPrizeTv);
            this.typeTv = itemView.findViewById(R.id.typeTv);
            this.winPriceTv = itemView.findViewById(R.id.winPriceTv);
            this.entryFeeTv = itemView.findViewById(R.id.entryFeeTv);
        }
    }
}
