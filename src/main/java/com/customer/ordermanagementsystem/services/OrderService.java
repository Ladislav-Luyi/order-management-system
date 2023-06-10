package com.customer.ordermanagementsystem.services;


import com.customer.ordermanagementsystem.domain.item.Item;
import com.customer.ordermanagementsystem.domain.order.Order;
import com.customer.ordermanagementsystem.domain.order.CustomerInfo;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public interface OrderService {

    void addOrderedItemsToModel(Model model, String nameOfAttributeForMapping);
    void addSingleOrderedItemToModel(Model model, int i, String nameOfAttributeForMapping);
    void addTotalPrice(Model model, String nameOfAttributeForMapping);
    void addItemToList(Item item);
    void removeItemFromList(int index);
    void addItemToIndexInList(int index, Item item);
    void removeIndexFromInnerList(int indexOuter, int indexInner);
    void setCustomerInfo(CustomerInfo customerInfo);
    void refreshPrice();
    Order getOrderInstance();
    void saveOrder();
    boolean isHigherThanMinimalValue();
    void addMinimalOrderValueToModel(Model model, String nameOfAttributeForMapping);
}
