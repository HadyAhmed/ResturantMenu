package com.hadi.resturant.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.hadi.resturant.R;
import com.hadi.resturant.adapter.DataItemAdapter;
import com.hadi.resturant.model.DataItem;
import com.hadi.resturant.sample.SampleDataProvider;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String ITEM_KEY = "item_key";
    public static final int SIGN_IN_REQUEST = 1001;
    public static final String USER_DATA = "user_email_key";
    private static final String TAG = MainActivity.class.getSimpleName();
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

        RecyclerView listView = findViewById(R.id.items);
        DataItemAdapter adapter = new DataItemAdapter(this, dataItems);
        listView.setAdapter(adapter);
        listView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.login_menu_item:
                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivityForResult(loginIntent, SIGN_IN_REQUEST);
                return true;
            default:
                //TODO : Add The Settings Activity Intent To Start Here
                return true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SIGN_IN_REQUEST && resultCode == RESULT_OK) {
            SharedPreferences.Editor editor = getSharedPreferences(USER_DATA, MODE_PRIVATE).edit();
            if (data != null) {
                editor.putString(LoginActivity.EMAIL_KEY, data.getStringExtra(LoginActivity.EMAIL_KEY));
            }
            editor.apply();
        }
    }
}
