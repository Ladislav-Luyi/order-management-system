package com.customer.ordermanagementsystem.services;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public interface CompanyService {
    void openAndCloseStore(String status);

    void openAndCloseStoreWithMessage(String status, String message);

    String getOpenAndCloseStoreMessage();

    boolean isStoreOpen();
}
