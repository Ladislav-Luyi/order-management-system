package com.customer.ordermanagementsystem.services;

import com.customer.ordermanagementsystem.orders.Item;
import com.customer.ordermanagementsystem.orders.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.math.BigDecimal;
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
        BigDecimal price = new BigDecimal(0);

        for(Item item1 : order.getOrderList()) {
            price = price.add(item1.getPrice()) ;

            for (Item item2 : item1.getItemList()){
                price = price.add(item2.getPrice()) ;
            }
        }

        model.addAttribute(nameOfAttributeForMapping, price);
    }
}
