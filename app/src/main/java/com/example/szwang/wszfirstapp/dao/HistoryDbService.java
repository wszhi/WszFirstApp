package com.example.szwang.wszfirstapp.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.szwang.wszfirstapp.bean.History;
import com.example.szwang.wszfirstapp.db.DaoMaster;
import com.example.szwang.wszfirstapp.db.DaoSession;
import com.example.szwang.wszfirstapp.db.HistoryDao;

import java.util.ArrayList;
import java.util.List;

public class HistoryDbService implements HistoryService {
    DaoMaster.OpenHelper openHelper = null;
    SQLiteDatabase db=null;
    DaoMaster daoMaster;
    DaoSession daoSession;
    public HistoryDbService(Context context) {
        openHelper = new DaoMaster.DevOpenHelper(context, "history.db", null);
        db = openHelper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession =daoMaster.newSession();
    }
    @Override
    public boolean addHistory(String historyItem){
        History history = new History();
        history.setHistoryItem(historyItem);
        HistoryDao historyDao = daoSession.getHistoryDao();
        historyDao.insert(history);
        return true;

    }
    @Override
    public List<String> listHistory(){
        HistoryDao historyDao = daoSession.getHistoryDao();
        //通过queryBuilder查询
        List<History> historyList = historyDao.queryBuilder().list();


        List<String> histories = new ArrayList<>();
        for(History history : historyList){
            histories.add(history.getHistoryItem());
        }
        return histories;
    }
    @Override
    public void delHistory(){
        HistoryDao historyDao = daoSession.getHistoryDao();
        historyDao.deleteAll();
    }
}
