package com.example.szwang.wszfirstapp.dao;

import com.example.szwang.wszfirstapp.entity.History;

import java.util.List;
import java.util.Objects;

/**
 * Created by szwang on 5/10/16.
 */
public interface HistoryService {
    public boolean addHistory(String historyItem);
    public List<String> listHistory();
    public void delHistory();
}
