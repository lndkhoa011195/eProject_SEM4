package com.group1.project4.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.group1.project4.R;
import com.group1.project4.models.GridItem;

import java.util.ArrayList;

public class GridAdapter extends ArrayAdapter<GridItem> {

    private Context mContext;
    private int layoutResourceId;
    private ArrayList<GridItem> mGridData = new ArrayList<GridItem>();

    public GridAdapter(Context mContext, int layoutResourceId, ArrayList<GridItem> mGridData) {
        super(mContext, layoutResourceId, mGridData);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.mGridData = mGridData;
    }

    /**
     * Updates grid data and refresh grid items.
     *
     * @param mGridData
     */
    public void setGridData(ArrayList<GridItem> mGridData) {
        this.mGridData = mGridData;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.titleTextView = row.findViewById(R.id.grid_item_title);
            holder.imageView = row.findViewById(R.id.grid_item_image);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        GridItem item = mGridData.get(position);
        holder.titleTextView.setText(Html.fromHtml(item.getTitle()));

        Picasso.with(mContext).load(item.getImage()).placeholder(R.drawable.watermark_icon).into(holder.imageView);
        return row;
    }

    static class ViewHolder {
        TextView titleTextView;
        ImageView imageView;
    }
}