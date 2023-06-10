package com.customer.ordermanagementsystem.services;

import com.customer.ordermanagementsystem.domain.item.Type;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public interface ItemService {
    void addAllItemsToModel(Model model);

    void addSingleItemToModel(Model model, Type type);
}
