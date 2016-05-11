package com.example.szwang.wszfirstapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.szwang.wszfirstapp.db.DbOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class HistoryDao implements HistoryService {
    private DbOpenHelper helper = null;
    public HistoryDao(Context context) {
        helper = new DbOpenHelper(context,"history.db",null,1);
    }
    @Override
    public boolean addHistory(String historyItem){
        boolean result=false;
        SQLiteDatabase database=helper.getWritableDatabase();
        database.beginTransaction(); // 开启事务
        try{
            ContentValues value = new ContentValues();
            value.put("historyItem", historyItem);
            database.insert("history", null, value);
            result=true;
            database.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            database.endTransaction();
        }
        return result;
    }
    @Override
    public List<String> listHistory(){
        List<String> histories = new ArrayList<>();
        SQLiteDatabase database=helper.getReadableDatabase();
        try{
            String sql="select * from history";
            Cursor cursor=database.rawQuery(sql,null);
            while (cursor.moveToNext()){
                histories.add(cursor.getString(cursor.getColumnIndex("historyItem")));
            }
            cursor.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return histories;
    }
    @Override
    public void delHistory(){
        String sql="DELETE FROM history";
        String sql2="DELETE FROM sqlite_sequence WHERE name = 'history'";
        SQLiteDatabase database=helper.getWritableDatabase();
        database.execSQL(sql);
        database.execSQL(sql2);
        database.close();
    }
}
