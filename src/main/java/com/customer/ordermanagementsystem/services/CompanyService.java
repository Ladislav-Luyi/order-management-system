package com.customer.ordermanagementsystem.services;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public interface CompanyService {
    void openAndCloseStore(String status);
    void openAndCloseStoreWithMessage(String status, String message);
    boolean isStoreOpenAccordingTimeSchedule();
    String getOpenAndCloseStoreMessage();
    boolean isStoreOpen();
    void addItemToModel(Model model, String nameOfAttributeForMapping);
}
