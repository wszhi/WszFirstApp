package com.example.szwang.wszfirstapp;

import android.content.Intent;
import android.widget.Button;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.robolectric.Shadows.shadowOf;

@Config(sdk = 21)
@RunWith(WszTestRunner.class)
public class CalculateActivityTest {

    private CalculateActivity calculateActivity;
    private HistoryActivity historyActivity;
    private Button historyButton;

    @Before
    public void setUp() throws Exception {


        calculateActivity = Robolectric.buildActivity(CalculateActivity.class).create().get();
        //calculateActivity = new CalculateActivity();
        //calculateActivity.onCreate(null);
        historyButton = (Button) calculateActivity.findViewById(R.id.history);
        historyActivity = Robolectric.buildActivity(HistoryActivity.class).create().get();
    }

    @Test
    public void shouldSetTitleWhenActivityCreated() {
        assertNotNull(calculateActivity);
        assertEquals("calculate", calculateActivity.getTitle());
    }

    @Test
    public void ShouldReturnCorrectResultWhenSum() throws Exception {
        String calculate = "3+2.0";
        Double result = Double.parseDouble(calculateActivity.calculateAction(calculate));
        Double request = 5D;
        assertEquals(request, result);
    }

    @Test
    public void ShouldReturnCorrectResultWhenMinus() throws Exception {
        String calculate = "1-10.0";
        Double result = Double.parseDouble(calculateActivity.calculateAction(calculate));
        Double request = -9D;
        assertEquals(request, result);
    }

    @Test
    public void ShouldReturnCorrectResultWhenNumberSmallThanZero() throws Exception {
        String calculate = "-1-10.0";
        Double result = Double.parseDouble(calculateActivity.calculateAction(calculate));
        Double request = -11D;
        assertEquals(request, result);
    }

    @Test
    public void ShouldReturnCorrectResultWhenMulti() throws Exception {
        String calculate = "3*2.0";
        Double result = Double.parseDouble(calculateActivity.calculateAction(calculate));
        Double request = 6D;
        assertEquals(request, result);
    }

    @Test
    public void shouldReturnFourWhenClickOneSumThree() throws Exception {
        // input 1
        calculateActivity.buttonNum.get(1).performClick();
        // input +
        calculateActivity.buttonOption.get(2).performClick();
        //input 3
        calculateActivity.buttonNum.get(3).performClick();
        //input =
        calculateActivity.buttonOption.get(4).performClick();
        Double request = 4D;
        Double result = Double.parseDouble(calculateActivity.result.getText().toString());
        assertEquals(request, result);
    }

    @Test
    public void shouldReturnNewPageWhenClickHistory() throws Exception {

        historyButton.performClick();
        Intent nextStartedIntent = shadowOf(calculateActivity).getNextStartedActivity();
        assertEquals(HistoryActivity.class.getName(), nextStartedIntent.getComponent().getClassName());
    }

    @Test
    public void ShouldReturnCorrectResultWhenDevided() throws Exception {
        String calculate = "6/2.0";
        Double result = Double.parseDouble(calculateActivity.calculateAction(calculate));
        Double request = 3D;
        assertEquals(request, result);
    }

    @Test
    public void ShouldReturnErrorWhenDevidedByZero() throws Exception {
        String calculate = "6/0";
        assertEquals("Error", calculateActivity.calculateAction(calculate));
    }

    @Test
    public void ShouldReturnCorrectResultWhenContinueMoreCalculate() throws Exception {
        String calculate = "3-2*2+2";
        Double result = Double.parseDouble(calculateActivity.calculateAction(calculate));
        Double request = 1D;
        assertEquals(request, result);
    }

    @Test
    public void ShouldReturnCorrectArrayListWhenAnalyticString() throws Exception {
        ArrayList<String> numbersAndOperator = new ArrayList<>();
        ArrayList<String> result = new ArrayList(Arrays.asList("3", "-", "2", "+", "2", "*", "2"));
        String calculate = "3-2+2*2";
        calculateActivity.analyticString(calculate, numbersAndOperator);
        assertEquals(result, numbersAndOperator);
    }

    @Test
    public void ShouldReturnCorrectNumberWhenCalculateArrayList() throws Exception {
        ArrayList<String> numbersAndOperator = new ArrayList(Arrays.asList("3", "-", "2", "+", "2", "*", "2"));
        calculateActivity.calculateNumbers(numbersAndOperator);
        Double result = Double.parseDouble(numbersAndOperator.get(0));
        Double expected = 5D;
        assertEquals(expected, result);
    }

    @Test
    public void ShouldReturnNoMultiAndDevidedArrayListWhenDoOperatorAction() throws Exception {
        ArrayList<String> numbersAndOperator = new ArrayList(Arrays.asList("3", "-", "2", "+", "2", "*", "2"));
        ArrayList<String> result = new ArrayList(Arrays.asList("3", "-", "2", "+", "4.0"));
        calculateActivity.optionsAction("*", "/", 0, numbersAndOperator.size() - 1, numbersAndOperator);
        assertEquals(result, numbersAndOperator);
    }

    @Test
    public void ShouldReturnNoSumAndMinusArrayListWhenDoOperatorAction() throws Exception {
        ArrayList<String> numbersAndOperator = new ArrayList(Arrays.asList("3", "-", "2", "+", "2"));
        ArrayList<String> result = new ArrayList(Arrays.asList("3.0"));
        calculateActivity.optionsAction("+", "-", 0, numbersAndOperator.size() - 1, numbersAndOperator);
        assertEquals(result, numbersAndOperator);
    }


}