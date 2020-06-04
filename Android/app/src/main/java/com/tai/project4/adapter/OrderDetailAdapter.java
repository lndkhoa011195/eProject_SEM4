package com.tai.project4.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.tai.project4.Item_Order_Detail;
import com.tai.project4.Product;
import com.tai.project4.R;
import com.tai.project4.models.CartResult;

import java.util.List;

public class OrderDetailAdapter  extends RecyclerView.Adapter<OrderDetailAdapter.ProductsViewHolder> {

    public static final String PREFS = "PREFS";
    Context context;
    SharedPreferences sp;
    List<CartResult> list;

    public OrderDetailAdapter(List<CartResult> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public OrderDetailAdapter.ProductsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_order_detail, parent, false);
        return new OrderDetailAdapter.ProductsViewHolder(view);

    }

    @Override
    public void onBindViewHolder(OrderDetailAdapter.ProductsViewHolder holder, int position) {
        String id = String.valueOf(list.get(position).getId());
        String name = list.get(position).getName();
        String desc = list.get(position).getDescription();
        String img = list.get(position).getImageURL();
        String price = String.valueOf(list.get(position).getOriginalPrice());
        String selling_price = String.valueOf(list.get(position).getSellingPrice());
        String brand = list.get(position).getManufacturerName();
        double p_mrp = Double.parseDouble(price);
        double p_sp = Double.parseDouble(selling_price);
        double p_dp = (p_mrp - p_sp) / (p_mrp / 100);
        int p_dp_i = (int) p_dp;
        String discount = String.valueOf(p_dp_i);
        String qty = String.valueOf(list.get(position).getQuantity());

        holder.pro_id.setText(id);
        holder.pro_name.setText(name);
        holder.pro_desc.setText(desc);
        holder.pro_price.setText(price);
        holder.pro_sp.setText(selling_price);
        holder.pro_brand.setText(brand);
        holder.pro_discount.setText(discount + " %   OFF");
        holder.pro_qty.setText(qty);

        if (Integer.parseInt(discount) <= 0) {
            holder.pro_discount.setVisibility(View.GONE);
        }
        if(selling_price.trim().equals(price.trim())){
            holder.pro_price.setVisibility(View.GONE);
        }

        Picasso.with(context).load(img).placeholder(R.drawable.watermark_icon).into(holder.pro_img);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ProductsViewHolder extends RecyclerView.ViewHolder {

        TextView pro_id;
        TextView pro_name;
        TextView pro_desc;
        TextView pro_price;
        TextView pro_sp;
        TextView pro_brand;
        TextView pro_discount;
        TextView pro_qty;
        ImageView pro_img;

        public ProductsViewHolder(final View itemView) {
            super(itemView);
            sp = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);

            pro_id = itemView.findViewById(R.id.product_id);
            pro_name = itemView.findViewById(R.id.product_name);
            pro_desc = itemView.findViewById(R.id.product_short_desc);
            pro_img = itemView.findViewById(R.id.product_img);
            pro_price = itemView.findViewById(R.id.product_price);
            pro_sp = itemView.findViewById(R.id.selling_price);
            pro_brand = itemView.findViewById(R.id.brand_name);
            pro_discount = itemView.findViewById(R.id.discount);
            pro_qty = itemView.findViewById(R.id.product_qty);

            strikeThroughText(pro_price);

            pro_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Product detail = new Product();
                    detail.startProductDetailActivity(pro_id.getText().toString(), context);
                }
            });
            pro_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Product detail = new Product();
//                    detail.startProductDetailActivity(pro_id.getText().toString(), context);

                }
            });
        }

        private void strikeThroughText(TextView price) {
            price.setPaintFlags(price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }
}