package com.megalotto.megalotto.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.paytm.pgsdk.PaytmConstants;
import com.megalotto.megalotto.R;
import com.megalotto.megalotto.activity.TicketDetailActivity;
import com.megalotto.megalotto.helper.AppConstant;
import com.megalotto.megalotto.helper.Function;
import com.megalotto.megalotto.helper.Preferences;
import com.megalotto.megalotto.model.Contest;

import java.util.List;

public class MyTicketAdapter extends RecyclerView.Adapter<MyTicketAdapter.ViewHolder> {
    Context context;
    List<Contest> dataArrayList;

    public MyTicketAdapter(Context applicationContext, List<Contest> dataArrayList) {
        this.context = applicationContext;
        this.dataArrayList = dataArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_my_ticket, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.dateTxt.setText("Date of purchase: " + this.dataArrayList.get(position).getDate());
        holder.typeTxt.setText(String.format("Ticket Type: %s", this.dataArrayList.get(position).getPkg_name()));
        holder.feeTxt.setText(AppConstant.CURRENCY_SIGN + "" + this.dataArrayList.get(position).getPrice());
        holder.prizeTxt.setText(AppConstant.CURRENCY_SIGN + "" + this.dataArrayList.get(position).getTotal_prize());
        holder.totalBoughtTxt.setText(this.dataArrayList.get(position).getNo_of_bought() + " Bought");
        holder.totalWinnerTxt.setText(this.dataArrayList.get(position).getNo_of_winners() + " Winners");
        holder.itemView.setOnClickListener(view -> MyTicketAdapter.this.m301xff17912b(position, view));
    }


    public void m301xff17912b(int position, View v) {
        Intent intent = new Intent(this.context, TicketDetailActivity.class);
        intent.putExtra("FEES_ID", this.dataArrayList.get(position).getId());
        intent.putExtra("ENTREE_FEES", this.dataArrayList.get(position).getPrice());
        intent.putExtra("TOTAL_PRIZE", this.dataArrayList.get(position).getTotal_prize());
        intent.putExtra("TOTAL_TICKET", this.dataArrayList.get(position).getNo_of_tickets());
        intent.putExtra("TOTAL_WINNERS", this.dataArrayList.get(position).getNo_of_winners());
        intent.putExtra("TOTAL_SOLD", this.dataArrayList.get(position).getNo_of_sold());
        intent.putExtra("TOTAL_BOUGHT", this.dataArrayList.get(position).getNo_of_bought());
        intent.putExtra(PaytmConstants.STATUS, this.dataArrayList.get(position).getStatus());
        intent.putExtra("TAG", "0");
        Preferences.getInstance(this.context).setString(Preferences.KEY_FEE_ID, this.dataArrayList.get(position).getId());
        Function.fireIntentWithData(this.context, intent);
    }

    @Override
    public int getItemCount() {
        return this.dataArrayList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView dateTxt;
        TextView feeTxt;
        TextView firstPrizeTxt;
        TextView prizeTxt;
        TextView totalBoughtTxt;
        TextView totalWinnerTxt;
        TextView typeTxt;

        public ViewHolder(View itemView) {
            super(itemView);
            this.dateTxt = itemView.findViewById(R.id.dateTxt);
            this.typeTxt = itemView.findViewById(R.id.typeTxt);
            this.prizeTxt = itemView.findViewById(R.id.prizeTxt);
            this.feeTxt = itemView.findViewById(R.id.feeTxt);
            this.firstPrizeTxt = itemView.findViewById(R.id.firstPrizeTxt);
            this.totalWinnerTxt = itemView.findViewById(R.id.totalWinnerTxt);
            this.totalBoughtTxt = itemView.findViewById(R.id.totalBoughtTxt);
        }
    }
}
