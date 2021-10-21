package com.example.tiemchungdientu;

import androidx.annotation.NonNull;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Converters {
    public static Long calendarToDatestamp(Calendar calendar){
        return calendar.getTimeInMillis();
    }

    public static Calendar datestampToCalendar(long value){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(value);
        return calendar;
    }

    public static Calendar stringToCalendar(String stringDate) {
        Calendar c1 = Calendar.getInstance();
        Date date = Date.valueOf(stringDate);
        c1.setTime(date);
        return c1;
    }

    public static long stringToDatestamp(String stringDate) {
        return calendarToDatestamp(stringToCalendar(stringDate));
    }

    public static String calendarToString_VI(Calendar calendar){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(calendar.getTime());
    }

    public static String calendarToString_US(Calendar calendar){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(calendar.getTime());
    }

    public static Calendar string_JavaToCalendar (String stringDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c1 = Calendar.getInstance();
        Date date = Date.valueOf(stringDate);
        c1.setTime(date);
        return c1;
    }

    public static String string_VNToString_US (String stringDate) {
        String[] splitsDate = stringDate.split("/");
        String finalDate = splitsDate[2] + "-" + splitsDate[1] + "-" + splitsDate[0];
        return finalDate;
    }

}
