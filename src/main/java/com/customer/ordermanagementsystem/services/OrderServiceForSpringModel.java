package com.customer.ordermanagementsystem.services;


import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public interface OrderServiceForSpringModel {

    void addOrderedItemsToModel(Model model);
    void addSingleOrderedItemToModel(Model model, int i);
}
