package com.customer.ordermanagementsystem.services;

import org.springframework.stereotype.Service;

@Service
public interface MenuTimeService {
    String getCurrentDate();
    boolean isMenuForCurrentHour();
}
