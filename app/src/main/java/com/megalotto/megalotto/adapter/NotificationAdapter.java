package com.megalotto.megalotto.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.common.internal.ImagesContract;
import com.megalotto.megalotto.R;
import com.megalotto.megalotto.activity.NotificationDetailsActivity;
import com.megalotto.megalotto.model.NotificationModel;

import java.util.List;


public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    Context context;
    List<NotificationModel> dataArrayList;

    public NotificationAdapter(Context applicationContext, List<NotificationModel> dataArrayList) {
        this.context = applicationContext;
        this.dataArrayList = dataArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_notification, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
//        holder.titleTv.setText(this.dataArrayList.get(position).getTitle());
//        holder.timeTv.setText(this.dataArrayList.get(position).getCreated());
//        if (this.dataArrayList.get(position).getImage() != null) {
//            Glide.with(this.context).load("https://megalotto.ratechnoworld.com/admin/" + this.dataArrayList.get(position).getImage()).apply(new RequestOptions().override(60, 60)).apply(new RequestOptions().placeholder(R.drawable.app_icon).error(R.drawable.app_icon)).apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE)).apply(RequestOptions.skipMemoryCacheOf(true)).into(holder.iconTv);
//        }
//        holder.itemView.setOnClickListener(view -> NotificationAdapter.this.m302xd7c6a6b8(position, view));
    }


    public void m302xd7c6a6b8(int position, View v) {
        Intent intent = new Intent(this.context, NotificationDetailsActivity.class);
//        intent.putExtra("title", this.dataArrayList.get(position).getTitle());
//        intent.putExtra("description", this.dataArrayList.get(position).getMessage());
//        intent.putExtra("image", this.dataArrayList.get(position).getImage());
//        intent.putExtra(ImagesContract.URL, this.dataArrayList.get(position).getUrl());
//        intent.putExtra("created", this.dataArrayList.get(position).getCreated());
        intent.addFlags(268435456);
        this.context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return this.dataArrayList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iconTv;
        TextView timeTv;
        TextView titleTv;

        public ViewHolder(View itemView) {
            super(itemView);
            this.iconTv = itemView.findViewById(R.id.iconTv);
            this.titleTv = itemView.findViewById(R.id.titleTv);
            this.timeTv = itemView.findViewById(R.id.timeTv);
        }
    }
}
