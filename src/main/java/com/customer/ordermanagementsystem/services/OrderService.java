package com.customer.ordermanagementsystem.services;


import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public interface OrderService {

    void addOrderedItemsToModel(Model model, String nameOfAttributeForMapping);
    void addSingleOrderedItemToModel(Model model, int i, String nameOfAttributeForMapping);
    void addTotalPrice(Model model, String nameOfAttributeForMapping);
    void refreshPrice();
}
