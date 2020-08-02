package com.customer.ordermanagementsystem.services;

import com.customer.ordermanagementsystem.orders.Item;
import com.customer.ordermanagementsystem.orders.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.text.DecimalFormat;

@Component
public class OrderServiceForSpringModelImpl implements OrderServiceForSpringModel{

    private final Order order;

    @Autowired
    public OrderServiceForSpringModelImpl(Order order) {
        this.order = order;
    }

    @Override
    public void addOrderedItemsToModel(Model model,String nameOfAttributeForMapping) {
        model.addAttribute(nameOfAttributeForMapping, order.getOrderList());
    }

    @Override
    public void addSingleOrderedItemToModel(Model model, int i,String nameOfAttributeForMapping) {
        model.addAttribute(nameOfAttributeForMapping, order.getOrderList().get(i));
    }

    @Override
    public void addTotalPrice(Model model, String nameOfAttributeForMapping) {
        float price = 0;

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        for(Item item : order.getOrderList()) {
            df.format(price);

            price += item.getPrice();
        }

        String roundedPrice2Decimal = df.format(price);


        model.addAttribute(nameOfAttributeForMapping, roundedPrice2Decimal);

    }
}
