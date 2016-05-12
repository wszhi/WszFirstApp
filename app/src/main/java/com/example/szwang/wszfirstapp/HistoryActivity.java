package com.example.szwang.wszfirstapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.szwang.wszfirstapp.dao.HistoryDbService;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HistoryActivity extends Activity{
    @BindView(R.id.list_history)
    protected ListView listView;
    @BindView(R.id.clean)
    protected Button cleanButton;

    private com.example.szwang.wszfirstapp.dao.HistoryService historyService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_view);
        ButterKnife.bind(this);
        historyService = new HistoryDbService(this);
        List<String> histories =historyService.listHistory();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                HistoryActivity.this, android.R.layout.simple_list_item_1, histories);
        listView.setAdapter(adapter);
    }
    @OnClick(R.id.clean)
    public void cleanHistory() {
        historyService.delHistory();
        Intent intent = new Intent(HistoryActivity.this, com.example.szwang.wszfirstapp.CalculateActivity.class);
        startActivity(intent);
    }
}
