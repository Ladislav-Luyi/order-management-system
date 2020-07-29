package com.customer.ordermanagementsystem.services;

import com.customer.ordermanagementsystem.orders.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Component
public class OrderServiceForSpringModelImpl implements OrderServiceForSpringModel{

    private final Order order;

    @Autowired
    public OrderServiceForSpringModelImpl(Order order) {
        this.order = order;
    }

    @Override
    public void addOrderedItemsToModel(Model model) {
        model.addAttribute("orderedItem", order.getOrderList());
    }

    @Override
    public void addSingleOrderedItemToModel(Model model, int i) {
        model.addAttribute("orderedItem", order.getOrderList().get(i));
    }
}
