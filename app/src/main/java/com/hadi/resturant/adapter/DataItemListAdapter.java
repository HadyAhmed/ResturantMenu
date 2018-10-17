package com.hadi.resturant.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hadi.resturant.R;
import com.hadi.resturant.model.DataItem;

import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.List;

public class DataItemListAdapter extends ArrayAdapter<DataItem> {
    private LayoutInflater mLayoutInflater;
    private List<DataItem> mDataItems;

    public DataItemListAdapter(Context context, List<DataItem> list) {
        super(context, R.layout.item_list, list);
        mDataItems = list;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_list, parent, false);
        }
        DataItem item = mDataItems.get(position);
        TextView itemHeader = convertView.findViewById(R.id.item_header);
        TextView itemPrice = convertView.findViewById(R.id.item_price);
        ImageView itemImage = convertView.findViewById(R.id.item_image);
        if (item != null) {
            itemHeader.setText(item.getItemName());
            itemPrice.setText(NumberFormat.getCurrencyInstance().format(item.getPrice()));

            InputStream inputStream = null;
            try {
                inputStream = getContext().getAssets().open(item.getImage());
                Drawable drawable = Drawable.createFromStream(inputStream, null);
                itemImage.setImageDrawable(drawable);
            } catch (IOException e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
        return convertView;
    }
}
