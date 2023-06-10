package com.customer.ordermanagementsystem.services;

import java.util.Calendar;
import java.util.Date;

public class TimeHelper {
    static int getCurrentHour() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        return cal.get(Calendar.HOUR_OF_DAY);
    }

    static int getCurrentDayFromWeek() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int day = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (day == 0) day = 7;
        return day;
    }

    static String getCurrentDayAndMonth() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        return cal.get(Calendar.DAY_OF_MONTH) + "." + (cal.get(Calendar.MONTH) + 1);
    }
}
