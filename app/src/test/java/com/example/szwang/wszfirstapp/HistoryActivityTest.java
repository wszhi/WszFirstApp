package com.example.szwang.wszfirstapp;

import android.content.Intent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;
import static org.robolectric.Shadows.shadowOf;

@Config(sdk = 21)
@RunWith(WszTestRunner.class)
public class HistoryActivityTest {

    private HistoryActivity historyActivity;
    private CalculateActivity calculateActivity;
    @Before
    public void setUp() throws Exception {
        historyActivity = Robolectric.buildActivity(HistoryActivity.class).create().get();
        calculateActivity = Robolectric.buildActivity(CalculateActivity.class).create().get();
    }
    @Test
    public void shouldSetTitleWhenActivityCreated() {
        assertNotNull(historyActivity);
        assertEquals("history", historyActivity.getTitle());
    }
    @Test
    public void shouldReturnNewPageWhenClickClean() throws Exception {
        historyActivity.cleanButton.performClick();
        Intent nextStartedIntent = shadowOf(historyActivity).getNextStartedActivity();
        assertEquals(CalculateActivity.class.getName(), nextStartedIntent.getComponent().getClassName());

        calculateActivity.buttonOption[7].performClick();
        nextStartedIntent = shadowOf(calculateActivity).getNextStartedActivity();
        assertEquals(HistoryActivity.class.getName(), nextStartedIntent.getComponent().getClassName());
        assertEquals(0,historyActivity.listView.getMaxScrollAmount());
    }
}