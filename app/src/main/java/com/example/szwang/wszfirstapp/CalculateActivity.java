package com.example.szwang.wszfirstapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.szwang.wszfirstapp.dao.HistoryDbService;
import com.example.szwang.wszfirstapp.dao.HistoryService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CalculateActivity extends Activity {
    @BindView(R.id.result)
    protected EditText result;
    @BindViews({ R.id.multi, R.id.division, R.id.sum, R.id.minus,R.id.equal,R.id.delete,R.id.clear,R.id.history })
    List<Button> buttonOption;

    @BindViews({ R.id.zero, R.id.one, R.id.two, R.id.three,
            R.id.four, R.id.five, R.id.six, R.id.seven, R.id.eight, R.id.nine, R.id.point })
    List<Button> buttonNum;
    static String SUM =  "+";
    static String MINUS =  "-";
    static String MULTI =  "*";
    static String DIV =  "/";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferencesEditor;

    HistoryService historyService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculate);
        ButterKnife.bind(this);
        historyService= new HistoryDbService(this);
        sharedPreferences=getSharedPreferences("resultOfCalculate", MODE_PRIVATE);
        sharedPreferencesEditor=sharedPreferences.edit();
        String lastNumber = sharedPreferences.getString("lastNumber","");
        result.setText(lastNumber);

    }

    @OnClick({ R.id.multi, R.id.division, R.id.sum, R.id.minus,R.id.zero, R.id.one, R.id.two, R.id.three,
            R.id.four, R.id.five, R.id.six, R.id.seven, R.id.eight, R.id.nine, R.id.point })
    public void showText(View v) {
        String inputText = result.getText().toString();
        Button button=(Button) findViewById(v.getId());
        result.setText(inputText + button.getText());
    }
    @OnClick(R.id.delete)
    public void delete() {
        String results=result.getText().toString();
        if(results.length()>0) {
            result.setText(results.substring(0, results.length()-1));
        }
    }
    @OnClick(R.id.clear)
    public void clear() {
        result.setText("");
    }
    @OnClick(R.id.history)
    public void history() {
        Intent intent = new Intent(CalculateActivity.this, HistoryActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.equal)
    public void equal() {
        String results=result.getText().toString();
        String resultNumber = calculateAction(results);
        result.setText(resultNumber);
        sharedPreferencesEditor.putString("lastNumber", resultNumber);
        sharedPreferencesEditor.commit();
        historyService.addHistory(results + "=" + resultNumber);
    }

    public String calculateAction(String calculateString)
    {
        ArrayList<String> numbersAndOperator = new ArrayList<>();
        analyticString(calculateString, numbersAndOperator);
        calculateNumbers(numbersAndOperator);
        return  numbersAndOperator.get(0);
    }
    public void optionsAction(String option1,String option2,int i,int stop,ArrayList<String> numbersAndOperator){
        while(i <= stop && numbersAndOperator.size()>=3)
        {
            String element = numbersAndOperator.get(i);
            double resultBuff = 0;
            if(element.equals(option1))
            {
                if(option1.equals(MULTI)) {
                    resultBuff = Double.parseDouble(numbersAndOperator.get(i - 1)) * Double.parseDouble(numbersAndOperator.get(i + 1));
                }

                if(option1.equals(SUM)) {
                    resultBuff = Double.parseDouble(numbersAndOperator.get(i - 1)) + Double.parseDouble(numbersAndOperator.get(i + 1));
                }
                numbersAndOperator.remove(i + 1);
                numbersAndOperator.remove(i);
                numbersAndOperator.set(i - 1, String.valueOf(resultBuff));
                stop = stop - 2;
            }
            if(element.equals(option2))
            {
                if(option2.equals(DIV)) {
                    if(Double.parseDouble(numbersAndOperator.get(i + 1)) == 0){
                        numbersAndOperator.set(0,"Error");
                        return;
                    }
                    resultBuff = Double.parseDouble(numbersAndOperator.get(i - 1)) / Double.parseDouble(numbersAndOperator.get(i + 1));
                }
                if(option2.equals(MINUS)) {
                    resultBuff = Double.parseDouble(numbersAndOperator.get(i - 1)) - Double.parseDouble(numbersAndOperator.get(i + 1));
                }
                numbersAndOperator.remove(i + 1);
                numbersAndOperator.remove(i);
                numbersAndOperator.set(i - 1, String.valueOf(resultBuff));
                stop = stop - 2;
            }
            else
            {
                i++;
            }
        }
    }
    public void calculateNumbers(ArrayList<String> numbersAndOperator)
    {
        int start=0;
        int stop=numbersAndOperator.size() - 1;
        int i = start;
        optionsAction(MULTI,DIV,i,stop,numbersAndOperator);
        i = start;
        optionsAction(SUM,MINUS,i,stop,numbersAndOperator);
    }
    public void analyticString(String calculateString,ArrayList<String> numbersAndOperator)
    {
        int positionStart = 0;
        int positionEnd = 0;
        char[] calculateChar = calculateString.toCharArray();
        while(positionStart != calculateChar.length )
        {
            positionEnd = positionStart;
            while( Character.isDigit(calculateChar[positionStart]) == Character.isDigit(calculateChar[positionEnd]) || calculateChar[positionEnd] == '.')
            {
                positionEnd++;
                if(positionEnd == calculateChar.length)
                {
                    break;
                }
            }
            String tmp = new String(calculateChar, positionStart, positionEnd - positionStart);
            int lengthOfList=numbersAndOperator.size();
            if(Character.isDigit(calculateChar[positionStart])) {
                if(lengthOfList>=2){
                    if(numbersAndOperator.get(lengthOfList-1).equals(MINUS)){
                        try
                        {
                            Double.parseDouble(numbersAndOperator.get(lengthOfList-2));
                        }catch(Exception e)
                        {
                            tmp=MINUS+tmp;
                            numbersAndOperator.remove(lengthOfList-1);
                        }
                    }
                }
                if(lengthOfList == 1){
                    if(numbersAndOperator.get(lengthOfList-1).equals(MINUS)){
                        tmp=MINUS+tmp;
                        numbersAndOperator.remove(lengthOfList-1);
                    }
                }
                numbersAndOperator.add(tmp);
            }
            else if(tmp.equals(SUM) || tmp.equals(MINUS) || tmp.equals(MULTI) || tmp.equals(DIV)){
                    numbersAndOperator.add(tmp);
            }
            else{
                throw new RuntimeException("Operator not allowd:" + tmp);
            }
            positionStart = positionEnd;
        }

    }

}
