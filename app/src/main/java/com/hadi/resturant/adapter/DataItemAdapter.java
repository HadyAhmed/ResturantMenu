package com.hadi.resturant.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hadi.resturant.R;
import com.hadi.resturant.activity.DetailsActivity;
import com.hadi.resturant.activity.MainActivity;
import com.hadi.resturant.model.DataItem;

import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.List;

public class DataItemAdapter extends RecyclerView.Adapter<DataItemAdapter.ViewHolder> {
    private Context mContext;
    private List<DataItem> mItemList;

    public DataItemAdapter(Context mContext, List<DataItem> mItemList) {
        this.mContext = mContext;
        this.mItemList = mItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_list_view, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        // Getting A Reference For The Current Item
        final DataItem item = mItemList.get(i);

        viewHolder.itemHeader.setText(item.getItemName());
        viewHolder.itemPrice.setText(NumberFormat.getCurrencyInstance().format(item.getPrice()));

        InputStream inputStream = null;
        try {
            inputStream = mContext.getAssets().open(item.getImage());
            Drawable drawable = Drawable.createFromStream(inputStream, null);
            viewHolder.itemImage.setImageDrawable(drawable);
        } catch (IOException e) {
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
        viewHolder.currentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailIntent = new Intent(mContext, DetailsActivity.class);
                detailIntent.putExtra(MainActivity.ITEM_KEY, item);
                mContext.startActivity(detailIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemHeader, itemPrice;
        ImageView itemImage;
        View currentView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemHeader = itemView.findViewById(R.id.item_header);
            itemPrice = itemView.findViewById(R.id.item_price);
            itemImage = itemView.findViewById(R.id.item_image);
            currentView = itemView;
        }
    }
}
