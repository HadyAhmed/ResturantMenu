package com.hadi.resturant.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataSource {
    private Context mContext; // Could be the activity or the application context
    private SQLiteDatabase mDatabase;
    private SQLiteOpenHelper mDBHelper;

    public DataSource(Context context) {
        this.mContext = context;
        mDBHelper = new DBHelper(mContext);
        mDatabase = mDBHelper.getWritableDatabase();
    }

    void open() {
        mDatabase = mDBHelper.getWritableDatabase();
    }

    void close() {
        mDBHelper.close();
    }
}
