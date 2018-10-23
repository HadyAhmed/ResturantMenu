package com.hadi.resturant.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.hadi.resturant.model.DataItem;

import java.util.ArrayList;
import java.util.List;

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

    public void seedDatabase(List<DataItem> dataItems) {
        long numItems = getDatabaseCount();
        if (numItems == 0) {
            for (DataItem items : dataItems) {
                try {
                    createItem(items);
                } catch (SQLException e) {
                    Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            Toast.makeText(mContext, "Items Inserted Into Database", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "Database Already Exist", Toast.LENGTH_SHORT).show();
        }
    }

    public List<DataItem> getAllItems() {
        List<DataItem> dataItemList = new ArrayList<>();
        // Cursor that loops allover the database table's queries.
        Cursor cursor = mDatabase.query(ItemsTable.TABLE_ITEMS, ItemsTable.ALL_COLUMNS,
                null, null, null, null, ItemsTable.COLUMN_NAME);
        // Creating a new object of DataItem type and add it into the list.
        while (cursor.moveToNext()) {
            DataItem item = new DataItem();
            item.setItemId(cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_ID)));
            item.setItemName(cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_NAME)));
            item.setCategory(cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_CATEGORY)));
            item.setDescription(cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_DESCRIPTION)));
            item.setImage(cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_IMAGE)));
            item.setSortPosition(cursor.getInt(cursor.getColumnIndex(ItemsTable.COLUMN_POSITION)));
            item.setPrice(cursor.getDouble(cursor.getColumnIndex(ItemsTable.COLUMN_PRICE)));
            dataItemList.add(item);
        }
        cursor.close();
        return dataItemList;
    }
}
