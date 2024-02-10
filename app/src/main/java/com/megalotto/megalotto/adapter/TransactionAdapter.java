package com.megalotto.megalotto.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.exifinterface.media.ExifInterface;
import androidx.recyclerview.widget.RecyclerView;

import com.megalotto.megalotto.R;
import com.megalotto.megalotto.helper.AppConstant;
import com.megalotto.megalotto.model.Contest;

import java.util.List;


public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {
    Context context;
    List<Contest> dataArrayList;

    public TransactionAdapter(Context applicationContext, List<Contest> dataArrayList) {
        this.context = applicationContext;
        this.dataArrayList = dataArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_transaction, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        char c;
        holder.currencyTv.setText(AppConstant.CURRENCY_SIGN);
        String status = this.dataArrayList.get(position).getStatus();
        switch (status.hashCode()) {
            case 48:
                if (status.equals("0")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 49:
                if (status.equals("1")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 50:
                if (status.equals(ExifInterface.GPS_MEASUREMENT_2D)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                holder.statusIv.setImageResource(R.drawable.ic_pending);
                break;
            case 1:
                holder.statusIv.setImageResource(R.drawable.ic_accept);
                break;
            case 2:
                holder.statusIv.setImageResource(R.drawable.ic_reject);
                break;
        }
        holder.date.setText(this.dataArrayList.get(position).getDate());
        holder.reqAmount.setText(this.dataArrayList.get(position).getReq_amount());
        holder.orderID.setText("Trans Id: " + this.dataArrayList.get(position).getOrder_id());
        holder.statusTv.setText(this.dataArrayList.get(position).getRemark());
    }

    @Override
    public int getItemCount() {
        return this.dataArrayList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView currencyTv;
        TextView date;
        TextView orderID;
        TextView reqAmount;
        ImageView statusIv;
        TextView statusTv;

        public ViewHolder(View itemView) {
            super(itemView);
            this.date = itemView.findViewById(R.id.date);
            this.currencyTv = itemView.findViewById(R.id.currencyTv);
            this.reqAmount = itemView.findViewById(R.id.reqAmount);
            this.orderID = itemView.findViewById(R.id.orderID);
            this.statusTv = itemView.findViewById(R.id.statusTv);
            this.statusIv = itemView.findViewById(R.id.statusIv);
        }
    }
}
