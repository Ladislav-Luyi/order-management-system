package com.customer.ordermanagementsystem.services;

import com.customer.ordermanagementsystem.domain.item.Item;
import com.customer.ordermanagementsystem.domain.order.CustomerInfo;
import com.customer.ordermanagementsystem.domain.order.Order;
import com.customer.ordermanagementsystem.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;


@Slf4j
@Service
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



    private BigDecimal getPriceWithoutDiscount() {
        Optional<BigDecimal> priceWithoutDiscount = order.getOrderList().stream()
                // each item can have list of items, e.g. ingredients
                .flatMap(item -> Stream.concat(Stream.of(item),
                        item.getItemList().stream()))
                .map(Item::getPrice)
                .reduce(BigDecimal::add);
        return priceWithoutDiscount.orElse(new BigDecimal(0));
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

        return order.getTotalPriceDiscount().compareTo(order.getMinimalValueForOrder()) != -1;
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
        refreshPrice();
        return order.getTotalPrice();
    }

    public void refreshPrice() {
        BigDecimal price = getPriceWithoutDiscount();
        BigDecimal totalDiscount = discountService.getDiscountValue(order.getOrderList());
        order.setTotalPriceDiscount(totalDiscount);
        order.setTotalPrice(price.subtract(totalDiscount));
    }
}
