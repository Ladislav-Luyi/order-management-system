package com.customer.ordermanagementsystem.shop.service;


import com.customer.ordermanagementsystem.shop.domain.item.Item;
import com.customer.ordermanagementsystem.shop.domain.order.CustomerInfo;

import java.math.BigDecimal;
import java.util.List;


public interface OrderService {
    void addItemToList(Item item);

    void removeItemFromList(int index);

    void addItemToIndexInList(int index, Item item);

    void removeIndexFromInnerList(int indexOuter, int indexInner);

    void setCustomerInfo(CustomerInfo customerInfo);

    void saveOrder();

    boolean isHigherThanMinimalValue();

    List<Item> getOrders();

    BigDecimal getMinimalOrderValue();

    BigDecimal getTotalPrice();
}
