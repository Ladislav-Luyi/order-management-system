package com.customer.ordermanagementsystem.company.service;

public interface CompanyService {
    void openAndCloseStore(String status);

    void openAndCloseStoreWithMessage(String status, String message);

    String getOpenAndCloseStoreMessage();

    boolean isStoreOpen();
}
