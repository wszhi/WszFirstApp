package com.example.szwang.wszfirstapp.dao;

import java.util.List;

/**
 * Created by szwang on 5/10/16.
 */
public interface HistoryService {
    public boolean addHistory(String historyItem);
    public List<String> listHistory();
    public void delHistory();
}
