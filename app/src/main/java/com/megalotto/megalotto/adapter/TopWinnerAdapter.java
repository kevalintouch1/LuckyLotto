package com.megalotto.megalotto.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.megalotto.megalotto.R;
import com.megalotto.megalotto.helper.AppConstant;
import com.megalotto.megalotto.model.Contest;

import java.util.List;
import java.util.Random;


public class TopWinnerAdapter extends RecyclerView.Adapter<TopWinnerAdapter.ViewHolder> {
    Context context;
    List<Contest> dataArrayList;

    public TopWinnerAdapter(Context applicationContext, List<Contest> dataArrayList) {
        this.context = applicationContext;
        this.dataArrayList = dataArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_top_winner, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.winningPrice.setText(AppConstant.CURRENCY_SIGN + "" + this.dataArrayList.get(position).getWin_prize());
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
        ImageView userImage;
        TextView username;
        TextView winningPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            this.username = itemView.findViewById(R.id.username);
            this.userImage = itemView.findViewById(R.id.userImage);
            this.winningPrice = itemView.findViewById(R.id.prize);
        }
    }
}
