package com.customer.ordermanagementsystem.shop.services;

import com.customer.ordermanagementsystem.shop.domain.item.Item;
import com.customer.ordermanagementsystem.shop.domain.item.Type;

import java.util.List;


public interface ItemService {
    List<Item> getItems();

    List<Item> getItemsOfType(Type type);
}
