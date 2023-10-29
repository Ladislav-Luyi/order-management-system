package com.customer.ordermanagementsystem.shop.service;

import com.customer.ordermanagementsystem.shop.domain.item.Item;
import com.customer.ordermanagementsystem.shop.domain.order.CustomerInfo;
import com.customer.ordermanagementsystem.shop.domain.order.Order;
import com.customer.ordermanagementsystem.shop.domain.order.OrderDefaults;
import com.customer.ordermanagementsystem.shop.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.math.BigDecimal;
import java.util.List;


@Slf4j
@Service
@SessionScope
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final Order order = new Order();
    private final OrderRepository orderRepository;
    private final OrderDefaults orderDefaults;


    @Override
    // TODO add logic into Order class
    public void addItemToList(Item item) {
        order.getShoppingCart().add(item);
    }

    @Override
    // TODO add logic into Order class
    public void removeItemFromList(int index) {
        if (order.getShoppingCart().isEmpty()) {
            return;
        }
        order.getShoppingCart().remove(index);
    }

    @Override
    // TODO add logic into Order class
    public void addItemToIndexInList(int index, Item item) {
        if (order.getShoppingCart().isEmpty()) {
            return;
        }
        log.debug("Adding subItem " + item);
        order.getShoppingCart().get(index).getItemList().add(item);

    }

    @Override
    // TODO add logic into Order class
    public void removeIndexFromInnerList(int indexOuter, int indexInner) {
        if (order.getShoppingCart().isEmpty()) {
            return;
        }
        log.debug("Removing subItem " + indexInner);
        order.getShoppingCart().get(indexOuter).getItemList().remove(indexInner);

    }

    @Override
    public void setCustomerInfo(CustomerInfo customerInfo) {
        order.setCustomerInfo(customerInfo);
    }


    @Override
    public void saveOrder() {
        // TODO add logic into Order class to be called in setters
        order.setOrderText(new OrderDescriptionComposer().composeOrderText(order));
        orderRepository.save(order);
    }

    @Override
    public boolean isHigherThanMinimalValue() {
        return order.getPriceDetails().getPriceAfterDiscount().compareTo(orderDefaults.getMinimalValueForOrder()) >= 0;
    }

    @Override
    public List<Item> getOrders() {
        return order.getShoppingCart();
    }

    @Override
    public BigDecimal getMinimalOrderValue() {
        return orderDefaults.getMinimalValueForOrder();
    }

    @Override
    public BigDecimal getTotalPrice() {
        return order.calculateTotalPrice();
    }
}
