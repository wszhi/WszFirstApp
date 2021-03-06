package com.example.szwang.wszfirstapp.db;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.example.szwang.wszfirstapp.bean.History;

import com.example.szwang.wszfirstapp.db.HistoryDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig historyDaoConfig;

    private final HistoryDao historyDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        historyDaoConfig = daoConfigMap.get(HistoryDao.class).clone();
        historyDaoConfig.initIdentityScope(type);

        historyDao = new HistoryDao(historyDaoConfig, this);

        registerDao(History.class, historyDao);
    }
    
    public void clear() {
        historyDaoConfig.getIdentityScope().clear();
    }

    public HistoryDao getHistoryDao() {
        return historyDao;
    }

}
