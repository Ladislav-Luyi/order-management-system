package com.customer.ordermanagementsystem.services;

public interface CompanyService {
    void openAndCloseStore(String status);

    void openAndCloseStoreWithMessage(String status, String message);

    String getOpenAndCloseStoreMessage();

    boolean isStoreOpen();
}
