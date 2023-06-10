package com.customer.ordermanagementsystem.services;

import com.customer.ordermanagementsystem.domain.item.Item;
import com.customer.ordermanagementsystem.domain.order.Order;
import com.customer.ordermanagementsystem.domain.order.CustomerInfo;
import com.customer.ordermanagementsystem.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.math.BigDecimal;


@Component
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final Order order;
    private final OrderRepository orderRepository;


    @Autowired
    public OrderServiceImpl(Order order, OrderRepository orderRepository) {
        this.order = order;
        this.orderRepository = orderRepository;
    }

    @Override
    public void addOrderedItemsToModel(Model model, String nameOfAttributeForMapping) {
        model.addAttribute(nameOfAttributeForMapping, order.getOrderList());
    }

    @Override
    public void addSingleOrderedItemToModel(Model model, int i, String nameOfAttributeForMapping) {
        model.addAttribute(nameOfAttributeForMapping, order.getOrderList().get(i));
    }

    @Override
    public void addTotalPrice(Model model, String nameOfAttributeForMapping) {
        BigDecimal totalPriceWithDiscount = order.getTotalPriceDiscount();

        model.addAttribute(nameOfAttributeForMapping, totalPriceWithDiscount);
    }

    @Override
    public void addItemToList(Item item) {
        order.getOrderList().add(item);
    }

    @Override
    public void removeItemFromList(int index) {
        if (order.getOrderList().size() > 0)
            order.getOrderList().remove(index);
    }

    @Override
    public void addItemToIndexInList(int index, Item item) {
        if (order.getOrderList().size() > 0) {
            log.debug("Adding subItem " + item);
            order.getOrderList().get(index).getItemList().add(item);
        }
    }

    @Override
    public void removeIndexFromInnerList(int indexOuter, int indexInner) {
        if (order.getOrderList().size() > 0) {
            log.debug("Removing subItem " + indexInner);
            order.getOrderList().get(indexOuter).getItemList().remove(indexInner);
        }
    }

    @Override
    public void setCustomerInfo(CustomerInfo customerInfo) {
        order.setCustomerInfo(customerInfo);
    }


    @Override
    public void refreshPrice() {
        BigDecimal price = new BigDecimal(0);

        for (Item item1 : order.getOrderList()) {

            if (item1.getPrice() != null) {
                price = price.add(item1.getPrice());
            }


            for (Item item2 : item1.getItemList()) {
                price = price.add(item2.getPrice());
            }
        }

        order.setTotalPrice(price);

    }

    @Override
    public Order getOrderInstance() {
        return order;
    }

    @Override
    public void saveOrder() {
        Order orderToSave = new Order();
        orderToSave.setCustomerInfo(order.getCustomerInfo());
        orderToSave.setTotalPrice(order.getTotalPrice());
        orderToSave.setTotalDiscount(order.getTotalDiscount());
        orderToSave.setTotalPriceDiscount(order.getTotalPriceDiscount());
        orderToSave.setOrderText(order.toString());

        orderRepository.save(orderToSave);
    }

    @Override
    public boolean isHigherThanMinimalValue() {

        if (order.getTotalPriceDiscount().compareTo(order.getMinimalValueForOrder()) == -1)
            return false;
        else
            return true;
    }

    @Override
    public void addMinimalOrderValueToModel(Model model, String nameOfAttributeForMapping) {
        model.addAttribute(nameOfAttributeForMapping, order.getMinimalValueForOrder());
    }
}
