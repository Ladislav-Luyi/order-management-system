package com.customer.ordermanagementsystem.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class MenuTimeServiceImpl implements MenuTimeService {

    @Value("${maxHourForMenu}")
    private int maxHourForMenu;

    @Override
    public String getCurrentDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        String day =  cal.get(Calendar.YEAR)
                + "-"
                + String.format("%02d", (  cal.get(Calendar.MONTH) + 1 ) )
                + "-"
                + String.format("%02d", cal.get(Calendar.DAY_OF_MONTH) );

        return day;
    }


    private int getCurrentHour() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        return cal.get(Calendar.HOUR_OF_DAY);

    }


    @Override
    public boolean isMenuForCurrentHour() {
        if (getCurrentHour() <= maxHourForMenu)
            return true;
        else
            return false;
    }
}
