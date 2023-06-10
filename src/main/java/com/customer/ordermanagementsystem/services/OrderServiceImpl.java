package com.customer.ordermanagementsystem.services;

import com.customer.ordermanagementsystem.domain.item.Item;
import com.customer.ordermanagementsystem.domain.order.Order;
import com.customer.ordermanagementsystem.domain.order.CustomerInfo;
import com.customer.ordermanagementsystem.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;


@Component
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final Order order;
    private final OrderRepository orderRepository;
    private final DiscountService discountService;


    @Autowired
    public OrderServiceImpl(Order order, OrderRepository orderRepository, DiscountService discountService) {
        this.order = order;
        this.orderRepository = orderRepository;
        this.discountService = discountService;
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
        BigDecimal totalDiscount = discountService.getDiscountValue(order.getOrderList());
        order.setTotalPriceDiscount(totalDiscount);
        order.setTotalPrice(price.subtract(totalDiscount));
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
    public List<Item> getOrders() {
        return order.getOrderList();
    }

    @Override
    public BigDecimal getMinimalOrderValue() {
        return order.getMinimalValueForOrder();
    }

    @Override
    public BigDecimal getTotalPrice() {
        return order.getTotalPrice();
    }
}
