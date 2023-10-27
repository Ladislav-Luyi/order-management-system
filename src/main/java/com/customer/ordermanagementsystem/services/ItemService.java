package com.customer.ordermanagementsystem.services;

import com.customer.ordermanagementsystem.domain.item.Item;
import com.customer.ordermanagementsystem.domain.item.Type;

import java.util.List;


public interface ItemService {
    List<Item> getItems();

    List<Item> getItemsOfType(Type type);
}
