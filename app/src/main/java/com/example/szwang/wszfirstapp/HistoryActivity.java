package com.example.szwang.wszfirstapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.szwang.wszfirstapp.dao.HistoryDao;
import com.example.szwang.wszfirstapp.dao.HistoryService;
import com.example.szwang.wszfirstapp.db.DbOpenHelper;
import com.example.szwang.wszfirstapp.entity.History;

import java.util.List;

public class HistoryActivity extends Activity implements View.OnClickListener{
    protected ListView listView;
    protected Button cleanButton;
    private DbOpenHelper dbOpenHelper;
    private HistoryService historyService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbOpenHelper=new DbOpenHelper(this,"history.db",null,1);
        setContentView(R.layout.history_view);
        historyService = new HistoryDao(this);
        List<String> histories =historyService.listHistory();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                HistoryActivity.this, android.R.layout.simple_list_item_1, histories);
        listView=(ListView) findViewById(R.id.list_history);
        listView.setAdapter(adapter);

        cleanButton = (Button) findViewById(R.id.clean);
        cleanButton.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.clean){
            historyService.delHistory();
            Intent intent = new Intent(HistoryActivity.this, CalculateActivity.class);
            startActivity(intent);
        }
    }
}
