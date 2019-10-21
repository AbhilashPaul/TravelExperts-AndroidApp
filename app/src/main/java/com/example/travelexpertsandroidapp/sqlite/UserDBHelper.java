package com.example.travelexpertsandroidapp.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class UserDBHelper extends SQLiteOpenHelper {

    public UserDBHelper(Context context, String name,
                    SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create agent table
        db.execSQL(UserContract.CREATE_CUSTOMER_TABLE);
    }

    //method to upgrade schema
    @Override
    public void onUpgrade(SQLiteDatabase db,
                          int oldVersion, int newVersion) {

        Log.d("User DB", "Upgrading db from version "
                + oldVersion + " to " + newVersion);

        db.execSQL(UserContract.DROP_CUSTOMER_TABLE);
        onCreate(db);
    }
}
