package com.customer.ordermanagementsystem.services;


import com.customer.ordermanagementsystem.domain.item.Item;
import com.customer.ordermanagementsystem.domain.order.CustomerInfo;

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
