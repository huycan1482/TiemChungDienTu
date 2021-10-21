package com.example.tiemchungdientu;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.Calendar;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void calendarToString_VI(){
        Calendar calendar = Calendar.getInstance();
        String result = Converters.calendarToString_VI(calendar);
        Assert.assertEquals("16-10-2021", result);
    }
}