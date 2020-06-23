package com.tai.project4.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.tai.project4.models.Product;
import com.tai.project4.R;
import com.tai.project4.interfaces.APIClient;
import com.tai.project4.interfaces.APIInterface;
import com.tai.project4.models.CartRequest;
import com.tai.project4.models.CartResult;
import com.tai.project4.util.LoadingDialog;
import com.tai.project4.util.NumberManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ProductsViewHolder> {
    public static final String PREFS = "PREFS";
    Context context;
    SharedPreferences sp;
    List<CartResult> list;
    private TextView total_saving;
    private TextView total_pamt;
    APIInterface apiInterface;
    Activity activity;

    public CartAdapter(Activity activity, List<CartResult> list, TextView total_saving, TextView total_pamt, Context context) {
        this.activity = activity;
        this.list = list;
        this.context = context;
        this.total_saving = total_saving;
        this.total_pamt = total_pamt;
    }

    @Override
    public CartAdapter.ProductsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_cart, parent, false);
        return new CartAdapter.ProductsViewHolder(view);

    }

    @Override
    public void onBindViewHolder(CartAdapter.ProductsViewHolder holder, int position) {
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
        String qty = String.valueOf(list.get(position).getQuantity());

        holder.pro_id.setText(id);
        holder.pro_name.setText(name);
        holder.pro_desc.setText(desc);
        holder.pro_price.setText(NumberManager.getInstance().format(originalPrice) + "đ");
        holder.pro_sp.setText(NumberManager.getInstance().format(sellingPrice) + "đ");
        holder.pro_brand.setText(brand);
        holder.pro_discount.setText(discount + " %   OFF");
        holder.pro_qty.setText(qty);

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
        TextView pro_qty;
        ImageView pro_del;
        ImageView pro_img;
        ImageView add, remove;

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
            pro_del = itemView.findViewById(R.id.product_del);
            pro_qty = itemView.findViewById(R.id.product_qty);
            add = itemView.findViewById(R.id.add);
            remove = itemView.findViewById(R.id.remove);

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

            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final LoadingDialog loadingDialog = new LoadingDialog(activity);
                    loadingDialog.showLoadingDialog();
                    sp = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
                    String loginid = sp.getString("loginid", null);
                    apiInterface = APIClient.getClient().create(APIInterface.class);
                    CartRequest request = new CartRequest(Integer.parseInt(loginid), Integer.parseInt(pro_id.getText().toString()), 1);
                    Call<String> cartRequestCall = apiInterface.AddToCart(request);
                    cartRequestCall.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if (!response.isSuccessful())
                                return;
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    loadingDialog.dismissLoadingDialog();
                                }
                            }, 500);
                            Toast.makeText(context, "Increase product quantity successfully", Toast.LENGTH_SHORT).show();

                            String result = response.body();
                            int qtyi = Integer.parseInt(pro_qty.getText().toString());
                            qtyi++;
                            pro_qty.setText(Integer.toString(qtyi));

                            String sOriginalPrice = pro_price.getText().toString().replace(".","");
                            int OriginalPrice = Integer.parseInt(sOriginalPrice.substring(0, sOriginalPrice.length()-1));
                            String sSellingPrice = pro_sp.getText().toString().replace(".","");
                            int SellingPrice = Integer.parseInt(sSellingPrice.substring(0, sSellingPrice.length()-1));
                            int profit = OriginalPrice - SellingPrice;

                            String sOldSaving = total_saving.getText().toString().replace(".","");
                            int oldSaving = Integer.parseInt(sOldSaving.substring(0, sOldSaving.length()-1));
                            int newSaving = oldSaving + profit;
                            total_saving.setText(NumberManager.getInstance().format(newSaving) + "đ");

                            String sOldTotal = total_pamt.getText().toString().replace(".","");
                            int oldTotal = Integer.parseInt(sOldTotal.substring(0, sOldTotal.length()-1));
                            int newTotal = oldTotal + SellingPrice;
                            total_pamt.setText(NumberManager.getInstance().format(newTotal) + "đ");
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            call.cancel();
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    loadingDialog.dismissLoadingDialog();
                                }
                            }, 500);
                        }
                    });
                }
            });

            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int qtyi = Integer.parseInt(pro_qty.getText().toString());
                    if (qtyi != 1) {
                        final LoadingDialog loadingDialog = new LoadingDialog(activity);
                        loadingDialog.showLoadingDialog();
                        sp = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
                        String loginid = sp.getString("loginid", null);
                        apiInterface = APIClient.getClient().create(APIInterface.class);
                        CartRequest request = new CartRequest(Integer.parseInt(loginid), Integer.parseInt(pro_id.getText().toString()), 1);
                        Call<String> cartRequestCall = apiInterface.RemoveFromCart(request);
                        cartRequestCall.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                if (!response.isSuccessful())
                                    return;
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        loadingDialog.dismissLoadingDialog();
                                    }
                                }, 500);
                                Toast.makeText(context, "Decrease product quantity successfully", Toast.LENGTH_SHORT).show();
                                int qtyi = Integer.parseInt(pro_qty.getText().toString());
                                if (qtyi != 1) {
                                    qtyi--;
                                    pro_qty.setText(Integer.toString(qtyi));
                                }

                                String sOriginalPrice = pro_price.getText().toString().replace(".","");
                                int OriginalPrice = Integer.parseInt(sOriginalPrice.substring(0, sOriginalPrice.length()-1));
                                String sSellingPrice = pro_sp.getText().toString().replace(".","");
                                int SellingPrice = Integer.parseInt(sSellingPrice.substring(0, sSellingPrice.length()-1));
                                int profit = OriginalPrice - SellingPrice;

                                String sOldSaving = total_saving.getText().toString().replace(".","");
                                int oldSaving = Integer.parseInt(sOldSaving.substring(0, sOldSaving.length()-1));
                                int newSaving = oldSaving - profit;
                                total_saving.setText(NumberManager.getInstance().format(newSaving) + "đ");

                                String sOldTotal = total_pamt.getText().toString().replace(".","");
                                int oldTotal = Integer.parseInt(sOldTotal.substring(0, sOldTotal.length()-1));
                                int newTotal = oldTotal - SellingPrice;
                                total_pamt.setText(NumberManager.getInstance().format(newTotal) + "đ");
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                call.cancel();
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        loadingDialog.dismissLoadingDialog();
                                    }
                                }, 500);
                            }
                        });

                    }
                }
            });

            pro_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int qtyi = Integer.parseInt(pro_qty.getText().toString());
                    sp = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
                    String loginid = sp.getString("loginid", null);
                    apiInterface = APIClient.getClient().create(APIInterface.class);
                    CartRequest request = new CartRequest(Integer.parseInt(loginid), Integer.parseInt(pro_id.getText().toString()), qtyi);
                    Call<String> cartRequestCall = apiInterface.RemoveFromCart(request);
                    cartRequestCall.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            Intent intent = ((Activity) context).getIntent();
                            ((Activity) context).finish();
                            context.startActivity(intent);
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            call.cancel();
                        }
                    });
                }
            });

        }

        private void strikeThroughText(TextView price) {
            price.setPaintFlags(price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }
}
