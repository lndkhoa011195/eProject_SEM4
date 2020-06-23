package com.tai.project4.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.tai.project4.AddToCart;
import com.tai.project4.AddorRemoveCallbacks;
import com.tai.project4.LoginActivity;
import com.tai.project4.models.Product;
import com.tai.project4.R;
import com.tai.project4.models.ProductResponse;
import com.tai.project4.util.NumberManager;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductsViewHolder> {
    public static final String PREFS = "PREFS";
    Context context;
    SharedPreferences sp;
    Activity activity;

    private List<ProductResponse> list;

    public ProductAdapter(Activity activity, List<ProductResponse> _list, Context _context) {
        this.activity  = activity;
        this.list = _list;
        this.context = _context;
    }


    @Override
    public ProductAdapter.ProductsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_recent_products, parent, false);
        return new ProductAdapter.ProductsViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ProductAdapter.ProductsViewHolder holder, int position) {
        String id = String.valueOf(list.get(position).getId());
        String name = list.get(position).getName();
        String desc = list.get(position).getDescription();
        String img = list.get(position).getImageURL();
        int originalPrice = (int)list.get(position).getOriginalPrice();
        int sellingPrice = (int)list.get(position).getSellingPrice();
        String brand = list.get(position).getManufacturerName();
        double p_mrp = list.get(position).getOriginalPrice();
        double p_sp = list.get(position).getSellingPrice();
        double p_dp = (p_mrp - p_sp) / (p_mrp / 100);
        int p_dp_i = (int) p_dp;
        String discount = String.valueOf(p_dp_i);

        holder.pro_id.setText(id);
        holder.pro_name.setText(name);
        holder.pro_desc.setText(desc);
        holder.pro_price.setText(NumberManager.getInstance().format(originalPrice) + "đ");
        holder.pro_sp.setText(NumberManager.getInstance().format(sellingPrice) + "đ");
        holder.pro_brand.setText(brand);
        holder.pro_discount.setText(discount + " %   OFF");

        if (Integer.parseInt(discount) <= 0) {
            holder.pro_discount.setVisibility(View.GONE);
        }
        if (originalPrice == sellingPrice) {
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
        TextView pro_add;
        ImageView pro_img;
        LinearLayout layout;


        public ProductsViewHolder(final View itemView) {

            super(itemView);
            pro_id = itemView.findViewById(R.id.product_id);
            pro_name = itemView.findViewById(R.id.product_name);
            pro_desc = itemView.findViewById(R.id.product_short_desc);
            pro_img = itemView.findViewById(R.id.product_img);
            pro_price = itemView.findViewById(R.id.product_price);
            pro_sp = itemView.findViewById(R.id.selling_price);
            pro_brand = itemView.findViewById(R.id.brand_name);
            pro_discount = itemView.findViewById(R.id.discount);
            pro_add = itemView.findViewById(R.id.product_add);
            strikeThroughText(pro_price);
            layout = itemView.findViewById(R.id.product_card);

            sp = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);



            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Product detail = new Product();
                    detail.startProductDetailActivity(pro_id.getText().toString(), context);
                }
            });
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
                    Product detail = new Product();
                    detail.startProductDetailActivity(pro_id.getText().toString(), context);
                }
            });
            pro_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (sp.getString("loginid", null) != null) {
                        AddToCart addToCart = new AddToCart(activity, context);
                        addToCart.addToCart(pro_id.getText().toString(), "1");
                        ((AddorRemoveCallbacks) context).onAddProduct();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Heyy..")
                                .setMessage("To add this item in your cart you have to login first. Do you want to login ")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(context, LoginActivity.class);
                                        context.startActivity(intent);
                                    }
                                })
                                .setNegativeButton("No Just Continue ", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .setCancelable(false);
                        builder.show();
                    }

                }
            });

        }


        private void strikeThroughText(TextView price) {
            price.setPaintFlags(price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }
}
