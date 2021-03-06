package com.hadi.resturant.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.hadi.resturant.R;
import com.hadi.resturant.adapter.DataItemListAdapter;
import com.hadi.resturant.model.DataItem;
import com.hadi.resturant.sample.SampleDataProvider;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String ITEM_KEY = "item_key";
    // Creating Placeholder for the list item data
    private List<DataItem> dataItems = SampleDataProvider.dataItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Getting A Reference for the custom tool bar
        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getResources().getString(R.string.app_name));
        toolbar.setSubtitle("Menu Items");

        // Sorting the list item alphabetically
        Collections.sort(dataItems, new Comparator<DataItem>() {
            @Override
            public int compare(DataItem o1, DataItem o2) {
                return o1.getItemName().compareTo(o2.getItemName());
            }
        });

        ListView listView = findViewById(R.id.items);
        DataItemListAdapter adapter = new DataItemListAdapter(this, dataItems);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent detailIntent = new Intent(MainActivity.this, DetailsActivity.class);
                detailIntent.putExtra(ITEM_KEY, dataItems.get(position));
                startActivity(detailIntent);
            }
        });

    }
}
