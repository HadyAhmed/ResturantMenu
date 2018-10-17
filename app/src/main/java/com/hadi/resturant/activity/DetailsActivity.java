package com.hadi.resturant.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hadi.resturant.R;
import com.hadi.resturant.model.DataItem;

import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.Objects;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        // Getting a reference to the custom toolbar
        Toolbar toolbar = findViewById(R.id.tool_bar_detail);
        setSupportActionBar(toolbar);
        // Setting Back stack
        toolbar.setNavigationIcon(android.R.drawable.arrow_up_float);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetailsActivity.this, MainActivity.class));
                finish();
            }
        });

        DataItem item = getIntent().getExtras().getParcelable(MainActivity.ITEM_KEY);
        if (item != null) {
            toolbar.setTitle(item.getItemName());
            toolbar.setSubtitle(item.getCategory());

            // Find the reference for the views
            ImageView itemImage = findViewById(R.id.item_image_detail);
            TextView itemName = findViewById(R.id.item_header_detail);
            TextView itemPrice = findViewById(R.id.item_price_detail);
            TextView itemCategory = findViewById(R.id.item_category_detail);
            TextView itemDescription = findViewById(R.id.item_description_detail);

            itemName.setText(item.getItemName());
            itemCategory.setText(item.getCategory());
            itemDescription.setText(item.getDescription());
            itemPrice.setText(NumberFormat.getCurrencyInstance().format(item.getPrice()));

            InputStream inputStream = null;
            try {
                inputStream = getAssets().open(item.getImage());
                Drawable drawable = Drawable.createFromStream(inputStream, null);
                itemImage.setImageDrawable(drawable);
            } catch (IOException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        }

    }
}
