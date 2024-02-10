package com.megalotto.megalotto.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.megalotto.megalotto.R;
import com.megalotto.megalotto.activity.PaymentActivity;
import com.megalotto.megalotto.activity.TicketDetailActivity;
import com.megalotto.megalotto.helper.AppConstant;
import com.megalotto.megalotto.helper.Function;
import com.megalotto.megalotto.helper.Preferences;
import com.megalotto.megalotto.model.Contest;

import java.util.List;

public class ContestAdapter extends RecyclerView.Adapter<ContestAdapter.ViewHolder> {
    Context context;
    List<Contest> dataArrayList;

    public ContestAdapter(Context applicationContext, List<Contest> dataArrayList) {
        this.context = applicationContext;
        this.dataArrayList = dataArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_ticket, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.feeTxt.setText(AppConstant.CURRENCY_SIGN + "" + this.dataArrayList.get(position).getPrice());
        holder.prizeTxt.setText(AppConstant.CURRENCY_SIGN + "" + this.dataArrayList.get(position).getTotal_prize());
        holder.firstPrizeTv.setText("1st Prize: " + AppConstant.CURRENCY_SIGN + "" + this.dataArrayList.get(position).getFirst_prize());
        holder.totalPrizeTv.setText(this.dataArrayList.get(position).getNo_of_winners() + " Winners");
        holder.roomStatusTv.setText((Integer.parseInt(this.dataArrayList.get(position).getNo_of_tickets()) - Integer.parseInt(this.dataArrayList.get(position).getNo_of_sold())) + " tickets left");
        holder.roomSizeTv.setText(this.dataArrayList.get(position).getNo_of_tickets() + "  tickets");
        holder.progressBar.setMax(Integer.parseInt(this.dataArrayList.get(position).getNo_of_tickets()));
        holder.progressBar.setProgress(Integer.parseInt(this.dataArrayList.get(position).getNo_of_sold()));
        if (Integer.parseInt(this.dataArrayList.get(position).getNo_of_sold()) >= Integer.parseInt(this.dataArrayList.get(position).getNo_of_tickets())) {
            holder.buyTxt.setText("Sold Out");
            holder.buyTxt.setEnabled(false);
        } else {
            holder.buyTxt.setText("Buy Ticket");
            holder.buyTxt.setEnabled(true);
        }
        holder.buyTxt.setOnClickListener(new View.OnClickListener() { // from class: com.ratechnoworld.megalotto.adapter.ContestAdapter$$ExternalSyntheticLambda0
            @Override 
            public final void onClick(View view) {
                ContestAdapter.this.m297x967db047(position, view);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.ratechnoworld.megalotto.adapter.ContestAdapter$$ExternalSyntheticLambda1
            @Override 
            public final void onClick(View view) {
                ContestAdapter.this.m298xc4564aa6(position, view);
            }
        });
    }

    
    /* renamed from: lambda$onBindViewHolder$0$com-ratechnoworld-megalotto-adapter-ContestAdapter  reason: not valid java name */
    public void m297x967db047(int position, View view) {
        Intent intent = new Intent(this.context, PaymentActivity.class);
        intent.putExtra("FEES_ID", this.dataArrayList.get(position).getId());
        intent.putExtra("ENTREE_FEES", this.dataArrayList.get(position).getPrice());
        Preferences.getInstance(this.context).setString(Preferences.KEY_FEE_ID, this.dataArrayList.get(position).getId());
        Preferences.getInstance(this.context).setString(Preferences.KEY_PRICE, this.dataArrayList.get(position).getPrice());
        Preferences.getInstance(this.context).setString(Preferences.KEY_WINNER, this.dataArrayList.get(position).getNo_of_winners());
        Preferences.getInstance(this.context).setString(Preferences.KEY_SOLD, this.dataArrayList.get(position).getNo_of_sold());
        Function.fireIntentWithData(this.context, intent);
    }

    
    /* renamed from: lambda$onBindViewHolder$1$com-ratechnoworld-megalotto-adapter-ContestAdapter  reason: not valid java name */
    public void m298xc4564aa6(int position, View view) {
        Intent intent = new Intent(this.context, TicketDetailActivity.class);
        intent.putExtra("FEES_ID", this.dataArrayList.get(position).getId());
        intent.putExtra("ENTREE_FEES", this.dataArrayList.get(position).getPrice());
        intent.putExtra("TOTAL_PRIZE", this.dataArrayList.get(position).getTotal_prize());
        intent.putExtra("FIRST_PRIZE", this.dataArrayList.get(position).getFirst_prize());
        intent.putExtra("TOTAL_TICKET", this.dataArrayList.get(position).getNo_of_tickets());
        intent.putExtra("TOTAL_WINNERS", this.dataArrayList.get(position).getNo_of_winners());
        intent.putExtra("TOTAL_SOLD", this.dataArrayList.get(position).getNo_of_sold());
        intent.putExtra("TAG", "1");
        Preferences.getInstance(this.context).setString(Preferences.KEY_FEE_ID, this.dataArrayList.get(position).getId());
        Function.fireIntentWithData(this.context, intent);
    }

    @Override
    public int getItemCount() {
        return this.dataArrayList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView buyTxt;
        TextView feeTxt;
        TextView firstPrizeTv;
        TextView prizeTxt;
        ProgressBar progressBar;
        TextView roomSizeTv;
        TextView roomStatusTv;
        TextView totalPrizeTv;

        public ViewHolder(View itemView) {
            super(itemView);
            this.prizeTxt = (TextView) itemView.findViewById(R.id.prizeTxt);
            this.feeTxt = (TextView) itemView.findViewById(R.id.feeTxt);
            this.roomStatusTv = (TextView) itemView.findViewById(R.id.roomStatusTv);
            this.roomSizeTv = (TextView) itemView.findViewById(R.id.roomSizeTv);
            this.firstPrizeTv = (TextView) itemView.findViewById(R.id.firstPrizeTv);
            this.totalPrizeTv = (TextView) itemView.findViewById(R.id.totalPrizeTv);
            this.progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
            this.buyTxt = (TextView) itemView.findViewById(R.id.buyTxt);
        }
    }
}
