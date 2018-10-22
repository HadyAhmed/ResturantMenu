package com.hadi.resturant.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.hadi.resturant.JSONHelper;
import com.hadi.resturant.R;
import com.hadi.resturant.adapter.DataItemAdapter;
import com.hadi.resturant.model.DataItem;
import com.hadi.resturant.sample.SampleDataProvider;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // Key For The Passed Item to Detail Activity
    public static final String ITEM_KEY = "item_key";
    // Request Code For The Passed Intent for results to login activity
    public static final int SIGN_IN_REQUEST = 1001;
    // Key For The Incoming Mail from the login activity
    public static final String USER_DATA = "user_email_key";
    // Instance For Debugging
    private static final String TAG = MainActivity.class.getSimpleName();
    // Request Code For Accessing the external storage
    private static final int READ_TO_EXTERNAL_REQUEST_CODE = 1002;
    // Creating Placeholder for the list item data
    private List<DataItem> dataItems = SampleDataProvider.dataItemList;
    // Reference To Check The Current Permission was granted or not yet
    private boolean accessToExternalStorage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Getting A Reference for the custom tool bar
        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getResources().getString(R.string.app_name));
        toolbar.setSubtitle(R.string.subtitle_menu_item);

        // Sorting the list item alphabetically
        Collections.sort(dataItems, new Comparator<DataItem>() {
            @Override
            public int compare(DataItem o1, DataItem o2) {
                return o1.getItemName().compareTo(o2.getItemName());
            }
        });

        // find the reference for the recycler view in the activity_main.xml
        RecyclerView recyclerView = findViewById(R.id.items);
        // Get the reference from the settings xml file
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isGrid = pref.getBoolean(getString(R.string.grid_key), false);
        if (isGrid) {
            // if the user select grid then show up menu in grid.
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            // else show up menu item in linear and add divider between them.
            recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        }
        DataItemAdapter adapter = new DataItemAdapter(this, dataItems);
        recyclerView.setAdapter(adapter);
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
                break;
            case R.id.settings_menu_item:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                break;
            case R.id.export_mItem:
                if (!accessToExternalStorage) {
                    checkStoragePermission();
                } else {
                    JSONHelper.exportToJSON(this, dataItems);
                }
                break;
            case R.id.import_mItem:
                if (!accessToExternalStorage) {
                    checkStoragePermission();
                } else {
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    // Do Some Code Here
                }
                break;
        }
        return super.onOptionsItemSelected(item);
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

    /**
     * @return if the external storage state is mounted
     */
    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    /**
     * @return if the external storage mounted and can be read
     */
    private boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return (Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state));
    }

    /**
     * @return if the app has checked the external storage access or not.
     */
    private boolean checkStoragePermission() {
        if (!isExternalStorageReadable() && !isExternalStorageWritable()) {
            Toast.makeText(this, "This App Needs External Storage Memory", Toast.LENGTH_SHORT).show();
            return false;
        }
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, READ_TO_EXTERNAL_REQUEST_CODE);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case READ_TO_EXTERNAL_REQUEST_CODE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    accessToExternalStorage = true;
                }
                break;

        }
    }
}
