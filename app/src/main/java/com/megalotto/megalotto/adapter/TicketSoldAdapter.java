package com.megalotto.megalotto.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.megalotto.megalotto.R;
import com.megalotto.megalotto.model.Contest;

import java.util.List;
import java.util.Random;


public class TicketSoldAdapter extends RecyclerView.Adapter<TicketSoldAdapter.ViewHolder> {
    Context context;
    List<Contest> dataArrayList;

    public TicketSoldAdapter(Context applicationContext, List<Contest> dataArrayList) {
        this.context = applicationContext;
        this.dataArrayList = dataArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_ticket_sold, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.ticketNo.setText(this.dataArrayList.get(position).getTicket_no());
        holder.username.setText(this.dataArrayList.get(position).getUsername());
        int[] androidColors = this.context.getResources().getIntArray(R.array.androidcolors);
        int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
        holder.userImage.setBackgroundColor(randomAndroidColor);
    }

    @Override
    public int getItemCount() {
        return this.dataArrayList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView ticketNo;
        ImageView userImage;
        TextView username;

        public ViewHolder(View itemView) {
            super(itemView);
            this.ticketNo = itemView.findViewById(R.id.ticket_no);
            this.username = itemView.findViewById(R.id.username);
            this.userImage = itemView.findViewById(R.id.userImage);
        }
    }
}
