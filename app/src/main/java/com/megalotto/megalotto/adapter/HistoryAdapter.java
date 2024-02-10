package com.megalotto.megalotto.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.megalotto.megalotto.R;
import com.megalotto.megalotto.activity.ResultsActivity;
import com.megalotto.megalotto.helper.Function;
import com.megalotto.megalotto.helper.Preferences;
import com.megalotto.megalotto.model.Contest;

import java.util.List;


public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    Context context;
    List<Contest> dataArrayList;

    public HistoryAdapter(Context applicationContext, List<Contest> dataArrayList) {
        this.context = applicationContext;
        this.dataArrayList = dataArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.titleTv.setText(String.format("Contest Id #%s", this.dataArrayList.get(position).getContest_id()));
        holder.dateTv.setText(String.format("Date: %s", this.dataArrayList.get(position).getDate()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HistoryAdapter.this.m299x8d3890c7(position, view);
            }
        });
    }

    public void m299x8d3890c7(int position, View view) {
        Intent intent = new Intent(this.context, ResultsActivity.class);
        intent.putExtra("ID", this.dataArrayList.get(position).getContest_id());
        Preferences.getInstance(this.context).setString(Preferences.KEY_CONSTANT_ID, this.dataArrayList.get(position).getContest_id());
        Function.fireIntentWithData(this.context, intent);
    }

    @Override
    public int getItemCount() {
        return this.dataArrayList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView dateTv;
        TextView titleTv;

        public ViewHolder(View itemView) {
            super(itemView);
            this.titleTv = itemView.findViewById(R.id.titleTv);
            this.dateTv = itemView.findViewById(R.id.dateTv);
        }
    }
}
