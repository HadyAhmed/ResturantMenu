package com.hadi.resturant.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hadi.resturant.model.DataItem;

public class DataSource {
    private Context mContext; // Could be the activity or the application context
    private SQLiteDatabase mDatabase;
    private SQLiteOpenHelper mDBHelper;

    public DataSource(Context context) {
        this.mContext = context;
        mDBHelper = new DBHelper(mContext);
        mDatabase = mDBHelper.getWritableDatabase(); // Creating or open database to be read or write
    }

    public void open() {
        mDatabase = mDBHelper.getWritableDatabase();
    }

    public void close() {
        mDBHelper.close();
    }

    public DataItem createItem(DataItem item) {
        ContentValues values = item.toValues();
        // this will add the object of DataItem type to the database using it's ContentValues Structure
        mDatabase.insert(ItemsTable.TABLE_ITEMS, null, values);
        return item;
    }

    public long getDatabaseCount() {
        return DatabaseUtils.queryNumEntries(mDatabase, ItemsTable.TABLE_ITEMS);
    }
}
