package com.customer.ordermanagementsystem.services;

import org.springframework.stereotype.Service;

@Service
public interface OpeningHoursService {
    /*
    This method returns boolean whether the store is open according opening hours
     */
    boolean isStoreOpen();
}
