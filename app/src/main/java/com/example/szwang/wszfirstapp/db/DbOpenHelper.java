package com.example.szwang.wszfirstapp.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by szwang on 5/10/16.
 */
public class DbOpenHelper extends SQLiteOpenHelper {

    public static final String CREATE_TABLE = "create table History(id integer primary key autoincrement,historyItem varchar(64))";
    private Context myContext;
    public DbOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        myContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
        Log.d("Database:", "Create succeeded");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
    }
}
