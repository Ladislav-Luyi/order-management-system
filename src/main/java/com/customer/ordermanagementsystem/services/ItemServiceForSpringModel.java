package com.customer.ordermanagementsystem.services;

import com.customer.ordermanagementsystem.orders.Type;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public interface ItemServiceForSpringModel {
    void addAllItemsToModel(Model model);
    void addSingleItemToModel(Model model, Type type);
}
