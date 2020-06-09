package com.tai.project4.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.tai.project4.OrderDetailsActivity;
import com.tai.project4.R;
import com.tai.project4.models.OrderResult;

import java.util.List;


public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.CustomerViewHolder> {

    public static final String PREFS = "PREFS";
    Context context;
    SharedPreferences sp;

    List<OrderResult> list;

    public OrderAdapter(List<OrderResult> list, Context context) {
        this.list=list;
        this.context=context;
    }

    @Override
    public OrderAdapter.CustomerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_orders, parent, false);
        return new OrderAdapter.CustomerViewHolder(view);

    }

    @Override
    public void onBindViewHolder(OrderAdapter.CustomerViewHolder holder, int position) {
        String order_id = String.valueOf(list.get(position).getOrderCode());
        String order_saving = String.valueOf(list.get(position).getOriginalSum()-list.get(position).getSellingSum());
        String order_payableamt = String.valueOf(list.get(position).getSellingSum());
        String order_statu = "";
        switch (list.get(position).getStatus()){
            case 1:
                order_statu = "NEW";
                break;
            case 2:
                order_statu = "PROCESSING";
                break;
            case 3:
                order_statu = "DONE";
                break;
        }
        String order_dt = list.get(position).getOrderDate();

        holder.tvOrderId.setText(order_id);
        holder.tvOrderSaving.setText(order_saving);
        holder.tvOrderPayableAmt.setText(order_payableamt);
        holder.tvOrderStatus.setText(order_statu);
        holder.tvOrderDate.setText(order_dt);
        holder.tvOrderTime.setText(order_dt.substring(11));

        if(order_statu.trim().equals("NEW")){
            holder.tvOrderStatus.setTextColor(context.getResources().getColor(R.color.holo_green_light));
        }else if(order_statu.trim().equals("PROCESSING")){
            holder.tvOrderStatus.setTextColor(context.getResources().getColor(R.color.orange));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CustomerViewHolder extends RecyclerView.ViewHolder {

        TextView tvOrderId;
        TextView tvOrderSaving;
        TextView tvOrderPayableAmt;
        TextView tvOrderStatus;
        TextView tvOrderDate;
        TextView tvOrderTime;
        CardView card ;

        public CustomerViewHolder(final View itemView) {

            super(itemView);
            tvOrderId = itemView.findViewById(R.id.orderId);
            tvOrderSaving = itemView.findViewById(R.id.total_savings);
            tvOrderPayableAmt = itemView.findViewById(R.id.payableAmount);
            tvOrderStatus = itemView.findViewById(R.id.status);
            tvOrderDate = itemView.findViewById(R.id.order_date);
            tvOrderTime = itemView.findViewById(R.id.order_time);

            card = itemView.findViewById(R.id.order_history_cart);

            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(context, OrderDetailsActivity.class);
                    i.putExtra("order_id",tvOrderId.getText().toString());
                    context.startActivity(i);
                }
            });
        }
    }
}
